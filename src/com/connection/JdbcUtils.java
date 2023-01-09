package com.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String  url = pros.getProperty("url");
        String drive=pros.getProperty("drive");

        Class.forName(drive);
        com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection(url,user,password);

        //System.out.println(con);
        return con;
    }
    //关闭资源
    public static void closeConnection(Connection con, Statement ps){
        try {
            if(ps!=null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con!=null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection(Connection con, Statement ps,ResultSet rs){
        try {
            if(ps!=null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con!=null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs!=null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
