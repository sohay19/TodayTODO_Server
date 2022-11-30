package com.bailey.dailytodolist_server.controller;


import com.bailey.dailytodolist_server.domain.dto.Response;
import com.bailey.dailytodolist_server.service.PushService;
import com.bailey.dailytodolist_server.service.RealtimeDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // 필수 매개변수가 있는 생성자를 만들어준다.
public class DailyToDoController {
    private final PushService pushService;
    private final RealtimeDatabaseService realtimeDBService;

    // REST API
    // GET : 조회
    // POST : 등록/업데이트 (전체)
    // PUT : 업데이트 (단일)
    // DELETE : 삭제

    @GetMapping("/")
    public Response healthCheck() {
        return new Response(true);
    }

    /* PUSH */
    @PostMapping("/push/send")
    public Response sendPush(@RequestBody Map<String, String> data) {
        pushService.defaultPush(data);

        return new Response(true);
    }

    /* DB */
    //토큰 등록을 위해 호출
    @PostMapping("/token/send")
    public Response pushToken(@RequestBody Map<String, String> data) {
        String uuid = data.get("uuid");
        String token = data.get("token");

        realtimeDBService.pushToken(uuid, token);

        return new Response(true);
    }
    //Notice
    @GetMapping("/notice")
    public Response getNotice() {
        Map<String, Map<String, String>> noticeList = realtimeDBService.loadNotice();

        return new Response(true, noticeList, "notice");
    }
    @PostMapping("/notice/send")
    public Response pushNotice(@RequestBody Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");

        realtimeDBService.pushNotice(title, body);

        return new Response(true);
    }
    //FAQ
    @GetMapping("/faq")
    public Response getFAQ() {
        Map<String, Map<String, String>> faqList = realtimeDBService.loadFAQ();

        return new Response(true, faqList, "faq");
    }
    @PostMapping("/faq/send")
    public Response pushFAQ(@RequestBody Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");

        realtimeDBService.pushFAQ(title, body);

        return new Response(true);
    }
}
