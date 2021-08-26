package com.Hackathon.ui.setting;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.Hackathon.Alarm;
import com.Hackathon.R;
import com.Hackathon.databinding.FragmentSettingBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class settingFragment extends Fragment {
    private Context context;

    private settingViewModel settingViewModel;
    private FragmentSettingBinding binding;

    //알람시간
    private Calendar calendar;

    private TimePicker timePicker;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.calendar = Calendar.getInstance();
        // 현재 날짜 표시
        displayDate();

        this.timePicker = (TimePicker) getActivity().findViewById(R.id.tp_timepicker);


        getActivity().findViewById(R.id.Calendar).setOnClickListener(mClickListener);
        getActivity().findViewById(R.id.AddAlarm).setOnClickListener(mClickListener);
    }

    private void displayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ((TextView) getActivity().findViewById(R.id.textView3)).setText(format.format(this.calendar.getTime()));
    }

    /* DatePickerDialog 호출 */
    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 알람 날짜 설정
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);

                // 날짜 표시
                displayDate();
            }
        }, this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH), this.calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }
    /* 알람 등록 */
    private void setAlarm() {
        // 알람 시간 설정
        this.calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getCurrentHour());
        this.calendar.set(Calendar.MINUTE, this.timePicker.getCurrentMinute());
        this.calendar.set(Calendar.SECOND, 0);

        // 현재일보다 이전이면 등록 실패
        if (this.calendar.before(Calendar.getInstance())) {
            Toast.makeText(getActivity(), "알람시간이 현재시간보다 이전일 수 없습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        // Receiver 설정
        Intent intent = new Intent(getActivity(), Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 알람 설정
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);

        // Toast 보여주기 (알람 시간 표시)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(getActivity(), "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Calendar:
                    // 달력
                    showDatePicker();

                    break;
                case R.id.AddAlarm:
                    // 알람 등록
                    setAlarm();

                    break;
            }
        }
    };






    private void setContentView(int activity_main) {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
       settingViewModel =
               new ViewModelProvider(this).get(settingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       final TextView textView = binding.textView;
        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
             textView.setText(s);
           }
       });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
