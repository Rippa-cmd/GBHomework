package ru.geekbrains.module2.lesson7.clientside;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

/**
 * Класс клиентской части чата (GUI)
 */
public class Client extends JFrame {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8081;
    private final int LOG_MESSAGE_SIZE = 100;

    private JTextField msgInputField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private File file;
    private BufferedWriter writer;
    private BufferedReader reader;

    private boolean isBanned = false;
    private boolean isAuthorized = false;

    private String login = "";

    public Client() {
        try {
            openConnection();
        } catch (IOException ignored) {
        }
        createGUI();
    }

    // Метод открытия соединения и прослушка
    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    if (!isAuthorized) {
                        if (strFromServer.equals("/banned")) {
                            isBanned = true;
                            continue;
                        } else if (strFromServer.equals("/unbanned")) {
                            isBanned = false;
                            continue;
                        }

                        // Если сервер дал добро на вход, запускаем логирование
                        if (strFromServer.startsWith("/login")) {
                            isAuthorized = true;
                            login = strFromServer.substring(7);
                            startLogger();
                            continue;
                        }
                    } else {
                        messageLogger(strFromServer);
                    }
                    chatArea.append(strFromServer + "\n");
                }
            } catch (EOFException ignored) {
                System.out.println("Bye bye");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Запускает ридеры и райтеры, если есть что в логах, выводит последние 100 сообщений
    public void startLogger() throws IOException {
        file = new File(login + "_log.txt");
        writer = new BufferedWriter(new FileWriter(file, true));
        reader = new BufferedReader(new FileReader(file));
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));

        while (lineNumberReader.readLine() != null) {
        }
        int count = lineNumberReader.getLineNumber() - 1;
        if (count > LOG_MESSAGE_SIZE) {
            count -= LOG_MESSAGE_SIZE;
            for (; count > 0; count--) {
                reader.readLine();
            }
        }
        String logMsg;
        chatArea.append("\t========== Log history of last " + LOG_MESSAGE_SIZE + " messages ==========\n");
        while ((logMsg = reader.readLine()) != null) {
            if (!logMsg.startsWith("\t"))         // Пропуск системных сообщений
                chatArea.append(logMsg + "\n");
        }
        chatArea.append("\t========== End of log history ==========\n");
        reader.close();
        lineNumberReader.close();
    }

    // Записывает в логи сообщения, игнорируя системные
    public void messageLogger(String message) throws IOException {
        if (!message.startsWith("\t")) {
            writer.write(message + "\n");
            writer.flush();
        }
    }

    // Метод отправки сообщения
    public void sendMessage() {
        if (!isBanned) {
            if (!msgInputField.getText().trim().isEmpty()) {
                try {
                    out.writeUTF(msgInputField.getText());
                    if (msgInputField.getText().equals("/exit")) {
                        this.dispose();
                        closeConnection();
                    }
                    msgInputField.setText("");
                    msgInputField.grabFocus();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You are banned!");
        }
    }

    // Закрываем сокет и потоки
    public void closeConnection() {
        try {
            if (in != null)
                in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Графическая часть
    public void createGUI() {

        // Настройка размера, заголовка и действия при закрытии
        setBounds(500, 500, 700, 300);
        setTitle("Chat Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatArea = new JTextArea(); // Поле отображения сообщений
        chatArea.setEditable(false); // Не изменяемо
        chatArea.setLineWrap(true); // Перенос строки

        // Делаем возможность скролла и добавляем его
        JScrollPane jsp = new JScrollPane(chatArea);
        add(jsp, BorderLayout.CENTER);

        // Создаем нижнюю панель с формой ввода сообщения и кнопкой отправки
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);

        msgInputField = new JTextField();
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        // Навешиваем слушателя на нажатие Enter'a
        msgInputField.addActionListener(e -> sendMessage());

        // Слушатель на нажатие кнопки
        JButton button = new JButton("Send");
        bottomPanel.add(button, BorderLayout.EAST);
        button.addActionListener(e -> sendMessage());

        // Настраиваем действие на закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/exit");
                    closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        // Делаем окно видимым
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client());
    }
}
