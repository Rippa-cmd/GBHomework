package ru.geekbrains.lesson9.model;

import ru.geekbrains.lesson9.interfaces.RunAndJump;


/**
 * Класс людей, плохи в прыжках, но хороши в беге на дальние дистанции
 */
public class Human implements RunAndJump {
    private String name;
    private double maxJumpHeight;
    private int maxRunDistance;
    private boolean canParticipate = true;

    public Human(String name, double maxJumpHeight, int maxRunDistance) {
        this.name = name;
        this.maxJumpHeight = maxJumpHeight;
        this.maxRunDistance = maxRunDistance;
    }

    public Human(double maxJumpHeight, int maxRunDistance) {
        this("Abstract human", maxJumpHeight, maxRunDistance);
    }

    public Human(String name) {
        this(name, 1, 1000);
    }

    public Human() {
        this("Abstract human", 1, 1000);
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
