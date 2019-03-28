package fintech;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс для ручного тестирования ArraySorter.
 */
public final class ArraySorterManualTest {

    /**
     * Запуск теста.
     */
    public void launch() {
        /*
         * тестируем сортировку
         * всеми видами произвольных массивов
         * с размерами от 0 до9
         */
        final int maxElements = 9;
        for (int i = 1; i < maxElements; i++) {
            testArray(createRandomArray(i));
        }
    }

    private int[] createRandomArray(final int length) {
        int[] ar = new int[length];
        Random random = new Random();
        final int maxNumber = 9;
        for (int i = 0; i < ar.length; i++) {
            ar[i] = random.nextInt(maxNumber);
        }
        return ar;
    }

    private void testArray(final int[] arrayToTest) {
        System.out.println("");
        System.out.println("ArrayToTest: " + Arrays.toString(arrayToTest));
        int[] initialArray = ArraySorter.shuffle(arrayToTest);
        System.out.println("Shuffled Initial: " + Arrays.toString(initialArray));
        int[] selectionSortedArray = ArraySorter.sortSelection(initialArray);
        System.out.println("Selection sorted: " + Arrays.toString(selectionSortedArray));
        int[] gnomeSortedArray = ArraySorter.sortSelection(initialArray);
        System.out.println("Gnome sorted: " + Arrays.toString(gnomeSortedArray));
        int[] cocktailSortedArray = ArraySorter.sortSelection(initialArray);
        System.out.println("Cocktail sorted: " + Arrays.toString(cocktailSortedArray));
        int[] quickSortedArray = ArraySorter.sortSelection(initialArray);
        System.out.println("Quick sorted: " + Arrays.toString(quickSortedArray));
    }
}
