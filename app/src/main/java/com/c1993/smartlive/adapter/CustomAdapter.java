package com.c1993.smartlive.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.c1993.smartlive.R;
import com.c1993.smartlive.model.Custom;

import java.util.List;

/**
 * Created by c1993 on 2016/5/7.
 */
public class CustomAdapter extends ArrayAdapter<Custom> {
    private int resourceId;
    public CustomAdapter(Context context, int textViewResourceId, List<Custom> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Custom custom=getItem(position);//获取当前习惯实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView customName=(TextView)view.findViewById(R.id.custom_name);
        TextView customTime=(TextView)view.findViewById(R.id.custom_time);
        customName.setText(custom.getC_name());
        customTime.setText(String.valueOf(custom.getC_time()));


        return view;
    }
}
