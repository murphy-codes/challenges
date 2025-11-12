// Source: https://leetcode.com/problems/minimum-number-of-operations-to-make-all-array-elements-equal-to-1/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-11
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 43.88 MB Beats 8.22%

/****************************************
* 
* You are given a 0-indexed array `nums` consisiting of positive integers. You
* _ can do the following operation on the array any number of times:
* Select an index `i` such that `0 <= i < n - 1` and replace either of
* _ `nums[i]` or `nums[i+1]` with their gcd value.
* Return the minimum number of operations to make all elements of `nums`
* _ equal to `1`. If it is impossible, return `-1`.
* The gcd of two integers is the greatest common divisor of the two integers.
*
* Example 1:
* Input: nums = [2,6,3,4]
* Output: 4
* Explanation: We can do the following operations:
* - Choose index i = 2 and replace nums[2] with gcd(3,4) = 1. Now we have nums = [2,6,1,4].
* - Choose index i = 1 and replace nums[1] with gcd(6,1) = 1. Now we have nums = [2,1,1,4].
* - Choose index i = 0 and replace nums[0] with gcd(2,1) = 1. Now we have nums = [1,1,1,4].
* - Choose index i = 2 and replace nums[3] with gcd(1,4) = 1. Now we have nums = [1,1,1,1].
*
* Example 2:
* Input: nums = [2,10,6,14]
* Output: -1
* Explanation: It can be shown that it is impossible to make all the elements equal to 1.
*
* Constraints:
* • `2 <= nums.length <= 50`
* • `1 <= nums[i] <= 10^6`
* 
****************************************/

class Solution {
    // Finds the minimum number of operations to make all elements equal to 1.
    // If any 1s exist, each remaining element can be converted in one operation.
    // Otherwise, find the shortest subarray with gcd == 1 to create the first 1,
    // then spread it across the array. Uses O(n² * logM) time and O(1) space,
    // where n = nums.length and M = max(nums[i]).
    public int minOperations(int[] nums) {
        int n = nums.length;
        int ones = 0;

        // Case 1: nums contains at least one 1
        for (int num : nums) if (num == 1) ones++;
        if (ones > 0) return n - ones;

        // Case 2: Otherwise, find shortest subarray with gcd == 1
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int currGcd = nums[i];
            for (int j = i + 1; j < n; j++) {
                currGcd = gcd(currGcd, nums[j]);
                if (currGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // no need to go further for this i
                }
            }
        }

        // If we never found a subarray with gcd == 1, it's impossible
        if (minLen == Integer.MAX_VALUE) return -1;

        // Otherwise, create the first 1 with (minLen - 1) operations,
        // then turn the rest into 1 with (n - 1) more operations.
        return (minLen - 1) + (n - 1);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return Math.abs(a);
    }
}
