package ru.geekbrains.module2.lesson4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Класс с методами по лямба выражениям и метод main
 */
public class Lambda {

    // Глобальные поля для лямба выражения
    private static int index;
    private static String reversed = "";
    private static Integer maximumNumber;
    private static Double avr = (double) 0;

    public static void main(String[] args) {

        // Задание по Swing
        // 1
        new ChatFrame();

        // Задания по lambda выражениям
        // 2
        System.out.println(search(3, Arrays.asList(1, 2, 3, 3, 5, 6, 7, 3)));

        // 3
        System.out.println(reverse("Hello World!"));

        // 4
        System.out.println(maximum(Arrays.asList(1, 2, 23, 3, 5, 6, 7, 15)));

        // 5
        System.out.println(average(Arrays.asList(1, 2, 23, 3, 5, 6, 7, 15)));

        // 6
        for (String s : search(Arrays.asList("a12", "andi", "ara", "vkys", "and", "sapling", "ass"))) {
            System.out.print(s + " ");
        }
    }

    // Ищем индекс первого вхождения данного целого числа в списке, иначе -1
    public static int search(Integer n, List<Integer> list) {
        index = -1;
        list.forEach(x -> {
            if (x.equals(n)) index = list.indexOf(x);
        });
        return index;
    }

    // Переворачиваем строку
    public static String reverse(String s) {
        s.chars().forEach(x -> reversed = (char) x + reversed);
        return reversed;
    }

    // Поиск максимума из списка
    public static Integer maximum(List<Integer> list) {
        if (list.size() != 0)
            maximumNumber = list.get(0);
        list.forEach(x -> {
            if (x > maximumNumber) maximumNumber = x;
        });
        return maximumNumber;
    }

    // Нахождение среднего арифметического чисел списка
    public static Double average(List<Integer> list) {
        list.forEach(x -> avr += x);
        return avr / list.size();
    }

    // Поиск строк, имеющие ровно 3 буквы и начинающиеся на 'a'
    public static List<String> search(List<String> list) {
        return list.stream().filter(x -> x.charAt(0) == 'a' && x.length() == 3).collect(Collectors.toList());
    }
}
