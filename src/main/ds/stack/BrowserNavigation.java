package main.ds.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserNavigation {

    public static void main(String[] args) {
        String[] urlsToVisit = {"google.com", "leetcode.com", "github.com"};
        int[] steps = {-1, -1, 1, 1, 1}; // -1 = back, 1 = forward

        simulateBrowser(urlsToVisit, steps);
    }

    private static void simulateBrowser(String[] urls, int[] steps) {
        Deque<String> backStack = new ArrayDeque<>();
        Deque<String> forwardStack = new ArrayDeque<>();
        String current = null;

        // Visit URLs
        for (String url : urls) {
            if (current != null) backStack.push(current);
            current = url;
            forwardStack.clear();
            System.out.println("Visited: " + current);
        }

        // Process BACK/FORWARD steps
        for (int step : steps) {
            if (step == -1) { // BACK
                if (!backStack.isEmpty()) {
                    forwardStack.push(current);
                    current = backStack.pop();
                    System.out.println("Back to: " + current);
                } else {
                    System.out.println("Cannot go back from: " + current);
                }
            } else if (step == 1) { // FORWARD
                if (!forwardStack.isEmpty()) {
                    backStack.push(current);
                    current = forwardStack.pop();
                    System.out.println("Forward to: " + current);
                } else {
                    System.out.println("Cannot go forward from: " + current);
                }
            }
        }
    }
}
