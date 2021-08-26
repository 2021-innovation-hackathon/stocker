package com.Hackathon;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<NewsItem> newsItems;

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        if(newsItems != null) {
            System.out.println(position);
            holder.onBind(newsItems.get(position));
        } else {
            Log.d("현재 newsItems가 null입니다.","");
        }
    }

    public void setFriendList(ArrayList<NewsItem> list){
        this.newsItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }

        void onBind(NewsItem item){
            // System.out.println("title " + item.getNewsTitle());
            // System.out.println("content " + item.getNewsContent());

            title.setText(item.getNewsTitle());
            content.setText(item.getNewsContent());
        }
    }
}