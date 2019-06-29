package com.adc.model;

import lombok.Data;

@Data
public class SchoolProvinceScore {
    private String schoolId;
    private String studentProvinceId;
    private Integer studentType;
    private String year;
    private Double maxScore;
    private Double minScore;
    private Double avgScore;
    private Double proScore;
    private Integer minPosition;
    private String batchName;
}
