package com.nhnacademy.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        //todo#0 {ip},{database},{username},{password}
        basicDataSource.setUrl("jdbc:mysql://{ip}:3306/{database}");
        basicDataSource.setUsername("{username}");
        basicDataSource.setPassword("{password}");

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        basicDataSource.setMaxWait(Duration.ofSeconds(2));
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(true);
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}