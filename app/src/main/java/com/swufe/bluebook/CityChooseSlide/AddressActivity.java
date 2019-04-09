package com.swufe.bluebook.CityChooseSlide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.Launch.LaunchActivity;
import com.swufe.bluebook.LoginOrRegister.LoginActivity;
import com.swufe.bluebook.MainActivity;
import com.swufe.bluebook.Predict.PredictActivity;
import com.swufe.bluebook.Predict.ResultActivity;
import com.swufe.bluebook.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class AddressActivity extends Activity implements OnScrollListener,Serializable {

	private BaseAdapter adapter;
	private ResultListAdapter resultListAdapter;
	private ListView personList;
	private ListView resultList;
	private TextView overlay; // 对话框首字母textview
	private MyLetterListView letterListView; // A-Z listview
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread; // 显示首字母对话框
	private List<CityBean> allCity_lists; // 所有城市列表
	private List<CityBean> city_lists;// 城市列表
	private List<CityBean> city_hot;
	private List<CityBean> city_result;
	private List<CityBean> city_history;
	private List<CityBean> city_located;
	public static LocationUtils mLocationUtils = null;
	private Toolbar toolbar;
	private String TAG ="AddressActivity";

	private EditText sh;
	private TextView tv_noresult;
//	private int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
//	private boolean isNeedFresh;

	private SharedPreferences spHis;
	private SharedPreferences spLoc;
	ArrayList<HashMap<String,Object>> list1 = new ArrayList<HashMap<String,Object>>();

	WindowManager windowManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		personList = (ListView) findViewById(R.id.list_view);
		allCity_lists = new ArrayList<CityBean>();
		city_hot = new ArrayList<CityBean>();
		city_result = new ArrayList<CityBean>();
		city_history = new ArrayList<CityBean>();
		city_located = new ArrayList<CityBean>();
		spLoc = getSharedPreferences("CityChose",Context.MODE_PRIVATE);

		resultList = (ListView) findViewById(R.id.search_result);
		sh = (EditText) findViewById(R.id.sh);
		tv_noresult = (TextView) findViewById(R.id.tv_noresult);
		list1=(ArrayList<HashMap<String,Object>>)getIntent().getSerializableExtra("retlist");


		toolbar=findViewById(R.id.address_toolbar);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 搜索历史城市
		SelectSpHistoy();

		sh.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				if (s.toString() == null || "".equals(s.toString())) {
					letterListView.setVisibility(View.VISIBLE);
					personList.setVisibility(View.VISIBLE);
					resultList.setVisibility(View.GONE);
					tv_noresult.setVisibility(View.GONE);
				} else {
					city_result.clear();
					letterListView.setVisibility(View.GONE);
					personList.setVisibility(View.GONE);
					getResultCityList(s.toString());
					if (city_result.size() <= 0) {
						tv_noresult.setVisibility(View.VISIBLE);
						resultList.setVisibility(View.GONE);
					} else {
						tv_noresult.setVisibility(View.GONE);
						resultList.setVisibility(View.VISIBLE);
						resultListAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
		letterListView
				.setOnTouchingLetterChangedListener((MyLetterListView.OnTouchingLetterChangedListener) new LetterListViewListener());
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
//		isNeedFresh = true;
		personList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (position >= 4) {
					String selectedCity = allCity_lists.get(position).getName();
					if(selectedCity.equals("成都市")||selectedCity.equals("重庆市")||selectedCity.equals("北京市")||selectedCity.equals("西安市")||selectedCity.equals("厦门市")||selectedCity.equals("南京市")){
						Editor edit1 = spLoc.edit();
						edit1.putString("CityChose", selectedCity);
						edit1.commit();
						User user = new User();
						user.setOwnCity(selectedCity);
						BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
						user.update(bmobUser.getObjectId(),new UpdateListener() {
							@Override
							public void done(BmobException e) {
								if(e==null){

								}else{

								}
							}
						});
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(AddressActivity.this,MainActivity.class);
								startActivity(intent);
							}
						}, 500);
						insertSpHistoy(allCity_lists.get(position));
						reFreshClub(allCity_lists.get(position).getId());
					}
				}
			}
		});

//		locateProcess = 1;
		personList.setAdapter(adapter);
		personList.setOnScrollListener(this);
		resultListAdapter = new ResultListAdapter(this, city_result);
		resultList.setAdapter(resultListAdapter);
		resultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Toast.makeText(getApplicationContext(),
						city_result.get(position).getName(), Toast.LENGTH_SHORT)
						.show();
				insertSpHistoy(city_result.get(position));
				reFreshClub(city_result.get(position).getId());
			}
		});
		initOverlay();
		cityInit();
		hotCityInit();
		// hisCityInit();
		setAdapter(allCity_lists, city_hot, city_history);

//		ExitApplication.getInstance().addActivity(this);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		windowManager.removeViewImmediate(overlay);
	}

	@SuppressWarnings("unchecked")
	private void getResultCityList(String keyword) {
		for (int i = 0; i < Constants.CITY_LIST.size(); i++) {
			String name = Constants.CITY_LIST.get(i).getName();
			String spell = Constants.CITY_LIST.get(i).getSpell();
			if (name.contains(keyword) || spell.contains(keyword)) {
				city_result.add(Constants.CITY_LIST.get(i));
			}
		}
		// 将得到的集合按照自定义的comparator的规则进行排序
		// Collections.sort(city_result, comparator);
	}

	private void cityInit() {
		CityBean city = new CityBean("", "定位", "0"); // 当前定位城市
		allCity_lists.add(city);
		city = new CityBean("", "最近", "1"); // 最近访问的城市
		allCity_lists.add(city);
		city = new CityBean("", "热门", "2"); // 热门城市
		allCity_lists.add(city);
		city = new CityBean("", "全部", "3"); // 全部城市
		allCity_lists.add(city);
		allCity_lists.addAll(Constants.CITY_LIST);
		Log.i(TAG, "cityInit: CITY_LIST="+Constants.CITY_LIST);
	}

	/*Toolbar */

	/**
	 * 热门城市
	 */
	public void hotCityInit() {
		CityBean city = new CityBean("51010075", "成都市", "chengdu");
		city_hot.add(city);
		city = new CityBean("31000289", "上海市", "shanghai");
		city_hot.add(city);
		city = new CityBean("11000131", "北京市", "beijing");
		city_hot.add(city);
		city = new CityBean("44030340", "深圳市", "shenzhen");
		city_hot.add(city);
		city = new CityBean("44010257", "广州市", "guangzhou");
		city_hot.add(city);
		city = new CityBean("50000132", "重庆市", "chongqing");
		city_hot.add(city);
		city = new CityBean("61010233", "西安市", "xian");
		city_hot.add(city);
		city = new CityBean("35020194", "厦门市", "xiamen");
		city_hot.add(city);
		city = new CityBean("32010315", "南京市", "nanjing");
		city_hot.add(city);
		city = new CityBean("13010150", "石家庄市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "沈阳市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "哈尔滨市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "杭州市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "福州市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "济南市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "武汉市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "昆明市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "兰州市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "南宁市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "银川市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "太原市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "长春市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "合肥市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "南昌市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "郑州市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "长沙市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "海口市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "贵阳市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "西宁市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "呼和浩特市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "拉萨市", "shijiazhuang");
		city_hot.add(city);
		city = new CityBean("13010150", "乌鲁木齐市", "shijiazhuang");
		city_hot.add(city);
	}

	private void setAdapter(List<CityBean> list, List<CityBean> hotList,
							List<CityBean> hisCity) {
		adapter = new ListAdapter(this, list, hotList, hisCity);
		personList.setAdapter(adapter);
	}

	private class ResultListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<CityBean> results = new ArrayList<CityBean>();

		public ResultListAdapter(Context context, List<CityBean> results) {
			inflater = LayoutInflater.from(context);
			this.results = results;
		}

		@Override
		public int getCount() {
			return results.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item1, null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.name.setText(results.get(position).getName());
			return convertView;
		}

		class ViewHolder {
			TextView name;
		}
	}

	public class ListAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<CityBean> list;
		private List<CityBean> hotList;
		private List<CityBean> hisCity;
		final int VIEW_TYPE = 5;

		public ListAdapter(Context context, List<CityBean> list,
						   List<CityBean> hotList, List<CityBean> hisCity) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			this.context = context;
			this.hotList = hotList;
			this.hisCity = hisCity;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = getAlpha(list.get(i).getSpell());
				// 上一个汉语拼音首字母，如果不存在为" "
				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
						.getSpell()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(list.get(i).getSpell());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE;
		}

		@Override
		public int getItemViewType(int position) {
			return position < 4 ? position : 4;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int viewType = getItemViewType(position);
			if (viewType == 0) { // 定位
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView city = (GridView) convertView
						.findViewById(R.id.recent_city);
				String showstring = "";
				String cityname = String.valueOf(LocationUtils.lcationMap.get(Constants.CITY));
				User user = new User();
				user.setCurrentCity((String) LocationUtils.lcationMap.get(Constants.CITY));
				Log.i(TAG, "onClick: "+(String) LocationUtils.lcationMap.get(Constants.CITY));
				BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
				user.update(bmobUser.getObjectId(),new UpdateListener() {
					@Override
					public void done(BmobException e) {
						if(e==null){
							Log.i(TAG, "done: chenggong");
						}
						else {
							Log.i(TAG, "done: "+e);
						}
					}
				});
				Log.i(TAG, "getView: "+LocationUtils.lcationMap.get(Constants.LATITUDE));
				if (CheckUtils.isEmpty(cityname)) {
					showstring = "";
				} else {
					showstring = cityname;
				}
				final String finalShowstring = showstring;
				CityBean city1 = new CityBean("", finalShowstring, "");
				if(city_located.isEmpty()&&!finalShowstring.isEmpty()){
					city_located.add(city1);
				}else if(!city_located.isEmpty()&&!finalShowstring.isEmpty()){
					city_located.set(0,city1);
				}
				city
						.setAdapter(new HitCityAdapter(context, city_located));
				// city.setText(GlobalApp.getInstance().getCityName());

				city.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						Editor edit1 = spLoc.edit();
						edit1.putString("CityChose", finalShowstring);
						edit1.commit();
						User user = new User();
						user.setOwnCity(finalShowstring);
						BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
						user.update(bmobUser.getObjectId(),new UpdateListener() {
							@Override
							public void done(BmobException e) {
								if(e==null){

								}else{

								}
							}
						});
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(AddressActivity.this,MainActivity.class);
								startActivity(intent);
							}
						}, 500);
						insertSpHistoy(city_located.get(position));
						reFreshClub(city_located.get(position).getId());
					}
				});
				TextView recentHint = (TextView) convertView
						.findViewById(R.id.recentHint);
				recentHint.setText("当前城市");

				// city.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// if (locateProcess == 2) {
				//
				// Toast.makeText(getApplicationContext(),
				// city.getText().toString(),
				// Toast.LENGTH_SHORT).show();
				// } else if (locateProcess == 3) {
				// locateProcess = 1;
				// personList.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
				// mLocationClient.stop();
				// isNeedFresh = true;
				// InitLocation();
				// currentCity = "";
				// mLocationClient.start();
				// }
				// }
				// });
				// ProgressBar pbLocate = (ProgressBar) convertView
				// .findViewById(R.id.pbLocate);
				// if (locateProcess == 1) { // 正在定位
				// locateHint.setText("正在定位");
				// city.setVisibility(View.GONE);
				// pbLocate.setVisibility(View.VISIBLE);
				// } else if (locateProcess == 2) { // 定位成功
				// locateHint.setText("当前定位城市");
				// city.setVisibility(View.VISIBLE);
				// city.setText(currentCity);
				// mLocationClient.stop();
				// pbLocate.setVisibility(View.GONE);
				// } else if (locateProcess == 3) {
				// locateHint.setText("未定位到城市,请选择");
				// city.setVisibility(View.VISIBLE);
				// city.setText("重新选择");
				// pbLocate.setVisibility(View.GONE);
				// }
			} else if (viewType == 1) { // 最近访问城市 俱乐部
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView rencentCity = (GridView) convertView
						.findViewById(R.id.recent_city);
				rencentCity
						.setAdapter(new HitCityAdapter(context, this.hisCity));
				rencentCity.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						Editor edit1 = spLoc.edit();
						edit1.putString("CityChose", city_history.get(position).getName());
						edit1.commit();
						User user = new User();
						user.setOwnCity(city_history.get(position).getName());
						BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
						user.update(bmobUser.getObjectId(),new UpdateListener() {
							@Override
							public void done(BmobException e) {
								if(e==null){

								}else{

								}
							}
						});
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(AddressActivity.this,MainActivity.class);
								startActivity(intent);
							}
						}, 500);
						insertSpHistoy(city_history.get(position));
						reFreshClub(city_history.get(position).getId());
					}
				});
				TextView recentHint = (TextView) convertView
						.findViewById(R.id.recentHint);
				recentHint.setText("最近访问的城市");
			} else if (viewType == 2) {// 运动圈
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView hotCity = (GridView) convertView
						.findViewById(R.id.recent_city);
				hotCity.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						Editor edit1 = spLoc.edit();
						edit1.putString("CityChose", city_hot.get(position).getName());
						edit1.commit();
						User user = new User();
						user.setOwnCity(city_hot.get(position).getName());
						BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
						user.update(bmobUser.getObjectId(),new UpdateListener() {
							@Override
							public void done(BmobException e) {
								if(e==null){

								}else{

								}
							}
						});
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(AddressActivity.this,MainActivity.class);
								startActivity(intent);
							}
						}, 500);
						insertSpHistoy(city_hot.get(position));
						reFreshClub(city_hot.get(position).getId());
					}
				});
				hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
				TextView hotHint = (TextView) convertView
						.findViewById(R.id.recentHint);
				hotHint.setText("热门城市");
			} else if (viewType == 3) {
				convertView = inflater.inflate(R.layout.total_item, null);
			} else {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.list_item1, null);
					holder = new ViewHolder();
					holder.alpha = (TextView) convertView
							.findViewById(R.id.alpha);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (position >= 1) {
					holder.name.setText(list.get(position).getName());
					String currentStr = getAlpha(list.get(position).getSpell());
					String previewStr = (position - 1) >= 0 ? getAlpha(list
							.get(position - 1).getSpell()) : " ";
					if (!previewStr.equals(currentStr)) {
						holder.alpha.setVisibility(View.VISIBLE);
						holder.alpha.setText(currentStr);
					} else {
						holder.alpha.setVisibility(View.GONE);
					}
				}
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha; // 首字母标题
			TextView name; // 城市名字
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	class HotCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<CityBean> hotCitys;

		public HotCityAdapter(Context context, List<CityBean> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position).getName());
			return convertView;
		}
	}

	class HitCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<CityBean> hotCitys;

		public HitCityAdapter(Context context, List<CityBean> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position).getName());
			return convertView;
		}
	}

	class DistricAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<CityBean> hotCitys;

		public DistricAdapter(Context context, List<CityBean> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position).getName());
			return convertView;
		}
	}

	private boolean mReady;

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		mReady = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private boolean isScroll = false;

	private class LetterListViewListener implements
			MyLetterListView.OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			isScroll = false;
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position);
				overlay.setText(s);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}
	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}
	}

	// 获得汉语拼音首字母
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "最近";
		} else if (str.equals("2")) {
			return "热门";
		} else if (str.equals("3")) {
			return "全部";
		} else {
			return "#";
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_TOUCH_SCROLL
				|| scrollState == SCROLL_STATE_FLING) {
			isScroll = true;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		if (!isScroll) {
			return;
		}

		if (mReady) {
			String text;
			String name = allCity_lists.get(firstVisibleItem).getName();
			String pinyin = allCity_lists.get(firstVisibleItem).getSpell();
			if (firstVisibleItem < 4) {
				text = name;
			} else {
				text = PingYinUtil.converterToFirstSpell(pinyin)
						.substring(0, 1).toUpperCase();
			}
			overlay.setText(text);
			overlay.setVisibility(View.VISIBLE);
			handler.removeCallbacks(overlayThread);
			// 延迟一秒后执行，让overlay为不可见
			handler.postDelayed(overlayThread, 1000);
		}
	}

	private void reFreshClub(String cityCode) {
		// 给fragment发送广播
		//点击事件刷新需要刷新的数据即可

//		globalApp.setCityCode(cityCode);
//		Intent intent = new Intent(ClubFragment.CLUBLIST);
//		intent.putExtra("city", cityCode);
//		AddressActivity.this.sendBroadcast(intent);
//
//
//		Intent intentCircle = new Intent(SportCircleFragment.PUBLISHTOSPCFRAG);
//		intentCircle.putExtra("city", cityCode);
//		AddressActivity.this.sendBroadcast(intentCircle);
//		finish();
	}

	private void insertSpHistoy(CityBean citybean) {
		String insertCitycode = citybean.getId();
		String key1 = spHis.getString("1", null);
		String key2 = spHis.getString("2", null);
		String key3 = spHis.getString("3", null);

		String city1code = "", city2code = "", city3code = "";

		if (!CheckUtils.isEmpty(key1)) {
			CityBean city1 = JSONObject.parseObject(key1, CityBean.class);
			city1code = city1.getId();
		}
		if (!CheckUtils.isEmpty(key2)) {
			CityBean city2 = JSONObject.parseObject(key2, CityBean.class);
			city2code = city2.getId();
		}
		if (!CheckUtils.isEmpty(key3)) {
			CityBean city3 = JSONObject.parseObject(key3, CityBean.class);
			city3code = city3.getId();
		}

		if (insertCitycode.equals(city1code)
				|| insertCitycode.equals(city2code)
				|| insertCitycode.equals(city3code)) {

		} else {
			Editor edit = spHis.edit();
			edit.putString("3", key2);
			edit.putString("2", key1);
			edit.putString("1", JSON.toJSONString(citybean));
			edit.commit();
		}
	}

	private void SelectSpHistoy() {
		spHis = getSharedPreferences(Constants.HISCITY_FILE,Context.MODE_PRIVATE);
		String key1 = spHis.getString("1", null);
		String key2 = spHis.getString("2", null);
		String key3 = spHis.getString("3", null);

		if (!CheckUtils.isEmpty(key1)) {
			CityBean city1 = JSONObject.parseObject(key1, CityBean.class);
			city_history.add(city1);
		}
		if (!CheckUtils.isEmpty(key2)) {
			CityBean city2 = JSONObject.parseObject(key2, CityBean.class);
			city_history.add(city2);
		}
		if (!CheckUtils.isEmpty(key3)) {
			CityBean city3 = JSONObject.parseObject(key3, CityBean.class);
			city_history.add(city3);
		}
	}
}
