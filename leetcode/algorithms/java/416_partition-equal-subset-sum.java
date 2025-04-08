// Source: https://leetcode.com/problems/partition-equal-subset-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 42.42 MB Beats 80.66%

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
    // This solution uses recursion with memoization to determine if a subset
    // with sum equal to half the total array sum exists. The time complexity
    // is O(n * s), where n is the number of elements and s is half the total
    // sum. The space complexity is O(s) due to the memoization array storing
    // results for subset sums from 0 to s.

    // Helper function to determine if a subset with sum 's' exists
    Boolean subsetSumExists(Boolean[] memo, int s, int index, int[] nums) {
        // Base case: if the subset sum reaches zero, a valid subset is found
        if (s == 0)
            return true;

        // If the sum becomes negative, this path is not valid
        if (s < 0)
            return false;

        // If we've reached the first element, check if it matches the remaining sum
        if (index == 0)
            return s == nums[0];

        // If the result for this sum has been computed before, return it
        if (memo[s] != null)
            return memo[s];

        // Recursively check if including or excluding the current element leads to a solution
        return memo[s] = subsetSumExists(memo, s - nums[index], index - 1, nums) 
                      || subsetSumExists(memo, s, index - 1, nums);
    }

    public boolean canPartition(int[] nums) {
        int s = 0;

        // Calculate the total sum of the array
        for (int i = 0; i < nums.length; i++)
            s += nums[i];

        // If the sum is odd, partitioning into equal subsets is impossible
        if (s % 2 != 0)
            return false;

        int n = nums.length;
        s /= 2; // We need to check if a subset exists with sum equal to half the total

        // Memoization array to store computed results for subproblems
        Boolean[] memo = new Boolean[s + 1];

        // Call the recursive function to determine if such a subset exists
        return subsetSumExists(memo, s, n - 1, nums);
    }
}
