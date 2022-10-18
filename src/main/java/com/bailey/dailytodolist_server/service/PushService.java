package com.bailey.dailytodolist_server.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PushService {
    public void silentPush() {
        ApnsConfig config = ApnsConfig.builder()
                .setAps(Aps.builder().setContentAvailable(true).build())
                .build();

        Message message = Message.builder()
                .setApnsConfig(config)
                .setTopic("ALL_USER")
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance()
                    .sendAll(List.of(message));

            System.out.println("success message count: " + response.getSuccessCount());
            response.getResponses().forEach(sendResponse -> {
                System.out.println(sendResponse.getMessageId());
            });
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void defaultPush() {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("알림")
                        .setBody("메세지")
                        .build())
                .putData("title","제목")
                .putData("memo","메모")
                .setTopic("ALL_USER")
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance()
                    .sendAll(List.of(message));

            System.out.println("success message count: " + response.getSuccessCount());
            System.out.println("failed message count: " + response.getFailureCount());
            response.getResponses().forEach(sendResponse -> {
                System.out.println(sendResponse.getMessageId());
            });

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
