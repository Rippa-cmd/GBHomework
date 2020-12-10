package ru.geekbrains.lesson3;

import java.util.Scanner;

public class theGame //started homework
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry",
                "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut",
                "pear", "pepper", "pineapple", "pumpkin", "potato"};
        // Загадываем случайное слово из массива и описываем переменную для ввода с клавиатуры
        String hiddenWord = words[(int) (Math.random() * (words.length - 0))], answer;
        // Ввел счетчик посимвольного сравнения для проверки, угадано ли слово, и счетчик количества выведенных символов
        int count = 0, symbols = 0;
        System.out.println("Угадайте слово:");
        // Бесконечный цикл угадывания слова, так как не получилось реализовать сравнение введенного и загаданного слов
        do {
            System.out.print("Ваше слово: ");
            // Ввод слова с клавиатуры
            answer = scan.nextLine();
            /* Цикл прогона (посимвольно) загаданного и введенного слова с двумя условиями для защиты от выхода за
             пределы одного из слов, и вывод результата сравнения
            */
            System.out.print("Загаданное слово: ");
            for (int i = 0; i < hiddenWord.length() && i < answer.length(); i++, symbols++) {
                if (answer.charAt(i) == hiddenWord.charAt(i)) {
                    System.out.print(answer.charAt(i));
                    count++;
                } else System.out.print("#");
            }
            /* Если все буквы на своих местах, выход из цикла угадывания слова
               Второе условие для защиты, когда введенное слово содержит загаданное слово, но отличается от него
             */
            if (count == hiddenWord.length() && count == answer.length())
                break;
            // Иначе заполнение остальных мест символом/символами '#' до 15
            for (int i = symbols; i <= 15; i++)
                System.out.print("#");
            System.out.println('\n' + "Попробуйте еще раз!");
            // Обнуление счетчиков
            count = 0;
            symbols = 0;
        } while (1 > 0);
        System.out.println('\n' + "Поздравляем, вы угадали загаданное слово!");
    }
}
