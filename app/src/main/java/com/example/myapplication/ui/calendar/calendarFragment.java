package com.example.myapplication.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class calendarFragment extends Fragment {

    private calendarViewModel slideshowViewModel;

    private CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(calendarViewModel.class);

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);


        calendarView = root.findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                System.out.println("test");
                calendarView.setDate(System.currentTimeMillis());
            }
        });


        return root;
    }
}