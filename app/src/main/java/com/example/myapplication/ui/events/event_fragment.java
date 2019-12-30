package com.example.myapplication.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignUp;
import com.example.myapplication.models.Event;
import com.example.myapplication.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class event_fragment extends AppCompatActivity {

    private EditText txtDate, txtTime, location, moreinfo, nameOfEvent ;

    Button go_back, register, cancel;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_show_more_info);

        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("event");
        String name = (String) intent.getStringExtra("name");

        System.out.println(name);

        txtDate = (EditText) findViewById(R.id.event_date);
        txtTime = (EditText) findViewById(R.id.event_time);
        nameOfEvent = (EditText) findViewById(R.id.event_name);
        location = (EditText) findViewById(R.id.event_location);
        moreinfo = (EditText) findViewById(R.id.event_info);

        go_back = (Button) findViewById(R.id.event_go_back);
        register = (Button) findViewById(R.id.event_register);
        cancel = (Button) findViewById(R.id.event_cancel_register);

        txtDate.setText(event.getDate());
        txtTime.setText(event.getTime());
        nameOfEvent.setText(event.getNameOfEvent());
        location.setText(event.getLocation());
        moreinfo.setText(event.getMoreInfo());


        // Get a reference to our posts
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("event").child(event.getKey());

        //run for the first time to check if the user is already registered
        //to the event so we can switch to the correct button.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.child("Participants").getChildren()){

                    if(ds.getValue().toString().equals(auth.getCurrentUser().getEmail()))   {
                        register.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

                //register the user to the event
                String uid = auth.getCurrentUser().getUid();
                ref.child("Participants").child(uid).setValue(auth.getCurrentUser().getEmail());

                Toast.makeText(getApplicationContext(), "הרשמה בוצעה בהצלחה", Toast.LENGTH_LONG).show();

                //register the event to the users profile
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref = database.getReference("users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String c_mail = auth.getCurrentUser().getEmail();
                            User user = (User) ds.getValue(User.class);
                            System.out.println(ds.getValue());
                            System.out.println(user.getEmail());
                            if(c_mail.equals(user.getEmail())) {
                                System.out.println("wow.. ");
                                ref.child(user.getKey()).child("myEvents").child(event.getKey()).setValue(event);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                register.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.VISIBLE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get a reference to our posts
                final FirebaseAuth auth = FirebaseAuth.getInstance();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref = database.getReference("event").child(event.getKey());

//                        registerToEvent();
                String uid = auth.getCurrentUser().getUid();
                ref.child("Participants").child(uid).removeValue();

                Toast.makeText(getApplicationContext(), "ביטול הרשמה מהאירוע בוצע בהצלחה!", Toast.LENGTH_LONG).show();

                register.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void registerToEvent()  {



    }

    private void clear_form() {

        nameOfEvent.getText().clear();
        txtDate.getText().clear();
        txtTime.getText().clear();
        location.getText().clear();
        moreinfo.getText().clear();

    }
}
