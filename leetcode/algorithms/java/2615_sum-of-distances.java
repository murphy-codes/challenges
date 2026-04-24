// Source: https://leetcode.com/problems/sum-of-distances/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-24
// At the time of submission:
//   Runtime 13 ms Beats 100.00%
//   Memory 108.22 MB Beats 95.13%

/****************************************
* 
* You are given a 0-indexed integer array `nums`. There exists an array `arr`
* _ of length `nums.length`, where `arr[i]` is the sum of `|i - j|` over all
* _ `j` such that `nums[j] == nums[i]` and `j != i`. If there is no such `j`,
* _ set `arr[i]` to be `0`.
* Return the array `arr`.
*
* Example 1:
* Input: nums = [1,3,1,1,2]
* Output: [5,0,3,4,0]
* Explanation:
* When i = 0, nums[0] == nums[2] and nums[0] == nums[3]. Therefore, arr[0] = |0 - 2| + |0 - 3| = 5.
* When i = 1, arr[1] = 0 because there is no other index with value 3.
* When i = 2, nums[2] == nums[0] and nums[2] == nums[3]. Therefore, arr[2] = |2 - 0| + |2 - 3| = 3.
* When i = 3, nums[3] == nums[0] and nums[3] == nums[2]. Therefore, arr[3] = |3 - 0| + |3 - 2| = 4.
* When i = 4, arr[4] = 0 because there is no other index with value 2.
*
* Example 2:
* Input: nums = [0,5,3]
* Output: [0,0,0]
* Explanation: Since each element in nums is distinct, arr[i] = 0 for all i.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `0 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Store running left/right counts and index sums for each value.
    // First pass builds total right-side sums/counts. During the second
    // pass, each index moves from right to left, allowing distance to be
    // computed in O(1) using aggregated sums instead of index lists.
    // Time: O(n), Space: O(n).

    private class Data {
        long leftSum;    // sum of indices already processed
        long rightSum;   // sum of indices not yet processed

        int leftCount;   // count of processed indices
        int rightCount;  // count of unprocessed indices
    }

    public long[] distance(int[] nums) {
        int n = nums.length;

        Map<Integer, Data> map = new HashMap<>();

        // First pass:
        // initialize right-side totals for every value
        for (int i = 0; i < n; i++) {
            Data data = map.get(nums[i]);

            if (data == null) {
                data = new Data();
                map.put(nums[i], data);
            }

            data.rightSum += i;
            data.rightCount++;
        }

        long[] result = new long[n];

        // Second pass:
        // move each index from right side to left side
        for (int i = 0; i < n; i++) {
            Data data = map.get(nums[i]);

            result[i] =
                data.rightSum - data.leftSum
                + 1L * data.leftCount * i
                - 1L * i * data.rightCount;

            data.leftSum += i;
            data.rightSum -= i;

            data.leftCount++;
            data.rightCount--;
        }

        return result;
    }
}