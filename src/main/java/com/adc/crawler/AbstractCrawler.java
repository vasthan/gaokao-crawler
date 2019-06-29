package com.adc.crawler;

import com.adc.utils.SessionManager;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractCrawler implements Crawler{

    private SqlSession getSession() {
        return SessionManager.get();
    }

    @Override
    public void crawl() {
        SqlSession session = getSession();
        doCrawl(session);
        closeSession(session);
    }

    protected abstract void doCrawl(SqlSession session);

    private void closeSession(SqlSession session) {
        session.close();
    }
}
