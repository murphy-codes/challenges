// Source: https://leetcode.com/problems/divide-array-into-equal-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-16

/****************************************
* 
* You are given an integer array `nums` consisting of `2 * n` integers.
* You need to divide `nums` into `n` pairs such that:
* • Each element belongs to exactly one pair.
* • The elements present in a pair are equal.
* Return `true` if nums can be divided into n pairs, otherwise return `false`.
*
* Example 1:
* Input: nums = [3,2,3,2,2,2]
* Output: true
* Explanation:
* There are 6 elements in nums, so they should be divided into 6 / 2 = 3 pairs.
* If nums is divided into the pairs (2, 2), (3, 3), and (2, 2), it will satisfy all the conditions.
*
* Example 2:
* Input: nums = [1,2,3,4]
* Output: false
* Explanation:
* There is no way to divide nums into 4 / 2 = 2 pairs such that the pairs satisfy every condition.
*
* Constraints:
* • nums.length == 2 * n
* • 1 <= n <= 500
* • 1 <= nums[i] <= 500
* 
****************************************/

class Solution {
    // Uses a frequency counter to count occurrences of each number in nums.
    // If all numbers appear an even number of times, they can form pairs.
    // Time Complexity: O(n), as we iterate through nums twice (count + check).
    // Space Complexity: O(1), since we use a fixed array or at most 500 keys in a HashMap.
    public boolean divideArray(int[] nums) {
        // Use an array instead of a HashMap (since 1 <= nums[i] <= 500)
        int[] count = new int[501];

        // Count occurrences
        for (int num : nums) {
            count[num]++;
        }

        // Check if all counts are even
        for (int c : count) {
            if (c % 2 != 0) {
                return false;
            }
        }

        return true;
    }
}
