package ds.recursion;

import utility.MemoryUsage;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private static Map<Integer, Long> cache; // Acts like a cache

    public Fibonacci () {
        if (this.cache != null) this.cache.clear();
        this.cache = new HashMap<>();
    }

    private static long fibonacciUsingMap(int pos) {
        if (pos == 1 || pos == 2) return 1;
        long sum = 0;
        if (cache.containsKey(pos)) { // Fetch cache if present
            return cache.get(pos);
        } else {
            sum = fibonacciUsingMap(pos - 1) + fibonacciUsingMap(pos - 2);;
            cache.put(pos, sum);
            return sum;
        }
    }

    private static long fibonacci(int pos) {
        if (pos == 1 || pos == 2) return 1;
        return  fibonacci(pos-1) + fibonacci(pos-2);
    }

    public static void main(String[] args){
        int location = 50;// Hangs the JVM after this :D
        long begin = System.currentTimeMillis();

        new Fibonacci();
        long resultOfFibonacciUsingMap = fibonacciUsingMap(location);

        long end = System.currentTimeMillis();
        long time = end-begin;
        System.out.println("With Map - " + resultOfFibonacciUsingMap);
        MemoryUsage.calculateMemoryUsage();
        System.out.println("Elapsed Time: "+time +" milli seconds");
        System.out.println("----------------------------------");


        new Fibonacci();
        long result = fibonacci(location);

        end = System.currentTimeMillis();
        time = end-begin;
        System.out.println("Normal - " + result);
        MemoryUsage.calculateMemoryUsage();
        System.out.println("Elapsed Time: "+time +" milli seconds");
    }
}
