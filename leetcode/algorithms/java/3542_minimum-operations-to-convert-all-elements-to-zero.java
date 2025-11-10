// Source: https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-09
// At the time of submission:
//   Runtime 35 ms Beats 64.08%
//   Memory 123.06 MB Beats 23.24%

/****************************************
* 
* You are given an array `nums` of size `n`, consisting of non-negative integers.
* _ Your task is to apply some (possibly zero) operations on the array so that
* _ all elements become 0.
* In one operation, you can select a subarray `[i, j]`
* _ (where `0 <= i <= j < n`) and set all occurrences of the minimum
* _ non-negative integer in that subarray to 0.
* Return the minimum number of operations required to make all elements in the array 0.
*
* Example 1:
* Input: nums = [0,2]
* Output: 1
* Explanation:
* Select the subarray [1,1] (which is [2]), where the minimum non-negative integer is 2. Setting all occurrences of 2 to 0 results in [0,0].
* Thus, the minimum number of operations required is 1.
*
* Example 2:
* Input: nums = [3,1,2,1]
* Output: 3
* Explanation:
* Select subarray [1,3] (which is [1,2,1]), where the minimum non-negative integer is 1. Setting all occurrences of 1 to 0 results in [3,0,2,0].
* Select subarray [2,2] (which is [2]), where the minimum non-negative integer is 2. Setting all occurrences of 2 to 0 results in [3,0,0,0].
* Select subarray [0,0] (which is [3]), where the minimum non-negative integer is 3. Setting all occurrences of 3 to 0 results in [0,0,0,0].
* Thus, the minimum number of operations required is 3.
*
* Example 3:
* Input: nums = [1,2,1,2,1,2]
* Output: 4
* Explanation:
* Select subarray [0,5] (which is [1,2,1,2,1,2]), where the minimum non-negative integer is 1. Setting all occurrences of 1 to 0 results in [0,2,0,2,0,2].
* Select subarray [1,1] (which is [2]), where the minimum non-negative integer is 2. Setting all occurrences of 2 to 0 results in [0,0,0,2,0,2].
* Select subarray [3,3] (which is [2]), where the minimum non-negative integer is 2. Setting all occurrences of 2 to 0 results in [0,0,0,0,0,2].
* Select subarray [5,5] (which is [2]), where the minimum non-negative integer is 2. Setting all occurrences of 2 to 0 results in [0,0,0,0,0,0].
* Thus, the minimum number of operations required is 4.
*
* Constraints:
* • `1 <= n == nums.length <= 10^5`
* • `0 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // Time: O(n) amortized — each element is pushed/popped at most once.
    // Space: O(n) worst-case for the stack (but typically much less).
    public int minOperations(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        int ops = 0;

        for (int v : nums) {
            // pop values greater than current (their segments end here)
            while (!stack.isEmpty() && stack.peekLast() > v) {
                stack.removeLast();
            }
            // if current value is positive and not equal to current last, it's a new layer
            if (v > 0 && (stack.isEmpty() || stack.peekLast() < v)) {
                stack.addLast(v);
                ops++;
            }
            // if v == 0 or v == stack.last, do nothing
        }
        return ops;
    }
}
