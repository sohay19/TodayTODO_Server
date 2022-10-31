package com.bailey.dailytodolist_server.controller;


import com.bailey.dailytodolist_server.domain.dto.Response;
import com.bailey.dailytodolist_server.service.PushService;
import com.bailey.dailytodolist_server.service.RealtimeDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    //push test
    @GetMapping("/push/send")
    public Response sendPush() {
        pushService.defaultPush();

        return new Response(true, "", "");
    }

    //토큰 등록을 위해 호출
    @PostMapping("/token/send")
    public Response pushToken(@RequestBody Map<String, String> data) {
        realtimeDBService.pushToken(data);

        return new Response(true, "", "");
    }
}
