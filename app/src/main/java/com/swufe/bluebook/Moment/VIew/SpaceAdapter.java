package com.swufe.bluebook.Moment.VIew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.squareup.picasso.Picasso;
import com.swufe.bluebook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andream on 2017/10/19.
 * listView适配器
 */

public class SpaceAdapter extends BaseAdapter {
    private List<TaskCompletedEvent> orders;
    private Context context;

    public SpaceAdapter(Context context, List<TaskCompletedEvent> orders) {
        this.context = context;
        this.orders = orders;
    }

    public void addOrder(List<TaskCompletedEvent> orders) {
        this.orders = orders;

    }

    @Override
    public int getCount() {
        if (orders == null)
            return 0;
        else
            return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.space_list, null);
            holder = new ViewHolder();
            holder.city = (TextView) view.findViewById(R.id.spaceListCity);
            holder.time = (TextView) view.findViewById(R.id.spaceListTime);
            holder.say = (TextView) view.findViewById(R.id.spaceListSay);
            holder.nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
            holder.word=view.findViewById(R.id.word);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String mySay = orders.get(i).getDescription();
        if (mySay == null || mySay.length() <= 0) {
            holder.say.setVisibility(View.GONE);
        } else {
            holder.say.setVisibility(View.VISIBLE);
            holder.say.setText(mySay);
        }
        holder.city.setText(orders.get(i).getTask().getTitle());
        holder.word.setText(orders.get(i).getTask().getCityName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date date=format.parse(orders.get(i).getUpdatedAt());
            String date1=format1.format(date);
            holder.time.setText(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (orders.get(i).isHaveIcon()) {//判断是否有图片
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int j = 0; j < orders.get(i).getPicUrls().size(); j++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(orders.get(i).getPicUrls().get(j));
                info.setBigImageUrl(orders.get(i).getPicUrls().get(j));
                imageInfo.add(info);
            }
            holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
        } else {
            holder.nineGrid.setVisibility(View.GONE);
        }
        return view;
    }

    private class ViewHolder {
        private TextView city;
        private TextView time;
        private TextView say;
        private NineGridView nineGrid;
        private TextView word;
    }
}

