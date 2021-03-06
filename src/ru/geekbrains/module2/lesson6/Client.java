package ru.geekbrains.module2.lesson6;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Класс клиентской части чата (GUI)
 */
public class Client extends JFrame {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8083;

    private JTextField msgInputField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (strFromServer.equals("/exit")) {
                        this.dispose();
                        closeConnection();
                        break;
                    }
                    chatArea.append("Server: " + strFromServer);
                    chatArea.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Метод отправки сообщения
    public void sendMessage() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                out.writeUTF(msgInputField.getText());
                if (msgInputField.getText().equals("/exit")) {
                    this.dispose();
                    closeConnection();
                }
                chatArea.append("Client: " + msgInputField.getText() + "\n");
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            }
        }
    }

    // Закрываем сокет и потоки
    public void closeConnection() {
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

    // Графическая часть
    public void createGUI() {

        // Настройка размера, заголовка и действия при закрытии
        setBounds(500, 500, 400, 300);
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
