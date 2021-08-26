package com.Hackathon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.Hackathon.CalendarAdapter;
import com.Hackathon.R;
import com.Hackathon.databinding.FragmentHomeBinding;
import com.Hackathon.OnSwipeTouchListener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener{

    protected FragmentHomeBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calendarRecyclerView = root.findViewById(R.id.calendarRecyclerView);
        monthYearText = root.findViewById(R.id.monthYearTV);
        selectedDate = LocalDate.now();
        setMonthView();
//        Button button = (Button)root.findViewById(R.id.TestButton);
//        button.setOnClickListener(view -> Toast.makeText(getContext(), "버튼 클릭!!!!!", Toast.LENGTH_SHORT).show());

        TextView prev = root.findViewById(R.id.tv_prev_month);
        prev.setOnClickListener(view ->{
            selectedDate = selectedDate.minusMonths(1);
            setMonthView();
        });

        TextView next = root.findViewById(R.id.tv_next_month);
        next.setOnClickListener(view ->{
            selectedDate = selectedDate.plusMonths(1);
            setMonthView();
        });

// 화면 스와이프시 달별 전환 시도중
//        calendarRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
//            public void onSwipeRight() {
//                selectedDate = selectedDate.minusMonths(1);
//                setMonthView();
//            }
//            public void onSwipeLeft() {
//                selectedDate = selectedDate.plusMonths(1);
//                setMonthView();
//            }
//        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setMonthView()
    {
        View root = binding.getRoot();
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            Intent intent = new Intent(getContext(), Home_addMemo.class);

            intent.putExtra("DATE",dayText);
            intent.putExtra("selected",monthYearFromDate(selectedDate));
            startActivity(intent);
        }
    }
}