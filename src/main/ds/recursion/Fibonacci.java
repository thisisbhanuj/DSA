package main.ds.recursion;

import utility.MemoryUsage;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private Map<Integer, Long> cache; // Acts like a cache

    public Fibonacci() {
        this.cache = new HashMap<>();
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

        // Fibonacci using memoization
        long begin = System.currentTimeMillis();
        Fibonacci fibonacciWithMap = new Fibonacci();
        long resultOfFibonacciUsingMap = fibonacciWithMap.fibonacciUsingMap(location);
        long end = System.currentTimeMillis();
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
