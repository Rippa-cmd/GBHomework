package ru.geekbrains.module2.lesson7.serverside.interfaces;

public interface AuthService {
    void start();
    void stop();
    String getNicknameByLoginAndPassword(String login, String password);

    // Для проверки занятости регестрируемого ника
    boolean isNicknameBusy(String nickname);

    // Добавление новой учетной записи
    void addEntryList(String[] logPassNick);
}
