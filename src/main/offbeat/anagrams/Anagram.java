package main.offbeat.anagrams;

import java.util.Arrays;

public class Anagram {
    static boolean isAnagram(String s, String t) {
        // If the lengths are different, they can't be anagrams
        if (s.length() != t.length()) {
            return false;
        }

        // Convert both strings to char arrays
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        // Sort both char arrays
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        // Compare sorted arrays
        return Arrays.equals(sArray, tArray);
    }

    public static void main(String[] args){
       boolean result = isAnagram("bhanuj", "anujbh");
       System.out.println("Is Anagram : " + result);
    }
}
