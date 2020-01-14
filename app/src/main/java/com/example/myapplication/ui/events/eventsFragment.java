package com.example.myapplication.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.CustomAdapter;
import com.example.myapplication.FirebaseAdapter;
import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class eventsFragment extends Fragment {

    private eventsViewModel eventsViewModel;

    private FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

    private ArrayList<Event> data = new ArrayList<>();

    private CustomAdapter myAdapter;

    private List<Event> events;

    private ListView mListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(eventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        mListView = root.findViewById(R.id.event_list);

        myAdapter = new CustomAdapter(data, root.getContext());

        mListView.setAdapter(myAdapter);

        final List<Event> events = new ArrayList<>();

        // Get a reference to our posts
        DatabaseReference eventsRef = firebaseAdapter.getEventsRef();

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds : dsChild.getChildren()) {
                        Event event = ds.getValue(Event.class);
                        System.out.println(ds.getValue().toString());

                        //use simple date format to read string as a date
                        SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm");
                        try {
                            Date d = dateF.parse(event.getDate());
                            Date x = timeF.parse(event.getTime());

                            long s = System.currentTimeMillis();

                            if (d.getTime() < s && x.getTime() < s){
                                //remove event from event list !
                                System.out.println("event has expired !");
                            }
                            else {
                                events.add(event);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }

                gotEventsFromFireBase(events);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return root;
    }

    private void gotEventsFromFireBase(List<Event> events) {
        this.events = events;
        for(Event event : events) {

            String name = event.getNameOfEvent();
            String date = event.getDate();
            String info = event.getMoreInfo();
            String location = event.getLocation();
            String time = event.getTime();

            data.add(event);

            myAdapter.notifyDataSetChanged();

        }
    }
}