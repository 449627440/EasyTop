package com.swufe.bluebook.CityChooseSlide;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图工具类
 * 
 * @author qp
 *
 */
public class LocationUtils {

	/**
	 * @seelcationMap.put("Latitude", location.getLatitude());//纬度
	 * @seelcationMap.put("Longitude", location.getLongitude());//经度
	 * @seelcationMap.put("street", location.getStreet());//道路名
	 * @seelcationMap.put("street_number", location.getStreetNumber());//门牌号码
	 * @seelcationMap.put("district", location.getDistrict());//区县
	 * @seelcationMap.put("city", location.getCity());//城市
	 * @seelcationMap.put("province", location.getProvince());//省份名称
	 * @seelcationMap.put("country", location.getCountry());//国家
	 * @seelcationMap.put("detail",
	 *      location.getAddrStr()+location.getLocationDescribe());//详细地址
	 */
	public static Map lcationMap = new HashMap();
	private Context context;
	private LocationClient mLocationClient;
	private Vibrator mVibrator;
//	public static double longitude;
	private static LocationUtils mInstance = null;
//	public static double latitude;
	public static BDLocation location;
	public static String TAG = "LocationUtils";

	private LocationUtils(Context context, Vibrator mVibrator) {
		this.context = context;
		this.mVibrator = mVibrator;
	}

	public static LocationUtils getInstance(Context context, Vibrator mVibrator) {
		if (CheckUtils.isEmpty(mInstance)) {
			mInstance = new LocationUtils(context, mVibrator);
		}
		return mInstance;
	}

	/**
	 * 开启百度定位
	 */
	public void startLocation() {
		if (!CheckUtils.isEmpty(mLocationClient)&&!mLocationClient.isStarted()) {
			mLocationClient.start();
			Log.i(TAG, "startLocation: ");
		} else {
//			ToastUtil.showLongToast(context, "LocationClient没有初始化！");
			Log.i(TAG, "startLocation: LocationClient没有初始化！");
		}
	}

	/**
	 * 停止百度定位
	 */
	public void stopLocation() {
		if (!CheckUtils.isEmpty(mLocationClient)&&mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

	/**
	 * 百度定位获取相关信息
	 * 
	 * @param locationMode
	 *            定位模式 默认 LocationMode.Hight_Accuracy 高精度 其他设置
	 *            LocationMode.Battery_Saving 低功耗 LocationMode.Device_Sensors
	 *            仅设备
	 * @param coorType
	 *            返回的定位结果坐标系 默认 bd09ll 百度经纬度球面坐标 其他设置 bd09 百度墨卡托平面坐标 0 不要求返回坐标
	 *            gcj02 国测局经纬度球面坐标
	 * @param ScanSpan
	 *            地位时间间隔 注意间隔需要大于等于1000ms才是有效的 默认1000ms
	 * @param GPSFlag
	 *            是否开启GPS地位
	 * @return
	 */
	public void getLcation(LocationMode locationMode, String coorType,
                           int ScanSpan, boolean GPSFlag) {
		mLocationClient = new LocationClient(context);
		LocationClientOption option = initLocationClientOption(locationMode,
				coorType, ScanSpan, GPSFlag);
		mLocationClient.registerLocationListener(myListener);
		Log.i(TAG, "getLcation: ");
	}

	public void requestLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		} else {
			Log.e("baidu",
					" mLocationClient not new or start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	/**
	 * 初始化地图定位参数
	 * 
	 * @param locationMode
	 *            定位模式 默认 LocationMode.Hight_Accuracy 高精度 其他设置
	 *            LocationMode.Battery_Saving 低功耗 LocationMode.Device_Sensors
	 *            仅设备
	 * @param coorType
	 *            返回的定位结果坐标系 默认 bd09ll 百度经纬度球面坐标 其他设置 bd09 百度墨卡托平面坐标 0 不要求返回坐标
	 *            gcj02 国测局经纬度球面坐标
	 * @param ScanSpan
	 *            地位时间间隔 注意间隔需要大于等于1000ms才是有效的 默认1000ms
	 * @param GPSFlag
	 *            是否开启GPS地位
	 * @return
	 */
	private LocationClientOption initLocationClientOption(
            LocationMode locationMode, String coorType, int ScanSpan,
            boolean GPSFlag) {
		LocationClientOption option = new LocationClientOption();
		// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		// LocationMode.Hight_Accuracy 高精度
		// LocationMode.Battery_Saving 低功耗
		// LocationMode.Device_Sensors 仅设备
		// if (CheckUtils.isEmpty(locationMode)) {
		// option.setLocationMode(LocationMode.Hight_Accuracy);
		// } else {
		// option.setLocationMode(locationMode);
		// }
		option.setLocationMode(LocationMode.Hight_Accuracy);
		// 可选，默认gcj02，设置返回的定位结果坐标系
		// 不要求返回坐标 0
		// 返回国测局坐标 gcj02 坐标为经纬度球面坐标
		// 返回百度坐标 bd09 坐标为墨卡托平面坐标
		// 返回百度坐标 bd09ll 坐标为经纬度球面坐标
		if (CheckUtils.isEmpty(coorType)) {
			option.setCoorType("bd09ll");
		} else {
			option.setCoorType(coorType);
		}
		option.setScanSpan(ScanSpan);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		// option.disableCache(true);// 禁止缓存地位
		mLocationClient.setLocOption(option);
		Log.i(TAG, "initLocationClientOption: ");
		return option;
	}

	/**
	 * 百度定位监听
	 */
	BDLocationListener myListener = new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation location) {
			LocationUtils.location = location;
			int requestCode = location.getLocType();
			switch (requestCode) {
			case 61:
				lcationMap.put(Constants.GPS, true);
//				latitude = location.getLatitude();
//				longitude = location.getLongitude();
				lcationMap.put(Constants.LATITUDE, location.getLatitude());// 纬度
				lcationMap.put(Constants.LONGITUDE, location.getLongitude());// 经度
				lcationMap.put(Constants.STREET, location.getStreet());// 道路名
				lcationMap.put(Constants.STREET_NUMBER, location.getStreetNumber());// 门牌号码
				lcationMap.put(Constants.DISTRICT, location.getDistrict());// 区县
				lcationMap.put(Constants.CITY, location.getCity());// 城市
				lcationMap.put(Constants.CITYCODE, location.getCityCode());// 城市
				lcationMap.put(Constants.PROVINCE, location.getProvince());// 省份名称
				lcationMap.put(Constants.COUNTRY, location.getCountry());// 国家
				lcationMap.put(Constants.DETAIL,
						location.getAddrStr() + location.getLocationDescribe());// 详细地址
				// location.getSpeed() 单位：公里每小时
				// getSatelliteNumber 卫星数
				// getAltitude() 海拔高度
				// getDirection() 方向
				// GPS 定位成功
				Log.i(TAG, "onReceiveLocation: 1");
				break;
			case 62:
				lcationMap.put(Constants.GPS, false);
				Toast.makeText(context, "无法获取有效定位依据，定位失败",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 2");
				break;
			case 63:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "网络异常，没有成功向服务器发起请求",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 3");
				break;
			case 65:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "定位缓存的结果",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 4");

				// 缓存 定位成功
				break;
			case 66:
				lcationMap.put(Constants.GPS, true);
//				latitude = location.getLatitude();
//				longitude = location.getLongitude();
                lcationMap.put(Constants.CITYCODE, location.getCityCode());// 城市
				lcationMap.put(Constants.LATITUDE, location.getLatitude());// 纬度
				lcationMap.put(Constants.LONGITUDE, location.getLongitude());// 经度
				lcationMap.put(Constants.STREET, location.getStreet());// 道路名
				lcationMap.put(Constants.STREET_NUMBER, location.getStreetNumber());// 门牌号码
				lcationMap.put(Constants.DISTRICT, location.getDistrict());// 区县
				lcationMap.put(Constants.CITY, location.getCity());// 城市
				lcationMap.put(Constants.PROVINCE, location.getProvince());// 省份名称
				lcationMap.put(Constants.COUNTRY, location.getCountry());// 国家
				lcationMap.put(Constants.DETAIL,
						location.getAddrStr() + location.getLocationDescribe());// 详细地址
				Log.i(TAG, "onReceiveLocation: 5");
				// 离线定位成功
				break;
			case 67:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "离线定位失败",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 6");
				break;
			case 68:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "网络连接失败时，查找本地离线定位时对应的返回结果",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 7");
				break;
			case 161:
				lcationMap.put(Constants.GPS, true);
				lcationMap.put(Constants.LATITUDE, location.getLatitude());// 纬度
				lcationMap.put(Constants.LONGITUDE, location.getLongitude());// 经度
//				latitude = location.getLatitude();
//				longitude = location.getLongitude();
				lcationMap.put(Constants.STREET, location.getStreet());// 道路名
				lcationMap.put(Constants.STREET_NUMBER, location.getStreetNumber());// 门牌号码
				lcationMap.put(Constants.DISTRICT, location.getDistrict());// 区县
				lcationMap.put(Constants.CITY, location.getCity());// 城市
				lcationMap.put(Constants.PROVINCE, location.getProvince());// 省份名称
				lcationMap.put(Constants.COUNTRY, location.getCountry());// 国家
				lcationMap.put(Constants.DETAIL,
						location.getAddrStr() + location.getLocationDescribe());// 详细地址
				lcationMap.put(Constants.OPERATORS, location.getOperators());// 运营商信息
                lcationMap.put(Constants.CITYCODE, location.getCityCode());// 城市
				// 网络定位成功
				Log.i(TAG, "onReceiveLocation: 7");
				break;
			case 162:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "请求串密文解析失败，一般是由于客户端SO文件加载失败造成",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 8");
				break;
			case 167:
				lcationMap.put(Constants.GPS, false);
                Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限",Toast.LENGTH_LONG);
				Log.i(TAG, "onReceiveLocation: 9");
				break;
			case 502:
				lcationMap.put(Constants.GPS, false);
				// ToastUtil.showLongToast(context, "key参数错误");
				Log.i(TAG, "onReceiveLocation: 10");
				break;
			case 505:
				lcationMap.put(Constants.GPS, false);
				// ToastUtil.showLongToast(context, "key不存在或者非法");
				Log.i(TAG, "onReceiveLocation: 11");
				break;
			case 601:
				lcationMap.put(Constants.GPS, false);
				// ToastUtil.showLongToast(context, "key服务被开发者自己禁用");
				Log.i(TAG, "onReceiveLocation: 12");
				break;
			case 602:
				lcationMap.put(Constants.GPS, false);
				// ToastUtil.showLongToast(context,
				// "key mcode不匹配，请确保：sha1正确且包名正确");
				Log.i(TAG, "onReceiveLocation: 13");
				break;
			default:
				Log.i(TAG, "onReceiveLocation: 14");
				break;
			}
		}
	};

}
