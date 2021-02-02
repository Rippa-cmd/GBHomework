package ru.geekbrains.module1.lesson6;

public class Cat extends Animals {

    private static int catsAmount = 0;

    public Cat(String name) {
        super(name, 200, 2, 0);
        catsAmount++;
        System.out.println(super.toString());
    }

    @Override
    public void swim(double distance) {
        System.out.println("Хватит мучать кота, он не умеет плавать!");
    }

    public static int getCatsAmount() {
        return catsAmount;
    }
}
