package ru.geekbrains.module3.lesson1;

import ru.geekbrains.module3.lesson1.models.Apple;
import ru.geekbrains.module3.lesson1.models.Box;
import ru.geekbrains.module3.lesson1.models.Orange;

import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) {

        workWithGenerics();

        Apple apple = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();
        Orange orange = new Orange();
        Orange orange2 = new Orange();
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();

        appleBox.add(apple);
        appleBox.add(apple2);
        appleBox.add(apple3);
        orangeBox.add(orange);
        orangeBox.add(orange2);

        assert appleBox.compare(orangeBox) == true;
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.add(new Apple());
        appleBox.moveBoxesContent(appleBox1);
        assert appleBox1.getWeight() == 4.0f;

    }

    // Запуск первых двух задач
    public static void workWithGenerics() {
        Double[] arr = {1.0, 2.0, 3.0, 4.0};
        replace(arr, 0, arr.length - 1);
        arrToList(arr);
    }

    // Первая задача
    public static <T> void replace(T[] arr, int first, int second) {
        T temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    // Вторая задача
    public static <T> ArrayList<T> arrToList(T[] arr) {
        ArrayList<T> list = new ArrayList<>();
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }
}