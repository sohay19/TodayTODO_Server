package com.bailey.dailytodolist_server.config;

import com.bailey.dailytodolist_server.service.PushService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
@NoArgsConstructor
@Slf4j
public class FirebaseConfig {
    private PushService pushService;


    @SneakyThrows //자동 try-catch
    @PostConstruct // 앱 실행 후 바로 시작하는 함수를 지칭한다.
    public void init() {
        ClassPathResource resource = new ClassPathResource("dailytodolist-57076-firebase-adminsdk-bbc2a-9637753538.json");
        InputStream serviceAccount = resource.getInputStream();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://dailytodolist-57076-default-rtdb.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (null == pushService) {
                    // ApplicationContextProvider : applicationContext 주입을 할 수 있도록 만든 클래스 config.ApplicationContextProvider.class
                    pushService = (PushService) ApplicationContextProvider.getApplicationContext().getBean("pushService");
                }
                Map<String, String> map = (Map)snapshot.getValue();
                Collection<String> values = map.values();
                pushService.setTokenList(new ArrayList<>(values));
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                Map<String, String> map = (Map)snapshot.getValue();
                Collection<String> values = map.values();
                pushService.setTokenList(new ArrayList<>(values));
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Map<String, String> map = (Map)snapshot.getValue();
                Collection<String> values = map.values();
                pushService.setTokenList(new ArrayList<>(values));
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
                Map<String, String> map = (Map)snapshot.getValue();
                Collection<String> values = map.values();
                pushService.setTokenList(new ArrayList<>(values));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
