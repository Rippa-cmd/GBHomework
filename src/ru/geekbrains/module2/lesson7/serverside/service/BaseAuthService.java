package ru.geekbrains.module2.lesson7.serverside.service;

import ru.geekbrains.module2.lesson7.serverside.interfaces.AuthService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс аутентификации пользователей, хранящий необходимую для этой операции информацию о пользователях
 */
public class BaseAuthService implements AuthService {

    private Statement statement = null;
    private ResultSet set = null;
    PreparedStatement preparedStatement = null;

    // Добавление нового пользователя
    @Override
    public boolean addEntry(String[] logPassNick) {
        try {
            preparedStatement = Singleton.getConnection().prepareStatement("INSERT INTO users (login, password, nick) VALUES (?, ?, ?)");
            preparedStatement.setString(1, logPassNick[1]);
            preparedStatement.setString(2, logPassNick[2]);
            preparedStatement.setString(3, logPassNick[3]);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void start() {
        try {
            statement = Singleton.getConnection().createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("AuthService started");
    }

    @Override
    public void stop() {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("AuthService stopped");
    }

    // Получение ника по логину и паролю
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {

            // Сделал через prepared для игнорирования спец символов ('," и тд)
            preparedStatement = Singleton.getConnection().prepareStatement("select nick from users where login = ? and password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            set = preparedStatement.executeQuery();
            if (set.next()) {
                return set.getString(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Заменяет ник в БД на новый
    public void nickChanger(String oldNick, String newNick) {
        try {
            preparedStatement = Singleton.getConnection().prepareStatement("update users set nick = ? where nick = ?");
            preparedStatement.setString(1, newNick);
            preparedStatement.setString(2, oldNick);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Занят ли ник
    @Override
    public boolean isNicknameBusy(String nickname) {
        try {
            set = statement.executeQuery("select nick from users");
            while (set.next()) {
                if (set.getString(1).equals(nickname))
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Занят ли логин
    @Override
    public boolean isLoginBusy(String login) {
        try {
            set = statement.executeQuery("select login from users");
            while (set.next()) {
                if (set.getString(1).equals(login))
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
