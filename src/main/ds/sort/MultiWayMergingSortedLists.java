package main.ds.sort;

import java.util.Arrays;

public class MultiWayMergingSortedLists {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        int[] c = {1, 3, 5, 7, 9};

        int[] result = mergelists(a, b, c);
        System.out.println(Arrays.toString(result));
    }

    private static int[] mergelists(int[] a, int[] b, int[] c) {
        int[] result = new int[a.length + b.length + c.length];
        int i = 0, j = 0, k = 0, l = 0;

        // Merge arrays a, b, and c
        while (i < a.length && j < b.length && k < c.length) {
            if (a[i] <= b[j] && a[i] <= c[k]) {
                result[l++] = a[i++];
            } else if (b[j] <= a[i] && b[j] <= c[k]) {
                result[l++] = b[j++];
            } else {
                result[l++] = c[k++];
            }
        }

        // Merge remaining elements of a and b
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[l++] = a[i++];
            } else {
                result[l++] = b[j++];
            }
        }

        // Merge remaining elements of b and c
        while (j < b.length && k < c.length) {
            if (b[j] <= c[k]) {
                result[l++] = b[j++];
            } else {
                result[l++] = c[k++];
            }
        }

        // Merge remaining elements of a and c
        while (i < a.length && k < c.length) {
            if (a[i] <= c[k]) {
                result[l++] = a[i++];
            } else {
                result[l++] = c[k++];
            }
        }

        // Copy any remaining elements of a
        while (i < a.length) {
            result[l++] = a[i++];
        }

        // Copy any remaining elements of b
        while (j < b.length) {
            result[l++] = b[j++];
        }

        // Copy any remaining elements of c
        while (k < c.length) {
            result[l++] = c[k++];
        }

        return result;
    }
}
