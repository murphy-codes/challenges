// Source: https://leetcode.com/problems/minimum-absolute-difference/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-25
// At the time of submission:
//   Runtime 9 ms Beats 99.64%
//   Memory 63.51 MB Beats 93.15%

/****************************************
* 
* Given an array of distinct integers `arr`, find all pairs of elements with
* _ the minimum absolute difference of any two elements.
* Return a list of pairs in ascending order(with respect to pairs),
* _ each pair `[a, b]` follows
* • `a, b` are from `arr`
* • `a < b`
* • `b - a` equals to the minimum absolute difference of any two elements in `arr`
*
* Example 1:
* Input: arr = [4,2,1,3]
* Output: [[1,2],[2,3],[3,4]]
* Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
*
* Example 2:
* Input: arr = [1,3,6,10,15]
* Output: [[1,3]]
*
* Example 3:
* Input: arr = [3,8,-10,23,19,-4,-14,27]
* Output: [[-14,-10],[19,23],[23,27]]
*
* Constraints:
* • `2 <= arr.length <= 10^5`
* • `-10^6 <= arr[i] <= 10^6`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Solution {
    // Use a counting-sort approach to sort the array efficiently by mapping values
    // into a boolean array of size (max-min+1). Then reconstruct the sorted array.
    // Iterate through consecutive pairs to find the minimum absolute difference.
    // Collect all pairs with this difference into a result list in ascending order.
    // Time: O(n + range) where range = max - min; Space: O(range) for boolean array.
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        // Find min and max of array
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // Counting sort using boolean array
        int range = max - min + 1;
        boolean[] present = new boolean[range];
        for (int num : arr) {
            present[num - min] = true;
        }

        // Reconstruct sorted array from boolean array
        int idx = 0;
        for (int i = 0; i < range; i++) {
            if (present[i]) {
                arr[idx++] = i + min;
            }
        }

        // Find minimum absolute difference pairs
        List<List<Integer>> result = new ArrayList<>();
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - 1; i++) {
            int currentDiff = arr[i + 1] - arr[i];
            if (currentDiff < minDiff) {
                result.clear();
                minDiff = currentDiff;
            }
            if (currentDiff == minDiff) {
                result.add(Arrays.asList(arr[i], arr[i + 1]));
            }
        }

        return result;
    }
}
