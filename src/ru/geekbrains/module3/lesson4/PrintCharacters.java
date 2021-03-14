package ru.geekbrains.module3.lesson4;

public class PrintCharacters {

    private volatile char symbol = 'A';

    public PrintCharacters() {
        new Thread(() -> printChar('A')).start();
        new Thread(() -> printChar('B')).start();
        new Thread(() -> printChar('C')).start();
    }

    private synchronized void printChar(char character) {
        for (int i = 0; i < 5; i++) {
            while (symbol != character) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            System.out.print(symbol);
            switch (character) {
                case ('A'):
                    symbol = 'B';
                    break;
                case ('B'):
                    symbol = 'C';
                    break;
                case ('C'):
                    symbol = 'A';
                    break;
            }
            notifyAll();
        }
    }
}
