package ru.geekbrains.lesson4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static final int SIZE = 5;
    public static final int DOTS_TO_WIN = 4;
    public static final char[][] map = new char[SIZE][SIZE];
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';

    public static final Scanner SCANNER = new Scanner(System.in);
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        fillingEmptyArray();
        drawMap();

        do {
            humanTurn();
            drawMap();
            if (linesChecker(DOT_X)) break;
            if (tieStatus()) break;

            AITurn();
            drawMap();
            if (linesChecker(DOT_O)) break;
        } while (!tieStatus());

    }

    // Проверка строк и столбцов на победу

    private static boolean linesChecker(char dot) {
        int rowWinFlag = 0;
        int colWinFlag = 0;
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                rowWinFlag = comboChecker(rowIndex, colIndex, rowWinFlag, dot);
                colWinFlag = comboChecker(colIndex, rowIndex, colWinFlag, dot);
                if (winStatus(dot, rowWinFlag, colWinFlag)) return true;
            }
            colWinFlag = 0;
            rowWinFlag = 0;
        }
        if (diagonalsChecker(dot)) return true;
        return false;
    }

    // Проверка диагоналей на победу

    private static boolean diagonalsChecker(char dot) {
        int diagCount = 0;
        int diagCountMirrored = 0;

        // Проверка главных диагоналей

        for (int diagonal = 0; diagonal < SIZE; diagonal++) {
            diagCount = comboChecker(diagonal, diagonal, diagCount, dot);
            diagCountMirrored = comboChecker(diagonal, SIZE - 1 - diagonal, diagCountMirrored, dot);
            if (winStatus(dot, diagCountMirrored, diagCount)) return true;
        }
        diagCount = 0;
        diagCountMirrored = 0;

        // Проверка побочных диагоналей

        for (int extraDiagonals = 0; extraDiagonals < SIZE - DOTS_TO_WIN; extraDiagonals++) {
            for (int rowIndex = 0; rowIndex < SIZE - extraDiagonals - 1; rowIndex++) {
                diagCount = comboChecker(rowIndex + 1 + extraDiagonals, rowIndex, diagCount, dot);
                diagCountMirrored = comboChecker(rowIndex, rowIndex + 1 + extraDiagonals, diagCountMirrored, dot);
                if (winStatus(dot, diagCountMirrored, diagCount)) return true;
            }
            diagCount = 0;
            diagCountMirrored = 0;
            for (int rowIndexInverted = 0, colIndexInverted = SIZE - extraDiagonals - 2; rowIndexInverted < SIZE - extraDiagonals - 1; rowIndexInverted++, colIndexInverted--) {
                diagCount = comboChecker(rowIndexInverted, colIndexInverted, diagCount, dot);
                diagCountMirrored = comboChecker(rowIndexInverted + 1 + extraDiagonals, colIndexInverted + 1 + extraDiagonals, diagCountMirrored, dot);
                if (winStatus(dot, diagCountMirrored, diagCount)) return true;
            }
            diagCount = 0;
            diagCountMirrored = 0;

        }
        return false;
    }

    // Проверка n-го кол-ва символов подряд для победы

    private static boolean winStatus(char dot, int rowWinFlag, int colWinFlag) {
        if (rowWinFlag == DOTS_TO_WIN || colWinFlag == DOTS_TO_WIN) {
            if (dot == DOT_X)
                System.out.println("Вы победили!");
            if (dot == DOT_O)
                System.out.println("Компьютер победил!");
            return true;
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

    // Ход компьютера

    private static void AITurn() {
        int rowIndex;
        int colIndex;
        do {
            rowIndex = RANDOM.nextInt(SIZE);
            colIndex = RANDOM.nextInt(SIZE);
        } while (!compCheckCell(rowIndex, colIndex));
        map[rowIndex][colIndex] = DOT_O;
    }

    // Ход игрока

    private static void humanTurn() {
        int rowIndex = -1;
        int colIndex = -1;
        do {
            System.out.print("Введите номер строки: ");
            rowIndex = SCANNER.nextInt() - 1;
            System.out.print("Введите номер столбца: ");
            colIndex = SCANNER.nextInt() - 1;
        } while (!checkCell(rowIndex, colIndex));
        map[rowIndex][colIndex] = DOT_X;
    }

    // Проверка валидности ячейки

    private static boolean checkCell(int rowIndex, int colIndex) {
        if (isCellNumberValid(rowIndex) || isCellNumberValid(colIndex)) {
            System.out.println("Введите число от 1 до " + SIZE + "!");
            return false;
        }
        if (map[rowIndex][colIndex] != DOT_EMPTY) {
            System.out.println("Выберите не занятую клетку!");
            return false;
        }
        return true;
    }

    // Проверка занятости ячейки для компьютера, без вывода сообщений в консоль и без проверки диапазона

    private static boolean compCheckCell(int rowIndex, int colIndex) {
        if (map[rowIndex][colIndex] != DOT_EMPTY) {
            return false;
        }
        return true;
    }

    private static int comboChecker(int rowIndex, int colIndex, int flag, int dot) {
        if (map[rowIndex][colIndex] == dot)
            flag++;
        else flag = 0;
        return flag;
    }

    // Проверка диапазона числа

    private static boolean isCellNumberValid(int index) {
        return index < 0 || index >= SIZE;
    }

    // Отрисовка текущего поля в консоль

    private static void drawMap() {
        for (int rowIndex = 0; rowIndex <= SIZE; rowIndex++) {
            System.out.print(rowIndex + " ");
        }
        System.out.println();
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            System.out.print(rowIndex + 1 + " ");
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                System.out.print(map[rowIndex][colIndex] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Заполнение поля пустыми ячейками

    private static void fillingEmptyArray() {
        for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SIZE; colIndex++) {
                map[rowIndex][colIndex] = DOT_EMPTY;
            }
        }
    }

}
