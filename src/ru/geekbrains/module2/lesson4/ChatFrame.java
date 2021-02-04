package ru.geekbrains.module2.lesson4;

import javax.swing.*;
import java.awt.*;

/**
 * Класс окна графического чата
 */
public class ChatFrame extends JFrame{

        public ChatFrame() {

            // Настройка размера, заголовка и действия при закрытии
            setBounds(500, 500, 400, 300);
            setTitle("Chat Frame");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JTextArea jta = new JTextArea(); // Поле отображения сообщений
            jta.setEditable(false); // Не изменяемо
            jta.setLineWrap(true); // Перенос строки

            // Делаем возможность скролла и добавляем его
            JScrollPane jsp = new JScrollPane(jta);
            add(jsp, BorderLayout.CENTER);

            // Создаем нижнюю панель с формой ввода сообщения и кнопкой отправки
            JPanel bottomPanel = new JPanel(new BorderLayout());
            add(bottomPanel, BorderLayout.SOUTH);

            JTextField jTextField = new JTextField();
            bottomPanel.add(jTextField, BorderLayout.CENTER);

            // Навешиваем слушателя на нажатие Enter'a
            jTextField.addActionListener(e -> {
                if (!jTextField.getText().trim().isEmpty()) {
                    jta.append(jTextField.getText() + '\n');
                    jTextField.setText("");
                }
            });

            // Слушатель на нажатие кнопки
            JButton button = new JButton("Send");
            bottomPanel.add(button, BorderLayout.EAST);
            button.addActionListener(e -> {
                if (!jTextField.getText().trim().isEmpty()) {
                    jta.append(jTextField.getText() + '\n');
                    jTextField.setText("");
                }
            });

            // Делаем окно видимым
            setVisible(true);
        }
}
