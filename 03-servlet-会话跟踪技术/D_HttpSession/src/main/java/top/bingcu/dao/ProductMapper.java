package top.bingcu.dao;

import top.bingcu.pojo.Product;

import java.util.*;

public class ProductMapper {
    private static Map<Integer, Product> productMap = new LinkedHashMap<Integer, Product>();

    static {
        init();
    }

    public ProductMapper(){
    }

    private static void init(){
        Product prdt1 = new Product(1, "电子鼓", "罗兰(Roland)电子鼓TD11k", 5800);
        Product prdt2 = new Product(2, "调音器", "京东超市卡马（KEPMA）T01卡马正品调音器原装通用民谣吉他调音表", 35);
        Product prdt3 = new Product(3, "电吉他", "PRS 印尼产电吉他SE STANDARD 24 ST22 ST24 22/24品电吉他", 6660);
        Product prdt4 = new Product(4, "贝斯", "YAMAHA雅马哈电贝斯TRBX304系列174EW初学入门演出四弦五弦印尼进口", 2699);
        Product prdt5 = new Product(5, "箱鼓", "京东国际MEINL 麦尔 箱鼓 非洲鼓 卡宏鼓 手鼓 Cajon MCAJ1卡洪鼓", 1300);
        Product prdt6 = new Product(6, "拾音器", "德博double吉他无线拾音器免开孔G0吉他精灵同频加振拾音器民谣木吉它扩音器x2", 538);

        productMap.put(prdt1.getId(), prdt1);
        productMap.put(prdt2.getId(), prdt2);
        productMap.put(prdt3.getId(), prdt3);
        productMap.put(prdt4.getId(), prdt4);
        productMap.put(prdt5.getId(), prdt5);
        productMap.put(prdt6.getId(), prdt6);
    }

    public Product obtain(int id) {
        return productMap.get(id);
    }


    public List<Product> obtainAll(){
        List list = new LinkedList();

        for (Integer id : productMap.keySet()) {
            list.add(productMap.get(id));
        }
        return list;
    }

}
