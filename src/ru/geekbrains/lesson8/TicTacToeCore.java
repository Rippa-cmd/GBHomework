package ru.geekbrains.lesson8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Основной класс игры, где инициализируется окно с кнопками
 */
public class TicTacToeCore {

    // Параметры, отвечающие за размер поля, и кол-во ячеек подряд для победы
    public static final int SIZE = 3;
    public static final int DOTS_TO_WIN = 3;

    public static JButton[][] buttons = new JButton[SIZE][SIZE];

    static class MyWindow extends JFrame {

        private static final String DRAW_X = "X";
        private static final String DRAW_O = "O";
        private static final String DRAW_EMPTY = "DRAW_EMPTY";


        public MyWindow() {

            setSize(800, 800);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(SIZE, SIZE));

            for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
                for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                    buttons[rowIndex][colIndex] = createButton(rowIndex, colIndex);
                    add(buttons[rowIndex][colIndex]);
                }
            }

            setVisible(true);

        }

        // Создание кнопок с логикой на нажатие и отрисовкой кружков и крестиков
        private JButton createButton(int rowIndex, int colIndex) {
            return new JButton() {
                {
                    setActionCommand(DRAW_EMPTY);
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String action = getActionCommand();
                            setActionCommand(DRAW_X);
                            TicTacToeAI.core(rowIndex, colIndex);
                        }
                    });
                }

                @Override
                public void paint(Graphics graphics) {
                    super.paint(graphics);

                    if (getActionCommand().equals(DRAW_EMPTY)) {
                        repaint();
                    } else if (getActionCommand().equals(DRAW_X)) {
                        Graphics2D g2d = (Graphics2D) graphics;
                        g2d.setStroke(new BasicStroke(10));
                        g2d.setColor(Color.BLUE);
                        g2d.drawLine(0, 0, this.getWidth(), this.getHeight());
                        g2d.drawLine(this.getWidth(), 0, 0, this.getHeight());
                        setEnabled(false);

                    } else if (getActionCommand().equals(DRAW_O)) {
                        graphics.setColor(Color.RED);
                        graphics.fillOval(0, 0, getWidth(), getHeight());
                        setEnabled(false);
                    }
                }
            };
        }
        // TODO Реализовать закрытие окон
//        public void closeWindow() {
//            this.setVisible(false);
//            dispose();
//        }

    }


    public static void main(String[] args) {
        MyWindow game = new MyWindow();
        TicTacToeAI ticTacToeAI = new TicTacToeAI();
    }

    // Перезапуск игры
    public static void newGame() {
        new MyWindow();
        new TicTacToeAI();
    }

}