// Source: https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-09
// At the time of submission:
//   Runtime 11 ms Beats 100.00%
//   Memory 137.54 MB Beats 5.63%

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
* THE PROBLEM'S DESCRIPTION DOESN'T MENTION THIS, BUT THE SUBARRAY YOU CHOOSE 
* CANNOT CONTAIN A ZERO-VALUE. SO RATHER THAN SAYING 'set all occurrences of the minimum
* non-negative integer non-negative integer' IT SHOULD SAY 'the minimum POSITIVE integer.
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
    // This solution uses a monotonic increasing stack to track "height layers" 
    // representing distinct positive values that must be zeroed out. Whenever a 
    // lower value appears, higher layers are popped and counted as completed. 
    // Remaining layers after traversal also require one operation each.
    // Time Complexity: O(n) — each element is pushed and popped at most once.
    // Space Complexity: O(n) — for the stack array storing up to n values.
    public int minOperations(int[] nums) {
        int[] stack = new int[nums.length + 1]; // Monotonic stack (stores increasing heights)
        int top = 0; // Stack pointer
        int ops = 0; // Operation counter

        for (int i = 0; i < nums.length; i++) {
            // Pop higher layers that end before nums[i]
            while (stack[top] > nums[i]) {
                top--;
                ops++;
            }

            // Push new layer if different from current top
            if (stack[top] != nums[i]) {
                stack[++top] = nums[i];
            }
        }

        // Add remaining active layers (each needs one final operation)
        return ops + top;
    }
}
