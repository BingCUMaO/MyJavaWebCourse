package top.bingcu.servlet;

import top.bingcu.dao.ProductMapper;
import top.bingcu.pojo.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;

@WebServlet("/product/detail")
public class ProductDetailServlet extends HttpServlet {

    private ProductMapper productMapper = new ProductMapper();

    private NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        showProductDetail(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    public void showProductDetail(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        Product product = productMapper.obtain(id);
        PrintWriter writer = response.getWriter();


        writer.println("<html>");

        writer.println("<head><title>商品详情</title></head>");
        writer.println("<body>");
        writer.println("<h5>"+"商品名："+product.getName()+"</h5>");
        writer.println("<h5>"+"描述："+product.getDescription()+"</h5>");
        writer.println("<h5>"+"价格："+nf.format(product.getPrice())+"</h5>");
        writer.println("<h5>数量：</h5>");
        writer.println("<form method='post' action='/cart'>");
        writer.println("<input type='hidden' name='action' value='append' />");
        writer.println("<input type='hidden' name='id' value='"+product.getId()+"' />");
        writer.println("<input name='quantity' />");
        writer.println("<input type='submit' value='立即购买' />");
        writer.println("</form>");
        writer.println("<br/>");
        writer.println("<br/>");
        writer.println("<a href='/product/list'>返回</a>");
        writer.println("</body>");

        writer.println("</html>");
    }
}
