package com.Hackathon;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Hackathon.ui.home.DateSingletonParse;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        String i = daysOfMonth.get(position);
        holder.dayOfMonth.setText(i);
        if (i != "") {
            LocalDate today = LocalDate.of(DateSingletonParse.yearValue, DateSingletonParse.monthValue, Integer.valueOf(i));
            if (today.getDayOfWeek().getValue() == 6) {
                holder.dayOfMonth.setTextColor(Color.parseColor("#1e73be"));
            } else if (today.getDayOfWeek().getValue() == 7) {
                holder.dayOfMonth.setTextColor(Color.parseColor("#dd3333"));
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}
