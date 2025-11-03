package main.ds.leetcode.easy;

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {
    public static void main(String[] args) {
        String data = "[(){((([])){})[()]}]";
        int track = data.length();
        Deque<Character> stack = new ArrayDeque<>(track);

        for(int i = 0; i < track; i++){
            if(data.charAt(i) == '['){
                stack.push(']');
            } else if(data.charAt(i) == '('){
                stack.push(')');
            } else if(data.charAt(i) == '{'){
                stack.push('}');
            } else if(!stack.isEmpty() &&
                    stack.peek() != null &&
                    data.charAt(i) == stack.peek()){
                stack.pop();
            } else {
                System.out.println("Not valid");
                return;
            }
        }

        if(stack.isEmpty()){
            System.out.println("Valid");
        }
    }
}
