package ru.geekbrains.module3.lesson1.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс коробки, хранищий только один тип класса фруктов, умеет считать вес, сравнивать коробки по весу, добавлять
 * фруктов, и перекидывать из одной коробки в другу (при учете что содержимое одних типов)
 *
 * @param <T> фрукты, которые могут лежать в этом классе (коробке)
 */
public class Box<T extends Fruit> {

    private List<T> list;

    public Box() {
        list = new ArrayList<>();
    }

    // Возвращает вес коробки
    public float getWeight() {
        if (!list.isEmpty())
            return list.size() * list.get(0).weight;
        return 0.0f;
    }

    // Сравнивает входящую коробку с данной
    public boolean compare(Box<?> box) {
        return this.getWeight() == box.getWeight();
    }

    // Перекидывает содержимое коробок
    public void moveBoxesContent(Box<T> box) {
        box.list.addAll(list);
        list.clear();
    }

    // Добавляет фрукт
    public void add(T t) {
        list.add(t);
    }
}
