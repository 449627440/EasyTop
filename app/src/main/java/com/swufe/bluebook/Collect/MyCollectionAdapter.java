package com.swufe.bluebook.Collect;

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

public class MyCollectionAdapter extends ArrayAdapter<MyCollection> {
    private int resourceId;

    public MyCollectionAdapter(Context context, int textViewResourceId, List<MyCollection> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        MyCollection collection = getItem(position); // 获取当前项的place实例
        ViewHolder viewHolder;
        View view;
        //通过图片的URL下载图片，并将其转换成int R.id...
        //int imageId=Integer.valueOf(collection.getPic_colletion());
        //int imageId=R.drawable.city_icon;
        if (convertView == null) {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.pic_colletion=view.findViewById(R.id.pic_collection);
            viewHolder.title_collection =view.findViewById(R.id.title_collection);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        //viewHolder.pic_colletion.setImageResource(imageId);
        Picasso.with(getContext()).load(collection.getPic_colletion()).into(viewHolder.pic_colletion);
        viewHolder.title_collection.setText(collection.getTitle_collection());
        return view;

    }

    class ViewHolder {
        TextView title_collection;
        ImageView pic_colletion;
    }
}
