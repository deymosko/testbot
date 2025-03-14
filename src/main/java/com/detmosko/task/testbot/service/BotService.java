package com.detmosko.task.testbot.service;


import com.detmosko.task.testbot.DeymoskoBotCfg;
import com.detmosko.task.testbot.TRANSACTION_TYPES;
import com.detmosko.task.testbot.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BotService implements LongPollingSingleThreadUpdateConsumer
{
    private DeymoskoBotCfg config;
    private final TelegramClient telegramClient;
    private TransactionService transactionService = new TransactionService();

    public BotService(DeymoskoBotCfg config)
    {
        this.config = config;
        this.telegramClient = new OkHttpTelegramClient(config.getBotToken());
    }

    @Override
    public void consume(Update update) {
        if(update.hasMessage() && update.getMessage().hasText())
        {
            switch (update.getMessage().getText())
            {
                case "/start":
                    startCommand(update.getMessage().getChatId(), update);
            }

        }
        else if (update.hasCallbackQuery())
        {
            handleCallback(update);
        }
    }

    private void handleCallback(Update update)
    {
        //generateTransaction(TRANSACTION_TYPES.MENU_INTERACT, update.getCallbackQuery().getFrom().getUserName());
        String callBackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        switch (callBackData)
        {
            case"button1":
                sendMessage(chatId, "Кнопка1");
                break;
            case"button2":
                sendMessage(chatId, "Кнопка2");
                break;
            case "next":
                editMessage(chatId, update.getCallbackQuery().getMessage().getMessageId(), "Меню2", getSecondMenuKeyboard());
                break;
            case "back":
                editMessage(chatId, update.getCallbackQuery().getMessage().getMessageId(), "Меню1", getMainMenuKeyboard());
                break;
        }
    }

    private void editMessage(Long chatId, Integer messageId, String newText, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessage = EditMessageText.builder()
                .chatId(chatId.toString())
                .messageId(messageId)
                .text(newText)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard.getKeyboard()).build())
                .build();

        try {
            telegramClient.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String text)
    {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text).build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startCommand(Long chatId, Update update)
    {
        openMainMenu(chatId);
        //generateTransaction(TRANSACTION_TYPES.SEND_MESSAGE, update.getMessage().getFrom().getUserName());
    }
    private void openMainMenu(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Меню 1")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboard(getMainMenuKeyboard().getKeyboard())
                        .build())
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private InlineKeyboardMarkup getMainMenuKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Кнопка 1").callbackData("button1").build()
                        )),
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Кнопка 2").callbackData("button2").build()
                        )),
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Далі").callbackData("next").build()
                        ))
                ))
                .build();
    }

    private InlineKeyboardMarkup  getSecondMenuKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Кнопка 1").callbackData("button1").build()
                        )),
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Кнопка 2").callbackData("button2").build()
                        )),
                        new InlineKeyboardRow(List.of(
                                InlineKeyboardButton.builder().text("Назад").callbackData("back").build()
                        ))
                ))
                .build();
    }

    private void generateTransaction(TRANSACTION_TYPES type, String username)
    {
        Transaction transaction = new Transaction();
        transaction.setUsername(username);
        transaction.setDate(LocalDate.now().toString());
        transaction.setTime(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond());
        transaction.setType(type);
        transactionService.add(transaction);
    }
}
