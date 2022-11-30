package com.bailey.dailytodolist_server.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeDatabaseService {
    private final Firestore database = FirestoreClient.getFirestore();


    /* TOKEN */
    //토큰 리스트 조회
    @SneakyThrows
    public ArrayList<String> loadToken() {
        DocumentReference docRef = database.collection("User").document("token");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();
        if (snapshot.exists()) {
            Map<String, String> map = snapshot.getData().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (String)e.getValue()));
            return new ArrayList<String>(map.values());
        } else {
            return new ArrayList<String>();
        }
    }
    //토큰 저장
    public void pushToken(String uuid, String token) {
        DocumentReference docRef = database.collection("User").document("token");
        Map<String, String> data = new HashMap<>();
        data.put(uuid, token);
        docRef.set(data);
    }


    /* NOTICE */
    @SneakyThrows
    public Map<String, Map<String, String>> loadNotice() {
        DocumentReference docRef = database.collection("Setting").document("Notice");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();
        if (snapshot.exists()) {
            Map<String, Map<String, String>> newMap = new HashMap<>();
            Map<String,Object> map = snapshot.getData();
            for (String key : map.keySet()) {
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> inMap = oMapper.convertValue(map.get(key), Map.class);
                newMap.put(key, inMap);
            }
            return newMap;
        } else {
            return new HashMap<>();
        }
    }
    @SneakyThrows
    public void pushNotice(String title, String body) {
        DocumentReference docRef = database.collection("Setting").document("Notice");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();
        int index = snapshot.getData().size();
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("body", body);
        Map<String, Map<String, String>> data = new HashMap<>();
        data.put(String.valueOf(index), map);
        docRef.set(data);
    }

    /* FAQ */
    @SneakyThrows
    public Map<String, Map<String, String>> loadFAQ() {
        DocumentReference docRef = database.collection("Setting").document("FAQ");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();
        if (snapshot.exists()) {
            Map<String, Map<String, String>> newMap = new HashMap<>();
            Map<String,Object> map = snapshot.getData();
            for (String key : map.keySet()) {
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> inMap = oMapper.convertValue(map.get(key), Map.class);
                newMap.put(key, inMap);
            }
            return newMap;
        } else {
            return new HashMap<>();
        }
    }
    @SneakyThrows
    public void pushFAQ(String title, String body) {
        DocumentReference docRef = database.collection("Setting").document("FAQ");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot snapshot = future.get();
        int index = snapshot.getData().size();
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("body", body);
        Map<String, Map<String, String>> data = new HashMap<>();
        data.put(String.valueOf(index), map);
        docRef.set(data);
    }
}
