package com.connection;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    //方式一
    @Test
    public void ConnectionTest() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();

        String s = "jdbc:mysql://localhost:3306/test";
        Properties ifo= new Properties();
        ifo.setProperty("user", "root");
        ifo.setProperty("password", "1234");
        Connection con = (Connection) driver.connect(s,ifo);
        System.out.println(con);
    }
    //方式二 反射+ 方式一:使得不出现第三方的API

    @Test
    public void ConnectionTest2() throws Exception {
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String s = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","1234");
        Connection con = (Connection) driver.connect(s,info);
        System.out.println(con);
    }

    //方式三： DriverMannager

    @Test
    public void ConnectionTest3() throws Exception {
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        DriverManager.registerDriver(driver);
        String s = "jdbc:mysql://localhost:3306/test";
        String password = "1234";
        String user = "root";
        Connection con = (Connection) DriverManager.getConnection(s,user,password);
        System.out.println(con);

    }
    //方式四  可以只加载驱动，不用显示注册
    @Test
    public void Connection4() throws Exception{
        String url = "jdbc:mysql://localhost:3306/test";
        String password = "1234";
        String user = "root";

        Class.forName("com.mysql.jdbc.Driver");
//      Driver driver = (Driver) clazz.newInstance();
//      DriverManager.registerDriver(driver);

        Connection con = (Connection) DriverManager.getConnection(url,user,password);
        System.out.println(con);
    }

    //方式五:将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    @Test
    public void Connection5() throws Exception {
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String  url = pros.getProperty("url");
        String drive=pros.getProperty("drive");

        Class.forName(drive);
        Connection con = (Connection) DriverManager.getConnection(url,user,password);

        System.out.println(con);


    }
}
