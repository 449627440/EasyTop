package com.swufe.bluebook.CityChooseSlide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量定义
 */
public abstract class Constants {
    public static List<CityBean> CITY_LIST = new ArrayList<CityBean>();
    public static String HISCITY_FILE = "HistoryCity"; // 历史城市记录保存SharedPreferences的文件
    public static final Map DISTRICT = new HashMap();
    static {
        DISTRICT.put("289", "310000");
    }

    public static String GPS="GPS";
    public static String LATITUDE="Latitude";
    public static String LONGITUDE="Longitude";
    public static String STREET="street";
    public static String STREET_NUMBER="street_number";
    public static String LBS_DISTRICT="district";
    public static String CITY="city";
    public static String CITYCODE="citycode";
    public static String PROVINCE="province";
    public static String COUNTRY="country";
    public static String DETAIL="detail";
    public static String OPERATORS="Operators";

}
