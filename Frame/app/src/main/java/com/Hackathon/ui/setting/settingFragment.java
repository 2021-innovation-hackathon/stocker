package com.Hackathon.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.Hackathon.R;
import com.Hackathon.ThemeUtil;
import com.Hackathon.databinding.FragmentSettingBinding;

public class settingFragment extends Fragment {

    private settingViewModel settingViewModel;
    private FragmentSettingBinding binding;

    private Switch mySwitch;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            // getActivity().setTheme(R.style.Theme_AppCompat_DayNight);
            ThemeUtil.applyTheme(getContext(), 1);
        } else {
            // getActivity().setTheme(R.style.Theme_AppCompat);
            ThemeUtil.applyTheme(getContext(), 0);
        }

        mySwitch = view.findViewById(R.id.swtNightMode);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            mySwitch.setChecked(true);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ThemeUtil.applyTheme(getContext(), 1);
                    //Objects.requireNonNull(getActivity()).recreate();
                    //getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SettingsFragment()).commit(); //<== both these two lines not working

                } else {
                    // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ThemeUtil.applyTheme(getContext(), 0);
                }
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(settingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSetting;
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