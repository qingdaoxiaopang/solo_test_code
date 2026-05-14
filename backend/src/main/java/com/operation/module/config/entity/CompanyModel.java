package com.operation.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cfg_company_model")
public class CompanyModel extends BaseEntity {

    private Long companyId;

    private Long modelId;
}
