// Source: https://leetcode.com/problems/minimum-operations-to-make-binary-array-elements-equal-to-one-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-18
// At the time of submission:
//   Runtime 7 ms Beats 57.77%
//   Memory 57.32 MB Beats 58.56%

/****************************************
* 
* You are given a binary array `nums`.
* [Binary Array — A binary array is an array which contains only 0 and 1.]
* You can do the following operation on the array any number of times (possibly zero):
* • Choose any 3 consecutive elements from the array and flip all of them.
* Flipping an element means changing its value from 0 to 1, and from 1 to 0.
* Return the minimum number of operations required to make all elements in `nums` equal to 1. If it is impossible, return -1.
*
* Example 1:
* Input: nums = [0,1,1,1,0,0]
* Output: 3
* Explanation:
* We can do the following operations:
* Choose the elements at indices 0, 1 and 2. The resulting array is nums = [1,0,0,1,0,0].
* Choose the elements at indices 1, 2 and 3. The resulting array is nums = [1,1,1,0,0,0].
* Choose the elements at indices 3, 4 and 5. The resulting array is nums = [1,1,1,1,1,1].
*
* Example 2:
* Input: nums = [0,1,1,1]
* Output: -1
* Explanation:
* It is impossible to make all elements equal to 1.
*
* Constraints:
* • 3 <= nums.length <= 10^5
* • 0 <= nums[i] <= 1
* 
****************************************/

class Solution {
    // Uses a greedy left-to-right approach to flip 0s in groups of three.
    // If any unflippable 0s remain at the end, returns -1 (impossible case).
    // Each flip operation modifies exactly three elements in the array.
    // Time Complexity: O(n), as we traverse the array once.
    // Space Complexity: O(1), since we modify the input array in place.
    public int minOperations(int[] nums) {
        int n = nums.length;
        int operations = 0;
        
        for (int i = 0; i <= n - 3; i++) {
            if (nums[i] == 0) {
                // Flip the next three elements
                nums[i] ^= 1;
                nums[i + 1] ^= 1;
                nums[i + 2] ^= 1;
                operations++;
            }
        }
        
        // Check if the array is fully converted to 1s
        for (int num : nums) {
            if (num == 0) return -1;
        }
        
        return operations;
    }
}
