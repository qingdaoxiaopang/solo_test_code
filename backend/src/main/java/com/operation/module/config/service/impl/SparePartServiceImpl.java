package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.SparePart;
import com.operation.module.config.entity.SparePartCategory;
import com.operation.module.config.entity.SparePartRecord;
import com.operation.module.config.mapper.SparePartCategoryMapper;
import com.operation.module.config.mapper.SparePartMapper;
import com.operation.module.config.mapper.SparePartRecordMapper;
import com.operation.module.config.service.ISparePartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartServiceImpl implements ISparePartService {

    private final SparePartCategoryMapper categoryMapper;
    private final SparePartMapper sparePartMapper;
    private final SparePartRecordMapper recordMapper;

    public SparePartServiceImpl(SparePartCategoryMapper categoryMapper,
                               SparePartMapper sparePartMapper,
                               SparePartRecordMapper recordMapper) {
        this.categoryMapper = categoryMapper;
        this.sparePartMapper = sparePartMapper;
        this.recordMapper = recordMapper;
    }

    @Override
    public List<SparePartCategory> getSparePartCategoryTree(Long projectId) {
        LambdaQueryWrapper<SparePartCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SparePartCategory::getProjectId, projectId)
               .orderByAsc(SparePartCategory::getSort)
               .orderByAsc(SparePartCategory::getCreateTime);
        List<SparePartCategory> allCategories = categoryMapper.selectList(wrapper);
        return buildCategoryTree(allCategories, 0L);
    }

    private List<SparePartCategory> buildCategoryTree(List<SparePartCategory> allCategories, Long parentId) {
        return allCategories.stream()
                .filter(cat -> (parentId == null && cat.getParentId() == null) ||
                             (parentId != null && parentId.equals(cat.getParentId())))
                .peek(cat -> cat.setChildren(buildCategoryTree(allCategories, cat.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean saveSparePartCategory(SparePartCategory category) {
        category.setProjectId(UserUtils.getProjectId());
        category.setCreateBy(UserUtils.getUserId());
        category.setCreateDeptId(UserUtils.getDeptId());
        return categoryMapper.insert(category) > 0;
    }

    @Override
    @Transactional
    public boolean updateSparePartCategory(SparePartCategory category) {
        category.setUpdateBy(UserUtils.getUserId());
        return categoryMapper.updateById(category) > 0;
    }

    @Override
    @Transactional
    public boolean deleteSparePartCategory(Long id) {
        LambdaQueryWrapper<SparePartCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SparePartCategory::getParentId, id);
        if (categoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该分类存在子分类，无法删除");
        }

        LambdaQueryWrapper<SparePart> sparePartWrapper = new LambdaQueryWrapper<>();
        sparePartWrapper.eq(SparePart::getCategoryId, id);
        if (sparePartMapper.selectCount(sparePartWrapper) > 0) {
            throw new BusinessException("该分类存在关联备件，无法删除");
        }

        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    public List<SparePart> getSparePartList(Long projectId) {
        LambdaQueryWrapper<SparePart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SparePart::getProjectId, projectId)
               .orderByDesc(SparePart::getCreateTime);
        return sparePartMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean saveSparePart(SparePart sparePart) {
        sparePart.setProjectId(UserUtils.getProjectId());
        sparePart.setCreateBy(UserUtils.getUserId());
        sparePart.setCreateDeptId(UserUtils.getDeptId());
        if (sparePart.getStockQuantity() == null) {
            sparePart.setStockQuantity(BigDecimal.ZERO);
        }
        return sparePartMapper.insert(sparePart) > 0;
    }

    @Override
    @Transactional
    public boolean updateSparePart(SparePart sparePart) {
        sparePart.setUpdateBy(UserUtils.getUserId());
        return sparePartMapper.updateById(sparePart) > 0;
    }

    @Override
    @Transactional
    public boolean deleteSparePart(Long id) {
        LambdaQueryWrapper<SparePartRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SparePartRecord::getSparePartId, id);
        List<SparePartRecord> records = recordMapper.selectList(wrapper);
        for (SparePartRecord record : records) {
            if ("PENDING".equals(record.getStatus()) || "APPROVED".equals(record.getStatus())) {
                throw new BusinessException("该备件存在未完成的申请记录，无法删除");
            }
        }
        return sparePartMapper.deleteById(id) > 0;
    }

    @Override
    public List<SparePartRecord> getSparePartRecords(Long sparePartId) {
        LambdaQueryWrapper<SparePartRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SparePartRecord::getSparePartId, sparePartId)
               .orderByDesc(SparePartRecord::getCreateTime);
        return recordMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean applySparePart(SparePartRecord record) {
        record.setProjectId(UserUtils.getProjectId());
        record.setApplicantId(UserUtils.getUserId());
        record.setCreateBy(UserUtils.getUserId());
        record.setCreateDeptId(UserUtils.getDeptId());
        record.setStatus("PENDING");
        return recordMapper.insert(record) > 0;
    }

    @Override
    @Transactional
    public boolean approveSparePartRecord(Long id, Long approverId, String remark) {
        SparePartRecord record = recordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("备件申请记录不存在");
        }
        if (!"PENDING".equals(record.getStatus())) {
            throw new BusinessException("只能审批待审批状态的申请");
        }

        record.setApproverId(approverId);
        record.setApproveTime(LocalDateTime.now());
        record.setApproveRemark(remark);
        record.setStatus("APPROVED");
        record.setUpdateBy(approverId);
        return recordMapper.updateById(record) > 0;
    }

    @Override
    @Transactional
    public boolean completeSparePartRecord(Long id) {
        SparePartRecord record = recordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("备件申请记录不存在");
        }
        if (!"APPROVED".equals(record.getStatus())) {
            throw new BusinessException("只能确认已批准的申请");
        }

        SparePart sparePart = sparePartMapper.selectById(record.getSparePartId());
        if (sparePart == null) {
            throw new BusinessException("备件不存在");
        }

        if (sparePart.getStockQuantity().compareTo(record.getQuantity()) < 0) {
            throw new BusinessException("库存不足，无法完成备件使用");
        }

        sparePart.setStockQuantity(sparePart.getStockQuantity().subtract(record.getQuantity()));
        sparePart.setUpdateBy(UserUtils.getUserId());
        sparePartMapper.updateById(sparePart);

        record.setStatus("COMPLETED");
        record.setUpdateBy(UserUtils.getUserId());
        return recordMapper.updateById(record) > 0;
    }
}
