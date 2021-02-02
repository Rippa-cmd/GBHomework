package ru.geekbrains.module1.lesson8;

import java.util.Random;

/**
 * Тело логики хода компьютера, в принципе функционал тот же,
 * что и в процедурном стиле, лишь немного подкорректированный
 */
public class TicTacToeAI {

    private static final int SIZE = TicTacToeCore.SIZE;
    private static final int DOTS_TO_WIN = TicTacToeCore.DOTS_TO_WIN;
    private static final char[][] map = new char[SIZE][SIZE];
    private static final char DOT_EMPTY = '•';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final Random RANDOM = new Random();

    public TicTacToeAI() {
        fillingEmptyArray();
        drawMap();

    }


    private static void drawMap() {
        for (int rowIndex = 0; rowIndex <= SIZE; rowIndex++) {
            System.out.print(rowIndex + "\t");
        }
        System.out.println();
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            System.out.print(rowIndex + 1 + "\t");
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                System.out.print(map[rowIndex][colIndex] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void fillingEmptyArray() {
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                map[rowIndex][colIndex] = DOT_EMPTY;
            }
        }
    }

    // Проверка строк и столбцов на победу
    private static boolean linesChecker(char dot, boolean predictionStatus) {
        int rowWinFlag = 0;
        int colWinFlag = 0;
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                rowWinFlag = comboChecker(rowIndex, colIndex, rowWinFlag, dot);
                colWinFlag = comboChecker(colIndex, rowIndex, colWinFlag, dot);
                if (winStatus(dot, rowWinFlag, colWinFlag, predictionStatus)) return true;
            }
            colWinFlag = 0;
            rowWinFlag = 0;
        }
        return diagonalsChecker(dot, predictionStatus);
    }

    // Счетчик занятых ячеек подряд
    private static int comboChecker(int rowIndex, int colIndex, int flag, int dot) {
        if (map[rowIndex][colIndex] == dot)
            flag++;
        else flag = 0;
        return flag;
    }

    // Проверка n-го кол-ва символов подряд для победы
    private static boolean winStatus(char dot, int rowWinFlag, int colWinFlag, boolean predictionStatus) {
        if (rowWinFlag == DOTS_TO_WIN || colWinFlag == DOTS_TO_WIN) {
            if (predictionStatus) return true;
            if (dot == DOT_X)
                System.out.println("Вы победили!");
            if (dot == DOT_O)
                System.out.println("Компьютер победил!");
            return true;
        }
        return false;
    }

    // Проверка диагоналей на победу
    private static boolean diagonalsChecker(char dot, boolean predictionStatus) {
        int diagCount = 0;
        int diagCountMirrored = 0;

        // Проверка главных диагоналей

        for (int diagonal = 0; diagonal < SIZE; diagonal++) {
            diagCount = comboChecker(diagonal, diagonal, diagCount, dot);
            diagCountMirrored = comboChecker(diagonal, SIZE - 1 - diagonal, diagCountMirrored, dot);
            if (winStatus(dot, diagCountMirrored, diagCount, predictionStatus)) return true;
        }
        diagCount = 0;
        diagCountMirrored = 0;

        // Проверка побочных диагоналей

        for (int extraDiagonals = 0; extraDiagonals < SIZE - DOTS_TO_WIN; extraDiagonals++) {
            for (int rowIndex = 0; rowIndex < SIZE - extraDiagonals - 1; rowIndex++) {
                diagCount = comboChecker(rowIndex + 1 + extraDiagonals, rowIndex, diagCount, dot);
                diagCountMirrored = comboChecker(rowIndex, rowIndex + 1 + extraDiagonals, diagCountMirrored, dot);
                if (winStatus(dot, diagCountMirrored, diagCount, predictionStatus)) return true;
            }
            diagCount = 0;
            diagCountMirrored = 0;
            for (int rowIndexInverted = 0, colIndexInverted = SIZE - extraDiagonals - 2; rowIndexInverted < SIZE - extraDiagonals - 1; rowIndexInverted++, colIndexInverted--) {
                diagCount = comboChecker(rowIndexInverted, colIndexInverted, diagCount, dot);
                diagCountMirrored = comboChecker(rowIndexInverted + 1 + extraDiagonals, colIndexInverted + 1 + extraDiagonals, diagCountMirrored, dot);
                if (winStatus(dot, diagCountMirrored, diagCount, predictionStatus)) return true;
            }
            diagCount = 0;
            diagCountMirrored = 0;

        }
        return false;
    }

    // Проверка на ничью
    private static boolean tieStatus() {
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                if (map[rowIndex][colIndex] == DOT_EMPTY)
                    return false;
            }
        }
        System.out.println("Ничья!");
        return true;
    }

    // Проверка валидности ячейки
    private static boolean compCheckCell(int rowIndex, int colIndex) {
        return map[rowIndex][colIndex] == DOT_EMPTY;
    }

    // Метод "угадывания" выигрышных комбинаций
    private static boolean prediction(char dot) {
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                if (compCheckCell(rowIndex, colIndex)) {
                    map[rowIndex][colIndex] = dot;
                    if (linesChecker(dot, true) && dot == DOT_O) {
                        TicTacToeCore.buttons[rowIndex][colIndex].setActionCommand("O");
                        return true;
                    } else if (linesChecker(dot, true) && dot == DOT_X) {
                        map[rowIndex][colIndex] = DOT_O;
                        TicTacToeCore.buttons[rowIndex][colIndex].setActionCommand("O");
                        return true;
                    } else map[rowIndex][colIndex] = DOT_EMPTY;
                }
            }

        }
        return false;
    }

    // Логика хода компьютера
    private static void AITurn() {
        int rowIndex;
        int colIndex;
        if (prediction(DOT_O)) return;
        if (prediction(DOT_X)) return;
        do {
            rowIndex = RANDOM.nextInt(SIZE);
            colIndex = RANDOM.nextInt(SIZE);
        } while (!compCheckCell(rowIndex, colIndex));
        map[rowIndex][colIndex] = DOT_O;
        TicTacToeCore.buttons[rowIndex][colIndex].setActionCommand("O");
    }

    // Если ничья или победа одной из сторон создание окна с предложением сыграть снова
    public static boolean finishAndRestart(char dot) {
        if (linesChecker(dot, false)) {
            if ((dot == 'X')) {
                new RestartWindow("Вы победили!");
            } else {
                new RestartWindow("Компьютер победил!");
            }
            return true;
        }
        if (tieStatus()) {
            new RestartWindow("Ничья!");
            return true;
        }
        return false;
    }

    // Перенос хода игрока в массив с посимвольным представлением игры и ход компьютера
    public static void core(int rowIndex, int colIndex) {
        map[rowIndex][colIndex] = DOT_X;
        drawMap();
        if (finishAndRestart('X')) return;
        // Если игра закончена, зачем компьютеру напрягаться?

        AITurn();
        drawMap();
        finishAndRestart('O');
    }

}
