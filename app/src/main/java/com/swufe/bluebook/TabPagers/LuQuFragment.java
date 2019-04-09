package com.swufe.bluebook.TabPagers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.Score;
import com.swufe.bluebook.Backstage.province;
import com.swufe.bluebook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import static com.tencent.smtt.sdk.TbsReaderView.TAG;

public class LuQuFragment extends Fragment {
    private SmartTable table;
    private LineChartView lineChart;
    String[] date ;//X轴的标注
    int[] score ;//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private String university;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_luqu, container, false);
        return mView;
    }
    public class SchoolInfo {
        public SchoolInfo(String year, String wenli,String pici, String highest, String lowest, String average) {
            this.year = year;
            this.wenli = wenli;
            this.pici = pici;
            this.highest = highest;
            this.lowest = lowest;
            this.average = average;
        }
        private String year;
        private String wenli;
        private String pici;
        private String highest;
        private String lowest;
        private String average;
    }

    public void updateUI(final String university){
        this.university = university;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        table = view.findViewById(R.id.smarttable1);
        table.setZoom(false);

        table.getConfig().setContentStyle(new FontStyle(50, Color.BLACK));

        lineChart = view.findViewById(R.id.line_chart);
        textView = view.findViewById(R.id.wenlikr);
        final String[] ctype = new String[]{"理科", "文科"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        Spinner lqmc_sp = view.findViewById(R.id.spinner);
        lqmc_sp.setAdapter(adapter);
        lqmc_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(ctype[i]);
                BmobQuery<province> query = new BmobQuery<>();
                query.addWhereEqualTo("city","成都市");
                query.findObjects(new FindListener<province>() {
                    @Override
                    public void done(List<province> list, BmobException e) {
                        if(e==null){
                            date = null;
                            score = null;
                            String province = list.get(0).getProvince();
                            Log.i(TAG, "done: province"+province);
                            BmobQuery<Score> query = new BmobQuery<>();
                            query.addWhereEqualTo("university",university);
                            query.addWhereEqualTo("province",province);
                            query.addWhereEqualTo("type",textView.getText());
                            query.findObjects(new FindListener<Score>() {
                                @Override
                                public void done(List<Score> list, BmobException e) {
                                    if(e==null){
                                        Column<String> year = new Column<>("年份", "year");
                                        Column<String> wenli = new Column<>("文理科", "wenli");
                                        Column<String> pici = new Column<>("录取批次", "pici");
                                        Column<String> highest = new Column<>("最高分", "highest");
                                        Column<String> lowest = new Column<>("最低分", "lowest");
                                        Column<String> average = new Column<>("平均分", "average");
                                        year.setAutoMerge(true);
                                        List<SchoolInfo> list1 = new ArrayList<>();
                                        date = new String[list.size()-1];
                                        score = new int[list.size()-1];
                                        for(int i=0;i<list.size()-1;i++){
                                            date[i] = list.get(i).getYear();
                                            Log.i(TAG, "done: "+date[i]);
                                            score[i] = list.get(i).getAveScore().intValue();
                                            Log.i(TAG, "done: "+score[i]);
                                            String max,min;
                                            if(list.get(i).getMaxScore()==null){
                                                max="---";
                                            }else {
                                                max = String.valueOf(list.get(i).getMaxScore());
                                            }
                                            if(list.get(i).getMinScore()==null){
                                                min="---";
                                            }else {
                                                min=String.valueOf(list.get(i).getMinScore());
                                            }
                                            list1.add(new SchoolInfo(list.get(i).getYear(), list.get(i).getType(), list.get(i).getOrder(), max, min, String.valueOf(list.get(i).getAveScore())));
                                        }
                                        Log.i(TAG, "done: ok");
                                        getAxisXLables();//获取x轴的标注
                                        Log.i(TAG, "done: ok1");
                                        getAxisPoints();//获取坐标点
                                        Log.i(TAG, "done: ok2");
                                        initLineChart();//初始化
                                        Log.i(TAG, "done: ok3");
                                        TableData<SchoolInfo> schoolInfoTableData = new TableData<SchoolInfo>("院校分数线", list1, year, wenli, pici,highest,lowest,average);
                                        table.setTableData(schoolInfoTableData);
                                    }else {
                                        Log.i(TAG, "done: why"+e);
                                    }
                                }
                            });
                        }else {
                            Log.i(TAG, "done: why1"+e);
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private int getRandom_chong(){
        int x=65+(int)(Math.random()*7);
        return x;
    }


    private int getRandom_wen(){
        int y=72+(int)(Math.random()*8);
        return y;
    }

    private int getRandom_bao(){
        int z=80+(int)(Math.random()*9);
        return z;
    }

    private void getAxisXLables(){
        mAxisXValues.clear();
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        mPointValues.clear();
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(true);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("历年分数线");  //表格名称
        axisX.setTextSize(12);//设置字体大小
        axisX.setMaxLabelChars(13); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("分数");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.BLACK);  //设置字体颜色
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 1);//最大方法比例
        lineChart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 11;
        lineChart.setCurrentViewport(v);
    }

}
