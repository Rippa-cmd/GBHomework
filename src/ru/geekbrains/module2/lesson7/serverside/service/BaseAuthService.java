package ru.geekbrains.module2.lesson7.serverside.service;

import ru.geekbrains.module2.lesson7.serverside.interfaces.AuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс аутентификации пользователей, хранящий необходимую для этой операции информацию о пользователях
 */
public class BaseAuthService implements AuthService {

    private List<Entry> entryList;

    // Добавление нового пользователя
    @Override
    public void addEntryList(String[] logPassNick) {
        entryList.add(new Entry(logPassNick[1], logPassNick[2], logPassNick[3]));
    }

    // Заполнение "базы данных" пользователями
    public BaseAuthService() {
        entryList = new ArrayList<>();
        entryList.add(new Entry("login1", "pass1", "nick1"));
        entryList.add(new Entry("login2", "pass2", "nick2"));
        entryList.add(new Entry("login3", "pass3", "nick3"));
    }

    @Override
    public void start() {
        System.out.println("AuthService started");
    }

    @Override
    public void stop() {
        System.out.println("AuthService stopped");
    }

    // Получение ника по логину и паролю
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (Entry e : entryList) {
            if (e.login.equals(login) && e.password.equals(password))
                return e.nickname;
        }
        return null;
    }

    // Занят ли ник
    @Override
    public boolean isNicknameBusy(String nickname) {
        for (Entry e : entryList) {
            if (e.nickname.equals(nickname))
                return true;
        }
        return false;
    }

    // Создание нового пользователя
    private class Entry {
        private String login;
        private String password;
        private String nickname;

        public Entry(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }
}
