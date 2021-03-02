package ru.geekbrains.module2.lesson7.serverside.service;

import ru.geekbrains.module2.lesson7.serverside.interfaces.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс серверной части чата, для отправки пользователями друг-другу сообщений
 */
public class Server {

    private final int PORT = 8081;

    private List<ClientHandler> clients;

    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }


    // Запуск сервера, создания списка клиентов, и ожидание пользователей, при подключении добавление в чат
    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            authService = new BaseAuthService();
            authService.start();

            System.out.println("Server started");

            clients = new ArrayList<>();

            System.out.println("Awaiting client connection...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                new ClientHandler(this, socket);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (authService != null)
                authService.stop();
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
        list.deleteCharAt(length - 1).deleteCharAt(length - 2);
        from.sendMessage("List of online users: " + list.toString());
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
