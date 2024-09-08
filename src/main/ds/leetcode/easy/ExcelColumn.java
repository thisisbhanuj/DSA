package main.ds.leetcode.easy;

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

    public static void main(String[] args) {
        titleToNumber("ABCD");
    }
}
