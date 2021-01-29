package ru.geekbrains.lesson11;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс Телефонного справочника, создает коллекцию и хранит экземпляры класса Contact (фамилия и номер)
 */
public abstract class PhoneDirectory {

    private static List<Contact> phoneDirectoryList = new LinkedList<>();

    public static void add(String surname, String phone) {
        phoneDirectoryList.add(new Contact(surname, phone));
    }

    public static void get(String surname) {
        System.out.println("По запросу \""+surname+"\" :");
        for (Contact contact : phoneDirectoryList) {
            if (contact.getSurname().equals(surname))
                System.out.println(contact.getPhone());
        }
    }

}
