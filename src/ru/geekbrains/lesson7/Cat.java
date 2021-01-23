package ru.geekbrains.lesson7;

import java.util.Random;

public class Cat {

    private String name;
    private int hungry;
    private boolean fullness;
    private static int catID = 1;
    private static final Random RAND = new Random();

    public Cat(String name, int hungry) {
        this.name = name;
        this.hungry = hungry;
        if (hungry > 0) fullness = false;
        catID++;
    }

    public Cat() {
        this("Cat" + catID, Cat.RAND.nextInt(30));
    }

    public boolean eat(Plate plate) {
        if (fullness) {
            System.out.println(name + " not hungry\n");
            return true;
        }
        System.out.println(name + " want to eat " + hungry + " food");
        if (plate.getFood() >= hungry) {
            plate.setFood(plate.getFood() - hungry);
            hungry = 0;
            System.out.println(name + " finished eating");
            plate.getInfo();
            fullness = true;
            return true;
        } else
            System.out.println("Too little food for " + name + '\n');
        return false;

    }

    public int getHungry() {
        return hungry;
    }

    public boolean getFullness() {
        return fullness;
    }

    public void getInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", hungry=" + hungry +
                ", fullness=" + fullness +
                '}';
    }
}
