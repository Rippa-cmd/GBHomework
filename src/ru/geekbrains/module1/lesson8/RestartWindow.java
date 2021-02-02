package ru.geekbrains.module1.lesson8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс запуска окна с запросом на повторную игру
 */
public class RestartWindow extends JFrame {

    public RestartWindow(String text) {
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton restart = new JButton(text + "  Еще раз?");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeCore.newGame();
                setVisible(false);
                dispose();
            }
        });
        add(restart);
        setVisible(true);
    }
}
