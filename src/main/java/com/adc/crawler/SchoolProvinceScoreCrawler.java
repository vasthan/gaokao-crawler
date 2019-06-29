package com.adc.crawler;

import com.adc.dao.SchoolProvinceScoreCrawlTaskDao;
import com.adc.dao.SchoolProvinceScoreDao;
import com.adc.model.SchoolProvinceScore;
import com.adc.model.SchoolProvinceScoreCrawlTask;
import com.adc.utils.Http;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class SchoolProvinceScoreCrawler extends AbstractCrawler {

    private String url = "https://static-data.eol.cn/www/2.0/schoolprovinceindex/%s/%s/%s/%s/1.json";

    public static void main(String[] args) {
        new SchoolProvinceScoreCrawler().crawl();
    }

    @Override
    protected void doCrawl(SqlSession session) {
        SchoolProvinceScoreCrawlTaskDao crawlTaskDao = session.getMapper(SchoolProvinceScoreCrawlTaskDao.class);
        SchoolProvinceScoreDao scoreDao = session.getMapper(SchoolProvinceScoreDao.class);

        int executeTasks = 0;
        int remain;
        do {
            List<SchoolProvinceScoreCrawlTask> crawlTasks = crawlTaskDao.selectTask(20);
            remain = crawlTasks.size();

            if (remain <= 0) {
                break;
            }

            List<SchoolProvinceScore> scores = new ArrayList<>();
            for (SchoolProvinceScoreCrawlTask task : crawlTasks) {
                List<SchoolProvinceScore> score = executeTask(task);
                task.setStatus(1);
                crawlTaskDao.update(task);

                if (!score.isEmpty()) {
                    scores.addAll(score);
                }
            }

            if (scores.size() > 0) {
                scoreDao.insertList(scores);
            }
            executeTasks += remain;
            System.out.println("已执行Task：" + executeTasks);
        } while (remain > 0);
    }

    private List<SchoolProvinceScore> executeTask(SchoolProvinceScoreCrawlTask task) {

        String targetUrl = String.format(url, task.getYear(), task.getSchoolId(), task.getStudentProvinceId(), task.getStudentType());
        try {
            String response = Http.get(targetUrl);
            JSONObject json = JSON.parseObject(response);
            JSONArray item = json.getJSONObject("data").getJSONArray("item");
            if (item == null || item.size() == 0) {
                return new ArrayList<>();
            }

            List<SchoolProvinceScore> scores = new ArrayList<>();
            for (JSONObject data : item.toJavaList(JSONObject.class)) {
                SchoolProvinceScore score = new SchoolProvinceScore();
                score.setSchoolId(task.getSchoolId());
                score.setStudentProvinceId(task.getStudentProvinceId());
                score.setStudentType(task.getStudentType());
                score.setYear(task.getYear());

                if (!"--".equals(data.getString("max"))) {
                    score.setMaxScore(data.getDoubleValue("max"));
                }
                if (!"--".equals(data.getString("min"))) {
                    score.setMinScore(data.getDoubleValue("min"));
                }
                if (!"--".equals(data.getString("average"))) {
                    score.setAvgScore(data.getDoubleValue("average"));
                }
                if (!"--".equals(data.getString("proscore"))) {
                    score.setProScore(data.getDoubleValue("proscore"));
                }

                score.setMinPosition(data.getIntValue("min_section"));
                score.setBatchName(data.getString("local_batch_name"));

                scores.add(score);
            }
            return scores;
        } catch (Exception e) {
            System.out.println("抓取分数失败，可能无数据【请核实】：" + targetUrl);
            return new ArrayList<>();
        }

    }
}
