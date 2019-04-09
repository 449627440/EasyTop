package com.swufe.bluebook.NotePackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.swufe.bluebook.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Note> noteList;
    private LayoutInflater mInflater;
    private Context mContext;
    private int index;

    public MyAdapter(Context context,List<Note> noteList,ListView listView) {
        this.mInflater = LayoutInflater.from(context);
        this.noteList = noteList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return noteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.show_time);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.show_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTime.setText(noteList.get(i).getTime());
        viewHolder.mContent.setText(noteList.get(i).getContent());

        index = i;

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext,UpdateOrReadActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("note_item",noteList.get(index));
////                intent.putExtras(bundle);
//                intent.putExtra("note_id",noteList.get(index).getId());
//                Log.d("Anonymous","备忘录ID:"+noteList.get(index).getId());
//                mContext.startActivity(intent);
//                Log.d("Anonymous","执行了适配器里的点击事件");
//            }
//        });

        return convertView;

    }

    class ViewHolder{
        public TextView mTime;
        public TextView mContent;
    }
}
