package com.adc.dao;

import com.adc.model.Province;

import java.util.List;

public interface ProvinceDao {

    void insert(Province province);

    List<Province> selectAll();
}
