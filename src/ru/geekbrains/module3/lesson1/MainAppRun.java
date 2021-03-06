package ru.geekbrains.module3.lesson1;


import ru.geekbrains.module3.lesson1.Objects.Box;
import ru.geekbrains.module3.lesson1.Objects.Apple;
import ru.geekbrains.module3.lesson1.interfaces.Fruit;
import ru.geekbrains.module3.lesson1.Objects.Orange;

import java.io.InvalidClassException;
import java.util.ArrayList;

public class MainAppRun {
    public static void main(String[] args) throws InvalidClassException {

        //workWithGenerics();

        Box<Fruit> box1 = new Box<>();
        box1.add(new Orange());

        Box<Fruit> box2 = new Box<>();
        box2.add(new Apple());

        box1.getWeight();
        box1.compare(box2);
        box1.mergeBoxesContent(box2);


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
        ArrayList<T> list = new ArrayList<T>();
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }
}
