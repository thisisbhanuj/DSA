package main.ds.search;

import java.util.Arrays;

public class BookAllocation {

    public static void main(String[] args) {
        int[] pages = {12, 34, 67, 90};
        int students = 2;
        int result = allocateMinimumPages(pages, students);
        System.out.println("Minimum possible maximum pages per student = " + result);
    }

    // Returns minimum possible maximum pages per student
    private static int allocateMinimumPages(int[] pages, int students) {
        int minimumPages = pages[pages.length - 1];
        int maximumPages = Arrays.stream(pages).sum();
        int minimum = 0;

        while (minimumPages < maximumPages){
            int mid = minimumPages + (maximumPages - minimumPages) / 2;
            if(isPossible(pages, students, mid)){
                maximumPages = mid - 1;
                minimum = mid;
            } else {
                minimumPages = mid + 1;
            }
        }

        return minimum;
    }

    // Greedy check: can we allocate under this max limit?
    private static boolean isPossible(int[] pages, int students, int mid) {
        int requiredStudents = 1;
        int count = 0;

        for(int page: pages){
            if(page + count > mid){
                requiredStudents++;
                count = page;

                if(requiredStudents > students){
                    return false;
                }
            } else {
                count += page;
            }
        }

        return true;
    }
}