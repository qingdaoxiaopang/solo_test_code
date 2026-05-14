package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.SparePart;
import com.operation.module.config.entity.SparePartCategory;
import com.operation.module.config.entity.SparePartRecord;
import java.util.List;

public interface ISparePartService {

    List<SparePartCategory> getSparePartCategoryTree(Long projectId);

    boolean saveSparePartCategory(SparePartCategory category);

    boolean updateSparePartCategory(SparePartCategory category);

    boolean deleteSparePartCategory(Long id);

    List<SparePart> getSparePartList(Long projectId);

    boolean saveSparePart(SparePart sparePart);

    boolean updateSparePart(SparePart sparePart);

    boolean deleteSparePart(Long id);

    List<SparePartRecord> getSparePartRecords(Long sparePartId);

    boolean applySparePart(SparePartRecord record);

    boolean approveSparePartRecord(Long id, Long approverId, String remark);

    boolean completeSparePartRecord(Long id);
}
