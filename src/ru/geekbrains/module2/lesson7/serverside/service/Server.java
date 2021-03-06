package ru.geekbrains.module2.lesson7.serverside.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.geekbrains.module2.lesson7.serverside.MainServerAPP;
import ru.geekbrains.module2.lesson7.serverside.interfaces.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс серверной части чата, для отправки пользователями друг-другу сообщений
 */
public class Server {

    private final int PORT = 8081;

    public static final Logger logger = LogManager.getLogger(MainServerAPP.class.getName());

    private List<ClientHandler> clients;

    public ExecutorService executorService;

    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }


    // Запуск сервера, создания списка клиентов, и ожидание пользователей, при подключении добавление в чат
    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            authService = new BaseAuthService();
            authService.start();

            logger.warn("Server started");

            clients = new ArrayList<>();
            executorService = Executors.newCachedThreadPool();

            logger.info("Awaiting client connection...");

            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("Client connected");
                new ClientHandler(this, socket);
            }


        } catch (IOException e) {
            Server.logger.error(e);
        } finally {
            if (authService != null)
                authService.stop();
            if (executorService != null)
                executorService.shutdownNow();
        }
    }

    // Реализация отправки сообщения всех пользователям
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    // Реализация отправки личного сообщения конкретному пользователю
    public synchronized void privateMessage(ClientHandler sender, String nickname, String message) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) {
                client.sendMessage(sender.getNickname() + " whisper to you: " + message);
                sender.sendMessage("You whisper to " + nickname + ": " + message);
                return;
            }
        }
    }

    // Выводит список пользователей в сети
    public synchronized void list(ClientHandler from) {
        StringBuilder list = new StringBuilder("");
        for (ClientHandler client : clients) {
            if (!client.getNickname().equals(from.getNickname()))
                list.append(client.getNickname()).append(", ");
        }
        int length = list.length();
        if (length > 0) {
            list.deleteCharAt(length - 1).deleteCharAt(length - 2);
            from.sendMessage("\tList of online users: " + list.toString());
        } else
            from.sendMessage("\tYou are only one");
    }

    // Подписка пользователя
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    // Отписка пользователя
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    // Проверка, залогинен ли пользователь с данным ником
    public boolean isNickBusy(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname))
                return true;
        }
        return false;
    }
}
