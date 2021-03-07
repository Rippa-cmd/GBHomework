package ru.geekbrains.module3.lesson1.models;

public class Orange extends Fruit {
    public Orange() {
        this.weight = 1.5f;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }
}
