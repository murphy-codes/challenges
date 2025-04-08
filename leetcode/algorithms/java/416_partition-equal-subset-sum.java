// Source: https://leetcode.com/problems/partition-equal-subset-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 9 ms Beats 98.90%
//   Memory 57.12 MB Beats 30.91%

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
    // This solution uses recursive backtracking with memoization to check if a subset
    // of nums adds up to half of the total sum. A 2D Boolean array stores results to
    // avoid redundant calculations. The time complexity is O(n * s), where n is the
    // number of elements and s is half the total sum. The space complexity is O(n * s)
    // due to the recursion stack and memoization table.
    private Boolean[][] memo;

    private boolean canPartitionRecursive(int targetSum, int index, int[] nums) {
        if (targetSum == 0) return true; // Found a valid subset sum
        if (targetSum < 0 || index < 0) return false; // Base cases
        if (memo[index][targetSum] != null) return memo[index][targetSum]; // Use memoized result

        // Recursively check if we can reach the target by including or excluding nums[index]
        return memo[index][targetSum] = canPartitionRecursive(targetSum - nums[index], index - 1, nums)
                || canPartitionRecursive(targetSum, index - 1, nums);
    }

    public boolean canPartition(int[] nums) {
        int totalSum = 0;

        for (int num : nums) totalSum += num; // Compute total sum

        if (totalSum % 2 != 0) return false; // If sum is odd, partition is impossible

        int targetSum = totalSum / 2;
        int n = nums.length;
        memo = new Boolean[n][targetSum + 1]; // Fix: Use a 2D memo array indexed by (index, targetSum)

        return canPartitionRecursive(targetSum, n - 1, nums);
    }
}
