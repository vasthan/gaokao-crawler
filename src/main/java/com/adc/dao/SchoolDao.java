package com.adc.dao;

import com.adc.model.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolDao {

    void insertList(@Param("schoolList") List<School> schoolList);

    List<School> selectAll();

    List<School> selectByStatus(@Param("status") Integer status);

    void update(School school);
}
