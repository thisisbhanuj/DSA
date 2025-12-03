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
            System.out.println("BS | minPages=" + minimumPages +
                    ", maxPages=" + maximumPages +
                    ", mid=" + mid);

            if(isPossible(pages, students, mid)){
                System.out.println("  feasible(mid=" + mid + ") -> shrink right");
                maximumPages = mid - 1;
                minimum = mid;
            } else {
                System.out.println("  NOT feasible(mid=" + mid + ") -> shift left");
                minimumPages = mid + 1;
            }
        }

        return minimum;
    }

    // Greedy check: can we allocate under this max limit?
    private static boolean isPossible(int[] pages, int students, int mid) {
        int requiredStudents = 1;
        int count = 0;

        System.out.println("  FEASIBILITY CHECK mid=" + mid);

        for(int page: pages){
            if(page + count > mid){
                System.out.println("    page=" + page +
                        ", runningCount=" + count +
                        " -> exceed");
                requiredStudents++;
                System.out.println("      exceed -> new student allocated; total=" + requiredStudents);
                count = page;

                if(requiredStudents > students){
                    System.out.println("  result: false (students exceeded)");
                    return false;
                }
            } else {
                count += page;
                System.out.println("    page=" + page +
                        ", runningCount=" + count +
                        ", requiredStudents=" + requiredStudents);
            }
        }

        System.out.println("  result: requiredStudents=" + requiredStudents +
                " <= students=" + students +
                " ? true");
        return true;
    }
}