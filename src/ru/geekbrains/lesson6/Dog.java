package ru.geekbrains.lesson6;

public class Dog extends Animals {

    private static int dogsAmount = 0;

    public Dog(String name) {
        super(name, 500, 0.5, 10);
        dogsAmount++;
        System.out.println(super.toString());
    }

    public static int getDogsAmount() {
        return dogsAmount;
    }
}


