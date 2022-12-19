package com.example.chat_22_12_19.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WriteMessageRequest {

    private String authorName;
    private String content;
}
