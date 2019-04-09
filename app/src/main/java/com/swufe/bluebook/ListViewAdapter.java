package com.swufe.bluebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.Ciyun;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.TabPagers.SchoolInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ListViewAdapter extends BaseAdapter {

    private List<SchoolInfo> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private static String TAG = "ListViewAdapter";


    public ListViewAdapter(Context context, List<SchoolInfo> data){
        this.context = context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Zujian{
        public ImageView imageView,imageView1;
        public TextView tv1;
        public TextView tv2;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if(convertView==null){
            zujian = new Zujian();
            convertView=layoutInflater.inflate(R.layout.list_item2,null);
            zujian.imageView=convertView.findViewById(R.id.imageView4);
            zujian.imageView1=convertView.findViewById(R.id.imageView6);
            zujian.tv1=convertView.findViewById(R.id.textView4);
            zujian.tv2=convertView.findViewById(R.id.textView5);
            convertView.setTag(zujian);
        }else {
            zujian=(Zujian)convertView.getTag();
        }

        zujian.tv1.setText(data.get(position).getUniversity());
        zujian.tv2.setText(data.get(position).getType());
        Picasso.with(context).load(data.get(position).getLogo()).fit().into(zujian.imageView);
        return convertView;
    }
}
