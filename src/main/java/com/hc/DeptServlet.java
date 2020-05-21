package com.hc;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: 梁云亮
 * @Date 2020/5/21 2:41
 * @Description:
 */
@WebServlet(urlPatterns = "/dept")
public class DeptServlet extends HttpServlet {

    private DeptService deptService = new DeptService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String op = req.getParameter("op");
        PrintWriter out = resp.getWriter();
        switch (op) {
            case "getAll":
                try {
                    getAll(out);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "getWithPage":
                try {
                    getWithPage(req, out);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        out.flush();
    }

    private void getWithPage(HttpServletRequest req, PrintWriter out) throws SQLException {
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        List<Dept> depts = deptService.getWithPage(pageNum, pageSize);
        PageBean<Dept> pageBean = new PageBean<>();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setRecords(depts);

        int count = deptService.count();
        pageBean.setTotal(count);
        pageBean.setPageSize(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        out.write(JSON.toJSONString(pageBean));
    }

    private void getAll(PrintWriter out) throws SQLException {
        List<Dept> depts = deptService.getAll();
        PageBean<Dept> pageBean = new PageBean<>();
        pageBean.setRecords(depts);
        out.write(JSON.toJSONString(pageBean));
    }
}
