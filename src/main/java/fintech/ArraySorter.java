package fintech;

import java.util.Random;

/**
 * Класс содержит статические методы
 * для сортировки массивов целых чисел
 * разными способами.
 */
public final class ArraySorter {

    /**
     * Приватный конструктор.
     * Для предотвращения
     * инициализации объекта
     */
    private ArraySorter() {
    }

    /**
     * Меняем местами значения ячеек в int[].
     * Вспомогательный метод
     *
     * @param intArray массив
     * @param i1       индекс ячейки 1 для свопа
     * @param i2       индекс ячейки 2 для свопа
     * @return массив с изменениями
     */
    private static int[] swapValuesInIntArray(final int[] intArray,
                                              final int i1,
                                              final int i2) {
        int[] newArray = intArray.clone();
        int a = newArray[i1];
        newArray[i1] = newArray[i2];
        newArray[i2] = a;
        return newArray;
    }

    /**
     * Тасование Фишера — Йетса.
     *
     * @param intArray массив для перемешивания
     * @return перемешанный массив
     */
    public static int[] shuffle(final int[] intArray) {
        if (intArray.length < 1) {
            throw (new ArraySorterException("Input Array is empty"));
        } else if (intArray.length == 1) {
            return intArray;
        }

        int[] newArray = intArray.clone();
        Random random = new Random();
        // перебираем массив с конца
        for (int i = newArray.length - 1; i > 0; i--) {
            /*
             * меняем местами число в текущей ячейке
             * и число в произвольной ячейке
             * cлева от текущей (исключая её,
             * чтобы массив не сохранил первоначальный вид)
             */
            newArray = swapValuesInIntArray(newArray, random.nextInt(i), i);
        }
        return newArray;
    }

    /**
     * Сортировка выбором.
     * Проходим по массиву, суём максимальные значения в конец.
     *
     * @param intArray массив для сортировки
     * @return сортированный массив
     */
    public static int[] sortSelection(final int[] intArray) {
        if (intArray.length < 1) {
            throw (new ArraySorterException("Input Array is empty"));
        } else if (intArray.length == 1) {
            return intArray;
        }

        int[] newArray = intArray.clone();
        // записываем последний индекс
        int lastIndex = newArray.length - 1;
        /*
         * проверяем диапазон 0-lastIndex,
         * после каждой проверки сдвигая lastIndex, пока не дойдём до 0
         */
        while (lastIndex > 0) {
            /*
             * находим максимальное значение
             * в регионе 0-lastIndex
             * за максимальное берём то,
             * что в конце проверяемого диапазона
             */
            int maxValueIndex = lastIndex;
            int maxValue = newArray[maxValueIndex];
            for (int i = 0; i <= lastIndex; i++) {
                if (newArray[i] > maxValue) {
                    maxValue = newArray[i];
                    maxValueIndex = i;
                }
            }
            /* меняем значения слота с максимальным значением
             * со значением последнего слота, если они не равны
             */
            if (maxValueIndex != lastIndex) {
                newArray = swapValuesInIntArray(newArray, maxValueIndex, lastIndex);
            }
            // сдвигаем последний индекс
            lastIndex--;
        }

        return newArray;
    }

    /**
     * Гномья сортировка.
     * Проверяем пары.
     * Если нечего менять, шагаем вперёд,
     * если нет порядка, меняем и шагаем назад
     *
     * @param intArray массив для сортировки
     * @return сортированный массив
     */
    public static int[] sortGnome(final int[] intArray) {
        if (intArray.length < 1) {
            throw (new ArraySorterException("Input Array is empty"));
        } else if (intArray.length == 1) {
            return intArray;
        }

        int[] newArray = intArray.clone();

        int i = 0;
        int j = 1;

        // пока второй индекс не выйдет за пределы массива
        while (j < newArray.length) {
            // если порядок неправильный, меняем местами
            if (newArray[i] > newArray[j]) {
                newArray = swapValuesInIntArray(newArray, i, j);
                // если есть, куда шагать, шаг назад, если нет, шаг вперёд
                if (i > 0) {
                    i--;
                    j--;
                } else {
                    i++;
                    j++;
                }
                // если порядок правильный, шаг вперёд
            } else {
                i++;
                j++;
            }
        }

        return newArray;
    }

    /**
     * Коктейльная сортировка.
     * Меняем пары, ходим по массиву туда-обратно
     *
     * @param intArray массив для сортировки
     * @return сортированный массив
     */
    public static int[] sortCocktail(final int[] intArray) {
        if (intArray.length < 1) {
            throw (new ArraySorterException("Input Array is empty"));
        } else if (intArray.length == 1) {
            return intArray;
        }

        int[] newArray = intArray.clone();
        int left = 0;
        int right = newArray.length - 1;

        /* был ли обмен
         * (если не было, значит, всё в порядке, останавливаем цикл)
         */
        boolean swapped = false;
        // пока проверяемый диапазон не сожмётся, проверяем
        while (left < right) {
            swapped = false;
            // проходим по массиву вправо, исправляя порядок пар
            for (int i = left; i < right; i++) {
                if (newArray[i] > newArray[i + 1]) {
                    newArray = swapValuesInIntArray(newArray, i + 1, i);
                    swapped = true;
                }
            }
            // если замен не было, значит, массив отсортирован
            if (!swapped) {
                break;
            }
            right--;

            swapped = false;
            // проходим по массиву влево
            for (int j = right; j > left; j--) {
                if (newArray[j - 1] > newArray[j]) {
                    newArray = swapValuesInIntArray(newArray, j - 1, j);
                    swapped = true;
                }
            }
            // если замен не было, значит, массив отсортирован
            if (!swapped) {
                break;
            }
            left++;
        }

        return newArray;
    }

    /**
     * Быстрая сортировка.
     * Берём значение в середине массива,
     * ищем слева значения больше, справа меньше,
     * меняем местами, пока проверяемые ячейки не сойдутся.
     * В месте схода проверяемых ячеек массив разделяется на 2 куска,
     * которые проверяем рекурсией
     *
     * @param intArray массив для сортировки
     * @return сортированный массив
     */
    public static int[] sortQuick(final int[] intArray) {
        if (intArray.length < 1) {
            throw (new ArraySorterException("Input Array is empty"));
        } else if (intArray.length == 1) {
            return intArray;
        }
        return sortQuickDivide(intArray, 0, intArray.length - 1);
    }

    /**
     * Вспомогательный метод для быстрой сортировки.
     *
     * @param intArray массив для сортировки
     * @param start    левый край массива
     * @param end      правй край массива
     * @return сортированный массив
     */
    private static int[] sortQuickDivide(final int[] intArray,
                                         final int start, final int end) {
        if (end <= start) {
            return intArray;
        }

        int[] newArray = intArray.clone();
        // опорный элемент из середины массива
        int baseIndex = start + (end - start) / 2;
        int baseValue = newArray[baseIndex];

        // слева ищем значение больше опорного
        // справа ищем значение меньше опорного
        int left = start;
        int right = end;
        while (left < right) {
            /*
             * идём вправо от последней проверки до опоры,
             * пока не натыкаемся на > опоры
             */
            int i = left;
            for (; i < baseIndex; i++) {
                if (newArray[i] > baseValue) {
                    break;
                } else {
                    left++;
                }
            }

            /*
             * идём влево от последней проверки до опоры,
             * пока не натыкаемся на < опоры
             */
            int j = right;
            for (; j > baseIndex; j--) {
                if (newArray[j] < baseValue) {
                    break;
                } else {
                    right--;
                }
            }

            // если после изменений края не пересеклись
            if (left < right) {
                // меняем значения
                newArray = swapValuesInIntArray(newArray, left, right);
                /*
                 * если с какого-то конца дошли до опоры,
                 * приравниваем опору к другому краю
                 * для разделения кусков
                 */
                if (left == baseIndex) {
                    baseIndex = right;
                } else if (right == baseIndex) {
                    baseIndex = left;
                }
            }
        }
        /*
         * рекурсвно сортируем кусочки:
         * от начала до опоры и от опоры+1 до конца
         */
        newArray = sortQuickDivide(newArray, start, baseIndex);
        newArray = sortQuickDivide(newArray, baseIndex + 1, end);
        return newArray;
    }
}
