package ru.geekbrains.module1.lesson6;


public abstract class Animals {
    protected String name;
    protected int runValue;
    protected double jumpValue;
    protected int swimValue;
    private static int animalsAmount = 0;

    protected Animals(String name, int runValue, double jumpValue, int swimValue) {
        this.name = name;
        this.runValue = (int) randomStrength(runValue);
        this.jumpValue = randomStrength(jumpValue);
        this.swimValue = (int) randomStrength(swimValue);
        animalsAmount++;
    }

    public void run(int distance) {
        if (this.runValue >= distance)
            System.out.println(this.name + " успешно пробежал " + distance + " метров");
        else System.out.println(this.name + " пробежал только " + this.runValue + " метров из " + distance);
    }

    public void jump(double height) {
        if (this.jumpValue >= height)
            System.out.println(this.name + " успешно прыгнул на " + height + " метров");
        else System.out.printf("%s прыгнул только на %.1f метров из %.1f\n", this.name, this.jumpValue, height);
    }

    public void swim(double distance) {
        if (this.swimValue >= distance)
            System.out.println(this.name + " успешно проплыл " + distance + " метров");
        else System.out.println(this.name + " проплыл только " + this.swimValue + " метров из " + distance);
    }

    // Метод случайного разброса характеристик животных

    private double randomStrength(double defaultStrength) {
        return (defaultStrength * (Math.random() + 0.5));
    }

    @Override
    public String toString() {
        return "Animals{" +
                "name='" + name + '\'' +
                ", runValue=" + runValue +
                ", jumpValue=" + jumpValue +
                ", swimValue=" + swimValue +
                '}';
    }

    public static void getQuantityInfo() {
        System.out.println("\nВсего животных - " + animalsAmount + ", из них: Котов - " + Cat.getCatsAmount() + " Собак - " + Dog.getDogsAmount());
    }

}
