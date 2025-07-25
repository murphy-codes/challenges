// Source: https://leetcode.com/problems/find-the-prefix-common-array-of-two-arrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-14
// At the time of submission:
//   Runtime 5 ms Beats 48.55%
//   Memory 46.04 MB Beats 6.71%

/****************************************
* 
* • You are given two 0-indexed integer permutations `A` and `B` of length `n`.
* • A prefix common array of `A` and `B` is an array `C` such that `C[i]` is equal to the count of numbers that are present at or before the index `i` in both `A` and `B`.
* • Return the prefix common array of `A` and `B`.
* • A sequence of `n` integers is called a permutation if it contains all integers from `1` to `n` exactly once.
* 
* Example 1:
* Input: A = [1,3,2,4], B = [3,1,2,4]
* Output: [0,2,3,4]
* Explanation: At i = 0: no number is common, so C[0] = 0.
* At i = 1: 1 and 3 are common in A and B, so C[1] = 2.
* At i = 2: 1, 2, and 3 are common in A and B, so C[2] = 3.
* At i = 3: 1, 2, 3, and 4 are common in A and B, so C[3] = 4.
* 
* Example 2:
* Input: A = [2,3,1], B = [3,1,2]
* Output: [0,1,3]
* Explanation: At i = 0: no number is common, so C[0] = 0.
* At i = 1: only 3 is common in A and B, so C[1] = 1.
* At i = 2: 1, 2, and 3 are common in A and B, so C[2] = 3.
* 
* Constraints:
* • 1 <= A.length == B.length == n <= 50
* • 1 <= A[i], B[i] <= n
* • It is guaranteed that A and B are both a permutation of n integers.
* 
****************************************/

class Solution {
    // We'll use sets to efficiently track numbers seen in A and B so far.
    // At each index i, we add A[i] and B[i] to their respective sets.
    // We check if A[i] or B[i] already exists in the opposite set to find common numbers.
    // A counter tracks the total common numbers, which we store in the result array C.
    // This approach ensures efficient membership checks and avoids double counting.
    // Time complexity: O(n), Space complexity: O(n).
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int n = A.length; // Length of arrays
        int[] C = new int[n]; // Result array
        Set<Integer> seenA = new HashSet<>(); // Numbers seen in A
        Set<Integer> seenB = new HashSet<>(); // Numbers seen in B
        int common = 0; // Count of common numbers
        
        for (int i = 0; i < n; i++) {
            // Add current elements to respective sets
            seenA.add(A[i]);
            seenB.add(B[i]);
            
            // Check for common numbers and increment if found
            if (seenB.contains(A[i])) {
                common++;
            }
            if (seenA.contains(B[i]) && A[i] != B[i]) { // Avoid double counting
                common++;
            }
            
            // Update result array
            C[i] = common;
        }
        
        return C; // Return the prefix common array
    }
}
