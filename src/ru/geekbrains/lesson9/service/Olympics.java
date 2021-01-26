package ru.geekbrains.lesson9.service;

import ru.geekbrains.lesson9.interfaces.ParticipantInterface;
import ru.geekbrains.lesson9.model.*;

/**
 * Основной класс логики спортивных состязаний, с заданным количеством участников и препятствий
 */
public class Olympics {
    private static final int quantityOfParticipants = 20;
    private static final int quantityOfObstacle = 10;

    // Главный метод класса, с заполнением массивов участниками и препятствиями и начала испытаний
    public static void main(String[] args) {

        ParticipantInterface[] participants = new ParticipantInterface[quantityOfParticipants];
        Object[] obstacle = new Object[quantityOfObstacle];

        fillTheArrayOfParticipants(participants);
        fillTheArrayOfObstacle(obstacle);

        testThemAll(participants, obstacle);

    }

    // Прогон массивов с участниками и препятствиями
    public static void testThemAll(ParticipantInterface[] participant, Object[] obstacle) {
        for (int numberOfObstacle = 0; numberOfObstacle < obstacle.length; numberOfObstacle++) {
            System.out.println("Obstacle №" + (numberOfObstacle + 1) + " ---------------------------\n");
            for (ParticipantInterface o : participant) {
                tryToOvercome(o, obstacle[numberOfObstacle]);
            }
        }
    }

    // Узнаем тип препятствия и отправляем на него
    public static void tryToOvercome(ParticipantInterface participant, Object obstacle) {
        if (obstacle instanceof Wall) {
            testingJump(participant, ((Wall) obstacle).getHeight());
        } else if (obstacle instanceof RunningTrack) {
            testingRun(participant, ((RunningTrack) obstacle).getDistance());
        } else System.out.println("Wrong obstacle");
    }

    // Узнаем, кто бежит, и проверяем, допущен ли он, после чего пускаем на беговую дорожку
    public static void testingRun(ParticipantInterface participant, int distance) {
        if (participant.isCanParticipate())
            participant.run(distance);
    }

    // Узнаем, кто прыгаег, и пускаем к препятствию
    public static void testingJump(ParticipantInterface participant, double height) {
        if (participant.isCanParticipate())
            participant.jump(height);
    }

    // Равномерное заполнение массива людьми, котами и роботами c разбросом характеристик
    public static void fillTheArrayOfParticipants(ParticipantInterface[] participant) {
        for (int i = 0, j = 0; i < quantityOfParticipants; i++, j++) {
            if (j == 0) {
                participant[i] = new Human("Participant №" + (i + 1), 1 * (Math.random() + 0.5), (int) (1000 * (Math.random() + 0.5)));
                continue;
            }
            if (j == 1) {
                participant[i] = new Cat("Participant №" + (i + 1), 2 * (Math.random() + 0.5), (int) (600 * (Math.random() + 0.5)));
                continue;
            }
            participant[i] = new Robot("Participant №" + (i + 1), 4 * (Math.random() + 0.5), (int) (3000 * (Math.random() + 0.5)));
            j -= 3;
        }
    }

    // Равномерное заполнение массива препятствий стенами и беговыми дорожками с увеличением дистанции и высоты
    public static void fillTheArrayOfObstacle(Object[] obstacle) {
        for (int i = 0; i < quantityOfObstacle; i++) {
            if ((i + 1) % 2 == 0) {
                obstacle[i] = new Wall(1 + (i * 0.5));
                continue;
            }
            obstacle[i] = new RunningTrack(400 + (i * 200));
        }
    }

}
