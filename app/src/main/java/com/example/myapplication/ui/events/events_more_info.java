package com.example.myapplication.ui.events;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.FirebaseAdapter;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class events_more_info extends AppCompatActivity {

    private EditText txtDate, txtTime, location, moreinfo, nameOfEvent ;

    private FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

    Button go_back, register, cancel, share;
    private Boolean isAdmin = false;
    private Event event;
    private User myUser;

    private String sharePath="no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_show_more_info);

        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("event");
        String name = (String) intent.getStringExtra("name");
        final Boolean flag = intent.getBooleanExtra("profile", false);


        txtDate = (EditText) findViewById(R.id.event_date);
        txtTime = (EditText) findViewById(R.id.event_time);
        nameOfEvent = (EditText) findViewById(R.id.event_name);
        location = (EditText) findViewById(R.id.event_location);
        moreinfo = (EditText) findViewById(R.id.event_info);

        go_back = (Button) findViewById(R.id.event_go_back);
        register = (Button) findViewById(R.id.event_register);
        cancel = (Button) findViewById(R.id.event_cancel_register);
        share = (Button) findViewById(R.id.event_share);

        txtDate.setText(event.getDate());
        txtTime.setText(event.getTime());
        nameOfEvent.setText(event.getNameOfEvent());
        location.setText(event.getLocation());
        moreinfo.setText(event.getMoreInfo());

        enable_info_edit(false);

        // Get a reference to our posts
        final FirebaseAuth auth = firebaseAdapter.getAuthInstance();
        final DatabaseReference eventRef = firebaseAdapter.getEventsRef();
        final DatabaseReference userRef = firebaseAdapter.getUsersRef();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);

                    if(user.getEmail().equals(auth.getCurrentUser().getEmail()))   {
                        if (user.getAmota()) {
//                            System.out.println("true");
//                            enable_info_edit(true);
                            isAdmin = true;

                            register.setVisibility(View.INVISIBLE);
                            cancel.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    for(DataSnapshot dsChild : ds.getChildren()) {

                        if (dsChild.hasChild("Participants")) {
                            for(DataSnapshot child : dsChild.child("Participants").getChildren()) {

                                if (child.getKey().toString().equals(auth.getCurrentUser().getUid())) {
                                    register.setVisibility(View.INVISIBLE);
                                    cancel.setVisibility(View.VISIBLE);
                                }
                            }
                        }
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
//                if(isAdmin) {
//                    String NameOfEvent = nameOfEvent.getText().toString();
//                    String Date = txtDate.getText().toString();
//                    String time = txtTime.getText().toString();
//                    String Location = location.getText().toString();
//                    String MoreInfo = moreinfo.getText().toString();
//
//                    Event newEvent = new Event(NameOfEvent, Date, time, Location, MoreInfo);
//                    newEvent.setKey(event.getKey());
//
//                    eventRef.setValue(newEvent);
//
//                }
                if(!flag) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        // REGISTER TO THE EVENT
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "הרשמה בוצעה בהצלחה", Toast.LENGTH_LONG).show();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String uid = auth.getCurrentUser().getUid();

                            User user = (User) ds.getValue(User.class);

                            if(uid.equals(ds.getKey().toString())) {
                                myUser = user;
                                userRef.child(auth.getCurrentUser().getUid()).child("myEvents").child(event.getKey()).setValue(event);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String eventID = ds.getKey().toString();
                            for(DataSnapshot dsChild : ds.getChildren()) {

                                if (dsChild.getKey().toString().equals(event.getKey())) {
                                    eventRef.child(eventID)
                                            .child(event.getKey())
                                            .child("Participants")
                                            .child(auth.getCurrentUser().getUid())
                                            .setValue(myUser);
                                }
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
                Toast.makeText(getApplicationContext(), "ביטול הרשמה מהאירוע בוצע בהצלחה!", Toast.LENGTH_LONG).show();

                firebaseAdapter.RemoveUserFromEvent(event.getKey(), firebaseAdapter);

                register.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap ss = screenShot(getWindow().getDecorView().findViewById(android.R.id.content).getRootView());
                File file = saveBitmap(ss, "ss.jpeg");

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);

                Uri pictureUri = FileProvider.getUriForFile(events_more_info.this, getApplicationContext().getPackageName() + ".provider", file);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);

                Intent chooser = Intent.createChooser(shareIntent, "Share File");

                List<ResolveInfo> resInfoList = events_more_info.this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    events_more_info.this.grantUriPermission(packageName, pictureUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                startActivity(chooser);

            }
        });

    }

    private void enable_info_edit(Boolean flag) {

        nameOfEvent.setClickable(flag);
        nameOfEvent.setFocusable(flag);

        txtDate.setClickable(flag);
        txtDate.setFocusable(flag);

        txtTime.setClickable(flag);
        txtTime.setFocusable(flag);

        location.setClickable(flag);
        location.setFocusable(flag);

        moreinfo.setClickable(flag);
        moreinfo.setFocusable(flag);
    }

    private void clear_form() {

        nameOfEvent.getText().clear();
        txtDate.getText().clear();
        txtTime.getText().clear();
        location.getText().clear();
        moreinfo.getText().clear();

    }

    private Bitmap screenShot(View view) {

        View rootView = view.findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();

    }

    private File saveBitmap(Bitmap bm, String fileName){
        final String path = events_more_info.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/Screenshots";
        File dir = new File(path);
        FileOutputStream fOut;
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);

        try {

            fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}


