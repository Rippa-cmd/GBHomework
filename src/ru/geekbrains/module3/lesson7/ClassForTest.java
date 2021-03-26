package ru.geekbrains.module3.lesson7;

import ru.geekbrains.module3.lesson7.annotations.AfterSuite;
import ru.geekbrains.module3.lesson7.annotations.BeforeSuite;
import ru.geekbrains.module3.lesson7.annotations.JustTest;

/**
 * Тестируемый класс для тестов с надлежащими аннотациями
 */
public class ClassForTest {

    @JustTest
    public void defaultPriority() {
        System.out.println(5);
    }

    @JustTest(priority = 5)
    private static void fivePriority() {
        System.out.println(5);
    }

    @JustTest(priority = 1)
    public String onePriority() {
        System.out.println(1);
        return "one";
    }

    @JustTest(priority = 10)
    private void tenPriority() {
        System.out.println(10);
    }

    //@BeforeSuite
    public void anotherBeforeSuite() {
        System.out.println("Must be exception");
    }

    @JustTest(priority = 9)
    public void ninePriority() {
        System.out.println(9);
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Test started");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Test stopped");
    }

    public void withoutAnnotation() {
        System.out.println("Must not start");
    }
}
