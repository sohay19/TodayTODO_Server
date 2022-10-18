package com.bailey.dailytodolist_server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Configuration
@NoArgsConstructor
public class FirebaseConfig {
    //try catch 생략
    @SneakyThrows
    // 앱 실행 후 바로 시작하는 함수를 지칭한다.
    @PostConstruct
    public void init() {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/dailytodolist-57076-firebase-adminsdk-bbc2a-9637753538.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://dailytodolist-57076-default-rtdb.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object data = snapshot.getValue();
                System.out.println(data);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error);
            }
        });
    }
}
