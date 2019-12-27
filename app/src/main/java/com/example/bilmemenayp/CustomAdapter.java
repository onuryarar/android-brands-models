package com.example.bilmemenayp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int layoutType;
    private ArrayList<BrandClass> brandList;

    public CustomAdapter(Context context, int layoutType, ArrayList<BrandClass> brandList) {
        this.context = context;
        this.layoutType = layoutType;
        this.brandList = brandList;
    }

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int position) {
        return brandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return brandList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(layoutType, null);
        ImageView logo = convertView.findViewById(R.id.imageView2);
        TextView brandName = convertView.findViewById(R.id.bl_textView);

        brandName.setText(brandList.get(position).getBrandName());
        Picasso.get().load(brandList.get(position).getLogo()).into(logo);
        return convertView;
    }
}
