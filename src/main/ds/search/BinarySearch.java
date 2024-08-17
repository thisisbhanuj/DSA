package main.ds.search;

public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 8;
        int index = binarySearch(array, 0, array.length - 1, target);
        System.out.println("Element found at index: " + index);
    }

    private static int binarySearch(int[] array, int low, int high, int target) {
        if (low <= high) {
            // Adding low and high directly can result in a sum that exceeds the maximum value an integer can hold (2^31 - 1).
            // This overflow wraps the value, causing incorrect behavior.
            // The calculation high - low is done first, which ensures the result is within the bounds of an integer.
            // This approach is efficient because it does not involve any additional space or significant computation, yet it makes the binary search safer and more robust.
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                return mid;
            }

            if (array[mid] < target) {
                return binarySearch(array, mid + 1, high, target);
            } else {
                return binarySearch(array, low, mid - 1, target);
            }
        }

        return -1;  // Return -1 if the element is not found
    }
}
