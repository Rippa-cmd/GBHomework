package ru.geekbrains.module2.lesson2.MyExeptions;

/**
 * Исключение при неправильной рамерности массива
 */
public class MyArraySizeException extends Exception {

    public MyArraySizeException() {
        System.out.println("Размерность массива не верна");
    }
}
