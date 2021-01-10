//package top.bingcu.servlet;
//
//import top.bingcu.dao.DBManager;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet("/fourth")
//public class FourthServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    private ServletContext servletContext;
//    private ServletConfig config;
//    private DBManager dbManager;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//
//        String driver = config.getInitParameter("driver");
//        String url = config.getInitParameter("url");
//
//        this.config = config;
//        this.dbManager = new DBManager(url, driver);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setContentType("text/html; charset=UTF-8");
//
//        String accountNumber = servletContext.getAttribute("accountNumber").toString();
//        //获取数据库的用户名和密码
//        String dbName = this.config.getInitParameter("user");
//        String dbPassword = this.config.getInitParameter("pwd");
//
//        double amount = 0.0;
//        boolean rowfound = true;
//        int totalrows = 0;
//        List<String[]> datas = new ArrayList<String[]>();
//        try {
//            String sql = "select mBalance from Account_holder where account_id=?";
//            String[] params = new String[]{accountNumber};
//            ResultSet rs = dbManager.getData(sql, params, dbName, dbPassword);
//
//            if (rs != null) {
//                rs.next();
//                amount = rs.getDouble(1);
//            }
//            try {
//                dbManager.close();
//            } catch (Exception e) {
//                sql = "select count(*) from Account_Holder_Transaction where account_id = ?";
//                rs = dbManager.getData(sql, params, dbName, dbPassword);
//                if (rs != null) {
//                    rs.next();
//                    totalrows = rs.getInt(1);
//                    totalrows -= 10;
//                }
//
//                try {
//                    dbManager.close();
//                } catch (Exception e1) {
//                    sql = "select account_id, " +
//                            "DATE_FORMAT(dDate_of_transaction, '%d'), " +
//                            "DATE_FORMAT(dDate_of_transaction, '%m'),  " +
//                            "DATE_FORMAT(dDate_of_transaction, '%Y'), " +
//                            "mAmount " +
//                            "from account_holder_transaction where account_id=?";
//
//                    rs =
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        PrintWriter writer = resp.getWriter();
//        writer.println("<html>");
//
//        writer.println("</html>");
//    }
//}
