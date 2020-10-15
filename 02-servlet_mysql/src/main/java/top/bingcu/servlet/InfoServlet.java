package top.bingcu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class InfoServlet extends HttpServlet {
    //        获取数据源参数
    private ServletConfig servletConfig = null;
    private  String driver  = null;
    private  String url  = null;
    private  String user  = null;
    private  String pwd  = null;

    //同个对象内，共用一个连接
    private Connection connection = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletConfig = config;

        driver = servletConfig.getInitParameter("driver");
        url = servletConfig.getInitParameter("url");
        user = servletConfig.getInitParameter("user");
        pwd = servletConfig.getInitParameter("pwd");

        //            加载注册驱动并获取连接
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        配置请求初始参数
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");


        String studentName ="'"+req.getParameter("studentName")+"'";
        String studentId = "'"+req.getParameter("studentId") +"'";

        String sql_insertStudent = "insert into info (student_id, student_name) values(" +studentId+", "+studentName+ ")";
        String sql_queryStudent = "select * from info";


        try {
            Statement stmt = connection.createStatement();
            int collumn = stmt.executeUpdate(sql_insertStudent);

            PrintWriter writer = resp.getWriter();
            if (collumn>0){
                writer.println("<h1>添加成功！</h1>");

                //查询整个表的数据，然后返回给前端页面
                ResultSet resultSet = stmt.executeQuery(sql_queryStudent);
                int row = resultSet.getRow();
                while (resultSet.next()) {
                    String id = resultSet.getString("student_id");
                    String name = resultSet.getString("student_name");
                    writer.println("<h1>姓名：" + name + "\t\t学号：" + id + "</h1>");
                }
            }else {
                writer.println("<h1>发生了点小意外呢~</h1>");
                writer.println("<h1>数据没能保存成功喔~</h1>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
