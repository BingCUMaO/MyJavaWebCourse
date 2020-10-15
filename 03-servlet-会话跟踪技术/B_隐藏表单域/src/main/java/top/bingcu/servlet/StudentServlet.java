package top.bingcu.servlet;

import top.bingcu.dao.StudentMapper;
import top.bingcu.pojo.bean.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns={
        "/student/list",
        "/student/editor",
        "/student/updater"
})
public class StudentServlet extends HttpServlet {

    private StudentMapper studentMapper = new StudentMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf8");

        String uri = req.getRequestURI();
        if (uri.endsWith("/student/list")) {
            showStudentList(req, resp);
        } else {
            showStudentForm(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf8");

        String uri = req.getRequestURI();
        if (uri.endsWith("/student/updater")) {
            String uuid = req.getParameter("uuid");
            String name = req.getParameter("name");
            String clazz = req.getParameter("clazz");

            Student student = studentMapper.obtain(uuid);
            student.setName(name);
            student.setClazz(clazz);
        }
        showStudentList(req, resp);
    }

    private void showStudentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>学生信息管理系统</title></head><body>");

        writer.println("<ul>");
        for (Student student : studentMapper.obtainAllStudents()) {
            writer.println("<li>");
            writer.print(student.getName() + "（" + student.getClazz() + "）" + "\t\t");
            writer.println("<a href='/student/editor?uuid="+student.getId()+"'>编辑</a>");
            writer.println("</li>");

        }
        writer.println("</ul>");

        writer.println("</body></html>");
    }

    private void showStudentForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String uuid = req.getParameter("uuid");
        Student student = studentMapper.obtain(uuid);

        if (student != null) {
            writer.println("<html><head><title>编辑学生信息</title></head><body>");
            writer.println("<h1>编辑学生信息</h1>");

            writer.println("<form method='post' action='/student/updater'>");
            writer.println("<input type='hidden' name='uuid' value='" + student.getId() + "'/>");
            writer.println("<table>");
            writer.println("<tr><td>姓名：</td>");
            writer.println("<td><input name='name' value='"+student.getName()+"'/></td></tr>");
            writer.println("<tr><td>班级：</td>");
            writer.println("<td><input name='clazz' value='"+student.getClazz()+"'/></td></tr>");
            writer.println("<tr><td colspan='2' style='text-align:right'>");
            writer.println("<input type='submit' value='更新' /></td></tr>");
            writer.println("<tr><td colspan='2'>");
            writer.println("<a href='/student/list'>返回学生列表</a>");
            writer.println("</td></tr>");
            writer.println("</table>");
            writer.println("</form>");

            writer.println("</body></html>");
        }
    }

}
