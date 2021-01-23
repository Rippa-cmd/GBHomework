package ru.geekbrains.lesson7;

public class Strings {
    public static void main(String[] args) {
        stringOperations("I like Java!!!");
    }

    private static void stringOperations(String s) {
        String wordJava = "Java";
        System.out.println("Последний символ - '" + s.charAt(s.length() - 1) + "'");
        System.out.println("Заканчивается ли строка на '!!!' - " + s.endsWith("!!!"));
        System.out.println("Начинается ли строка на \"I like\" - " + s.startsWith("I like"));
        System.out.println("содержит ли ваша строка подстроку " + wordJava + " - " + s.contains(wordJava));
        int posOfWord = s.indexOf(wordJava);
        System.out.println("Позиция подстроки " + wordJava + " - " + posOfWord);
        System.out.println("Заменить все символы “а” на “о” - " + s.replace("a", "o"));
        System.out.println("Преобразование к верхнему регистру - " + s.toUpperCase());
        System.out.println("Преобразование к нижнему регистру" + s.toLowerCase());
        //Вырезанное слово Java
        System.out.println("Вырезать строку " + wordJava + " - " + s.substring(posOfWord, (posOfWord + wordJava.length())));
        //Строка без вырезанного слова Java через метод String.substring()
        System.out.println("Без вырезанной строки " + wordJava + " - " + s.replaceFirst(s.substring(posOfWord, (posOfWord + wordJava.length())), ""));
    }
}
