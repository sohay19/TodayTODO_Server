package com.bailey.dailytodolist_server.service;

import com.google.firebase.messaging.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Getter
@Setter
public class PushService {
    private final RealtimeDatabaseService realtimeDBService;
//    private List<String> tokenList = new ArrayList<>();

    //SilentPush 스케줄링 12시간마다
//    @Scheduled(cron = "0 0 0/12 * * *")
//    public void sendSilentPush() {
//        silentPush();
//    }

    //SilentPush
    private void silentPush() {
        ArrayList<String> tokenList = realtimeDBService.loadToken();

        ApnsConfig config = ApnsConfig.builder()
                .setAps(Aps.builder().setContentAvailable(true).build())
                .build();

        MulticastMessage messages = MulticastMessage.builder()
                .setApnsConfig(config)
                .addAllTokens(tokenList)
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance()
                    .sendMulticast(messages);

            log.info("success message count: {}", response.getSuccessCount());
            response.getResponses().forEach(sendResponse -> {
                log.info(sendResponse.getMessageId());
            });
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    //전체 푸쉬
    public void defaultPush(Map<String, String> data) {
        ArrayList<String> tokenList = realtimeDBService.loadToken();

        String title = data.get("title");
        String body = data.get("body");

        ApnsConfig config = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setSound("default")
                        .setCategory("DailyTODO")
                        .build())
                .build();

        MulticastMessage messages = MulticastMessage.builder()
                .setApnsConfig(config)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .addAllTokens(tokenList)
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance()
                    .sendMulticast(messages);

            log.info("success message count: {}", response.getSuccessCount());
            response.getResponses().forEach(sendResponse -> {
                log.info(sendResponse.getMessageId());
            });
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
