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

@WebServlet(urlPatterns = {"/product/list"})
public class ShopServlet extends HttpServlet {

    private ProductMapper productMapper = new ProductMapper();

    private NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        listProducts(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    public void listProducts(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<html>");

        writer.println("<head><title>BinGCU の Shop</title></head>");
        writer.println("<body>");
        writer.println("<ul>");
        for (Product product : productMapper.obtainAll()) {
            writer.println("<li>");
            writer.println(
                    product.getName()+"（¥"+nf.format(product.getPrice())+"）（"
                    +"<a href='/product/detail?id="+product.getId()+"'>商品明细</a>"+"）"
            );
            writer.println("</li>");
        }
        writer.println("</ul>");
        writer.println("<a href='/cart'>查看购物车</a>");
        writer.println("</body>");

        writer.println("</html>");
    }
}
