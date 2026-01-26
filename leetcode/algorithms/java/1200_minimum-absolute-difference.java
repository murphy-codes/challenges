// Source: https://leetcode.com/problems/minimum-absolute-difference/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-25
// At the time of submission:
//   Runtime 21 ms Beats 72.27%
//   Memory 63.74 MB Beats 76.30%

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

class Solution {
    // Sort the input array to bring elements with smallest differences together.
    // Iterate through consecutive pairs to find the minimum absolute difference.
    // Collect all pairs that have this minimum difference in ascending order.
    // Time complexity: O(n log n) due to sorting, where n is arr.length.
    // Space complexity: O(n) for storing the result list of pairs.
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(arr);
        int minDiff = 1_000_000;
        for (int i = 0; i < arr.length - 1; i++) {
            int diff = arr[i+1] - arr[i];
            if(diff < minDiff) {
                minDiff = diff;
                results = new ArrayList<>();
                results.add(Arrays.asList(arr[i], arr[i+1]));
            } else if (diff == minDiff) {
                results.add(Arrays.asList(arr[i], arr[i+1]));
            }
        }
        return results;
    }
}