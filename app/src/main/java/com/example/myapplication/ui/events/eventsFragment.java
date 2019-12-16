package com.example.myapplication.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class eventsFragment extends Fragment {

    private eventsViewModel eventsViewModel;

    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private ArrayList<Event> data = new ArrayList<>();
    private ArrayList<String> data2 = new ArrayList<>();

    private ArrayAdapter<Event> eventAdapter;
    private ArrayAdapter<String> stringAdapter;

    private List<Event> events;

    private ListView mListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(eventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        mListView = root.findViewById(R.id.event_list);

        stringAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, data2);
        
        mListView.setAdapter(stringAdapter);

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("event");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Event> events = new ArrayList<>();
                for(DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    Event event = dsChild.getValue(Event.class);
                    events.add(event);
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
            System.out.println(event);

            String name = event.getNameOfEvent();
            String date = event.getDate();
            String info = event.getMoreInfo();
            String location = event.getLocation();
            String time = event.getTime();

            data.add(event);
            data2.add(name);

            stringAdapter.notifyDataSetChanged();

        }
    }
}