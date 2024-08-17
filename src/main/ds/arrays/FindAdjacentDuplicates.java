package main.ds.arrays;

import java.util.ArrayList;
import java.util.List;

public class FindAdjacentDuplicates {

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8};

        List<Integer> adjacentDuplicates = findAdjacentDuplicates(array);

        if (adjacentDuplicates.isEmpty()) {
            System.out.println("No adjacent duplicates found.");
        } else {
            System.out.println("Adjacent duplicates: " + adjacentDuplicates);
        }
    }

    public static List<Integer> findAdjacentDuplicates(int[] array) {
        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == array[i + 1]) {
                duplicates.add(array[i]);
            }
        }

        return duplicates;
    }
}

