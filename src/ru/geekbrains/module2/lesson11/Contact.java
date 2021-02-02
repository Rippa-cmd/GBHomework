package ru.geekbrains.module2.lesson11;

/**
 * Класс контакта, хранит фамилию и телефонный номер
 */
public final class Contact {
    private String surname;
    private String phone;

    public Contact(String surname, String phone) {
        this.surname = surname;
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }
}
