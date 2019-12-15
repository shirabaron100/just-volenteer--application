package com.example.myapplication.ui.create_events;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class create_eventsFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DATE_DIALOG_ID = 0;

    private DatePickerDialog mDatePickerDialog;
    private Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private create_eventsViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sendViewModel =
                ViewModelProviders.of(this).get(create_eventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_events, container, false);

        btnDatePicker = root.findViewById(R.id.btn_date);
        btnTimePicker = (Button) root.findViewById(R.id.btn_time);
        txtDate = (EditText) root.findViewById(R.id.in_date);
        txtTime = (EditText) root.findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                txtDate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

}