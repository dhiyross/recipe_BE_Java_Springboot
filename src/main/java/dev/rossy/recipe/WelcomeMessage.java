package dev.rossy.recipe;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {
    public String getWelcomeMessage(){
        return "Hello World";

    }
}