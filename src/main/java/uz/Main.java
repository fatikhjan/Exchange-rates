package uz;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.bot.ValutaKursiBot;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {

        TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new ValutaKursiBot());

    }
}
