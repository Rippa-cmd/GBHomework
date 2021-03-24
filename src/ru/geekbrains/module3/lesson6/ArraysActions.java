package ru.geekbrains.module3.lesson6;

public class ArraysActions {
    public int[] searchAfterFour(int[] arr) {
        int lastFour = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4)
                lastFour = i;
        }
        if (lastFour == arr.length - 1) {
            return null;
        } else if (lastFour != -1) {
            lastFour++;
            int[] newArray = new int[arr.length - lastFour];
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = arr[lastFour];
                lastFour++;
            }
            return newArray;
        }
        throw new RuntimeException();
    }

    public boolean containsOneAndFour(int[] arr) {
        boolean one = false;
        boolean four = false;

        for (int i = 0; i < arr.length; i++) {
            if (!one)
                if (arr[i] == 1)
                    one = true;
            if (!four)
                if (arr[i] == 4)
                    four = true;
            if (one && four)
                return true;
        }
        return false;
    }
}
