package ru.geekbrains.module2.lesson1.model;

import ru.geekbrains.module2.lesson1.interfaces.ParticipantInterface;

/**
 * Класс роботов, хороши и в беге и в прыжках, на то они и роботы
 */

public class Robot implements ParticipantInterface {
    private String name;
    private double maxJumpHeight;
    private int maxRunDistance;
    private boolean canParticipate = true;

    public Robot(String name, double maxJumpHeight, int maxRunDistance) {
        this.name = name + " (Robot)";
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunDistance = maxRunDistance;
    }

    public Robot(double maxJumpHeight, int maxRunDistance) {
        this("Unknown Model", maxJumpHeight, maxRunDistance);
    }

    public Robot(String name) {
        this(name, 4, 3000);
    }

    public Robot() {
        this("Unknown Model", 4, 3000);
    }


    // Метод бега
    @Override
    public void run(int distance) {
        System.out.println(name + " is running");
        if (distance <= maxRunDistance)
            System.out.println(name + " could run " + distance + '\n');
        else {
            System.out.println(name + " could run just " + maxRunDistance + " and it's out\n");
            canParticipate = false;
        }
    }


    // Метод прыжка
    @Override
    public void jump(double height) {
        System.out.println(name + " is jumping");
        if (height <= maxJumpHeight)
            System.out.println(name + " could jump " + height + '\n');
        else {
            System.out.printf("%s could jump just %.2f and it's out\n\n", name, maxJumpHeight);
            canParticipate = false;
        }
    }

    public double getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public int getMaxRunDistance() {
        return maxRunDistance;
    }

    public boolean isCanParticipate() {
        return canParticipate;
    }
}
