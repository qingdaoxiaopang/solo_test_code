package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.Manufacturer;

public interface IManufacturerService extends IService<Manufacturer> {

    boolean saveManufacturer(Manufacturer manufacturer);

    boolean updateManufacturer(Manufacturer manufacturer);

    boolean deleteManufacturer(Long id);

    boolean hasRelatedModels(Long id);
}
