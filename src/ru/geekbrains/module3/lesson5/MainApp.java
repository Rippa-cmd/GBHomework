package ru.geekbrains.module3.lesson5;

import ru.geekbrains.module3.lesson5.models.Car;
import ru.geekbrains.module3.lesson5.models.Race;
import ru.geekbrains.module3.lesson5.models.Road;
import ru.geekbrains.module3.lesson5.models.Tunnel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainApp {
    public static final int CARS_COUNT = 10;
    public static final CyclicBarrier barrier = new CyclicBarrier(CARS_COUNT + 1);
    public static volatile CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
    public static final Semaphore semaphore = new Semaphore(CARS_COUNT / 2);
    public static final Lock lock = new ReentrantLock();
    public static final String[] winners = new String[3];

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        // Если не все готовы, ждем
        while (barrier.getNumberWaiting() != CARS_COUNT) {
            Thread.sleep(1000);
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        // Если все готовы, даем старт
        barrier.await();

        // Если все финишировали, завершаем соревнование
        countDownLatch.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        // Вывод победителей
        System.out.println("Встречайте победителей!!!");
        for (int i = 0; i < winners.length; i++) {
            System.out.println(i + 1 + " место - " + winners[i]);
        }
    }
}

