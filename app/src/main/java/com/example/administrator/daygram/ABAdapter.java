package com.example.administrator.daygram;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/25.
 */
public class ABAdapter extends BaseAdapter {
    //itemA类的type标志
    private static final int TYPE_A = 0;
    //itemB类的type标志
    private static final int TYPE_B = 1;

    private Context context;

    //整合数据
//    private List<Object> data = new ArrayList<>();
    private ArrayList<Object>data;


    public ABAdapter(Context context, ArrayList<Object> as) {
        this.context = context;

        //把数据装载同一个list里面
        //这里把所有数据都转为object类型是为了装载同一个list里面好进行排序
        data=as;

    }

    /**
     * 获得itemView的type
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position) instanceof Class_A) {
            result = TYPE_A;
        } else if (data.get(position) instanceof Class_Parent) {
            result = TYPE_B;
        }
        return result;
    }

    /**
     * 获得有多少中view type
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
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
        //创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if (convertView == null) {
            //实例化
            holder1 = new ViewHolder1();
            holder2 = new ViewHolder2();
            //根据不同的type 来inflate不同的item layout
            //然后设置不同的tag
            //这里的tag设置是用的资源ID作为Key
            switch (type) {
                case TYPE_A:
                    convertView = View.inflate(context, R.layout.item_a, null);
                    holder1.week = (TextView) convertView.findViewById(R.id.week);
                    holder1.day = (TextView) convertView.findViewById(R.id.day);
                    holder1.daily = (TextView) convertView.findViewById(R.id.daily);
                    convertView.setTag(R.id.tag_first, holder1);
                    break;
                case TYPE_B:
                    convertView = View.inflate(context, R.layout.item_b, null);
//                    holder2.content = (TextView) convertView.findViewById(R.id.item_list_for_b_content);
//                    holder2.time = (TextView) convertView.findViewById(R.id.item_list_for_b_time);
//                    holder2.img = (ImageView) convertView.findViewById(R.id.item_list_for_b_img);
                    convertView.setTag(R.id.tag_second, holder2);
                    break;
            }

        } else {
            //根据不同的type来获得tag
            switch (type) {
                case TYPE_A:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_first);
                    break;
                case TYPE_B:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.tag_second);
                    break;
            }
        }

        Object o = data.get(position);
        //根据不同的type设置数据
        switch (type) {
            case TYPE_A:
                Class_A a = (Class_A) o;
                holder1.week.setText(a.getWeekAsString(context));
                holder1.day.setText(String.valueOf(a.getDate(Class_Parent.DAY)));
                if(a.getDate(Class_Parent.WEEK)==1)
                    holder1.day.setTextColor(0xffff0000);
                else
                    holder1.day.setTextColor(0xff000000);
                holder1.daily.setText(a.getDailyContent_Pre());
                break;

            case TYPE_B:
                Class_Parent b = (Class_Parent) o;
             /*   holder2.content.setText("BContent:" + b.getContent());
                holder2.time.setText("BTime:" + b.getTime());
                holder2.img.setImageResource(b.getImgResourceID());*/
                break;
        }
        return convertView;
    }

/*
    public class MyComparator implements Comparator {

        public int compare(Object arg0, Object arg1) {
            //根据不同的情况来进行排序

            if (arg0 instanceof A && arg1 instanceof B) {

                A a = (A) arg0;
                B b = (B) arg1;
                return Integer.valueOf(a.getTime()).compareTo(b.getTime());

            } else if (arg0 instanceof B && arg1 instanceof A) {
                B b = (B) arg0;
                A a = (A) arg1;
                return Integer.valueOf(b.getTime()).compareTo(Integer.valueOf(a.getTime()));

            } else if (arg0 instanceof A && arg1 instanceof A) {

                A a0 = (A) arg0;
                A a1 = (A) arg1;
                return Integer.valueOf(a0.getTime()).compareTo(Integer.valueOf(a1.getTime()));

            } else {
                B b0 = (B) arg0;
                B b1 = (B) arg1;
                return Integer.valueOf(b0.getTime()).compareTo(Integer.valueOf(b1.getTime()));
            }
        }
    }*/

    /**
     * item A 的Viewholder
     */
    private static class ViewHolder1 {
        TextView week;
        TextView day;
        TextView daily;

    }

    /**
     * item B 的Viewholder
     */
    private static class ViewHolder2 {
        TextView week;
    }

}
