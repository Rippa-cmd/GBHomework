package ru.geekbrains.module2.lesson10.MyExeptions;

/**
 * Исключение, если в переменной типа String не число (или невозможно распарсить в тип Integer)
 */
public class MyArrayDataException extends Exception{

    public MyArrayDataException(int rowIndex, int colIndex) {
        System.out.println("По адресу "+rowIndex+" "+colIndex+" не число!");
    }
}
