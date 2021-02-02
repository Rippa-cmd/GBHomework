package ru.geekbrains.module2.lesson1.model;

import ru.geekbrains.module2.lesson1.interfaces.ParticipantInterface;


/**
 * Класс котов, хороши в прыжках, по плохи в беге на дальние дистанции
 */
public class Cat implements ParticipantInterface {
    private String name;
    private double maxJumpHeight;
    private int maxRunDistance;
    private boolean canParticipate = true;


    public Cat(String name, double maxJumpHeight, int maxRunDistance) {
        this.name = name + " (Cat)";
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunDistance = maxRunDistance;
    }

    public Cat(double maxJumpHeight, int maxRunDistance) {
        this("Abstract cat", maxJumpHeight, maxRunDistance);
    }

    public Cat(String name) {
        this(name, 2, 600);

    }

    public Cat() {
        this("Abstract Cat", 2, 600);
    }


    // Метод бега
    @Override
    public void run(int distance) {
        System.out.println(name + " is running");
        if (distance <= maxRunDistance)
            System.out.println(name + " could run " + distance + '\n');
        else {
            System.out.println(name + " could run just " + maxRunDistance + " and he's out\n");
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
            System.out.printf("%s could jump just %.2f and he's out\n\n", name, maxJumpHeight);
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
