package ru.geekbrains.module3.lesson1.Objects;

import ru.geekbrains.module3.lesson1.interfaces.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс коробки, хранищий только один тип класса фруктов, умеет считать вес, сравнивать коробки по весу, добавлять
 * фруктов, и перекидывать из одной коробки в другу (при учете что содержимое одних типов)
 *
 * @param <T> фрукты, которые могут лежать в этом классе (коробке)
 */
public class Box<T> {

    private List<T> list;

    public Box() {
        list = new ArrayList<>();
    }

    // Возвращает вес коробки
    public float getWeight() {
        if (list.size() > 0)
            return list.size() * ((Fruit) list.get(0)).getWeight();
        return 0.0f;
    }

    // Сравнивает входящую коробку с данной
    public boolean compare(Box<T> box) {
        return this.getWeight() - box.getWeight() == 0;
    }

    // Перекидывает содержимое коробок
    public void mergeBoxesContent(Box<T> box) {
        if (box.equals(this))  // Если передана та же самая коробка, выходим
            return;
        if (box.getWeight() != 0) {         // Если другая коробка пуста, то нечего перекидывать
            if (list.size() == 0) {
                list.addAll(box.list);
                box.list.clear();
            } else if (list.get(0).getClass().equals(box.list.get(0).getClass())) {
                list.addAll(box.list);
                box.list.clear();
            }
        }
    }

    // Добавляет фрукт
    public void add(T t) {
        if (list.size() > 0) {
            if (t.getClass().equals(list.get(0).getClass())) {
                list.add(t);
                System.out.println(list.getClass());
            } else
                System.out.println("Invalid type of fruit");
            return;
        }
        list.add(t);
    }
}
