// Source: https://leetcode.com/problems/max-sum-of-a-pair-with-equal-sum-of-digits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-12

/****************************************
* 
* You are given a 0-indexed array `nums` consisting of positive integers. You can choose two indices `i` and `j`, such that `i != j`, and the sum of digits of the number `nums[i]` is equal to that of `nums[j]`.
* 
* Return the maximum value of `nums[i] + nums[j]` that you can obtain over all possible indices `i` and `j` that satisfy the conditions.
* 
* Example 1:
* Input: nums = [18,43,36,13,7]
* Output: 54
* Explanation: The pairs (i, j) that satisfy the conditions are:
* - (0, 2), both numbers have a sum of digits equal to 9, and their sum is 18 + 36 = 54.
* - (1, 4), both numbers have a sum of digits equal to 7, and their sum is 43 + 7 = 50.
* So the maximum sum that we can obtain is 54.
* 
* Example 2:
* Input: nums = [10,12,19,14]
* Output: -1
* Explanation: There are no two numbers that satisfy the conditions, so we return -1.
* 
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^9
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Uses a HashMap to track the largest number for each digit sum.
    // Iterates through nums, updating the max sum for matching digit sums.
    // Time complexity: O(n), since each number is processed once.
    // Space complexity: O(n), storing at most n unique digit sums.
    public int maximumSum(int[] nums) {
        HashMap<Integer, Integer> digitSumMap = new HashMap<>();
        int maxSum = -1;

        for (int num : nums) {
            int sumDigits = getDigitSum(num);

            // If there exists a previous number with the same digit sum, update maxSum
            if (digitSumMap.containsKey(sumDigits)) {
                maxSum = Math.max(maxSum, digitSumMap.get(sumDigits) + num);
            }

            // Update the largest number for this digit sum
            digitSumMap.put(sumDigits, Math.max(digitSumMap.getOrDefault(sumDigits, 0), num));
        }

        return maxSum;
    }

    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
