package ru.geekbrains.module3.lesson1.Objects;

import ru.geekbrains.module3.lesson1.interfaces.Fruit;

public class Apple implements Fruit {
    final float appleWeight = 1.0f;

    @Override
    public float getWeight() {
        return appleWeight;
    }
}
