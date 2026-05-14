package com.operation.module.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectQuery {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String keyword;
    private String status;
    private String stage;
    private String regionCode;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
}
