// Source: https://leetcode.com/problems/minimum-operations-to-make-array-values-equal-to-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-08
// At the time of submission:
//   Runtime 2 ms Beats 82.19%
//   Memory 44.30 MB Beats 98.97%
//     (Leetcode was having trouble that day and didn't display the comparison between my code submission and others.)


/****************************************
* 
* You are given an integer array `nums` and an integer `k`.
* An integer `h` is called valid if all values in the array that are strictly greater than `h` are identical.
* For example, if `nums = [10, 8, 10, 8]`, a valid integer is `h = 9` because
* _ all `nums[i] > 9` are equal to 10, but 5 is not a valid integer.
* You are allowed to perform the following operation on `nums`:
* • Select an integer h that is valid for the current values in nums.
* • For each index `i` where `nums[i] > h`, set `nums[i]` to `h`.
* Return the minimum number of operations required to make every element
* _ in `nums` equal to `k`. If it is impossible to make all elements equal to `k`, return -1.
*
* Example 1:
* Input: nums = [5,2,5,4,5], k = 2
* Output: 2
* Explanation:
* The operations can be performed in order using valid integers 4 and then 2.
*
* Example 2:
* Input: nums = [2,1,2], k = 2
* Output: -1
* Explanation:
* It is impossible to make all the values equal to 2.
*
* Example 3:
* Input: nums = [9,7,5,3], k = 1
* Output: 4
* Explanation:
* The operations can be performed using valid integers in the order 7, 5, 3, and 1.
*
* Constraints:
* • 1 <= nums.length <= 100
* • 1 <= nums[i] <= 100
* • 1 <= k <= 100
* 
****************************************/

import java.util.HashSet;

class Solution {
    // This solution iterates through the array to track distinct values > k
    // using a HashSet. If any value < k is encountered, it immediately returns -1
    // since it's impossible to reach k. The number of distinct values > k
    // determines the number of operations needed. Time complexity is O(n),
    // and space complexity is O(n) in the worst case due to the HashSet.
    public int minOperations(int[] nums, int k) {
        HashSet<Integer> distinct = new HashSet<>();
        
        for (int num : nums) {
            if (num < k) return -1; // If any value is less than k, return -1 immediately
            if (num > k) distinct.add(num); // Track distinct values greater than k
        }
        
        return distinct.size(); // The number of unique values > k determines the operations
    }
}
