// Source: https://leetcode.com/problems/minimize-the-maximum-difference-of-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-13
// At the time of submission:
//   Runtime 16 ms Beats 97.07%
//   Memory 56.30 MB Beats 48.78%

/****************************************
* 
* You are given a 0-indexed integer array `nums` and an integer `p`. Find `p`
* _ pairs of indices of `nums` such that the maximum difference amongst all the
* _ pairs is minimized. Also, ensure no index appears more than once amongst
* _ the `p` pairs.
* Note that for a pair of elements at the index `i` and `j`, the difference of
* _ this pair is `|nums[i] - nums[j]|`, where `|x|` represents the
* _ absolute value of `x`.
* Return the minimum maximum difference among all p pairs. We define the maximum
* _ of an empty set to be zero.
*
* Example 1:
* Input: nums = [10,1,2,7,1,3], p = 2
* Output: 1
* Explanation: The first pair is formed from the indices 1 and 4, and the second pair is formed from the indices 2 and 5.
* The maximum difference is max(|nums[1] - nums[4]|, |nums[2] - nums[5]|) = max(0, 1) = 1. Therefore, we return 1.
*
* Example 2:
* Input: nums = [4,2,1,2], p = 1
* Output: 0
* Explanation: Let the indices 1 and 3 form a pair. The difference of that pair is |2 - 2| = 0, which is the minimum we can attain.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 0 <= nums[i] <= 10^9
* • 0 <= p <= (nums.length)/2
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort the array and binary search the minimum possible maximum difference.
    // For each guess, greedily form non-overlapping pairs with diff ≤ guess.
    // If we can form at least `p` such pairs, try smaller diffs; else, increase.
    // Time complexity: O(n log n + n log(maxDiff))
    // Space complexity: O(1) (in-place sorting and constant extra space)
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int left = 0, right = nums[nums.length - 1] - nums[0];

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canFormPairs(nums, p, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canFormPairs(int[] nums, int p, int maxDiff) {
        int count = 0;
        for (int i = 1; i < nums.length && count < p; ) {
            if (nums[i] - nums[i - 1] <= maxDiff) {
                count++;
                i += 2; // skip next index to avoid overlap
            } else {
                i++;
            }
        }
        return count >= p;
    }
}
