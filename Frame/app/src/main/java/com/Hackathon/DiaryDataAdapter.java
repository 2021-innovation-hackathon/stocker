package com.Hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiaryDataAdapter extends BaseAdapter {

    Context context = null;
    LayoutInflater mLayoutInflater = null;
    List<DiaryItem> sample;

    public DiaryDataAdapter(Context context, List<DiaryItem> data) {
        context = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public DiaryItem getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.dailynote, null);

        TextView stockName = (TextView)view.findViewById(R.id.stockName);
        TextView stockPrice = (TextView)view.findViewById(R.id.stockprice);
        TextView stockQuantity = (TextView)view.findViewById(R.id.stockquantity);

        stockName.setText(sample.get(position).stockName);
        stockPrice.setText(Integer.toString(sample.get(position).price));
        stockQuantity.setText(Integer.toString(sample.get(position).quantity));

        return view;
    }
}