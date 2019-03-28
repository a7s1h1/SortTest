package fintech;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Класс для тестирования метода класса ArraySorter.
 * Использует с JUnitParamsRunner, чтобы запускать параметризованные методы.
 */
@RunWith(JUnitParamsRunner.class)
public class ArraySorterTest {

    /*
     * Перемешанный массив для теста метода shuffle
     */
    private int[] shuffledArray;
    /*
     * Сортированный массив для теста методов сортировки
     */
    private int[] sortedArray;


    /**
     * Наборы валидных тестовых данных.
     * <p>
     * Единственным валидным значением,
     * которое принимают тестировемые методы класса ArraySorter,
     * является массив целых чисел.
     * <p>
     * Валидные (=>успех):
     * массив с одним элементом int:
     * - макс
     * - мин
     * - середина
     * массив с >1 элементов int:
     * - 2 разных несортированных элемента,
     * - 3 разных несортированных элемента,
     * - 3 одинаковых элемента,
     * - 2 одинаковых + 3 одинковых элемента (без сортировки),
     * - 10 разных несортированных элементов
     *
     * @return массив наборов тестовых данных
     */
    private Object[] getValidParameters() {
        return new Object[]{
                new int[]{Integer.MAX_VALUE},   // 1 макс
                new int[]{Integer.MIN_VALUE},   // 1 мин
                new int[]{0},                   // 1 сред
                new int[]{1, -1},               // >1(2 разных)
                new int[]{0, 1, -1},            // >1(3 разных)
                new int[]{1, 1, 1},             // >1(3 одинаковых)
                new int[]{1, 1, 0, 0, 0},
                // >1(10 разных несортированных)
                new int[]{1, 2, 3, 4, 5, -1, -2, -3, -4, -5}
        };
    }

    /**
     * Невалидные данные: Null.
     * Вызывает исключение NullPointerException
     *
     * @return массив наборов тестовых данных
     * с едиственным значением - null
     */
    private Object[] getNull() {
        return new Object[]{
                null
        };
    }

    /**
     * Невалидные данные: пустой массив.
     * Вызывает исключение ArraySorterException
     *
     * @return массив наборов тестовых данных
     * с едиственным значением - пустым массивом int[]
     */
    private Object[] getEmptyArray() {
        return new Object[]{
                new int[0],
        };
    }

    /**
     * Очистка данных после каждого теста
     */
    @After
    public void doAfterClass() {
        shuffledArray = null;
        sortedArray = null;
    }

    /*
     * Тестирование метода shuffle.
     *
     * При вводе валидного значения
     * метод shuffle должен возвращать массив int[],
     * соответствующий следующим требованиям:
     * - не null;
     * - размер >0;
     * - размер = размеру входного массива;
     * - не равен по составу входному массиву, если элементы разные
     * - равен по составу входному массиву, если элементы одиаковые
     *
     * При вводе невалидного значения
     * метод shuffle выбрасывает исключение
     */

    /**
     * Метод должен возращать int[].
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testShuffleReturnsIntArray(int[] initialArray) {
        shuffledArray = ArraySorter.shuffle(initialArray);
        assertThat("Должен вернуться массив чисел",
                shuffledArray, isA(int[].class));
    }

    /**
     * Метод не должен возращать null.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testShuffleReturnsNotNull(int[] initialArray) {
        shuffledArray = ArraySorter.shuffle(initialArray);
        assertThat("Должен вернуться не null",
                shuffledArray != null);
    }

    /**
     * Метод не должен возращать пустой массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testShuffleReturnsNotEmptyArray(int[] initialArray) {
        shuffledArray = ArraySorter.shuffle(initialArray);
        assertThat("Размер массива должен быть >0",
                shuffledArray.length > 0);
    }

    /**
     * Метод должен возращать массив такой же длины, как на входе.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testShuffleReturnsEqualLengthArray(int[] initialArray) {
        shuffledArray = ArraySorter.shuffle(initialArray);
        assertThat("Размер массива должен совпадать с первоначальным",
                shuffledArray.length == initialArray.length);
    }

    /**
     * Метод должен возращать массив, отличающийся от массива на входе.
     * Но если во входном массиве все элементы одинаковые,
     * возвращаемый массив должен быть таким же.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testShuffleReturnsDifferentArray(int[] initialArray) {
        shuffledArray = ArraySorter.shuffle(initialArray);
        // отличаться должен только массив, где элементы разные
        boolean shouldBeDifferent = false;
        if (initialArray.length > 1) {
            for (int i = 0; i < initialArray.length - 1; i++) {
                // при нахождении отличного элемента прерываем цикл
                if (initialArray[i] != initialArray[i + 1]) {
                    shouldBeDifferent = true;
                    break;
                }
            }
        }

        // тест проводится только для массивов с >1 элементов
        if (shouldBeDifferent) {
            assertThat("Массив c разными элементами" +
                            "должен отличаться от первоначального",
                    !Arrays.equals(shuffledArray, initialArray));
        } else {
            assertThat("Массив с одинаковыми элементами" +
                            "не должен быть равен первоначальному",
                    Arrays.equals(shuffledArray, initialArray));
        }
        /*
         * TODO
         * Нет проверки произвольности перемешивания
         * для массива из >1 элементов
         * Хорошо бы устроить несколько прогонов
         * и удостовериться, что результаты не одинаковые.
         * Чем меньше элементов в массиве,
         * тем выше вероятность повтора комбинации,
         * тем больше требуется прогонов.
         */
    }

    /**
     * Метод должен выкидывать NullPointerException
     * при передаче в него null.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = NullPointerException.class)
    @Parameters(method = "getNull")
    public void testShuffleThrowsNullPointerException(int[] initialArray) {
        // NullPointerException ожидается только при initialArray==null
        shuffledArray = ArraySorter.shuffle(initialArray);
    }

    /**
     * Метод должен выкидывать ArraySorterException
     * при передаче в него пустого массива.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = ArraySorterException.class)
    @Parameters(method = "getEmptyArray")
    public void testShuffleThrowsArraySorterException(int[] initialArray) {
        // NullPointerException ожидается только при пустом массиве
        shuffledArray = ArraySorter.shuffle(initialArray);
    }


    /*
     * Тестирование методов сортировки:
     * sortSelection(),
     * sortGnome(),
     * sortCocktail(),
     * sortQuick().
     * <p>
     * При вводе валидного значения
     * любой метод сортировки должен возвращать массив int[],
     * соответствующий следующим требованиям:
     * - не null;
     * - размер >0;
     * - размер = размеру входного массива;
     * - должен быть отсортирован по возрастанию
     * <p>
     * При вводе невалидного значения
     * любой метод сортировки выбрасывает исключение
     */

    /*
     * Тестирование sortSelection()
     */

    /**
     * Метод должен возращать int[].
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortSelectionReturnsIntArray(int[] initialArray) {
        sortedArray = ArraySorter.sortSelection(initialArray);
        assertThat("Должен вернуться массив чисел",
                sortedArray, isA(int[].class));
    }

    /**
     * Метод не должен возращать null.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortSelectionReturnsNotNull(int[] initialArray) {
        sortedArray = ArraySorter.sortSelection(initialArray);
        assertThat("Должен вернуться не null",
                sortedArray != null);
    }

    /**
     * Метод не должен возращать пустой массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortSelectionReturnsNotEmptyArray(int[] initialArray) {
        sortedArray = ArraySorter.sortSelection(initialArray);
        assertThat("Размер массива должен быть >0",
                sortedArray.length > 0);
    }

    /**
     * Метод должен возращать массив такой же длины, как на входе.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortSelectionReturnsEqualLengthArray(int[] initialArray) {
        sortedArray = ArraySorter.sortSelection(initialArray);
        assertThat("Размер массива должен совпадать с первоначальным",
                sortedArray.length == initialArray.length);
    }

    /**
     * Метод должен возращать сортированный массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortSelectionReturnsSortedArray(int[] initialArray) {
        sortedArray = ArraySorter.sortSelection(initialArray);
        boolean sorted = checkSorted(sortedArray);

        assertThat("Массив " + Arrays.toString(sortedArray)
                        + " должен быть отсортирован",
                sorted);
    }

    /**
     * Метод должен выкидывать NullPointerException
     * при передаче в него null.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = NullPointerException.class)
    @Parameters(method = "getNull")
    public void testSortSelectionThrowsNullPointerException(int[] initialArray) {
        // NullPointerException ожидается только при initialArray==null
        sortedArray = ArraySorter.sortSelection(initialArray);
    }

    /**
     * Метод должен выкидывать ArraySorterException
     * при передаче в него пустого массива.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = ArraySorterException.class)
    @Parameters(method = "getEmptyArray")
    public void testSortSelectionThrowsArraySorterException(int[] initialArray) {
        // NullPointerException ожидается только при пустом массиве
        sortedArray = ArraySorter.sortSelection(initialArray);
    }

    /*
     * Тестирование sortGnome()
     */

    /**
     * Метод должен возращать int[].
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortGnomeReturnsIntArray(int[] initialArray) {
        sortedArray = ArraySorter.sortGnome(initialArray);
        assertThat("Должен вернуться массив чисел",
                sortedArray, isA(int[].class));
    }

    /**
     * Метод не должен возращать null.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortGnomeReturnsNotNull(int[] initialArray) {
        sortedArray = ArraySorter.sortGnome(initialArray);
        assertThat("Должен вернуться не null",
                sortedArray != null);
    }

    /**
     * Метод не должен возращать пустой массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortGnomeReturnsNotEmptyArray(int[] initialArray) {
        sortedArray = ArraySorter.sortGnome(initialArray);
        assertThat("Размер массива должен быть >0",
                sortedArray.length > 0);
    }

    /**
     * Метод должен возращать массив такой же длины, как на входе.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortGnomeReturnsEqualLengthArray(int[] initialArray) {
        sortedArray = ArraySorter.sortGnome(initialArray);
        assertThat("Размер массива должен совпадать с первоначальным",
                sortedArray.length == initialArray.length);
    }

    /**
     * Метод должен возращать сортированный массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortGnomeReturnsSortedArray(int[] initialArray) {
        sortedArray = ArraySorter.sortGnome(initialArray);
        boolean sorted = checkSorted(sortedArray);

        assertThat("Массив " + Arrays.toString(sortedArray)
                        + " должен быть отсортирован",
                sorted);
    }

    /**
     * Метод должен выкидывать NullPointerException
     * при передаче в него null.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = NullPointerException.class)
    @Parameters(method = "getNull")
    public void testSortGnomeThrowsNullPointerException(int[] initialArray) {
        // NullPointerException ожидается только при initialArray==null
        sortedArray = ArraySorter.sortGnome(initialArray);
    }

    /**
     * Метод должен выкидывать ArraySorterException
     * при передаче в него пустого массива.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = ArraySorterException.class)
    @Parameters(method = "getEmptyArray")
    public void testSortGnomeThrowsArraySorterException(int[] initialArray) {
        // NullPointerException ожидается только при пустом массиве
        sortedArray = ArraySorter.sortGnome(initialArray);
    }

    /*
     * Тестирование sortCocktail()
     */

    /**
     * Метод должен возращать int[].
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortCocktailReturnsIntArray(int[] initialArray) {
        sortedArray = ArraySorter.sortCocktail(initialArray);
        assertThat("Должен вернуться массив чисел",
                sortedArray, isA(int[].class));
    }

    /**
     * Метод не должен возращать null.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortCocktailReturnsNotNull(int[] initialArray) {
        sortedArray = ArraySorter.sortCocktail(initialArray);
        assertThat("Должен вернуться не null",
                sortedArray != null);
    }

    /**
     * Метод не должен возращать пустой массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortCocktailReturnsNotEmptyArray(int[] initialArray) {
        sortedArray = ArraySorter.sortCocktail(initialArray);
        assertThat("Размер массива должен быть >0",
                sortedArray.length > 0);
    }

    /**
     * Метод должен возращать массив такой же длины, как на входе.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortCocktailReturnsEqualLengthArray(int[] initialArray) {
        sortedArray = ArraySorter.sortCocktail(initialArray);
        assertThat("Размер массива должен совпадать с первоначальным",
                sortedArray.length == initialArray.length);
    }

    /**
     * Метод должен возращать сортированный массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortCocktailReturnsSortedArray(int[] initialArray) {
        sortedArray = ArraySorter.sortCocktail(initialArray);
        boolean sorted = checkSorted(sortedArray);

        assertThat("Массив " + Arrays.toString(sortedArray)
                        + " должен быть отсортирован",
                sorted);
    }

    /**
     * Метод должен выкидывать NullPointerException
     * при передаче в него null.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = NullPointerException.class)
    @Parameters(method = "getNull")
    public void testSortCocktailThrowsNullPointerException(int[] initialArray) {
        // NullPointerException ожидается только при initialArray==null
        sortedArray = ArraySorter.sortCocktail(initialArray);
    }

    /**
     * Метод должен выкидывать ArraySorterException
     * при передаче в него пустого массива.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = ArraySorterException.class)
    @Parameters(method = "getEmptyArray")
    public void testSortCocktailThrowsArraySorterException(int[] initialArray) {
        // NullPointerException ожидается только при пустом массиве
        sortedArray = ArraySorter.sortCocktail(initialArray);
    }

    /*
     * Тестирование sortQuick()
     */

    /**
     * Метод должен возращать int[].
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortQuickReturnsIntArray(int[] initialArray) {
        sortedArray = ArraySorter.sortQuick(initialArray);
        assertThat("Должен вернуться массив чисел",
                sortedArray, isA(int[].class));
    }

    /**
     * Метод не должен возращать null.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortQuickReturnsNotNull(int[] initialArray) {
        sortedArray = ArraySorter.sortQuick(initialArray);
        assertThat("Должен вернуться не null",
                sortedArray != null);
    }

    /**
     * Метод не должен возращать пустой массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortQuickReturnsNotEmptyArray(int[] initialArray) {
        sortedArray = ArraySorter.sortQuick(initialArray);
        assertThat("Размер массива должен быть >0",
                sortedArray.length > 0);
    }

    /**
     * Метод должен возращать массив такой же длины, как на входе.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortQuickReturnsEqualLengthArray(int[] initialArray) {
        sortedArray = ArraySorter.sortQuick(initialArray);
        assertThat("Размер массива должен совпадать с первоначальным",
                sortedArray.length == initialArray.length);
    }

    /**
     * Метод должен возращать сортированный массив.
     * Используются наборы валидных данных.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test
    @Parameters(method = "getValidParameters")
    public void testSortQuickReturnsSortedArray(int[] initialArray) {
        sortedArray = ArraySorter.sortQuick(initialArray);
        boolean sorted = checkSorted(sortedArray);

        assertThat("Массив " + Arrays.toString(sortedArray)
                        + " должен быть отсортирован",
                sorted);
    }

    /**
     * Метод должен выкидывать NullPointerException
     * при передаче в него null.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = NullPointerException.class)
    @Parameters(method = "getNull")
    public void testSortQuickThrowsNullPointerException(int[] initialArray) {
        // NullPointerException ожидается только при initialArray==null
        sortedArray = ArraySorter.sortQuick(initialArray);
    }

    /**
     * Метод должен выкидывать ArraySorterException
     * при передаче в него пустого массива.
     *
     * @param initialArray массив на входе для перемешивания
     */
    @Test(expected = ArraySorterException.class)
    @Parameters(method = "getEmptyArray")
    public void testSortQuickThrowsArraySorterException(int[] initialArray) {
        // NullPointerException ожидается только при пустом массиве
        sortedArray = ArraySorter.sortQuick(initialArray);
    }

    /**
     * Проверка сортировки массива.
     * Вспомогательный метод.
     * @param intArray массив чисел
     * @return  отсортирован ли массив по возрастанию
     */
    private boolean checkSorted(final int[] intArray){
        boolean sorted = true;
        /*
         * Проверка сортированности.
         * Проходим по всем элементам, начиная с индекса 1.
         * Каждый элемент должен быть >= предыдущего.
         */
        if (intArray.length > 1) {
            for (int i = 1; i < intArray.length; i++) {
                // при нарушении порядка прерываем цикл
                if (intArray[i] < intArray[i - 1]) {
                    sorted = false;
                    break;
                }
            }
        }

        return sorted;
    }
}