package com.udemy.shellsort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] intArray = new Random().ints(100, -100, 100).toArray();

        Map<Integer, Integer> mapBeforeSort = getNumberPerCount(intArray);

        System.out.println(Arrays.toString(intArray));

        for (int k = getK(intArray.length); k >= 1; k--) {
            int gap = getKnuthValue(k); // (3^k - 1) / 2
            for (int i = gap; i < intArray.length; i++) {
                int newElement = intArray[i];
                int j;
                for (j = i; j - gap >= 0; j = j - gap) {
                    if (intArray[j - gap] <= newElement) {
                        intArray[j] = newElement;
                        break;
                    }
                    intArray[j] = intArray[j - gap];
                }
                intArray[j] = newElement;
            }
        }

        System.out.println(Arrays.toString(intArray));

        Map<Integer, Integer> mapAfterSort = getNumberPerCount(intArray);

        System.out.println(isMapsEqual(mapBeforeSort, mapAfterSort));
    }

    private static int getK(int arrayLength) {
        int gap;
        int k = 0;

        do {
            gap = getKnuthValue(k);
            k++;
        } while (gap < arrayLength);

        return k - 1;
    }

    private static int getKnuthValue(int k) {
        return (3 ^ k - 1) / 2;
    }

    private static Map<Integer, Integer> getNumberPerCount(int[] intArray) {
        Map<Integer, Integer> numberPerCount = new HashMap<>();
        for (int intFromArray : intArray) {
            numberPerCount.computeIfPresent(intFromArray, (key, value) -> value + 1);
            numberPerCount.putIfAbsent(intFromArray, 1);
        }
        return numberPerCount;
    }

    private static boolean isMapsEqual(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
        return map1.entrySet().stream().allMatch(entry -> Objects.equals(map2.get(entry.getKey()), entry.getValue()));
    }
}
