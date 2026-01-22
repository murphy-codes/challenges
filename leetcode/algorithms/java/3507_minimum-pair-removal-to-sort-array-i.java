// Source: https://leetcode.com/problems/minimum-pair-removal-to-sort-array-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-21
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 44.26 MB Beats 84.20%

/****************************************
* 
* Given an array `nums`, you can perform the following operation any number of times:
* • Select the adjacent pair with the minimum sum in `nums`. If multiple such
* __ pairs exist, choose the leftmost one.
* • Replace the pair with their sum.
* Return the minimum number of operations needed to make the array non-decreasing.
* An array is said to be non-decreasing if each element is greater than or equal
* _ to its previous element (if it exists).
*
* Example 1:
* Input: nums = [5,2,3,1]
* Output: 2
* Explanation:
* The pair `(3,1)` has the minimum sum of 4. After replacement, `nums = [5,2,4]`.
* The pair `(2,4)` has the minimum sum of 6. After replacement, `nums = [5,6]`.
* The array `nums` became non-decreasing in two operations.
*
* Example 2:
* Input: nums = [1,2,2]
* Output: 0
* Explanation:
* The array `nums` is already sorted.
*
* Constraints:
* • `1 <= nums.length <= 50`
* • `-1000 <= nums[i] <= 1000`
* 
****************************************/

class Solution {
    // Repeatedly simulate the required operation until the array becomes
    // non-decreasing. Each iteration scans the current array to both
    // find the leftmost adjacent pair with the minimum sum and check
    // sortedness, then merges that pair in-place by shifting elements.
    // Time: O(n^2) worst case, Space: O(1) extra space.
    public int minimumPairRemoval(final int[] nums) {
        int length = nums.length;
        int operations = 0;

        while (length > 1) {
            int minPairSum = Integer.MAX_VALUE;
            int minPairIndex = -1;
            boolean isNonDecreasing = true;

            // Scan array once to:
            // 1) find leftmost minimum-sum adjacent pair
            // 2) detect if array is already non-decreasing
            for (int i = 1; i < length; ++i) {
                int pairSum = nums[i - 1] + nums[i];

                if (pairSum < minPairSum) {
                    minPairSum = pairSum;
                    minPairIndex = i - 1;
                }

                if (nums[i - 1] > nums[i]) {
                    isNonDecreasing = false;
                }
            }

            // If already sorted, no more operations needed
            if (isNonDecreasing) {
                return operations;
            }

            // Merge the minimum-sum pair
            nums[minPairIndex] = minPairSum;

            // Shift elements left to remove the second element of the pair
            for (int i = minPairIndex + 1; i < length - 1; ++i) {
                nums[i] = nums[i + 1];
            }

            operations++;
            length--; // effective array size shrinks by one
        }

        return operations;
    }
}
