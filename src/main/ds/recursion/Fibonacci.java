package main.ds.recursion;

import utility.MemoryUsage;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private final Map<Integer, Long> cache = new HashMap<>();

    // Iterative Dynamic Programming Approach (Tabulation)
    //
    // Reduces the time complexity from exponential (O(2^n) for naive recursion) to linear (O(n) for DP) by storing intermediate results.
    // Uses O(n) space for the DP table, which is generally more efficient than the recursive stack space.
    private long fibonacciUsingDP(int pos) {
        if (pos == 1 || pos == 2) return 1;

        long[] dp = new long[pos + 1];
        dp[1] = 1;
        dp[2] = 1;

        for (int i = 3; i <= pos; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[pos];
    }

    // Fibonacci using memoization with a map
    private long fibonacciUsingMap(int pos) {
        if (pos == 1 || pos == 2) return 1;

        if (cache.containsKey(pos)) {
            return cache.get(pos);
        } else {
            long sum = fibonacciUsingMap(pos - 1) + fibonacciUsingMap(pos - 2);
            cache.put(pos, sum);
            return sum;
        }
    }

    // Fibonacci using plain recursion
    private static long fibonacci(int pos) {
        if (pos == 1 || pos == 2) return 1;
        return fibonacci(pos - 1) + fibonacci(pos - 2);
    }

    public static void main(String[] args) {
        int location = 50;

        Fibonacci fibonacciWithDP = new Fibonacci();

        // Fibonacci using iterative dynamic programming
        long begin = System.currentTimeMillis();
        long resultOfFibonacciUsingDP = fibonacciWithDP.fibonacciUsingDP(location);
        long end = System.currentTimeMillis();
        System.out.println("With DP - " + resultOfFibonacciUsingDP);
        MemoryUsage.calculateMemoryUsage();
        System.out.println("Elapsed Time: " + (end - begin) + " milliseconds");
        System.out.println("----------------------------------");

        // Fibonacci using memoization
        begin = System.currentTimeMillis();
        Fibonacci fibonacciWithMap = new Fibonacci();
        long resultOfFibonacciUsingMap = fibonacciWithMap.fibonacciUsingMap(location);
        end = System.currentTimeMillis();
        System.out.println("With Map - " + resultOfFibonacciUsingMap);
        MemoryUsage.calculateMemoryUsage();
        System.out.println("Elapsed Time: " + (end - begin) + " milliseconds");
        System.out.println("----------------------------------");

        // Fibonacci using plain recursion
        begin = System.currentTimeMillis();
        long result = fibonacci(location);
        end = System.currentTimeMillis();
        System.out.println("Normal - " + result);
        MemoryUsage.calculateMemoryUsage();
        System.out.println("Elapsed Time: " + (end - begin) + " milliseconds");
    }
}
