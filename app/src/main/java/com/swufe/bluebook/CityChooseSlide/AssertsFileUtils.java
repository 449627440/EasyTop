package com.swufe.bluebook.CityChooseSlide;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AssertsFileUtils {

	/**
	 * 读取asserts目录下的文件
	 * 
	 * @param fileName
	 *            eg:"updatelog.txt"
	 * @return 对应文件的内容
	 * 
	 * */
	public static String readFileFromAssets(Context context, String fileName)
			throws IOException, IllegalArgumentException {
		if (null == context || TextUtils.isEmpty(fileName)) {
			throw new IllegalArgumentException("bad arguments!");
		}

		AssetManager assetManager = context.getResources().getAssets();
		InputStream input = assetManager.open(fileName);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = input.read(buffer)) != -1) {
			output.write(buffer, 0, length);
		}
		output.close();
		input.close();

		return output.toString();
	}

	public static void getCityAll(Context context) {
		try {
			String cityjson = readFileFromAssets(context, "cityjson.txt");
			Log.i("Assets", "getCityAll: cityjson="+cityjson);
			Constants.CITY_LIST = JSON.parseArray(cityjson, CityBean.class);
			Collections.sort(Constants.CITY_LIST, new Comparator<CityBean>() {
				@Override
				public int compare(CityBean lhs, CityBean rhs) {
					// TODO Auto-generated method stub
					String a = lhs.getSpell().substring(0, 1);
					String b = rhs.getSpell().substring(0, 1);
					int flag = a.compareTo(b);
					if (flag == 0) {
						return a.compareTo(b);
					} else {
						return flag;
					}
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据百度地图定位到的code得到后台对应的cityCode
	 * 
	 * @param location
	 *            百度地图定位得到的code
	 * @return 后台对应的cityCode
	 */
	public static String getCityCodeByKey(String location) {
		String cityCode = "";
		if(!CheckUtils.isEmpty(location)){
			for (int i = 0; i < Constants.CITY_LIST.size(); i++) {
				CityBean city = Constants.CITY_LIST.get(i);
				if (!CheckUtils.isEmpty(city.getKey())) {
					if (location.equals(city.getKey())) {
						cityCode = city.getId();
					}
				}
			}
		}
		return cityCode;
	}

	/**
	 * 根据百度地图定位到的城市名称得到后台对应的cityCode
	 * 
	 * @param name
	 *            百度地图定位到的城市名称
	 * @return 后台对应的cityCode
	 */
	public static String getCityCodeByName(String name) {
		String cityCode = "";
		for (int i = 0; i < Constants.CITY_LIST.size(); i++) {
			CityBean city = Constants.CITY_LIST.get(i);
			if (!CheckUtils.isEmpty(city.getName())) {
				if (name.equals(city.getName())) {
					cityCode = city.getId();
				}
			}
		}
		return cityCode;
	}

	/**
	 * 根据城市Code得到后台对应的下级城市的列表
	 * 
	 * @param code
	 *            城市Code
	 * @return 下级城市的列表
	 */
	public static List<CityBean> getChildCityByCode(String code) {
		List<CityBean> child = new ArrayList<CityBean>();
		for (int i = 0; i < Constants.CITY_LIST.size(); i++) {
            CityBean city = Constants.CITY_LIST.get(i);
			if (!CheckUtils.isEmpty(city.getId())) {
				if (code.equals(city.getId())) {
					String childStr = city.getDistricList();
					child = JSON.parseArray(childStr, CityBean.class);
				}
			}
		}
		return child;
	}
//
//	public static void getWeatherCityAll(Context context) {
//		try {
//			String cityjson = readFileFromAssets(context, "weatherjson.txt");
//			Constants.WEATHER_CITY_LIST = JSON.parseArray(cityjson,Map.class);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static String getWeatherCityCode(String name){
//		String cityCode = null;
//		for (int i = 0; i < Constants.WEATHER_CITY_LIST.size(); i++) {
//			Map weatherMap = Constants.WEATHER_CITY_LIST.get(i);
//			String cityName = String.valueOf(weatherMap.get("name"));
//			if(name.startsWith(cityName)){
//				cityCode = String.valueOf(weatherMap.get("code"));
//			}else{
//				continue;
//			}
//		}
//		return cityCode;
//	}
}
