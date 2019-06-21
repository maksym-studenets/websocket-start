package com.example.websocketstart;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final HashService hashService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public List<Greeting> greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return hashService.greeting(message);
    }
}
