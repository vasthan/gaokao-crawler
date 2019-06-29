package com.adc.dao;

import com.adc.model.SchoolProvinceScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolProvinceScoreDao {
    void insertList(@Param("scoreList") List<SchoolProvinceScore> scoreList);
}
