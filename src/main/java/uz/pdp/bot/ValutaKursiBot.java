package uz.pdp.bot;

import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.model.Valuta;
import uz.pdp.service.utills.AppConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValutaKursiBot extends TelegramLongPollingBot {
    @Override
    public String getBotToken() {
        return "";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        String rub = "RUB";
        String usd = "USD";
        String eur = "EUR";
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            Message message = update.getMessage();
            String text = message.getText();
            if (text.equals("/start")) {
                sendMessage.setText("<b>Valuta</b> ");
                sendMessage.setParseMode("HTML");
                sendMessage.setReplyMarkup(getMarkUp(rub, usd, eur));
            } else if (text.equals(usd)) {
                sendMessage.setText(getValut(usd)+ " = " + usd);
            } else if (text.equals(rub)) {
                sendMessage.setText(getValut(rub) + " = " + rub);
            } else if (text.equals(eur)) {
                sendMessage.setText(getValut(eur) + " = " + eur);
            }
        }
        execute(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    private String getValut(String valutName) throws IOException {
        URL fileInputStream = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/" + valutName + "/");
        Scanner scanner = new Scanner(fileInputStream.openStream());
        StringBuilder stringBuilder = new StringBuilder("");
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        String str = stringBuilder.toString();
        List<Valuta> valutaList = AppConstants.GSON.fromJson(str, new TypeToken<List<Valuta>>() {
        }.getType());
        return String.valueOf(valutaList.get(0).getRate());
    }

    private ReplyKeyboardMarkup getMarkUp(String... names) {
        List<KeyboardRow> rowList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(names[i]);
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(keyboardButton);
            rowList.add(keyboardRow);
        }
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rowList);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        return markup;
    }
}
