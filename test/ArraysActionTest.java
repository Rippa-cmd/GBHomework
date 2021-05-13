import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geekbrains.module3.lesson6.ArraysActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ArraysActionTest {

    private int[] actual;
    private int[] expected;

    private static ArraysActions arraysActions;


    @BeforeAll
    public static void init() {
        arraysActions = new ArraysActions();
    }

    // В одном тесте

    @ParameterizedTest
    @CsvSource({
            "1 2 3 4 5, 5",
            "1 3 2 4 5 4 1 3, 1 3",
            "1 1, java.lang.RuntimeException",
            "2 3 4 5 4 6 4,",
            "10 2 4 12 3 2 4 1, 1",
            "4 1, 1",
            "1 2 3 5 6 7 8, java.lang.RuntimeException",
    })
    public void sumTest(String first, String second) {
        actual = Stream.of(first.split(" ")).mapToInt(Integer::parseInt).toArray();
        if (second != null && second.equals("java.lang.RuntimeException")) {
            assertThrows(RuntimeException.class, () -> arraysActions.searchAfterFour(actual));
            return;
        }

        if (second != null)
            expected = Stream.of(second.split(" ")).mapToInt(Integer::parseInt).toArray();

        assertArrayEquals(expected, arraysActions.searchAfterFour(actual));
    }

    @ParameterizedTest
    @CsvSource({
            "1 1 4 1 4, true",
            "1 1 1 1 1 1 1 1, false",
            "1 4, true",
            "4 4 4 4, false",
            "3, false",
    })
    public void containsOneAndFourTestOld(String first, String second) {
        actual = Stream.of(first.split(" ")).mapToInt(Integer::parseInt).toArray();
        if (second.equals("false"))
            assertFalse(arraysActions.containsOneAndFour(actual));
        else
            assertTrue(arraysActions.containsOneAndFour(actual));
    }


    // Набор тестов


    /**
     * Провераяет метод на выбрасываемость исключения RuntimeException (должен выбросить, если нет '4' в входящем массиве)
     *
     * @param first     входящий массив
     * @param className класс исключения
     */

    @ParameterizedTest
    @MethodSource("arrWithNoFour")
    public void searchAfterFourTestOne(int[] first, Class className) {
        assertThrows(className, () -> arraysActions.searchAfterFour(first));
    }

    /**
     * Провераяет возвращаемый методом массив
     *
     * @param first  входящий массив
     * @param second ожидаемый массив
     */
    @ParameterizedTest
    @MethodSource("arrWithFourAndNotNullExpectedArr")
    public void searchAfterFourTestTwo(int[] first, int[] second) {
        assertArrayEquals(second, arraysActions.searchAfterFour(first));
    }

    /**
     * Проверяет возвращаемый методом массив, должен быть null
     *
     * @param first входящий массив
     */
    @ParameterizedTest
    @MethodSource("arrWithFourAndNullExpectedArr")
    public void searchAfterFourTestThree(int[] first) {
        assertArrayEquals(null, arraysActions.searchAfterFour(first));
    }

    /**
     * Открывает поток массивов, содержащих последним элементом '4'
     *
     * @return поток массивов
     */

    public static Stream<Object> arrWithFourAndNullExpectedArr() {
        List<Object> list = new ArrayList<>();
        list.add(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 4});
        list.add(new int[]{4, 3, 32, 34, 54, 4});
        list.add(new int[]{9, 4, 1, 22, 54, 4, 5, 4});
        list.add(new int[]{4, 4, 5, 4, 4, 4, 5, 4});
        return list.stream();
    }

    /**
     * Открывает поток массивов, содержищие '4', и ожидаемый массив
     *
     * @return поток массивов
     */

    public static Stream<Object> arrWithFourAndNotNullExpectedArr() {
        List<Object> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[]{5, 6, 7, 8, 9}));
        list.add(Arguments.arguments(new int[]{4, 3, 32, 34, 54, 41}, new int[]{3, 32, 34, 54, 41}));
        list.add(Arguments.arguments(new int[]{9, 4, 1, 22, 54, 4, 5, 1}, new int[]{5, 1}));
        list.add(Arguments.arguments(new int[]{4, 1}, new int[]{1}));
        return list.stream();
    }

    /**
     * Открывает поток массивов, не содержащие в себе '4', и класс исключения
     *
     * @return поток массивов и исключений
     */
    public static Stream<Object> arrWithNoFour() {
        List<Object> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 2, 3, 5, 6, 7, 8, 9}, RuntimeException.class));
        list.add(Arguments.arguments(new int[]{44, 67, 32, 34, 54, 41}, RuntimeException.class));
        list.add(Arguments.arguments(new int[]{9, 0, 1, 22, 54, 2, 5, 1}, RuntimeException.class));
        list.add(Arguments.arguments(new int[]{1}, RuntimeException.class));
        return list.stream();
    }


    /**
     * Проверяем возвращаемое методом значение, должно быть true
     *
     * @param actual массив, содержащий 1 и 4
     */

    @ParameterizedTest
    @MethodSource("arrWithOneAndFour")
    public void containsOneAndFourTestOne(int[] actual) {
        assertTrue(arraysActions.containsOneAndFour(actual));
    }

    /**
     * Проверяем возвращаемое методом значение, должно быть false
     *
     * @param actual массив, не содержащий 1 и 4
     */

    @ParameterizedTest
    @MethodSource("arrWithoutOneAndFour")
    public void containsOneAndFourTestTwo(int[] actual) {
        assertFalse(arraysActions.containsOneAndFour(actual));
    }

    /**
     * Проверяем возвращаемое методом значение, должно быть false
     *
     * @param actual массив, не содержащий 1
     */

    @ParameterizedTest
    @MethodSource("arrWithoutOne")
    public void containsOneAndFourTestThree(int[] actual) {
        assertFalse(arraysActions.containsOneAndFour(actual));
    }

    /**
     * Проверяем Возвращаемое методом значение, должно быть false
     *
     * @param actual массив, не содержащий 4
     */
    @ParameterizedTest
    @MethodSource("arrWithoutFour")
    public void containsOneAndFourTestFour(int[] actual) {
        assertFalse(arraysActions.containsOneAndFour(actual));
    }

    /**
     * Открывает поток массивов, содержащие в себе '1' и '4'
     *
     * @return поток массивов, содержащие 1 и 4
     */

    public static Stream<Object> arrWithOneAndFour() {
        List<Object> list = new ArrayList<>();
        int[] arr;
        for (int numberOfArrays = 0; numberOfArrays < 100; numberOfArrays++) {
            int arrLength = (int) (Math.random() * 100) + 4;
            arr = new int[arrLength];
            for (int j = 0; j < arrLength; j++) {
                arr[j] = (int) (Math.random() * 10);
            }

            // Гарантия, что будут 1 и 4
            arr[arr.length / 2 + 1] = 1;
            arr[arr.length / 2 - 1] = 4;

            list.add(arr);
        }
        return list.stream();
    }

    /**
     * Открывает поток массивов, не содержащие в себе '1' и '4'
     *
     * @return поток массивов, не содержащие 1 и 4
     */

    public static Stream<Object> arrWithoutOneAndFour() {
        List<Object> list = new ArrayList<>();
        int[] arr;
        for (int numberOfArrays = 0; numberOfArrays < 100; numberOfArrays++) {
            int arrLength = (int) (Math.random() * 100) + 4;
            arr = new int[arrLength];
            for (int j = 0; j < arrLength; j++) {
                arr[j] = (int) (Math.random() * 10);
                if (arr[j] == 1)
                    arr[j] = -1;
                if (arr[j] == 4)
                    arr[j] = -4;
            }
            list.add(arr);
        }
        return list.stream();
    }

    /**
     * Открывает поток массивов, содержащие в себе '4', но не '1'
     *
     * @return поток массивов, содержащие 4, но не 1
     */

    public static Stream<Object> arrWithoutOne() {
        List<Object> list = new ArrayList<>();
        int[] arr;
        for (int numberOfArrays = 0; numberOfArrays < 100; numberOfArrays++) {
            int arrLength = (int) (Math.random() * 100) + 4;
            arr = new int[arrLength];
            for (int j = 0; j < arrLength; j++) {
                arr[j] = (int) (Math.random() * 10);
                if (arr[j] == 1)
                    arr[j] = -1;
            }

            // Гарантия, что будет 4
            arr[arr.length / 2 - 1] = 4;

            list.add(arr);
        }
        return list.stream();
    }

    /**
     * Открывает поток массивов, содержащие в себе '1', но не '4'
     *
     * @return поток массивов, содержащие 1, но не 4
     */

    public static Stream<Object> arrWithoutFour() {
        List<Object> list = new ArrayList<>();
        int[] arr;
        for (int numberOfArrays = 0; numberOfArrays < 100; numberOfArrays++) {
            int arrLength = (int) (Math.random() * 100) + 4;
            arr = new int[arrLength];
            for (int j = 0; j < arrLength; j++) {
                arr[j] = (int) (Math.random() * 10);
                if (arr[j] == 4)
                    arr[j] = -4;
            }

            // Гарантия, что будет 1
            arr[arr.length / 2 + 1] = 1;

            list.add(arr);
        }
        return list.stream();
    }
}
