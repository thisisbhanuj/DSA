package main.ds.stack.monotonic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
    🔥 Core Intuition — “Next Greater / Smaller Element”

    Stack is used to maintain a monotonic property (either increasing or decreasing),
    allowing you to find next greater/smaller elements in O(n) time.

    🧠 Universal Intuition
    When you traverse the array:
    -> The stack keeps indices of elements whose answer hasn’t been found yet.
    -> When the current element breaks the monotonic order,
        → pop from stack until order is restored.
        → the element that caused the pop is the “next greater/smaller” for those popped.
 */
public class MonotonicPatterns {
    public int[] nextGreaterToRight(int[] arr){
        Deque<Integer> stack = new ArrayDeque<>();
        int[] results = new int[arr.length];

        for(int i = arr.length - 1; i >= 0; i--){
            // Pop smaller elements
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]){
                stack.pop();
            }
            results[i] = stack.isEmpty() ? -1 : arr[stack.peek()];
            stack.push(i);
        }

        return results;
    }

    public int[] nextGreaterToLeft(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] results = new int[arr.length];

        for(int i = 0; i < arr.length; i++){
            // Pop smaller elements
            while(!stack.isEmpty() && arr[stack.peek()] <= arr[i]){
                stack.pop();
            }
            results[i] = stack.isEmpty() ? -1 : arr[stack.peek()];
            stack.push(i);
        }

        return results;
    }

    public int[] nextSmallerToRight(int[] arr){
        int[] results = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = arr.length - 1; i >= 0; i--){
            // Pop larger elements
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }
            results[i] = stack.isEmpty() ? -1 : arr[stack.peek()];
            stack.push(i);
        }

        return results;
    }

    public int[] nextSmallerToLeft(int[] arr) {
        int[] results = new int[arr.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i < arr.length; i++){
            // Pop larger elements
            while(!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                stack.pop();
            }
            results[i] = stack.isEmpty() ? -1 : arr[stack.peek()];
            stack.push(i);
        }

        return results;
    }

    public static void main(String[] args) {
        int[] arr = {1,6,3,4,1,5};
        MonotonicPatterns monotonicPatterns = new MonotonicPatterns();
        int[] nextGreaterToRight = monotonicPatterns.nextGreaterToRight(arr);
        int[] nextGreaterToLeft = monotonicPatterns.nextGreaterToLeft(arr);
        int[] nextSmallerToRight = monotonicPatterns.nextSmallerToRight(arr);
        int[] nextSmallerToLeft = monotonicPatterns.nextSmallerToLeft(arr);

        System.out.println("-----------------------------------------------------");
        System.out.println("Array              -> " + Arrays.toString(arr));
        System.out.println("-----------------------------------------------------");
        System.out.println("nextGreaterToRight -> " + Arrays.toString(nextGreaterToRight));
        System.out.println("nextGreaterToLeft  -> " + Arrays.toString(nextGreaterToLeft));
        System.out.println("nextSmallerToRight -> " + Arrays.toString(nextSmallerToRight));
        System.out.println("nextSmallerToLeft  -> " + Arrays.toString(nextSmallerToLeft));
        System.out.println("-----------------------------------------------------");
    }
}
