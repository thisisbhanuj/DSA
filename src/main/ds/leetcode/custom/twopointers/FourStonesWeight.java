package main.ds.leetcode.custom.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourStonesWeight {
    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 1, 6, 7, 8, 4, 5};
        int target = 8;

        List<List<Integer>> result = weightsSum(weights, target, 4);
        System.out.println("Weight of stones -> " + result);
    }

    private static List<List<Integer>> weightsSum(int[] weights, int target, int group) {
        Arrays.sort(weights);
        return sum(weights, target, group, 0);
    }

    private static List<List<Integer>> sum(int[] weights, int target, int group, int start) {
        List<List<Integer>> result = new ArrayList<>();

        if (group == 2){
            int left = start; int right = weights.length - 1;
            while(left < right){
                if(weights[left] + weights[right] == target) {
                  result.add(Arrays.asList(weights[left], weights[right]));
                  left++;
                  right--;
                } else if (weights[left] + weights[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return result;
        }

        for(int left = start; left <= weights.length - group; left++){
            List<List<Integer>> list = sum(weights, target - weights[left], group - 1, left + 1);
            for(List<Integer> temp: list) {
                List<Integer> obj = new ArrayList<>();
                obj.add(weights[left]);
                obj.addAll(temp);
                result.add(obj);
            }
        }

        return result;
    }
}
