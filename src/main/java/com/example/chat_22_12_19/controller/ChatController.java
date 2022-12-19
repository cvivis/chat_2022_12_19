package com.example.chat_22_12_19.controller;

import com.example.chat_22_12_19.domain.ChatMessage;
import com.example.chat_22_12_19.domain.RsData;
import com.example.chat_22_12_19.domain.WriteMessageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
public class ChatController {


    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record WriteMessageResponse(Long id){
    }

    public record MessagesResponse(List<ChatMessage> chatMessages,int size){
    }
    public record MessagesRequset(@RequestParam(defaultValue = "0", required = false) Long fromId){
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest req) {
        ChatMessage message = new ChatMessage(req.getAuthorName(), req.getContent());

        chatMessages.add(message);

        return new RsData(
                "S-1",
                "메세지가 작성되었습니다.",
               new WriteMessageResponse(message.getId())
        );
    }

    @GetMapping("/messages")
    @ResponseBody
    public RsData<MessagesResponse> messages(MessagesRequset req) {
        List<ChatMessage> messages = chatMessages;
        if(req.fromId != null) {
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId)
                    .findFirst()
                    .orElse(-1); // 없다면 -1
            if(index != -1){
                messages = messages.subList(index +1 , messages.size());
            }
        }

            return new RsData<>(
                    "S-1",
                    "성공",
                    new MessagesResponse(messages, messages.size())
            );
        }
}
