package com.bailey.dailytodolist_server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean success;
    private Map<String, String> result;
    private String message;

    public Response(boolean isSuccess) {
        this.success = isSuccess;
    }
}