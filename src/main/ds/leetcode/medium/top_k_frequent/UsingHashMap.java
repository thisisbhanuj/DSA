package main.ds.leetcode.medium.top_k_frequent;

import java.util.*;

/*This approach has a time complexity of O(n log n) because of HASHMAP soring*/
public class UsingHashMap {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 3, 2};
        int k = 3; // Change k to the desired value for top-k frequent elements

        Map<Integer, Integer> map = new HashMap<>();
        for (int value : array) {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        // Create a list of map entries and sort them based on their frequency in descending order
        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(map.entrySet());
        sortedList.sort((a, b) ->  b.getValue() - a.getValue());

        // Extract the top-k frequent elements from the sorted list
        List<Integer> topKFrequent = new ArrayList<>();
        for (int i = 0; i < k && i < sortedList.size(); i++) {
            topKFrequent.add(sortedList.get(i).getKey());
        }

        System.out.println("Top-" + k + " frequent elements: " + topKFrequent);
    }
}
