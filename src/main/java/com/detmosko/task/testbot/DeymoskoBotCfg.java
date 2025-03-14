package com.detmosko.task.testbot;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class DeymoskoBotCfg {

    @Value("${bot.token}")
    public String botToken;
    @Value("${bot.username}")
    String botUsername;

    public String getBotToken() {
        return botToken;
    }

}
