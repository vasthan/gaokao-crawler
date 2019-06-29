package com.adc.model;

import lombok.Data;

@Data
public class SchoolProvinceScoreCrawlTask {
    private Integer id;
    private String schoolId;
    private String studentProvinceId;
    private Integer studentType;
    private String year;
    private Integer status;
}
