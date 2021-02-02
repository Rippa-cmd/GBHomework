package ru.geekbrains.module1.lesson7;


public class FeedTheCat {

    private static final int numberOfCats = 6;

    public static void main(String[] args) {

        Cat[] cats = new Cat[numberOfCats];
        for (int i = 0; i < 6; i++)
            cats[i] = new Cat();

        Plate plate = new Plate();
        plate.getInfo();

        for (Cat cat : cats)
            cat.eat(plate);

        plate.addFood(20);
        plate.getInfo();

    }
}
