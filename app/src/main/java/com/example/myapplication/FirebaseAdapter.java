package com.example.myapplication;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAdapter {

    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private DatabaseReference eventsRef;
    private FirebaseAuth authInstance;
    private FirebaseUser firebaseUser;

    public FirebaseAdapter() {
        this.database = FirebaseDatabase.getInstance();
        this.usersRef = this.database.getReference("Users");
        this.eventsRef = this.database.getReference("Events");
        this.authInstance = FirebaseAuth.getInstance();
        this.firebaseUser = this.authInstance.getCurrentUser();
    }

    public FirebaseAuth getAuthInstance() {
        return authInstance;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }

    public DatabaseReference getEventsRef() {
        return eventsRef;
    }

    public void RemoveUserFromEvent(final String key, FirebaseAdapter adapter) {

        final DatabaseReference ref = adapter.getEventsRef();
        final FirebaseAuth auth = adapter.getAuthInstance();
        final DatabaseReference uRef = adapter.getUsersRef();
        final String key_ = key;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String eventID = ds.getKey().toString();
                    for(DataSnapshot dsChild : ds.getChildren()) {

                        if (dsChild.getKey().toString().equals(key_)) {
                            ref.child(eventID)
                                    .child(key_)
                                    .child("Participants")
                                    .child(auth.getCurrentUser().getUid())
                                    .removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        uRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    if(ds.getKey().toString().equals(auth.getCurrentUser().getUid())) {
                        uRef.child(auth.getCurrentUser().getUid()).child("myEvents").child(key_).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
