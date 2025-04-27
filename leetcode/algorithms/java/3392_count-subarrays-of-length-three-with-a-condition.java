// Source: https://leetcode.com/count-subarrays-of-length-three-with-a-condition/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-25
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 45.31 MB Beats 20.64%

/****************************************
* 
* Given an integer array `nums`, return the number of subarrays of
* _ length 3 such that the sum of the first and third numbers equals
* _ exactly half of the second number.
*
* Example 1:
* Input: nums = [1,2,1,4,1]
* Output: 1
* Explanation:
* Only the subarray [1,4,1] contains exactly 3 elements where the sum of the first and third numbers equals half the middle number.
*
* Example 2:
* Input: nums = [1,1,1]
* Output: 0
* Explanation:
* [1,1,1] is the only subarray of length 3. However, its first and third numbers do not add to half the middle number.
*
* Constraints:
* • 3 <= nums.length <= 100
* • -100 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Iterate each subarray of length 3 in nums.
    // Only consider subarrays where middle element is even.
    // Check if first + third element equals half the middle element.
    // Time: O(n), Space: O(1), constant checks per index.
    public int countSubarrays(int[] nums) {
        int count = 0;
        for (int i = 0; i <= nums.length - 3; i++) {
            if (nums[i + 1] % 2 == 0 && nums[i] + nums[i + 2] == nums[i + 1] / 2) {
                count++;
            }
        }
        return count;
    }
}
