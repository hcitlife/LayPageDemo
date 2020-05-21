package com.hc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @Author: 梁云亮
 * @Date 2020/5/21 2:25
 * @Description:
 */
public class DeptServiceTest {

    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into tb_dept (dname,loc)values (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < 99; i++) {
            ps.setString(1, UUID.randomUUID().toString().substring(14, 23));
            ps.setString(2, UUID.randomUUID().toString().substring(14, 23));
            ps.addBatch();
        }
        ps.executeBatch();
    }

    @Test
    public void getAllTest() throws SQLException {
        new DeptService().getAll().forEach(System.out::println);
    }


    @Test
    public void getWithPageTest() throws SQLException {
        new DeptService().getWithPage(2, 6).forEach(System.out::println);
    }

    @Test
    public void count() throws SQLException {
        int res = new DeptService().count();
        System.out.println(res);
    }

}
