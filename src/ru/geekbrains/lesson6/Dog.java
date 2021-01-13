package ru.geekbrains.lesson6;

public class Dog extends Animals {
    public Dog(String name) {
        super(name, 500, 0.5, 10);
        super.incrementDogsQuantity();
        System.out.println(super.toString());
    }

}


