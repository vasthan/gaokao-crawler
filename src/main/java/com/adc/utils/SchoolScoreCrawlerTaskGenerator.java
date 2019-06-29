package com.adc.utils;

import com.adc.dao.SchoolDao;
import com.adc.dao.SchoolProvinceScoreCrawlTaskDao;
import com.adc.model.School;
import com.adc.model.SchoolProvinceScoreCrawlTask;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class SchoolScoreCrawlerTaskGenerator {

    public static void main(String[] args) {
        generateTask("2018", "34", 1);
    }

    /**
     * @param year          年份
     * @param province      省份
     * @param studentType   考生类型：1-理科 2-文科 3-综合
     */
    public static void generateTask(String year, String province, Integer studentType) {
        SqlSession session = SessionManager.get();
        SchoolProvinceScoreCrawlTaskDao crawlTaskDao = session.getMapper(SchoolProvinceScoreCrawlTaskDao.class);

        SchoolDao schoolDao = session.getMapper(SchoolDao.class);
        final List<School> schools = schoolDao.selectAll();

        List<SchoolProvinceScoreCrawlTask> tasks = new ArrayList<>(schools.size());
        for (School school : schools) {
            SchoolProvinceScoreCrawlTask task = new SchoolProvinceScoreCrawlTask();
            task.setSchoolId(school.getSchoolId());
            task.setYear(year);
            task.setStudentProvinceId(province);
            task.setStudentType(studentType);
            task.setStatus(0);
            tasks.add(task);
        }
        crawlTaskDao.insertList(tasks);

        session.close();
    }
}
