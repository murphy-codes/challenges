// Source: https://leetcode.com/problems/partition-equal-subset-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 42.80 MB Beats 77.98%

/****************************************
* 
* Given an integer array `nums`, return `true` if you can partition the array into two subsets
* _ such that the sum of the elements in both subsets is equal or `false` otherwise.
*
* Example 1:
* Input: nums = [1,5,11,5]
* Output: true
* Explanation: The array can be partitioned as [1, 5, 5] and [11].
*
* Example 2:
* Input: nums = [1,2,3,5]
* Output: false
* Explanation: The array cannot be partitioned into equal sum subsets.
*
* Constraints:
* • 1 <= nums.length <= 200
* • 1 <= nums[i] <= 100
* 
****************************************/

class Solution {
    Boolean canPartition(Boolean[] memo, int s, int index, int[] nums) {
        if (s == 0)
            return true;

        if (s < 0)
            return false;
       
        if (index == 0)
            return s == nums[0];
       
        if (memo[s] != null)
            return memo[s];
       
        return memo[s] = canPartition(memo, s - nums[index], index - 1, nums) || canPartition(memo, s, index - 1, nums);
    }

    public boolean canPartition(int[] nums) {

        int s = 0;

        for (int i = 0; i < nums.length; i++)
            s += nums[i];

        if (s % 2 != 0)
            return false;

        int n = nums.length;

        s /= 2;

        Boolean[] memo = new Boolean[s + 1];

        return canPartition(memo, s, n - 1, nums);

    }
}
