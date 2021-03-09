package ru.geekbrains.module2.lesson7.serverside.interfaces;

public interface AuthService {
    void start();
    void stop();

    String getNicknameByLoginAndPassword(String login, String password);

    // Для проверки занятости регестрируемого ника
    boolean isNicknameBusy(String nickname);

    // Для проверки занятости регестрируемого логина
    boolean isLoginBusy(String nickname);

    // Добавление новой учетной записи
    boolean addEntry(String[] logPassNick);

    // Заменяет ник в БД на новый
    void nickChanger(String oldNick, String newNick);
}
