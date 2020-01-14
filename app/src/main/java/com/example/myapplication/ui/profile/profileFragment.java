package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.CustomAdapter;
import com.example.myapplication.FirebaseAdapter;
import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.example.myapplication.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {

    private profileViewModel homeViewModel;

    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseAdapter firebaseAdapter = new FirebaseAdapter();

    private TextView profile_email, profile_name;

    private ArrayList<Event> data = new ArrayList<>();

    private CustomAdapter myAdapter;

    private ListView mListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(profileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_email = root.findViewById(R.id.profile_email);
        profile_name = root.findViewById(R.id.profile_name);

        mListView = root.findViewById(R.id.profile_events);

        myAdapter = new CustomAdapter(data, root.getContext());

        mListView.setAdapter(myAdapter);

        // Get a reference to our posts
        final DatabaseReference ref = firebaseAdapter.getUsersRef();

        final FirebaseUser currentFirebaseUser = firebaseAdapter.getFirebaseUser();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);

                    if(user.getEmail().equals(currentFirebaseUser.getEmail())) {
                        System.out.println("success!");

                        profile_email.setText(user.getEmail());
                        profile_name.setText(user.getName());

                        if(ds.hasChild("myEvents")) {
                            for(DataSnapshot dsc : ds.child("myEvents").getChildren()) {
                                Event event = dsc.getValue(Event.class);
                                data.add(event);
                                myAdapter.setFlag(true);
                                myAdapter.notifyDataSetChanged();

                            }

                        }
                        else if(ds.hasChild("my Created Events")) {
                            for(DataSnapshot dsc : ds.child("my Created Events").getChildren()) {
                                Event event = dsc.getValue(Event.class);
                                data.add(event);
                                myAdapter.setFlag(true);
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
}