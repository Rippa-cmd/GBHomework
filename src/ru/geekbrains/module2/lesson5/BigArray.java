package ru.geekbrains.module2.lesson5;

public class BigArray {

    public final int size = 10000000;
    public final int h = size / 2;
    public float[] arr = new float[size];

    public void calculations(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
