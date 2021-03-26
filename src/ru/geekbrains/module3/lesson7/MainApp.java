package ru.geekbrains.module3.lesson7;


import ru.geekbrains.module3.lesson7.annotations.AfterSuite;
import ru.geekbrains.module3.lesson7.annotations.BeforeSuite;
import ru.geekbrains.module3.lesson7.annotations.JustTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Класс, тестирующий объект типа Class, вызывающий методы с аннотациями:
 * JustTest по приоритетности (от 1 до 10),
 * BeforeSuite перед всеми тестами,
 * AfterSuite после всех тестов,
 *
 * @throws RuntimeException если в тестируемом классе больше одного метода с аннотацией @AfterSuite или @BeforeSuite
 */
public class MainApp {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        start(ClassForTest.class);
    }

    /**
     * Метод, принимающий на вход обьект типа Class, тестирующий методы согласно аннотациям
     *
     * @param classObject тестирующий обьект типа Class
     */
    public static void start(Class classObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        // Разбиваем класс на методы
        Method[] methods = classObject.getDeclaredMethods();
        LinkedList<Method> methodLinkedList = new LinkedList<>();

        // Счетчики для проверки повторений аннотаций
        boolean hasBeforeSuiteAnnotation = false;
        boolean hasAfterSuiteAnnotation = false;

        // Добавляем в LinkedList методы с аннотацией @JustTest
        for (Method method : methods) {
            if (method.getAnnotation(JustTest.class) != null) {
                methodLinkedList.add(method);
            }
        }

        // Сортируем LinkedList с тестируемыми методами согласно приоритету
        Collections.sort(methodLinkedList, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o2.getAnnotation(JustTest.class).priority() - o1.getAnnotation(JustTest.class).priority();
            }
        });

        // Ищем методы с аннотациями @BeforeSuite и @AfterSuite
        for (Method method : methods) {

            // Если находим @BeforeSuite, добавляем в начало, если их несколько - выбрасываем исключение
            if (method.getAnnotation(BeforeSuite.class) != null) {
                if (!hasBeforeSuiteAnnotation) {
                    methodLinkedList.addFirst(method);
                    hasBeforeSuiteAnnotation = true;
                } else throw new RuntimeException();
            }

            // Если находим @AfterSuite, добавляем в конец, если их несколько - выбрасываем исключение
            if (method.getAnnotation(AfterSuite.class) != null) {
                if (!hasAfterSuiteAnnotation) {
                    methodLinkedList.addLast(method);
                    hasAfterSuiteAnnotation = true;
                } else throw new RuntimeException();
            }
        }

        // Вызываем методы по списку
        for (Method method : methodLinkedList) {
            method.setAccessible(true);
            method.invoke(classObject.getConstructor().newInstance());
        }
    }
}
