package ru.geekbrains.lesson6;

import java.util.Random;

public class AnimalsActions {

    public static final int runDistance = 200;
    public static final int jumpHeight = 2;
    public static final int swimDistance = 20;
    public static int maxAnimalsQuantity = 10;
    public static final Random rand = new Random();


    public static void main(String[] args) {
        // Создание и заполнение массива случайным колличеством кошек и собак

        maxAnimalsQuantity = rand.nextInt(maxAnimalsQuantity);
        Animals[] animals = new Animals[maxAnimalsQuantity];

        for (int i = 0; i < maxAnimalsQuantity; i++) {
            if (Math.random() >= 0.5)
                animals[i] = new Cat("Cat№" + i);
            else animals[i] = new Dog("Dog№" + i);

        }
        System.out.println();

        // Проверка животных на выносливость

        for (int i = 0; i < maxAnimalsQuantity; i++) {
            System.out.println(animals[i].name + " проходит испытания:");
            animals[i].run(runDistance);
            animals[i].jump(jumpHeight);
            animals[i].swim(swimDistance);
            System.out.println();
        }
        Animals.getQuantityInfo();
    }


}