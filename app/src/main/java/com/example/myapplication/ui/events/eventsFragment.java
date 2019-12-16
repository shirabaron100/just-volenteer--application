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

public class eventsFragment extends Fragment {

    private eventsViewModel eventsViewModel;

    private DatabaseReference mRef;

    private ArrayList<String> data = new ArrayList<>();

    private ListView mListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(eventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);

        mListView = root.findViewById(R.id.event_list);

        mRef = FirebaseDatabase.getInstance().getReference();

        mListView.setAdapter(arrayAdapter);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String name = dataSnapshot.getValue(Event.class).getNameOfEvent();

                data.add(name);

                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        mRef.child("events").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//
//                    String date = ds.child("date").getValue(String.class);
//
//                    String location = ds.child("location").getValue(String.class);
//
//                    String moreInfo = ds.child("moreInfo").getValue(String.class);
//
//                    String name = ds.child("nameOfEvent").getValue(String.class);
//
//                    String time = ds.child("time").getValue(String.class);
//
//                    data.add(name);
//
//                    arrayAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        events.addValueEventListener(valueEventListener);


        return root;
    }
}