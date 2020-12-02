package ru.geekbrains.lesson2;

public class lessonArrays
{
    public static void main(String[] args)
{
    changeValue();
    fillArray();
    multiplicationByTwo();
    diagonal(10);
    minMax();
    checkBalance(new int[]{1, 2, 3, 4, 5, 14, 1});
    shiftTheArray(new int[]{0,1,2,3,4}, 5);
}

    static void changeValue() //Первое задание
    {
        int[] simpleArray = { 1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < simpleArray.length; i++) //перебор массива
        {
            if (simpleArray[i]==0)
                simpleArray[i]=1;       //замена 0 на 1, и 1 на 0
            else simpleArray[i]=0;
            System.out.print(simpleArray[i]+" ");
        }
        System.out.println();
    }

    static void fillArray() //Заполнение пустого целочисленного массив размером 8 с помощью цикла
    {
        int plusThree = 0;
        int[] emptyArray8 = new int[8];
        for (int i = 0; i < emptyArray8.length; i++)   //перебор массива
        {
            emptyArray8[i] = plusThree;
            plusThree+=3;                   //запись в ячейку массива переменной и увеличение ее на 3
            System.out.print(emptyArray8[i]+" ");
        }
        System.out.println();
    }

    static void multiplicationByTwo() //Умножение на 2 элементов массива меньше 6
    {
        int[] pseudoRandomArray = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < pseudoRandomArray.length; i++)
        {
            if (pseudoRandomArray[i]<6)
                pseudoRandomArray[i]*=2;
            System.out.print(pseudoRandomArray[i]+" ");
        }
        System.out.println();
    }

    static void diagonal(int lenght)   //Создание квадратного массив и заполнение его диагональных элементов единицами;
    {
        int[][] squareDiagonalArray = new int[lenght][lenght];
        int secondDiagonal = squareDiagonalArray.length;
        for (int i = 0; i < squareDiagonalArray.length; i++) //заполнение диагональных элементов через цикл
        {
            secondDiagonal-=1;
            squareDiagonalArray[i][i] = 1;
            squareDiagonalArray[i][secondDiagonal] = 1;
        }
//        for (int i = 0; i < squareDiagonalArray.length; i++){          //Вывод результата в консоль
//            for (int b = 0; b < squareDiagonalArray.length; b++){
//                System.out.print(squareDiagonalArray[i][b]+"\t");
//            }
//            System.out.println("");
//        }

    }

    static void minMax() //Поис минимального и максимального элемента массива
    {
        int[] array = { 5, 7, 96, 8, 41, 61, 97, 15, 12 };
        int min, max = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i]>max)
                max=array[i];
        }
        min=max;
        for (int i = 0; i < array.length; i++)             //можно было бы реализовать в одном цикле путем задания максимального допустимого значения в min,
        {                                                  //но так как без помощи интернета, а максимальное значение не помню (и 0x111111.. и тд тоже) то вот так, главное работает
            if (array[i]<min)
                min=array[i];
        }
    }

    static boolean checkBalance(int[] checkArray)   //Проверка суммы левой и правой части массива
    {
        int checkSumBefore = 0, checkSumAfter = 0;
        for (int i = 0; i < checkArray.length-1; i++)   //Для защиты от выхода за пределы массива 'checkArray.length-1' при подсчете суммы правой части массива
        {
            for (int b = i; b >= 0; b--)
                checkSumBefore += checkArray[b]; //подсчет суммы левой части

            for (int c = i+1; c < checkArray.length; c++)  //'c = i+1' что бы начинать суммировать со следующего элемента
                checkSumAfter += checkArray[c];  ////подсчет суммы правой части
            if (checkSumAfter==checkSumBefore) return true;
            checkSumBefore = 0;   //обнуление сумм для повторной проверки
            checkSumAfter = 0;
        }
        return false;  // если части массива равны по сумме, возвращает true из цикла, в противном случае цикл завершается и возвращается false
    }

    static int[] shiftTheArray(int[] madArray, int n)  //Смещение массива
    {
        int firstTemp = madArray[0], secondTemp = 0, timer = 0;  //два буфера для смещения элементов, один счетчик повторений (защита от закольцовывания)
        if (n < 0) {                                    // сведение к виду 0<n<madArray.lenght
            do n += madArray.length;
            while (n < 0);
        }
        else if (n > madArray.length)
            n %=madArray.length;
        if (n==0||n==madArray.length) return madArray;
        int shiftN = n;
        for (int i = 0; i < madArray.length; i++) {  //реализовал смещение массивов поэлементно через n элементов по кругу
            if (shiftN%madArray.length!=timer) {
                secondTemp = madArray[shiftN % madArray.length];
                madArray[shiftN % madArray.length] = firstTemp;
                firstTemp = secondTemp;
                shiftN += n;
            } else {                                //если круг зациклился на тех же элементах, переходит на следующий круг
                madArray[shiftN % madArray.length] = firstTemp;
                firstTemp = madArray[(shiftN+1) % madArray.length];
                shiftN+=n+1;
                timer++;
            }
        }
        for (int i = 0; i < madArray.length; i++)
            System.out.print(madArray[i]+" ");
        return madArray;
    }
}
