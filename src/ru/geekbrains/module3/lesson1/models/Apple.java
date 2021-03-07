package ru.geekbrains.module3.lesson1.models;

public class Apple extends Fruit {
    public Apple() {
        this.weight = 1.0f;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }
}
