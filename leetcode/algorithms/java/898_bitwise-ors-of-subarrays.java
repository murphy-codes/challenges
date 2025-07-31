// Source: https://leetcode.com/problems/bitwise-ors-of-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-30
// At the time of submission:
//   Runtime 165 ms Beats 82.38%
//   Memory 71.12 MB Beats 85.25%

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
    // For each index, OR the current value with previous elements to build
    // subarray ORs. Stop early if OR result doesn't change (due to bit overlap).
    // Store all distinct ORs in a global set to avoid duplicates.
    // Time: O(n * B), where B = number of bits (~30); Space: O(n)
    public int subarrayBitwiseORs(int[] arr) {
        Set<Integer> uniqueORs = new HashSet<>();

        for (int end = 0; end < arr.length; end++) {
            uniqueORs.add(arr[end]);

            for (int start = end - 1; start >= 0; start--) {
                // If OR'ing with arr[end] doesn't change arr[start], stop early
                if ((arr[start] | arr[end]) == arr[start]) break;

                arr[start] |= arr[end];  // In-place OR update
                uniqueORs.add(arr[start]);
            }
        }

        return uniqueORs.size();
    }
}
