package top.bingcu.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet("/second")
public class SecondServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        ServletContext servletContext = this.getServletContext();
        String value = servletContext.getAttribute("accountNumber").toString();

        PrintWriter writer = resp.getWriter();
        writer.println("<html>");

        writer.println("<head>");
        writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" >");
        writer.println("<title>BinGCU</title>");
        writer.println("</head>");

        writer.println("<body>");
        writer.println("<h1>BinGCU</h1>");
        writer.println("<form method=\"post\" action='/third'>");
        writer.println("<b>点击【充值】按钮，将钱款充值到您的账户</b>");
        writer.println("<table>");
        writer.println("<tr>");
        writer.println("<td>账户名：</td>");
        writer.println("<td>"+value+"</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>校验码：</td>");
        writer.println("<td><input type='text' name='checknum'>"+this.rand(req)+"</td>");
        writer.println("</tr>");
        writer.println("<tr>");
        writer.println("<td>输入需要充值的额度：</td>");
        writer.println("<td><input type='text' name='amount' value=0></td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("<input type='submit' value='充值' >");
        writer.println("</form>");
        writer.println("</body>");

        writer.println("</html>");
        writer.close();
    }

    private String rand(HttpServletRequest request) {
        Random random = new Random();

        StringBuilder randString = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            randString.append(rand);
        }

        HttpSession session = request.getSession();
        session.setAttribute("rand", randString.toString());
        return randString.toString();
    }
}
