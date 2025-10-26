package main.ds.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class ExcelColumn {
    static void titleToNumber(String columnTitle) {
        int result = 0;

        for (int i = 0; i < columnTitle.length(); i++) {
            // Get the current character and convert it to its respective value
            char currentChar = columnTitle.charAt(i);
            int charValue = currentChar - 'A' + 1;

            // Multiply previous result by 26 and add current value
            result = result * 26 + charValue;
        }

        System.out.println(result);;
    }

    public static void titleToNumberUsingMap(String columnTitle) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 1); map.put('B', 2); map.put('C', 3); map.put('D', 4);
        map.put('E', 5); map.put('F', 6); map.put('G', 7); map.put('H', 8);
        map.put('I', 9); map.put('J', 10); map.put('K', 11); map.put('L', 12);
        map.put('M', 13); map.put('N', 14); map.put('O', 15); map.put('P', 16);
        map.put('Q', 17); map.put('R', 18); map.put('S', 19); map.put('T', 20);
        map.put('U', 21); map.put('V', 22); map.put('W', 23); map.put('X', 24);
        map.put('Y', 25); map.put('Z', 26);

        int total = columnTitle.length();
        int sum = 0;

        // Traverse from left to right, like base-26
        for (int i = 0; i < total; i++) {
            sum = sum * 26 + map.get(columnTitle.charAt(i));
        }

        System.out.println(sum);
    }

    public static void main(String[] args) {
        titleToNumber("ABCD");
        titleToNumberUsingMap("ABCD");
    }
}
