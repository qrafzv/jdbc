package com.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Druid {
    @Test
     public void getConnection() throws Exception {
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection con = source.getConnection();

        System.out.println(con);

    }
    //创建连接
    @Test
    public void test() throws Exception {
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        pros.load(is);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
        Connection con = dataSource.getConnection();
        System.out.println(con);

//        //执行查询多条记录
//        QueryRunner qr = new QueryRunner();
//        String sql = "SELECT name,password,address,phone FROM user Where id < ?";
//        MapListHandler handler = new MapListHandler();
//        List<Map<String, Object>> query = qr.query(con,sql, handler, 4);
//        System.out.println(query);

        //执行添加操作

//        QueryRunner qr = new QueryRunner();
//        String sql = "INSERT INTO user (name,password,address,phone) VALUES(?,?,?,?)";
//        int updatenum = qr.update(con, sql, "新一","213213","日本","2323");
//        System.out.println(updatenum);

        //执行特殊值的操作
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT COUNT(*) FROM user";
        ScalarHandler handler = new ScalarHandler();
        long query = (long) qr.query(con,sql, handler);
        System.out.println(query);

        DbUtils.closeQuietly(con);
    }

}
