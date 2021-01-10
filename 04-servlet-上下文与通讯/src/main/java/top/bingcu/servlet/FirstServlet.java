package top.bingcu.servlet;

import top.bingcu.dao.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstServlet extends HttpServlet {
    private static final long serialVersionUID  = 1L;
    private static final String EMPTY = " ";

    private  ServletContext servletContext;
    private ServletConfig config;
    private DBManager dbManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String driver = config.getInitParameter("driver");
        String url = config.getInitParameter("url");

        this.config = config;
        this.dbManager = new DBManager(url, driver);
    }

    @Override
    public void destroy() {
        this.dbManager.close();
        this.dbManager = null;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        //初始化账户
        this.servletContext = this.getServletContext();
        this.servletContext.setAttribute("accountNumber", EMPTY);

        //获取账户ID和密码
        String accountNumber = req.getParameter("accountNumber");
        String password = req.getParameter("password");

        try {
            //获取数据库的用户名和密码
            String dbName = this.config.getInitParameter("user");
            String dbPassword = this.config.getInitParameter("pwd");
            //设置查询的sql
            String sql = "select * from login where account_id=? And password=?";
            String[] params = new String[]{accountNumber, password};

            ResultSet resultSet = this.dbManager.getData(sql, params, dbName, dbPassword);

            boolean exist = resultSet.next();
            String resultToSecond = "NA";
            if (exist) {
                resultToSecond = resultSet.getString(1);
                this.servletContext.setAttribute("accountNumber", resultToSecond);
                //如果账号有效，则调用第二个servlet
                RequestDispatcher dispatcher = this.servletContext.getRequestDispatcher("/second");

                if (dispatcher == null)
                    resp.sendError(resp.SC_NO_CONTENT);
                dispatcher.forward(req, resp);
                dbManager.close();
            }else {
                PrintWriter writer = resp.getWriter();
                writer.println("<html>");
                writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" >");
                writer.println("<body>");
                writer.println("账户信息有误，请检查您的输入是否正确");
                writer.println("</body>");
                writer.println("</html>");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
