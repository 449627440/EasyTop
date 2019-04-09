package com.swufe.bluebook.CityChooseSlide;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;
import com.swufe.bluebook.PersonalCenter.Photo.ActivityStack;
import com.swufe.bluebook.PersonalCenter.Photo.KevinApplication;
import com.swufe.bluebook.Moment.VIew.ImageLoader;
import com.swufe.bluebook.R;

public class GlobalApp extends Application {

	protected static GlobalApp kevinApplication = null;
	/** 上下文 */
	protected Context mContext          = null;
	/** Activity 栈 */
	public ActivityStack mActivityStack = null;

    public static LocationUtils mLocationUtils = null;
    private static GlobalApp mInstance = null;
	public void onCreate() {
		super.onCreate();
        mInstance=this;
		AssertsFileUtils.getCityAll(this);
		SDKInitializer.initialize(getApplicationContext());// 初始化百度地图
		Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);// 获取震动类
		mLocationUtils = LocationUtils.getInstance(getApplicationContext(),
				vibrator);// 初始化百度地图工具类
		mLocationUtils.getLcation(null, null, 2000, true);// 初始化百度地图定位设置
		mLocationUtils.startLocation();// 启动定位
		kevinApplication = this;
		mContext = getApplicationContext();     // 获取上下文
		mActivityStack = new ActivityStack();   // 初始化Activity 栈

		initConfiguration();


		NineGridView.setImageLoader(new GlideImageLoader());
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new ImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);  //显示拍照按钮
		imagePicker.setCrop(true);        //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true); //是否按矩形区域保存
		imagePicker.setSelectLimit(9);    //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
	}

	private void initConfiguration() {
	}

	public static GlobalApp getmInstance() {
        return mInstance;
    }

	public static GlobalApp getInstance() {
		return kevinApplication;
	}

    public static void setmInstance(GlobalApp mInstance) {
        GlobalApp.mInstance = mInstance;
    }

	private class GlideImageLoader implements NineGridView.ImageLoader {
		@Override
		public void onDisplayImage(Context context, ImageView imageView, String url) {
			Glide.with(context).load(url)//
					.placeholder(R.drawable.ic_default_image)//
					.error(R.drawable.ic_default_image)//
					.into(imageView);

		}

		@Override
		public Bitmap getCacheImage(String url) {
			return null;
		}
	}
}
