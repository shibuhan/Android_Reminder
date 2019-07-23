package com.example.charlotte.reminder.Fire7;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 *
 */
public class TaskAdapter extends RealmBaseAdapter<Task> {

    private static class ViewHolder {
        TextView date;
        TextView title;
    }

    public TaskAdapter(@Nullable OrderedRealmCollection<Task> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_2, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Task task = adapterData.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        String formatDate = sdf.format(task.getDate()) + " (残り日数: " + differenceDays(task.getDate(), today) + "日)";
        viewHolder.date.setText(formatDate);
        if(differenceDays(task.getDate(), today) == 0){
            viewHolder.date.setTextColor(0xffFF0000);
        } else if(differenceDays(task.getDate(), today) < 0) {
            viewHolder.date.setTextColor(0xffA0522D);
        } else {
            viewHolder.date.setTextColor(0xff000000);
        }
        viewHolder.title.setText(task.getTitle());
        return convertView;
    }

    /**
     * 2つの日付の差を求める
     * @param date1 日付 java.util.Date
     * @param date2 日付 java.util.Date
     * @return    2つの日付の差
     */
    private static int differenceDays(Date date1,Date date2) {
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 1000 * 60 * 60 * 24;
        if((datetime1 - datetime2) > 0){
            long diffDays = (datetime1 - datetime2) / one_date_time + 1;
            return (int)diffDays;
        }else{
            long diffDays = (datetime1 - datetime2) / one_date_time;
            return (int)diffDays;
        }
    }
}
