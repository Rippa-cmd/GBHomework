package ru.geekbrains.lesson5;

import java.util.Random;

public class Person {
    private String fullName;
    private String position;
    private String email;
    private String phoneNumber;
    private int salary;
    private int age;

    public static void main(String[] args) {
        Random RAND = new Random();
        Person[] persArray = new Person[5];
        String[] names = {"Vladislav", "Vladimir", "Andrey", "Alexandr", "Oleg", "Ivan", "Sergey", "Mihail"};
        String name;
        // "Рандомное" заполнение массива объектов
        for (int person = 0; person < persArray.length; person++) {
            name = names[RAND.nextInt(names.length)];
            persArray[person] = new Person(name+" "+names[RAND.nextInt(names.length)]+"ich", "Developer", name+RAND.nextInt(50)+"@mailbox.com", "8("+(RAND.nextInt(20)+900)+")-"+RAND.nextInt(10)*100+"-55-65", (RAND.nextInt(7)+3)*10000, (RAND.nextInt(26)+25));
            persArray[person].moreThanForty();
        }
        //persArray[0].getInfo();
    }

    // Заполнение объекта "Сотрудник"

    public Person(String fullName, String position, String email, String phoneNumber, int salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public void moreThanForty(){
        if (this.age>40)
            this.getInfo();
    }

    // Вывод информации об сотруднике в консоль

    public void getInfo() {
        System.out.println("Name - "+this.fullName+"\nPosition - "+this.position+"\nEmail - "+this.email+"\nNumber - "+this.phoneNumber+"\nSalary - "+this.salary+"\nAge - "+this.age+"\n \n");
    }
}