package com.example.kasayonetimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DenemeAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<DenemeModel> denemeModelList;


    public DenemeAdapter(Context context, List<DenemeModel> denemeModelList) {
        layoutInflater = LayoutInflater.from(context);
        this.denemeModelList = denemeModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return denemeModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return denemeModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View lineView;
        lineView = layoutInflater.inflate(R.layout.custom_deneme_list, null);
        TextView text1 = lineView.findViewById(R.id.textView2);
        TextView text2 = lineView.findViewById(R.id.textView3);
        ImageView imageView = lineView.findViewById(R.id.imageView);

        DenemeModel denemeModel = denemeModelList.get(i);
        text1.setText(denemeModel.getDen1());
        text2.setText(denemeModel.getDen2());
        imageView.setImageResource(R.drawable.search);
        return lineView;
    }
}
