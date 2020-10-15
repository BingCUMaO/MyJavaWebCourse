package top.bingcu.dao;

import java.util.*;

public class AttractionMapper {

    private Map<String, String> namesMap = new HashMap<>();
    private Map<String, List<String>> attractionMap = new HashMap<>();



    public AttractionMapper(){
        initDatas();
    }

    private void initDatas(){
        //四川
        List<String> sichuan = new ArrayList<>();
        sichuan.add("都江堰景区");
        sichuan.add("峨眉山");
        sichuan.add("锦里古街");
        sichuan.add("武侯祠");
        sichuan.add("成都大熊猫繁育研究基地");
        sichuan.add("稻城");
        sichuan.add("乐山");
        sichuan.add("康定");
        sichuan.add("甘孜");
        sichuan.add("大邑");

        //深圳
        List<String> shenzheng = new ArrayList<>();
        shenzheng.add("东部华侨城 ");
        shenzheng.add("莲花山公园  ");
        shenzheng.add("大梅沙海滨公园  ");
        shenzheng.add("锦绣中华民俗村  ");
        shenzheng.add("梧桐山  ");
        shenzheng.add("深圳欢乐谷 ");
        shenzheng.add("南澳旅游区 ");
        shenzheng.add("小梅沙 ");
        shenzheng.add("较场尾 ");
        shenzheng.add("中英街 ");
        shenzheng.add("小梅沙海洋世界");
        shenzheng.add("红树林");

        namesMap.put("sichuan", "四川");
        attractionMap.put("sichuan", sichuan);

        namesMap.put("shenzheng", "深圳");
        attractionMap.put("shenzheng", shenzheng);
    }

    public List<String> obtainAttraction(String cityId) {
        return attractionMap.get(cityId);
    }

    public Set<String> obtainAttractionIds(){
        return attractionMap.keySet();
    }

    public String obtainCityName(String cityId){
        return namesMap.get(cityId);
    }

    public List<String> obtainCityNames(){
        Set<String> keys = namesMap.keySet();

        List<String> names = new ArrayList<>();
        names.addAll(keys);

        return names;
    }

    public int getAttractionTotal(String cityId) {
        return obtainAttraction(cityId).size();
    }
}
