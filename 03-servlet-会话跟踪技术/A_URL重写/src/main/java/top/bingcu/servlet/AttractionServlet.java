package top.bingcu.servlet;

import top.bingcu.dao.AttractionMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/attraction")
public class AttractionServlet extends HttpServlet {

    private AttractionMapper attractionMapper = new AttractionMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //config
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf8");

        String city = req.getParameter("city");
        if (  (city == null || city == "")  || attractionMapper.obtainCityName(city) == null  ) {
            showNavigatePage(req, resp);
        } else{
            showAttractionDatas(req, resp, city);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void showNavigatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>十大旅游景点</title></head><body>");
        writer.println("<h3>请选择城市：</h3>");
        for (String cityId : attractionMapper.obtainAttractionIds()) {
            writer.println("<a href='?city=" + cityId+"&page=1" + "'>"+attractionMapper.obtainCityName(cityId)+"</a>");
        }
        writer.println("</body></html>");
    }

    private void showAttractionDatas(HttpServletRequest req, HttpServletResponse resp, String city) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        int page = Integer.parseInt(req.getParameter("page"));
        String cityId = req.getParameter("city");
        String cityName = attractionMapper.obtainCityName(cityId);
        int attractionTotal = attractionMapper.getAttractionTotal(cityId);
        int maxPageNum = (int) Math.ceil(attractionTotal/5.0);

        writer.println("<html><head><title>十大旅游景点</title></head>");
        writer.println("<body>");
        //回到主页
        writer.println("<h2><a href='/attraction'>回到主页</a></h2>");
        writer.println("<br></br>");

        //根据url中的city和page参数，在页面写入不同的数据
        int start = page * 5 - 5;
        int end = start + 4;            //区间[start, end]
        List<String> atts = attractionMapper.obtainAttraction(cityId);
        while (start <= end&&start<attractionTotal) {
            writer.println("<div>");
            writer.println(atts.get(start));
            writer.println("</div>");
            start++;
        }

        writer.println("<br></br>");

        //在每个页面上写入不同页面的链接
        int pageTick = 0;
        while (pageTick++ < maxPageNum) {
            writer.println("<a href='?city="+cityId+"&page="+pageTick+"'>页" + pageTick + "</a>");
        }
        writer.println("</body></html>");

    }
}
