package ru.geekbrains.module3.lesson5.models;

import ru.geekbrains.module3.lesson5.MainApp;

public class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);

                // Ждем очереди в туннель
                MainApp.semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);

                // Выходим из очереди
                MainApp.semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
