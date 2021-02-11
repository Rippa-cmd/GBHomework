package ru.geekbrains.module2.lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Класс серверной части чата (консоль)
 */
public class Server {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Server() {

        try (ServerSocket serverSocket = new ServerSocket(8083);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server started");
            socket = serverSocket.accept();
            System.out.println("Client connected");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Отдельный поток для отправки сообщения
            Thread t1 = new Thread(() -> {
                while (true) {
                    try {
                        String strReader = "";
                        if (reader.ready()) {
                            strReader = reader.readLine();
                        }
                        if (!strReader.trim().isEmpty()) {
                            out.writeUTF(strReader);
                            if (strReader.equals("/exit")) {
                                closeConnection();
                                break;
                            }
                            System.out.println("Server: " + strReader);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });
            t1.start();

            // получение сообщения
            while (true) {
                String str = in.readUTF();
                if (str.equals("/exit")) {
                    closeConnection();
                    break;
                }
                System.out.println("Client: " + str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Закрываем сокет и потоки
    private void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Server();
    }
}
