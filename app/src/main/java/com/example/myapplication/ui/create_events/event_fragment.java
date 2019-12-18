package com.example.myapplication.ui.create_events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.google.firebase.database.FirebaseDatabase;

public class event_fragment extends AppCompatActivity {

    private EditText txtDate, txtTime, location, moreinfo, nameOfEvent ;

    Button go_back, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_show_more_info);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        System.out.println(event);

        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        nameOfEvent = (EditText) findViewById(R.id.nameOfEVENT);
        location = (EditText) findViewById(R.id.location_in);
        moreinfo = (EditText) findViewById(R.id.info_in);

        go_back = (Button) findViewById(R.id.event_go_back);
        register = (Button) findViewById(R.id.event_register);

//        txtDate.setText(event.getDate());
//        txtTime.setText(event.getTime());
//        nameOfEvent.setText(event.getNameOfEvent());
//        location.setText(event.getLocation());
//        moreinfo.setText(event.getMoreInfo());


        // GO BACK TO MAIN SCREEN
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // REGISTER TO THE EVENT
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void clear_form() {

        nameOfEvent.getText().clear();
        txtDate.getText().clear();
        txtTime.getText().clear();
        location.getText().clear();
        moreinfo.getText().clear();

    }
}
