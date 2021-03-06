package ru.geekbrains.module3.lesson1.Objects;

import ru.geekbrains.module3.lesson1.interfaces.Fruit;

public class Orange implements Fruit {
    final float orangeWeight = 1.5f;

    @Override
    public float getWeight() {
        return orangeWeight;
    }
}
