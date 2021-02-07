package ru.geekbrains.module2.lesson5;

import java.util.Arrays;


/**
 * Класс реализует два метода, один проходит по массиву в главном потоке, второй в двух открытых потоках
 */
public class Main {
    public static long a = System.currentTimeMillis();

    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }


    public static void firstMethod() {
        BigArray bigArray = new BigArray();
        Arrays.fill(bigArray.arr, 1);
        a = System.currentTimeMillis();
        bigArray.calculations(bigArray.arr);
        System.out.println("Время выполнения первого метода в мс - " + (System.currentTimeMillis() - a));
    }

    public static void secondMethod() {
        BigArray bigArray = new BigArray();
        Arrays.fill(bigArray.arr, 1);
        a = System.currentTimeMillis();
        float[] a1 = new float[bigArray.h];
        float[] a2 = new float[bigArray.h];

        // Решил оставить мат действия в теле потока, что бы не замедлять процесс вычислений
        Thread t1 = new Thread(() -> {
//            synchronized (bigArray) {
            System.arraycopy(bigArray.arr, 0, a1, 0, bigArray.h);
            for (int i = 0; i < a1.length; i++)
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.arraycopy(a1, 0, bigArray.arr, 0, bigArray.h);
//            }
        });

        Thread t2 = new Thread(() -> {
//            synchronized (bigArray) {
            System.arraycopy(bigArray.arr, bigArray.h, a2, 0, bigArray.h);
            for (int i = 0; i < a2.length; i++)
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            System.arraycopy(a2, 0, bigArray.arr, bigArray.h, bigArray.h);
//            }

        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        System.out.println("Время выполнения второго метода в мс - " + (System.currentTimeMillis() - a));

    }

}
