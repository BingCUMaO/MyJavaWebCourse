package top.bingcu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();

        writer.println("<h1>Hello, I am BinGCU.</h1>");
        writer.println("<h4>学号：1913420129</h4>");
        writer.println("<h4>姓名：邱祺桑</h4>");

    }
}
