package main.ds.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);

        for (int index = 0; index < nums.length; index++) {
            if(map.containsKey(target - nums[index])){
                return new int[] {index, map.get(target - nums[index])};
            }
            map.put(nums[index], index);
        }

        return new int[] {};
    }
}
