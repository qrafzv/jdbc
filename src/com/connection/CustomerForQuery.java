package com.connection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class CustomerForQuery {
    @Test
    public void test(){
//        String sql = "SELECT id,name,email FROM customers WHERE id = ?";
//        Customers customers = queryForCustomer(sql, 13);
//        System.out.println(customers.toString());
        String sql1 = "SELECT name,email FROM customers WHE5 RE name = ?";
        Customers customers = queryForCustomer(sql1, "周杰伦");
        System.out.println(customers.toString());


    }

    public Customers queryForCustomer(String sql,Object ...args){
        Connection con = null;
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
            if (resultSet.next()) {
                int columnCount = metaData.getColumnCount();
                Customers c = new Customers();
                for (int i=0;i<columnCount;i++){
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnName = metaData.getColumnName(i+1);
                    //给c对象指定的columnName属性赋值为columnValue
                    //反射
                    Field field = Customers.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(c,columnValue);
                }
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(con,ps,resultSet);
        }
        return null;
    }
}
