// Source: https://leetcode.com/problems/maximum-candies-allocated-to-k-children/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-14

/****************************************
* 
* You are given a 0-indexed integer array `candies`. Each element in the array denotes a pile of candies of size `candies[i]`. You can divide each pile into any number of sub piles, but you cannot merge two piles together.
*
* You are also given an integer `k`. You should allocate piles of candies to `k` children such that each child gets the same number of candies. Each child can be allocated candies from only one pile of candies and some piles of candies may go unused.
*
* Return the maximum number of candies each child can get.
*
* Example 1:
* Input: candies = [5,8,6], k = 3
* Output: 5
* Explanation: We can divide candies[1] into 2 piles of size 5 and 3, and candies[2] into 2 piles of size 5 and 1. We now have five piles of candies of sizes 5, 5, 3, 5, and 1. We can allocate the 3 piles of size 5 to 3 children. It can be proven that each child cannot receive more than 5 candies.
*
* Example 2:
* Input: candies = [2,5], k = 11
* Output: 0
* Explanation: There are 11 children but only 7 candies in total, so it is impossible to ensure each child receives at least one candy. Thus, each child gets no candy and the answer is 0.
*
* Constraints:
* • 1 <= candies.length <= 10^5
* • 1 <= candies[i] <= 10^7
* • 1 <= k <= 10^12
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Uses binary search to find the maximum candies each child can get.
    // The search range is from 1 to the largest candy pile in O(log M) time.
    // For each mid value, we check if we can allocate candies to k children
    // using O(N) iteration. The total time complexity is O(N log M).
    // Space complexity is O(1) since only a few integer variables are used.
    public int maximumCandies(int[] candies, long k) {
        int left = 1, right = Arrays.stream(candies).max().getAsInt();
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long count = 0;

            // Count how many children can receive at least 'mid' candies
            for (int candy : candies) {
                count += candy / mid;
            }

            if (count >= k) { // If we can serve at least k children, increase mid
                result = mid;
                left = mid + 1;
            } else { // Otherwise, try a smaller mid
                right = mid - 1;
            }
        }

        return result;
    }
}
