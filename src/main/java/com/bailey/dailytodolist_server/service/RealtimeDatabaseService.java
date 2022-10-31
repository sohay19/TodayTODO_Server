package com.bailey.dailytodolist_server.service;

import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeDatabaseService {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    public void pushToken(Map<String, String> data) {
        data.entrySet().forEach( stringStringEntry -> {
            databaseReference.child("token").child(stringStringEntry.getKey()).setValueAsync(stringStringEntry.getValue());
        });
    }
}
