// Source: https://leetcode.com/problems/bitwise-ors-of-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-30
// At the time of submission:
//   Runtime 359 ms Beats 45.49%
//   Memory 73.25 MB Beats 63.52%

/****************************************
* 
* Given an integer array `arr`, return the number of distinct bitwise ORs of all
* _ the non-empty subarrays of `arr`.
* The bitwise OR of a subarray is the bitwise OR of each integer in the subarray.
* _ The bitwise OR of a subarray of one integer is that integer.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: arr = [0]
* Output: 1
* Explanation: There is only one possible result: 0.
*
* Example 2:
* Input: arr = [1,1,2]
* Output: 3
* Explanation: The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
* These yield the results 1, 1, 2, 1, 3, 3.
* There are 3 unique values, so the answer is 3.
*
* Example 3:
* Input: arr = [1,2,4]
* Output: 6
* Explanation: The possible results are 1, 2, 3, 4, 6, and 7.
*
* Constraints:
* • 1 <= arr.length <= 5 * 10^4
* • 0 <= arr[i] <= 10^9
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // For each element, track all OR results of subarrays ending at that index.
    // Reuse previous results by OR'ing current number with each previous value.
    // Add all results to a global set to keep track of unique OR values.
    // Time: O(n * B), where B is the number of bits (~30), Space: O(n)
    public int subarrayBitwiseORs(int[] arr) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> prev = new HashSet<>();
        
        for (int num : arr) {
            Set<Integer> curr = new HashSet<>();
            curr.add(num);
            for (int val : prev) {
                curr.add(val | num);
            }
            prev = curr;
            result.addAll(curr);
        }
        
        return result.size();
    }
}
