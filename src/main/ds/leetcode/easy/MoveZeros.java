package main.ds.leetcode.easy;

import java.util.Arrays;

public class MoveZeros {
    public void moveZeroes(int[] nums) {
        int position = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != 0){
                nums[position++] = nums[i];
            }
        }

        Arrays.fill(nums, position, nums.length, 0);
    }
}
