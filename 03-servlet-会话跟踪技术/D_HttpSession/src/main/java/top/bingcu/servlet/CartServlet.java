package top.bingcu.servlet;

import top.bingcu.dao.ProductMapper;
import top.bingcu.pojo.Cart;
import top.bingcu.pojo.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private ProductMapper productMapper = new ProductMapper();

    private NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null || action.equals("")) {
            showCartList(req, resp);

        } else if (action.equals("append")){
            appendCart(req, resp);
            resp.sendRedirect("/product/list");

        } else if (action.equals("remove")) {
            //这里表示用来删除购物车中的某件商品
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    public void showCartList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();


        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }


        writer.println("<html>");

        writer.println("<head><title>商品详情</title></head>");
        writer.println("<body>");
        writer.println("<ul>");
        if (cartList.size() <= 0) {
            writer.println("<li>购物车空空如也~</li>");
        }
        for (Cart cart : cartList) {
            writer.println("<li>");
            Product p = cart.getProduct();
            writer.println("商品名："+p.getName()+"（价格：¥"+p.getPrice()+"）描述："+p.getDescription()+"【数量："+cart.getQuantity()+"】");
            writer.println("</li>");
        }
        writer.println("</ul>");
        writer.println("<a href='/product/list'>返回</a>");
        writer.println("</body>");

        writer.println("</html>");

        session.setAttribute("cart", cartList);
    }

    public void appendCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }


        int id = Integer.parseInt(req.getParameter("id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        Cart newCart = new Cart();
        newCart.setProduct(productMapper.obtain(id));
        newCart.setQuantity(quantity);
        cartList.add(newCart);

        session.setAttribute("cart", cartList);
    }

    }
