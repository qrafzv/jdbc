package com.connection;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Exer2Test {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入四级/六级：");
//        int type = scanner.nextInt();
//        System.out.println("请输入身份证号：");
//        String idCard = scanner.next();
//        System.out.println("请输入准考证号：");
//        String examCard = scanner.next();
//        System.out.println("请输入学术姓名：");
//        String studentName=scanner.next();
//        System.out.println("请输入所在城市：");
//        String city=scanner.next();
//        System.out.println("请输入考试成绩：");
//        String grade=scanner.next();
//
//        String sql = "INSERT INTO examstudent (type,idcard,examcard,studentname,location,grade) VALUES (?,?,?,?,?,?)";
//        int insertCount = update(sql,type,idCard,examCard,studentName,city,grade);
//        if (insertCount>0){
//            System.out.println("添加成功");
//        }else{
//            System.out.println("添加失败");
//        }

        System.out.println("请选择你要输入的类型");


    }


    public static int update(String sql, Object... arg){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection) JdbcUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i=0;i<arg.length;i++) {
                ps.setObject(i+1,arg[i]);
            }
            ps.execute();
            return ps.executeUpdate();
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
        return 0;
    }

    //问题2根据身份证或者准考证号查询学术成绩信息

    public static <T>T queryForCustomer(Class<T> clazz,String sql,Object ...args){
        java.sql.Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = JdbcUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                ArrayList<T> list = new ArrayList<T>();
                T t = clazz.newInstance();
                for (int i=0;i<columnCount;i++){
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnName = metaData.getColumnName(i+1);
                    //给c对象指定的columnName属性赋值为columnValue
                    //反射
                    Field field = Customers.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(con,ps,resultSet);
        }
        return null;
    }
}
