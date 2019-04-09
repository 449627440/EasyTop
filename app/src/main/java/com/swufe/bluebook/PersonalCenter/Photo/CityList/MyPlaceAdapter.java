package com.swufe.bluebook.PersonalCenter.Photo.CityList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.R;

import java.util.List;

public class MyPlaceAdapter extends ArrayAdapter<MyPlace> {
    private int resourceId;

    public MyPlaceAdapter(Context context, int textViewResourceId, List<MyPlace> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        MyPlace place = getItem(position); // 获取当前项的place实例
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.cityName=view.findViewById(R.id.cityname);
            viewHolder.pic =view.findViewById(R.id.place_pic);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.cityName.setText(place.getCityName());
        Picasso.with(getContext()).load(place.getPicUrl()).fit().into(viewHolder.pic);
        return view;

    }

    class ViewHolder {
        TextView cityName;
        ImageView pic;
    }
}

