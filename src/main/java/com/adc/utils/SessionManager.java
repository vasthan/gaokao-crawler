package com.adc.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SessionManager {
    private static SqlSessionFactory SESSION_FACTORY;

    public static SqlSession get() {
        if (SESSION_FACTORY == null) {
            try {
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                SESSION_FACTORY = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return SESSION_FACTORY.openSession(true);
    }
}
