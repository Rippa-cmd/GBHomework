package ru.geekbrains.module1.lesson7;

public class Plate {

    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public Plate() {
        this(50);
    }

    public void addFood(int food) {
        this.food += food;
        System.out.println("Added " + food + " food");
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void getInfo() {
        System.out.println("In plate " + food + " food\n");
    }
}
