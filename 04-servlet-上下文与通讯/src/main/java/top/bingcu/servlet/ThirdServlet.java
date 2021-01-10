package top.bingcu.servlet;

import top.bingcu.dao.DBManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/third")
public class ThirdServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        //获取数据库的用户名和密码
        String dbName = this.config.getInitParameter("user");
        String dbPassword = this.config.getInitParameter("pwd");

        String accountNumber = servletContext.getAttribute("accountNumber").toString();
        String amount = req.getParameter("amount");
        String checknum = req.getParameter("checknum");
        boolean checkCorrect = true;
        boolean amountCorrect = true;

        HttpSession session = req.getSession();

        //检测校验码是否为空
        if (checknum == null || checknum.length() == 0 || !checknum.equals(session.getAttribute("rand"))) {
            checkCorrect = false;
        }

        double mdeposit = Double.valueOf(amount).doubleValue();
        if (mdeposit <= 0) {
            amountCorrect = false;
        }

        PrintWriter writer = resp.getWriter();
        writer.println("<html>");

        writer.println("<head>");
        writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" >");
        writer.println("<title>BinGCU</title>");
        writer.println("</head>");

        writer.println("<body>");
        if (!checkCorrect) {
            writer.println("请输入正确的校验码");
        }
        if (!amountCorrect) {
            writer.println("充值金额必须是有效的");
            writer.println("<br>");
            writer.println("请重新输入有效金额");
        }
        if (checkCorrect && amountCorrect) {
            String vcparticulars = "充值";
            //将详细详细插入到Account_Holder_transaction表中
            try {
                String sql = "insert Account_holder_transacion values(?,?,?,?)";
                Object[] params = new Object[]{
                        accountNumber,
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        vcparticulars,
                        mdeposit
                };
                if (!dbManager.setData(sql, params, dbName, dbPassword)) {
                    System.out.println("在Account_Holder_Transaction表中插入行出错");
                }else{
                    writer.println("您的充值详情已被记录");
                    writer.println("<br>");
                    writer.println("点击【报告】按钮，查看最后10笔充值。");
                    writer.println("<br>");
                    writer.println("<br>");
                    writer.println("<form method='post' action='fourth' >");
                    writer.println("<input type='submit' value='报告'>");
                    writer.println("</form>");
                }

                sql = "select mBalance from Account_Holder where account_id=?";
                String[] params1 = new String[]{accountNumber};
                ResultSet resultSet = dbManager.getData(sql, params1, dbName, dbPassword);
                resultSet.next();
                double total = resultSet.getDouble(1);
                total += mdeposit;
                sql = "update Account_holder set mBalance=? where account_id=?";
                Object[] params2 = new Object[]{total, accountNumber};
                if (!dbManager.setData(sql, params2, dbName, dbPassword)) {
                    System.out.println("在Account_Holder表中更新数据出错");
                }
                dbManager.close();




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer.println("</body>");

        writer.println("</html>");

    }
}
