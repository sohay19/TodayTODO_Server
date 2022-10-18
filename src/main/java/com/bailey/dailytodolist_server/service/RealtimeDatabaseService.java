package com.bailey.dailytodolist_server.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabaseService {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    public void pushToken() {
        databaseReference.child("token").child("uuid").setValueAsync("tmpToken");
    }
}
