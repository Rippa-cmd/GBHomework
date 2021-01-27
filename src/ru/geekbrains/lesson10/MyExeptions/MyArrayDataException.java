package ru.geekbrains.lesson10.MyExeptions;

/**
 * Исключение, если в переменной типа String не число (или невозможно распарсить в тип Integer)
 */
public class MyArrayDataException extends Exception{

    // Запоминаем индексы ошибочной ячейки
    private static int lastRowIndexException;
    private static int lastColIndexException;

    public MyArrayDataException(int rowIndex, int colIndex) {
        lastRowIndexException = rowIndex;
        lastColIndexException = colIndex;
        System.out.println("По адресу "+rowIndex+" "+colIndex+" не число, будет заменено на 0");
    }

    public static int getLastRowIndexException() {
        return lastRowIndexException;
    }

    public static int getLastColIndexException() {
        return lastColIndexException;
    }
}
