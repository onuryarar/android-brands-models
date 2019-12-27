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

public class CustomModelAdapter extends BaseAdapter {

    private Context context;
    private int layoutType;
    private ArrayList<ModelClass> modelList;

    public CustomModelAdapter(Context context, int layoutType, ArrayList<ModelClass> modelList) {
        this.context = context;
        this.layoutType = layoutType;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return modelList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(layoutType, null);
        ImageView image = convertView.findViewById(R.id.ml_imageView);
        TextView modelName = convertView.findViewById(R.id.ml_textView);
        TextView details = convertView.findViewById(R.id.ml_textView2);

        modelName.setText(modelList.get(position).getModel());
        details.setText(modelList.get(position).getDetails());
        Picasso.get().load(modelList.get(position).getImage()).into(image);
        return convertView;
    }
}
