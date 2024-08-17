package main.ds.recursion;

import java.util.Arrays;

public class Recursion {
    public static void main(String[] args) {
        // Reverse Array
        int[] array = {1,2,3,4,5};
        System.out.println("Original Array : " + Arrays.toString(array));
        if (array.length > 0) {
            int[] reversed = reverseTwoPointerArray(array, 0, array.length - 1);
            if (reversed!= null && reversed.length > 0) {
                System.out.println("Reversed Array : " + Arrays.toString(reversed));
            }
            // Alternate way
            reverseOnePointer(array, 0);
            if (array!= null && array.length > 0) {
                System.out.println("Reversed Again : " + Arrays.toString(array));
            }
        }

        // Palindrome
        String palindrome = "AABBBAA";
        int end = palindrome.length();
        System.out.println(palindrome(palindrome, 0, end));
    }

    private static int[] reverseTwoPointerArray(int[] arr, int start, int end) {
        if (start == end) return arr;
        int[] array = arr;
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
        int[] reversedArray = reverseTwoPointerArray(array, start + 1, end - 1);
        return reversedArray;
    }

    private static void reverseOnePointer(int[] arr, int start) {
        int len = arr.length;
        if (start < len / 2) {
            int temp = arr[start];
            arr[start] = arr[len - 1 - start];
            arr[len - 1 - start] = temp;
            reverseOnePointer(arr, start + 1);
        }
    }

    private static boolean palindrome(String context, int start, int end) {
        if (context == null || (start >= end)) {
            return false;
        }
        if (start == end - 1) {
            return true;
        }
        if (context.charAt(start) == context.charAt(end - 1)) {
            return palindrome(context, start + 1, end - 1);
        } else {
            return false;
        }
    }

    private static void generateSubSet() {

    }
}
