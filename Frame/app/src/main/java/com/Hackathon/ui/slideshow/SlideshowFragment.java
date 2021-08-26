package com.Hackathon.ui.slideshow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.Hackathon.DiaryItem;
import com.Hackathon.DiaryItemDB;
import com.Hackathon.R;
import com.Hackathon.databinding.FragmentSlideshowBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

public class  SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    PieChart pieChart;
    List<DiaryItem> list = null;

    private void showPieChart(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            list = DiaryItemDB.getInstance(getContext()).diaryItemDao().getAll();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BiffException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();

        if(list != null) {
            System.out.println("null이 아님");

            for(DiaryItem d : list) {
                Integer i = typeAmountMap.get(d.stockName);
                if(i != null) {
                    typeAmountMap.replace(d.categoryName, d.price * d.quantity + i);
                } else {
                    typeAmountMap.put(d.categoryName, d.price * d.quantity);
                    colors.add(Color.parseColor(d.categoryColor));
                }
            }
        } else {
            System.out.println("왜 null");
        }

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pieChart = (PieChart)getActivity().findViewById(R.id.pieChart_view);
        showPieChart();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}