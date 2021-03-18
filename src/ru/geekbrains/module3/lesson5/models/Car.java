package ru.geekbrains.module3.lesson5.models;

import ru.geekbrains.module3.lesson5.MainApp;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            // Ждет старта
            MainApp.barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        // Проверка на лидерство и запись (тройка лидеров) после финиша
        victoryPlace();
    }

    private void victoryPlace() {
        try {
            
            // Блокируем CountDownLatch для проверки, каким по счету финишировали
            MainApp.lock.lock();
            switch ((int) MainApp.countDownLatch.getCount()) {
                case (MainApp.CARS_COUNT):
                    MainApp.winners[0] = this.name;
                    break;
                case (MainApp.CARS_COUNT - 1):
                    MainApp.winners[1] = this.name;
                    break;
                case (MainApp.CARS_COUNT - 2):
                    MainApp.winners[2] = this.name;
                    break;
            }

            // Даем знать, что мы финишировали
            MainApp.countDownLatch.countDown();
        } finally {
            MainApp.lock.unlock();
        }
    }
}
