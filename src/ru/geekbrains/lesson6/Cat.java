package ru.geekbrains.lesson6;

public class Cat extends Animals {
    public Cat(String name) {
        super(name, 200, 2, 0);
        super.incrementCatsQuantity();
        System.out.println(super.toString());
    }

    @Override
    public void swim(double distance) {
        System.out.println("Хватит мучать кота, он не умеет плавать!");
    }

}
