package com.adc.dao;

import com.adc.model.SchoolProvinceScoreCrawlTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolProvinceScoreCrawlTaskDao {
    List<SchoolProvinceScoreCrawlTask> selectTask(@Param("limit") Integer limit);

    void update(SchoolProvinceScoreCrawlTask task);

    void insertList(@Param("taskList") List<SchoolProvinceScoreCrawlTask> taskList);
}
