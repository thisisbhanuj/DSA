package ds.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeAndSortArrays {
    public static int[] sort(int[] left, int[] right){
        int i = 0, j = 0, k = 0;
        int[] array = new int[left.length + right.length];
        while(i < left.length && j < right.length){
            if (left[i] < right[j]){
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length){
            array[k] = left[i];
            i++; k++;
        }

        while (j < right.length){
            array[k] = right[j];
            j++; k++;
        }

        return array;
    }

    public static int[] mergeSort(int[] mergedArray) {
        if (mergedArray.length <= 1) return mergedArray;
        int mid = mergedArray.length / 2;
        int[] left = Arrays.copyOfRange(mergedArray, 0, mid);
        int[] right = Arrays.copyOfRange(mergedArray, mid, mergedArray.length);
        left = mergeSort(left);
        right = mergeSort(right);

        return sort(left, right);
    }

    public static int[] merge(int[] a, int[] b){
        List<Integer> list = new ArrayList<>();
        for (int i : a) {
            list.add(i);
        }
        for (int i : b) {
            list.add(i);
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = {5, 1, 0, 8, 3};
        int[] b = {2, 10, 9, 6, 4};

        int[] mergedArray = merge(a, b);
        System.out.println("Merged Array: " + Arrays.toString(mergedArray));

        int[] sortedArray = mergeSort(mergedArray);
        System.out.println("Sorted Array: " + Arrays.toString(sortedArray));

    }
}
