package com.connection;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PreparedStatementTest {
    @Test
    public void test(){
        //得到连接
        Connection con = null;
        PreparedStatement ps = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String  url = pros.getProperty("url");
            String drive=pros.getProperty("drive");

            Class.forName(drive);
            con = (Connection) DriverManager.getConnection(url,user,password);

            //System.out.println(con);

            //预编译SQL语句，返回PreparedStatement的实例
            String sql = "INSERT INTO customers (name,email,birth) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,"大黄蜂");
            ps.setString(2,"123@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1001-01-01");

            ps.setDate(3, new java.sql.Date(date.getTime()));

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
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




    }

    @Test
    public void test2() throws Exception {
        //获取连接
        Connection con = (Connection) JdbcUtils.getConnection();
        //预编译sql，获取preparestatement实例
        String sql = "UPDATE customers SET name = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        //填充占位符
        ps.setObject(1,"莫扎特");
        ps.setObject(2,18);
        //执行
        ps.execute();
        //关闭
        JdbcUtils.closeConnection(con,ps);
    }

    @Test
    public void test3(){
//        String sql = "INSERT INTO `order` (order_name) VALUES (?)";
//        update(sql,"xx");
//        String sql2 = "DELETE FROM customers WHERE id = ?";
//        update(sql2,3);


    }
    //通用增删改
    public void update(String sql,Object ...arg){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection) JdbcUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i=0;i<arg.length;i++) {
                ps.setObject(i+1,arg[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps!=null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con!=null)
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //查询
    @Test
    public void test4(){
        Connection con  = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = (Connection) JdbcUtils.getConnection();

            String sql ="SELECT id,name,email,birth FROM customers WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setObject(1,1);
            resultSet = ps.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date date = resultSet.getDate(4);

                Customers c = new Customers(id,name,email,date);
                System.out.println(c.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(con,ps,resultSet);
        }


    }
}
