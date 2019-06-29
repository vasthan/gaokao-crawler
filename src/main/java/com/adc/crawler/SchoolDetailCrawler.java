package com.adc.crawler;

import com.adc.dao.SchoolDao;
import com.adc.model.School;
import com.adc.utils.Http;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SchoolDetailCrawler extends AbstractCrawler {

    @Override
    protected void doCrawl(SqlSession session) {
        String url = "https://static-data.eol.cn/www/school/%s/info.json";

        SchoolDao schoolDao = session.getMapper(SchoolDao.class);

        List<School> schools = schoolDao.selectByStatus(0);
        int num = 0;
        try {
            for (School school : schools) {
                String response = Http.get(String.format(url, school.getSchoolId()));
                JSONObject json = JSON.parseObject(response);

                school.setSchoolName(json.getString("name"));
                school.setProvinceId(json.getString("province_id"));
                school.setProvinceName(json.getString("province_name"));
                school.setCityId(json.getString("city_id"));
                school.setCityName(json.getString("city_name"));
                school.setLevel(json.getString("level_name"));
                school.setType(json.getString("type_name"));
                school.setNature(json.getString("school_nature_name"));
                school.setEmail(json.getString("email"));
                school.setPhone(json.getString("phone"));
                school.setSite(json.getString("site"));
                school.setAddress(json.getString("address"));
                school.setStatus(1);

                schoolDao.update(school);

                num++;
                System.out.println("待抓取学校信息：" + schools.size() + "个，已抓取 " + num);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            System.out.println("【ERROR】" + "抓取学校信息失败.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SchoolDetailCrawler().crawl();
    }
}
