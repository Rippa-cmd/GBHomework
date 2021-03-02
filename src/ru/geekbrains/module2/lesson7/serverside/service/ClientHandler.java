package ru.geekbrains.module2.lesson7.serverside.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Класс серверной части каждого пользователя для обмена сообщениями
 */
public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private long timeout = System.currentTimeMillis();
    private int maxTimeout = 120000;

    private boolean isAlreadyClosedConnection = false;

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    // Сервер конкретного пользователя
    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            nickname = "Noname";

            new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (Exception ignored) {
                } finally {
                    closeConnection();
                }
            }).start();

            // Поток отключения клиента по таймеру
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(maxTimeout - (System.currentTimeMillis() - timeout));
                        if (System.currentTimeMillis() - timeout > maxTimeout) {
                            sendMessage("You were kicked out for timeout");
                            closeConnection();
                            break;
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }).start();

        } catch (IOException ignored) {
            closeConnection();
            throw new RuntimeException("Problems with ClientHandler");
        }
    }

    // Процесс аутентификации (регистрации или авторизации)
    private void authentication() throws IOException, InterruptedException {
        Thread.sleep(500);
        sendMessage("\tWelcome to chat! Log in or sign up\n\tWrite /help for more info");
        int timer = 0;
        while (true) {
            String str = dis.readUTF();
            timeout = System.currentTimeMillis();  // Обнуляем таймер таймаута
            if (str.startsWith("/")) {
                if (str.startsWith("/help")) {
                    sendMessage("\tFor log in write you login and password in format\n\t/auth login password");
                    sendMessage("\tFor sign up write you login, password and nickname in format\n\t/signup login password nickname");
                } else if (str.startsWith("/auth ")) {
                    String[] logpass = str.split(" ");
                    if (logpass.length > 2) {
                        String name = server.
                                getAuthService().
                                getNicknameByLoginAndPassword(logpass[1], logpass[2]);
                        if (name != null) {
                            if (!server.isNickBusy(name)) {
                                sendMessage("\tYou are in! Your nickname is: " + name);
                                nickname = name;
                                server.subscribe(this);
                                server.broadcastMessage("\tHello, " + nickname);
                                return;
                            }
                            sendMessage("\tThis user is already in");
                            continue;
                        }
                    }
                    timer++;
                    sendMessage(str);
                    sendMessage("\tWrong login or password\n\tYou still have " + (3 - timer) + " attempts");
                    if (timer >= 3) {
                        sendMessage("\tToo many wrong tries, you are banned for 1 minute");
                        sendMessage("/banned");
                        Thread.sleep(60000);
                        sendMessage("\tYou are unbanned");
                        sendMessage("/unbanned");
                        timer = 0;
                        timeout = System.currentTimeMillis();
                    }
                } else if (str.startsWith("/signup ")) {
                    if (signUp(str)) return;
                }
            } else sendMessage("\tWrong command, write /help for more info");
        }
    }

    //Логика регистрации нового пользователя
    private boolean signUp(String message) {
        String[] logPassNick = message.split(" ");
        if (logPassNick.length == 4) {
            if (!server.getAuthService().isNicknameBusy(logPassNick[3])) {
                server.getAuthService().addEntryList(logPassNick);
                nickname = logPassNick[3];
                server.subscribe(this);
                server.broadcastMessage("\tHello, " + nickname);
                return true;
            } else
                sendMessage("\tNickname " + logPassNick[3] + " is busy");
        } else {
            sendMessage(message);
            sendMessage("\tWrong fields");
        }
        return false;
    }

    // Обработка полученного от клиента сообщения
    private void readMessage() throws IOException {
        maxTimeout *= 1.5;  // Переводим таймер на 3 минуты
        while (true) {
            String message = dis.readUTF();
            timeout = System.currentTimeMillis(); // Обнуляем таймер таймаута
            System.out.println(nickname + ": " + message);
            if (message.startsWith("/")) {
                if (message.equals("/exit"))
                    return;
                if (message.startsWith("/w"))
                    preparePrivateMessage(message);
                if (message.trim().equals("/list"))
                    server.list(this);
                continue;
            }
            if (!message.trim().isEmpty()) {
                server.broadcastMessage(nickname + ": " + message);
            }
        }
    }

    //Побготовка личного сообщения
    private void preparePrivateMessage(String message) {
        String[] str = message.split(" ", 3);
        if (!str[0].equals("/w")) {
            sendMessage("Invalid command");
            return;
        }
        if (str.length < 3) {
            if (str.length == 1) {
                sendMessage("Empty nickname");
            } else
                sendMessage("Empty message");
            return;
        }
        if (server.isNickBusy(str[1]))
            server.privateMessage(this, str[1], str[2]);
        else
            sendMessage("Invalid nickname");
    }

    //Отправка сообщения клиенту
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {
        }
    }

    //Закрытие потоков
    private void closeConnection() {
        if (!isAlreadyClosedConnection) {
            server.broadcastMessage("\t" + nickname + " exit from chat");
            System.out.println(nickname + " exit from chat");
            server.unsubscribe(this);
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isAlreadyClosedConnection = true;
        }
    }
}
