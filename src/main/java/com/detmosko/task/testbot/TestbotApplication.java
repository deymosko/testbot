package com.detmosko.task.testbot;

import com.detmosko.task.testbot.service.BotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TestbotApplication {
    public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TestbotApplication.class, args);
        DeymoskoBotCfg config = context.getBean(DeymoskoBotCfg.class);
		try {
			TelegramBotsLongPollingApplication botApp = new TelegramBotsLongPollingApplication();
			botApp.registerBot(config.getBotToken(), new BotService(config));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
