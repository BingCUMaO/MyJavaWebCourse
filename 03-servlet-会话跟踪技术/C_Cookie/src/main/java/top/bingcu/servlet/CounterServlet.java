package top.bingcu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {
        "/counter/addition"
})
public class CounterServlet extends HttpServlet {
    private static final String COOKIE_NAME = "visitCount";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean hasCounterCookie = false;
        Cookie[] cookies = req.getCookies();
        Cookie counterCookie = null;

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head><title>BinGCU のBlog</title></head>");
        writer.println("<body>");

        writer.println("<h2>计数器</h2>");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    hasCounterCookie = true;
                    counterCookie = cookie;
                }
            }
        }
        Integer visitCount = 0;
        if (hasCounterCookie) {
             visitCount = 1 + Integer.parseInt(counterCookie.getValue());
             counterCookie.setValue(visitCount.toString());
        }else {
            visitCount++;
            counterCookie = new Cookie(COOKIE_NAME, visitCount.toString());
        }
        resp.addCookie(counterCookie);
        writer.println("访问次数：" + visitCount.intValue());

        writer.println("</body>");
        writer.println("</html>");
    }
}
