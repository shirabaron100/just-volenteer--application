package com.example.myapplication.ui.create_events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.TextUtilsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class create_eventsFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DATE_DIALOG_ID = 0;
    DatabaseReference DbEvent;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    private Button btnDatePicker, btnTimePicker,saveEvent;
    private EditText txtDate, txtTime, location,moreinfo, nameOfEvent ;


    private create_eventsViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DbEvent= FirebaseDatabase.getInstance().getReference("event");
        sendViewModel =
                ViewModelProviders.of(this).get(create_eventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_events, container, false);

        saveEvent=root.findViewById(R.id.save_event);
        btnDatePicker = root.findViewById(R.id.btn_date);
        btnTimePicker = (Button) root.findViewById(R.id.btn_time);
        txtDate = (EditText) root.findViewById(R.id.in_date);
        txtTime = (EditText) root.findViewById(R.id.in_time);
        nameOfEvent= (EditText) root.findViewById(R.id.nameOfEVENT);
        location = (EditText) root.findViewById(R.id.location_in);
        moreinfo= (EditText) root.findViewById(R.id.info_in);


        location = (EditText) root.findViewById(R.id.location_in);

        setDateTimeField();

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addevent ();
                clear_form();
                Toast.makeText(getContext(), "אירוע נוצר בהצלחה!", Toast.LENGTH_SHORT).show();
            }
        });


        //dialog date
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        setmTimePicker();

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.setTitle("Select Time");
                mTimePickerDialog.show();
            }
        });


//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Uri gm = Uri.parse("geo:0,0?q=");
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gm);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        startActivity(mapIntent);
//                    }
//                }, 1000);
//            }
//        });

        return root;
    }

    private void clear_form() {

        nameOfEvent.getText().clear();
        txtDate.getText().clear();
        txtTime.getText().clear();
        location.getText().clear();
        moreinfo.getText().clear();

    }

    private void addevent () {
        String NameOfEvent = nameOfEvent.getText().toString();
        String Date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        String Location = location.getText().toString();
        String MoreInfo = moreinfo.getText().toString();

        if (!TextUtils.isEmpty(NameOfEvent)) {
            String id=DbEvent.push().getKey();
            Event event = new Event(NameOfEvent, Date, time, Location, MoreInfo);
            event.setKey(id);
            DbEvent.child(id).setValue(event);
        }

    }
    private void setmTimePicker() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);


        mTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                txtTime.setText(hourOfDay+ ":" + minute);

            }
        },hour, minute, true);

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
                mDatePickerDialog.getDatePicker().setMinDate(startDate.getTime()-(startDate.getTime()%(24*60*60*1000)));

                txtDate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

}