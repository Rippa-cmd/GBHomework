package ru.geekbrains.lesson9.service;

import ru.geekbrains.lesson9.model.*;

/**
 * Основной класс логики спортивных состязаний, с заданным количеством участников и препятствий
 */
public class Olympics {
    private static final int quantityOfParticipants = 20;
    private static final int quantityOfObstacle = 10;

    // Главный метод класса, с заполнением массивов участниками и препятствиями и начала испытаний
    public static void main(String[] args) {

        Object[] participants = new Object[quantityOfParticipants];
        Object[] obstacle = new Object[quantityOfObstacle];

        fillTheArrayOfParticipants(participants);
        fillTheArrayOfObstacle(obstacle);

        testThemAll(participants, obstacle);

    }

    // Прогон массивов с участниками и препятствиями
    public static void testThemAll(Object[] participant, Object[] obstacle) {
        for (int numberOfObstacle = 0; numberOfObstacle < obstacle.length; numberOfObstacle++) {
            System.out.println("Obstacle №" + (numberOfObstacle + 1) + " ---------------------------\n");
            for (Object o : participant) {
                tryToOvercome(o, obstacle[numberOfObstacle]);
            }
        }
    }

    // Узнаем тип препятствия и отправляем на него
    public static void tryToOvercome(Object participant, Object obstacle) {
        if (obstacle instanceof Wall) {
            testingJump(participant, ((Wall) obstacle).getHeight());
        } else if (obstacle instanceof RunningTrack) {
            testingRun(participant, ((RunningTrack) obstacle).getDistance());
        } else System.out.println("Wrong obstacle");
    }

    // Узнаем, кто бежит, и проверяем, допущен ли он, после чего пускаем на беговую дорожку
    public static void testingRun(Object participant, int distance) {
        if (participant instanceof Cat) {
            if (((Cat) participant).isCanParticipate())
                ((Cat) participant).run(distance);
        } else if (participant instanceof Human) {
            if (((Human) participant).isCanParticipate())
                ((Human) participant).run(distance);
        } else if (participant instanceof Robot) {
            if (((Robot) participant).isCanParticipate())
                ((Robot) participant).run(distance);
        } else System.out.println("Wrong participant");
    }

    // Узнаем, кто прыгаег, и пускаем к препятствию
    public static void testingJump(Object participant, double height) {
        if (participant instanceof Cat) {
            if (((Cat) participant).isCanParticipate())
                ((Cat) participant).jump(height);
        } else if (participant instanceof Human) {
            if (((Human) participant).isCanParticipate())
                ((Human) participant).jump(height);
        } else if (participant instanceof Robot) {
            if (((Robot) participant).isCanParticipate())
                ((Robot) participant).jump(height);
        } else System.out.println("Wrong participant");
    }

    // Равномерное заполнение массива людьми, котами и роботами c разбросом характеристик
    public static void fillTheArrayOfParticipants(Object[] participant) {
        for (int i = 0, j = 0; i < quantityOfParticipants; i++, j++) {
            if (j == 0) {
                participant[i] = new Human(1 * (Math.random() + 0.5), (int) (1000 * (Math.random() + 0.5)));
                continue;
            }
            if (j == 1) {
                participant[i] = new Cat(2 * (Math.random() + 0.5), (int) (600 * (Math.random() + 0.5)));
                continue;
            }
            participant[i] = new Robot(4 * (Math.random() + 0.5), (int) (3000 * (Math.random() + 0.5)));
            j -= 3;
        }
    }

    // Равномерное заполнение массива препятствий стенами и беговыми дорожками с увеличением дистанции и высоты
    public static void fillTheArrayOfObstacle(Object[] obstacle) {
        for (int i = 0; i < quantityOfObstacle; i++) {
            if (i % 2 == 0) {
                obstacle[i] = new Wall(1 + (i * 0.5));
                continue;
            }
            obstacle[i] = new RunningTrack(400 + (i * 200));
        }
    }

}
