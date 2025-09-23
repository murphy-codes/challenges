// Source: https://leetcode.com/problems/minimum-operations-to-exceed-threshold-value-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-23
// At the time of submission:
//   Runtime 24 ms Beats 99.67%
//   Memory 64.44 MB Beats 99.33%

/****************************************
* 
* You are given a 0-indexed integer array `nums`, and an integer `k`.
* You are allowed to perform some operations on `nums`, where in a single 
* _ operation, you can:
* • Select the two smallest integers `x` and `y` from nums.
* • Remove `x` and `y` from nums.
* • Insert `(min(x, y) * 2 + max(x, y))` at any position in the array.
* Note that you can only apply the described operation if `nums` contains at 
* _ least two elements.
* Return the minimum number of operations needed so that all elements of the 
* _ array are greater than or equal to `k`.
* 
* Example 1:
* Input: nums = [2,11,10,1,3], k = 10
* Output: 2
* Explanation:
* In the first operation, we remove elements 1 and 2, then add 1 * 2 + 2 to nums. nums becomes equal to [4, 11, 10, 3].
* In the second operation, we remove elements 3 and 4, then add 3 * 2 + 4 to nums. nums becomes equal to [10, 11, 10].
* At this stage, all the elements of nums are greater than or equal to 10 so we can stop. 
* It can be shown that 2 is the minimum number of operations needed so that all elements of the array are greater than or equal to 10.
* 
* Example 2:
* Input: nums = [1,1,2,4,9], k = 20
* Output: 4
* Explanation:
* After one operation, nums becomes equal to [2, 4, 9, 3]. 
* After two operations, nums becomes equal to [7, 4, 9]. 
* After three operations, nums becomes equal to [15, 9]. 
* After four operations, nums becomes equal to [33].
* At this stage, all the elements of nums are greater than 20 so we can stop. 
* It can be shown that 4 is the minimum number of operations needed so that all elements of the array are greater than or equal to 20.
* 
* Constraints:
* • 2 <= nums.length <= 2 * 10^5
* • 1 <= nums[i] <= 10^9
* • 1 <= k <= 10^9
* • The input is generated such that an answer always exists. That is, there exists some sequence of operations after which all elements of the array are greater than or equal to `k`.
* 
****************************************/

class Solution {
    // Solution uses sorting + merge technique to avoid a heap. The array is
    // sorted once, then two "streams" are merged: (1) original sorted nums,
    // (2) newly generated values stored back into nums. At each step the
    // two smallest values are combined. Time: O(n log n), Space: O(1).
    public int minOperations(int[] nums, int k) {
        Arrays.sort(nums); // sort input first
        // i -> index for original sorted nums
        // j -> index for newly formed numbers (stored back into nums)
        // count -> number of operations performed
        for (int i = 0, j = 0, count = 0, first, second; ; ++count) {
            // Select smaller between next original and next generated
            if (i < nums.length && (j >= count || nums[i] <= nums[j])) first = nums[i++];
            else first = nums[j++];
            // If the smallest element already >= k, we are done
            if (first >= k) return count;
            // Select the second smallest
            if (i < nums.length && (j >= count || nums[i] <= nums[j])) second = nums[i++];
            else second = nums[j++];
            // Combine them into a new value
            long combined = 2L * first + second; // use long to check overflow
            // Store result back into nums as part of "generated stream"
            nums[count] = combined < k ? (int) combined : k;
        }
    }
}
