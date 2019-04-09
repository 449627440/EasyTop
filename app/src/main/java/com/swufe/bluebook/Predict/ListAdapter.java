package com.swufe.bluebook.Predict;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.ContentActivity;
import com.swufe.bluebook.ListViewAdapter;
import com.swufe.bluebook.R;
import com.swufe.bluebook.TabPagers.SchoolInfo;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<SchoolInfo> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private static String TAG = "ListAdapter";


    public ListAdapter(Context context, List<SchoolInfo> data){
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
            zujian = new ListAdapter.Zujian();
            convertView=layoutInflater.inflate(R.layout.list_item2,null);
            zujian.imageView=convertView.findViewById(R.id.imageView4);
            zujian.imageView1=convertView.findViewById(R.id.imageView6);
            zujian.tv1=convertView.findViewById(R.id.textView4);
            zujian.tv2=convertView.findViewById(R.id.textView5);
            convertView.setTag(zujian);
        }else {
            zujian=(Zujian)convertView.getTag();
        }
        Log.i(TAG, "getView: "+data.get(position).getLogo());
        Picasso.with(context).load(data.get(position).getLogo()).fit().into(zujian.imageView);
        Log.i(TAG, "getView: "+data.get(position).getLogo());
        zujian.tv1.setText(data.get(position).getUniversity());
        Log.i(TAG, "getView: "+data.get(position).getUniversity());
        zujian.tv2.setText(data.get(position).getType());
        Log.i(TAG, "getView: "+data.get(position).getType());

        return convertView;
    }
}
