package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.CompanyModel;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.entity.MaintenanceCompany;
import com.operation.module.config.mapper.CompanyModelMapper;
import com.operation.module.config.mapper.DeviceModelMapper;
import com.operation.module.config.mapper.MaintenanceCompanyMapper;
import com.operation.module.config.service.IMaintenanceCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceCompanyServiceImpl extends ServiceImpl<MaintenanceCompanyMapper, MaintenanceCompany>
        implements IMaintenanceCompanyService {

    private final CompanyModelMapper companyModelMapper;
    private final DeviceModelMapper deviceModelMapper;

    public MaintenanceCompanyServiceImpl(CompanyModelMapper companyModelMapper,
                                        DeviceModelMapper deviceModelMapper) {
        this.companyModelMapper = companyModelMapper;
        this.deviceModelMapper = deviceModelMapper;
    }

    @Override
    @Transactional
    public boolean saveMaintenanceCompany(MaintenanceCompany company) {
        company.setProjectId(UserUtils.getProjectId());
        company.setCreateBy(UserUtils.getUserId());
        company.setCreateDeptId(UserUtils.getDeptId());
        checkCodeUnique(company.getCode(), company.getProjectId(), null);
        return this.save(company);
    }

    @Override
    @Transactional
    public boolean updateMaintenanceCompany(MaintenanceCompany company) {
        checkCodeUnique(company.getCode(), company.getProjectId(), company.getId());
        company.setUpdateBy(UserUtils.getUserId());
        return this.updateById(company);
    }

    @Override
    @Transactional
    public boolean deleteMaintenanceCompany(Long id) {
        LambdaQueryWrapper<CompanyModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyModel::getCompanyId, id);
        companyModelMapper.delete(wrapper);
        return this.removeById(id);
    }

    @Override
    public List<DeviceModel> getCompanyModels(Long companyId) {
        MaintenanceCompany company = this.getById(companyId);
        if (company == null) {
            throw new BusinessException("维护公司不存在");
        }

        LambdaQueryWrapper<CompanyModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyModel::getCompanyId, companyId);
        List<CompanyModel> relations = companyModelMapper.selectList(wrapper);

        if (relations.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> modelIds = relations.stream()
                .map(CompanyModel::getModelId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<DeviceModel> modelWrapper = new LambdaQueryWrapper<>();
        modelWrapper.eq(DeviceModel::getProjectId, company.getProjectId())
                    .in(DeviceModel::getId, modelIds);
        return deviceModelMapper.selectList(modelWrapper);
    }

    @Override
    @Transactional
    public boolean addCompanyModel(Long companyId, Long modelId) {
        MaintenanceCompany company = this.getById(companyId);
        if (company == null) {
            throw new BusinessException("维护公司不存在");
        }

        LambdaQueryWrapper<CompanyModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyModel::getCompanyId, companyId)
               .eq(CompanyModel::getModelId, modelId);
        if (companyModelMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该型号已关联到此公司");
        }

        CompanyModel relation = new CompanyModel();
        relation.setCompanyId(companyId);
        relation.setModelId(modelId);
        relation.setCreateBy(UserUtils.getUserId());
        relation.setCreateDeptId(UserUtils.getDeptId());
        return companyModelMapper.insert(relation) > 0;
    }

    @Override
    @Transactional
    public boolean removeCompanyModel(Long companyId, Long modelId) {
        LambdaQueryWrapper<CompanyModel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyModel::getCompanyId, companyId)
               .eq(CompanyModel::getModelId, modelId);
        return companyModelMapper.delete(wrapper) > 0;
    }

    private void checkCodeUnique(String code, Long projectId, Long excludeId) {
        LambdaQueryWrapper<MaintenanceCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceCompany::getCode, code)
               .eq(MaintenanceCompany::getProjectId, projectId);
        if (excludeId != null) {
            wrapper.ne(MaintenanceCompany::getId, excludeId);
        }
        if (this.count(wrapper) > 0) {
            throw new BusinessException("公司编码已存在");
        }
    }
}
