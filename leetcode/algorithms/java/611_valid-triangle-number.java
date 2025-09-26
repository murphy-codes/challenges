// Source: https://leetcode.com/problems/valid-triangle-number/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-25
// At the time of submission:
//   Runtime 22 ms Beats 99.95%
//   Memory 43.90 MB Beats 10.11%

/****************************************
* 
* Given an integer array `nums`, return the number of triplets chosen from the
* _ array that can make triangles if we take them as side lengths of a triangle.
*
* Example 1:
* Input: nums = [2,2,3,4]
* Output: 3
* Explanation: Valid combinations are:
* 2,3,4 (using the first 2)
* 2,3,4 (using the second 2)
* 2,2,3
*
* Example 2:
* Input: nums = [4,2,3,4]
* Output: 4
*
* Constraints:
* • `1 <= nums.length <= 1000`
* • `0 <= nums[i] <= 1000`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort the array, then fix the largest side nums[k]. For each k, use a
    // two-pointer scan (i, j) to count valid pairs. If nums[i] + nums[j] > nums[k],
    // then all indices between i..j-1 with j form valid triangles. Otherwise,
    // increment i to try larger values. Sorting takes O(n log n), and the two-
    // pointer scan takes O(n^2). Overall time complexity: O(n^2). Space: O(1).
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums); // Step 1: Sort array
        int n = nums.length;
        int count = 0;
        // Step 2: Fix the largest side nums[k]
        for (int k = n - 1; k >= 2; k--) {
            int i = 0, j = k - 1;
            // Step 3: Use two-pointer search for valid (a, b)
            while (i < j) {
                if (nums[i] + nums[j] > nums[k]) {
                    count += (j - i); // all pairs from i..j-1 are valid
                    j--;
                } else {
                    i++;
                }
            }
        }
        return count;
    }
}
