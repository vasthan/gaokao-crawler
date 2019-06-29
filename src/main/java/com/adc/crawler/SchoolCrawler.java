package com.adc.crawler;

import com.adc.dao.SchoolDao;
import com.adc.model.School;
import com.adc.utils.Http;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class SchoolCrawler extends AbstractCrawler {

    public static void main(String[] args) {
        new SchoolCrawler().crawl();
    }

    @Override
    protected void doCrawl(SqlSession session) {

        final SchoolDao schoolDao = session.getMapper(SchoolDao.class);

        int resultItem;
        int page = 1;
        int sum = 0;
        int itemSize;

        String response = null;
        try {
            do {
                response = Http.get(String.format("https://api.eol.cn/gkcx/api/?page=%s&size=20&uri=apigkcx/api/school/hotlists", page++));
                JSONObject data = JSON.parseObject(response).getJSONObject("data");

                resultItem = data.getIntValue("numFound");
                JSONArray item = data.getJSONArray("item");
                itemSize = item.size();
                if (itemSize == 0) {
                    break;
                }
                sum += itemSize;

                System.out.println("总共 " + resultItem + " 个学校信息待抓取，已抓取 " + sum);
                final List<School> schools = parseSchool(item);
                schoolDao.insertList(schools);

                Thread.sleep(1000);
            } while (itemSize > 0);
        } catch (Exception e) {
            System.out.println("抓取失败：" + response);
            e.printStackTrace();
        }
        System.out.println("抓取结束，共抓取了 " + sum + " 个学校信息");

    }

    private List<School> parseSchool(JSONArray items) {
        final List<JSONObject> jsonList = items.toJavaList(JSONObject.class);

        List<School> schools = new ArrayList<>();
        for (JSONObject json : jsonList) {
            School school = new School();
            school.setSchoolId(json.getString("school_id"));
            school.setSchoolName(json.getString("name"));
            schools.add(school);
        }
        return schools;

    }
}
