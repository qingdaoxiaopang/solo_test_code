package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.CompanyModel;
import com.operation.module.config.entity.DeviceModel;
import com.operation.module.config.entity.MaintenanceCompany;
import java.util.List;

public interface IMaintenanceCompanyService extends IService<MaintenanceCompany> {

    boolean saveMaintenanceCompany(MaintenanceCompany company);

    boolean updateMaintenanceCompany(MaintenanceCompany company);

    boolean deleteMaintenanceCompany(Long id);

    List<DeviceModel> getCompanyModels(Long companyId);

    boolean addCompanyModel(Long companyId, Long modelId);

    boolean removeCompanyModel(Long companyId, Long modelId);
}
