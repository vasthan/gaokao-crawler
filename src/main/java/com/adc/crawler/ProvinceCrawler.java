package com.adc.crawler;

import com.adc.dao.ProvinceDao;
import com.adc.model.Province;
import com.adc.utils.Http;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ProvinceCrawler extends AbstractCrawler{

    public void doCrawl(SqlSession session) {
        String response = Http.get("https://gkcx.eol.cn/interface/gaokao.eol.cn/pc_fsx.shtml");
        Document doc = Jsoup.parse(response);

        final Elements elements = doc.select("select.provincelocal>option");

        final ProvinceDao provinceDao = session.getMapper(ProvinceDao.class);

        elements.forEach(element -> {
            if (StringUtils.isNotBlank(element.val())) {
                Province province = new Province();
                province.setProvinceId(element.val());
                province.setProvinceName(element.text());
                provinceDao.insert(province);
            }
        });
    }

    public static void main(String[] args) {
        new ProvinceCrawler().crawl();
    }
}
