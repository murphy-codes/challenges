// Source: https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-26
// At the time of submission:
//   Runtime 42 ms Beats 99.82%
//   Memory 44.63 MB Beats 96.85%

/****************************************
* 
* A sequence `x_1, x_2, ..., x_n` is Fibonacci-like if:
* • `n >= 3`
* • `x_i + x_i+1 == x_i+2` for all `i + 2 <= n`
* Given a strictly increasing array `arr` of positive integers forming a sequence, 
* _ return the length of the longest Fibonacci-like subsequence of `arr`. If one 
* _ does not exist, return `0`.
* A subsequence is derived from another sequence `arr` by deleting any number of 
* _ elements (including none) from `arr`, without changing the order of the 
* _ remaining elements. For example, `[3, 5, 8]` is a 
* _ subsequence of `[3, 4, 5, 6, 7, 8]`.
*
* Example 1:
* Input: arr = [1,2,3,4,5,6,7,8]
* Output: 5
* Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].
*
* Example 2:
* Input: arr = [1,3,7,11,12,14,18]
* Output: 3
* Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].
*
* Constraints:
* • 3 <= arr.length <= 1000
* • 1 <= arr[i] < arr[i + 1] <= 10^9
* 
****************************************/

class Solution {
    // This solution finds the length of the longest Fibonacci-like subsequence
    // by simulating all possible Fibonacci pairs using a greedy approach.
    // It uses a HashSet for O(1) lookups and prunes early with the golden ratio.
    // Time complexity: O(n^2 log M), where M is the max number in arr.
    // Space complexity: O(n) for the HashSet storing the array's elements.
    public int lenLongestFibSubseq(int[] arr) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : arr) {
            numSet.add(num);
        }

        int maxLength = 2;
        int n = arr.length;

        for (int i = 0; i < n - maxLength; i++) {
            // Prune if even the longest possible Fib sequence is too small
            if (arr[i] * Math.pow(1.618, maxLength - 1) > arr[n - 1])
                break;

            for (int j = i + 1; j < n - maxLength + 1; j++) {
                if (arr[j] * Math.pow(1.618, maxLength - 2) > arr[n - 1])
                    break;

                int first = arr[i];
                int second = arr[j];
                int currentLength = 2;

                // Try building Fibonacci sequence
                while (numSet.contains(first + second)) {
                    int next = first + second;
                    first = second;
                    second = next;
                    currentLength++;
                }

                maxLength = Math.max(maxLength, currentLength);
            }
        }

        return maxLength < 3 ? 0 : maxLength;
    }
}
