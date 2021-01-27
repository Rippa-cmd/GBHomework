package ru.geekbrains.lesson10;


import ru.geekbrains.lesson10.MyExeptions.MyArrayDataException;
import ru.geekbrains.lesson10.MyExeptions.MyArraySizeException;

/**
 * Класс проверяет размерность строкового массива, и суммирует все его элементы
 */
public class SumAndCheckItUp {

    // С какой ячейки продолжаем суммировать (что бы не гонять метод заного)
    private static int startRowIndex = 0;
    private static int startColIndex = 0;

    private static int sum = 0;

    // Флаг завершения работы метода
    private static boolean finished;

    // Задаем разрядность нашего массива
    private static final int maxRowIndex = 4;
    private static final int maxColIndex = 4;

    // Какой-то входящий массив
    private static String[][] array = new String[3][5];


    public static void main(String[] args) {

        // В данном случае заполняю массив циклами, так как исходно он пустой
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = "" + (i + j);
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        // Немного абсудности для теста
        array[2][3] = "Something";
        array[1][0] = "Something2";

        // Гоняем метод, пока не пройдет по всему массиву
        while (!finished) {
            try {
                checkAndSum(array);
            } catch (MyArraySizeException e) {

                // Если ловим исключени об неправильном размере, создаем "првильный" массив
                array = createTrueArray(array);
            } catch (MyArrayDataException e) {

                // Если преобразование не удалось, пишем туда 0, и продолжаем
                array[startRowIndex][startColIndex] = "0";
            }
        }

        // Вывод успешности суммирования
        System.out.println("\nSuccess! Sum = " + sum);
    }

    public static void checkAndSum(String[][] array) throws MyArraySizeException, MyArrayDataException {

        // Проверка массива на размерность 4х4
        if (array.length != maxRowIndex || array[0].length != maxColIndex) {
            throw new MyArraySizeException();
        }

        // Прогон массива по строкам и столбцам
        for (int rowIndex = startRowIndex; rowIndex < maxRowIndex; rowIndex++) {
            for (int colIndex = startColIndex; colIndex < maxColIndex; colIndex++) {

                // Пытаем распарсить строку
                try {

                    // В случаем усеха суммируем
                    sum += Integer.parseInt(array[rowIndex][colIndex]);
                } catch (NumberFormatException e) {

                    // Если не удается, запоминаем индексы ячейки и выбрасываем ошибку с данными ячейки
                    startRowIndex = rowIndex;
                    startColIndex = colIndex;
                    throw new MyArrayDataException(rowIndex, colIndex);
                }
            }

            // Если продолжали работу метода, сбрасываем индекс столбца
            startColIndex = 0;
        }

        // В случаем успеха, поднимаем флаг
        finished = true;
    }

    /**
     * Метод преобразует исходный массив в массив вида 4х4 (по конфигурации maxRowIndex x maxColIndex), отсекая лишние
     * элементы, в случае если исходный массив выходит за заданные рамки, и / или заполняет нулями добавленные элементы,
     * если исходный массив был меньше (работает только с массивами типа матрицы, зубчатые массивы будут частично
     * заполнены нулями)
     */

    public static String[][] createTrueArray(String[][] array) {

        System.out.println("Пересобираем массив\n");

        // Создаем массив с "правильными" параметрами
        String[][] trueArray = new String[maxRowIndex][maxColIndex];

        for (int rowIndex = 0; rowIndex < maxRowIndex; rowIndex++) {
            for (int colIndex = 0; colIndex < maxColIndex; colIndex++) {

                try {

                    // Если такой элемент существует, и он не пустой, то перезаписываем в новый массив
                    if (array[rowIndex][colIndex] != null)
                        trueArray[rowIndex][colIndex] = array[rowIndex][colIndex];

                        // Если пустой элемент, или ловим ошибку, пишем нулик
                    else
                        trueArray[rowIndex][colIndex] = "0";
                } catch (ArrayIndexOutOfBoundsException e) {
                    trueArray[rowIndex][colIndex] = "0";
                }

            }
        }

        // Возвращаем новый массив
        return trueArray;
    }
}
