package main.ds.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class Roman {
    public int romanToInt(String s) {
        char[] romans = s.toCharArray();
        int result = 0;
        Map<Character, Integer> map = new HashMap<>(romans.length);
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        for (int i = 0; i < romans.length; i++) {
            char current = romans[i];

            if (i > 0) {
                char prev = romans[i - 1];

                // Subtractive combinations
                if (prev == 'I' && (current == 'V' || current == 'X')) {
                    result -= 2 * map.get(prev);
                } else if (prev == 'X' && (current == 'L' || current == 'C')) {
                    result -= 2 * map.get(prev);
                } else if (prev == 'C' && (current == 'D' || current == 'M')) {
                    result -= 2 * map.get(prev);
                }
            }

            result += map.get(current);
        }

        return result;
    }

    public int romanToIntS(String s) {
        char[] romans = s.toCharArray();
        int result = 0;
        Map<Character, Integer> map = new HashMap<>(romans.length);
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        for (int i = 0; i < romans.length; i++) {
            int current = map.get(romans[i]);

            // If the current value is less than the next value, subtract it
            if (i < romans.length - 1 && current < map.get(romans[i + 1])) {
                result -= current;
            } else {
                result += current;
            }
        }

        return result;
    }
}
