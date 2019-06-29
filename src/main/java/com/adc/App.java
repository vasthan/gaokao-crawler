package com.adc;
import com.adc.crawler.ProvinceCrawler;
import com.adc.crawler.SchoolCrawler;
import com.adc.crawler.SchoolDetailCrawler;
import com.adc.crawler.SchoolProvinceScoreCrawler;
import com.adc.utils.SchoolScoreCrawlerTaskGenerator;

public class App {

    public static void main(String[] args) {

        // Step1: 爬取省份信息
        new ProvinceCrawler().crawl();

        // Step2: 爬取学校列表
        new SchoolCrawler().crawl();

        // Step3: 爬取学校详细信息
        new SchoolDetailCrawler().crawl();

        // Step4: 生成分数爬虫任务
        SchoolScoreCrawlerTaskGenerator.generateTask("2018", "34", 1);

        // Step5: 爬取分数
        new SchoolProvinceScoreCrawler().crawl();
    }
}
