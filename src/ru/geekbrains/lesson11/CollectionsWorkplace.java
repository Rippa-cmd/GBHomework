package ru.geekbrains.lesson11;

import java.util.*;

/**
 * Основной класс программы, выводит на экран уникальные слова из массива слов, и считает количество повторений;
 * И заполнает Телефонный справочник данными, после чего по запросу Фамилии выдает телефонные номера
 */
public class CollectionsWorkplace {
    public static void main(String[] args) {
        String[] words = {"apple", "orange", "garlic", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "avocado", "cherry",
                "garlic", "banana", "broccoli", "avocado", "apple", "cherry", "garlic"};

        countRepeats(words);

        fillTheDictionary(20);

        PhoneDirectory.add("Галкин", "8-800-555-35-35");

        PhoneDirectory.get("Андреев");
        PhoneDirectory.get("Петров");
        PhoneDirectory.get("Галкин");

    }

    // Преобразует массив строк в коллекцию типа HashSet, что бы выделить уникальные слова, после чего считает сколько
    // раз эти слова повторялись и выводит информацию в терминал
    public static void countRepeats(String[] words) {
        Set<String> setWords = new HashSet<>();
        Collections.addAll(setWords, words);
        int repeats;
        for (String word : setWords) {
            repeats = 0;
            for (String s : words) {
                if (s.equals(word)) repeats++;
            }
            if (repeats == 1)
                System.out.println(word + " уникально");
            else
                System.out.println(word + " встречалось " + repeats + " раз");
        }
        System.out.println();
    }

    // Автоматическое заполнение словаря (рандомно, фамилии из массива и номера )
    public static void fillTheDictionary(int count) {
        String[] surnames = {"Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов", "Михайлов",
                "Новиков", "Федоров", "Морозов", "Волков", "Алексеев", "Лебедев", "Семенов", "Егоров", "Павлов",
                "Козлов", "Степанов", "Николаев", "Орлов", "Андреев", "Макаров", "Никитин", "Захаров", "Зайцев",
                "Соловьев", "Борисов"};
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            PhoneDirectory.add(surnames[rand.nextInt(surnames.length)], "+" + rand.nextInt(2147483647));
        }
    }
}
