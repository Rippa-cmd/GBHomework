package ru.geekbrains.module1.lesson1;

public class FirstApp {
    public static void main(String[] args) //задание №1
    {
        task2();
        task3(2,2,6,5);
        task4(14,5);
        task5(-3);
        task6(5);
        task7("Rippa");
        task8(2000);

    }

    static void task2() //Создание переменных всех пройденных типов данных, и инициализирование их значения;
    {
        byte a = 1;
        short b = 2;
        int c = 4;
        long d = 8;

        float fl = 4.4f;
        double dbl = 8.7;

        char ch = 123; // or char ch = '}';
        boolean i = true;

        String first = "строковая";
    }

    static double task3(double a, double b, double c, double d) //Вычисление и возвращение результата выражения
    {
        return(a*(b+(c/d)));
        /* или
        double result;
        result = a*(b+(c/d));
        return(result);
         */
    }

    static boolean task4(int a, int b) //Проверка суммы на принадлежность диапазону от 10 до 20(включительно)
    {
        if (((a+b)>=10)&&((a+b)<=20))
        {
            return(true);
        }
        else return(false);
    }

    static void task5(int a) //Проверка на положительность\отрицательность
    {
        if (a>=0)
            System.out.println("Число положительное");
        else System.out.println("Число отрицательное");

    }

    static boolean task6(int a) //Проверка на отрицательность
    {
        if (a<0) return(true);
        else return(false);
    }

    static void task7(String name) //Приветствие в чате указанного имени
    {
        System.out.println("Привет, "+name+"!");
    }

    static void task8(int year) //Проверка на високосность года
    {
        if (((year%4)==0)&&((year%100)!=0))
            System.out.println("Год високосный");
        else if ((year%400)==0)
            System.out.println("Год високосный");
        else System.out.println("Год не високосный");
    }
}