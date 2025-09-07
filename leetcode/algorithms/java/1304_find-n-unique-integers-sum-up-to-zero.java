// Source: https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-07
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.75 MB Beats 51.66%

/****************************************
* 
* Given an integer `n`, return any array containing `n` unique integers
* _ such that they add up to `0`.
*
* Example 1:
* Input: n = 5
* Output: [-7,-1,1,3,4]
* Explanation: These arrays also are accepted [-5,-1,1,2,3] , [-3,-1,2,-2,4].
*
* Example 2:
* Input: n = 3
* Output: [-1,0,1]
*
* Example 3:
* Input: n = 1
* Output: [0]
*
* Constraints:
* • 1 <= n <= 1000
* 
****************************************/

class Solution {
    // This solution constructs an array of n unique integers that sum to zero by
    // centering the sequence around zero. If n is odd, zero is included naturally;
    // if n is even, we skip zero to maintain uniqueness. Each number is generated
    // in O(1) time using a simple formula, producing a sequence like [-k,...,-1,0,1,...,k].
    // Time complexity is O(n) to fill the array, and space complexity is O(n) for the array.
    public int[] sumZero(int n) {
        int[] unique = new int[n];
        if (n==0) return unique;
        int j = (n / 2);
        for (int i = 0; i < n; i++) {
            if (n % 2 == 0 && i-j == 0) j-=1; // skip 0 if n is even
           unique[i]=i-j;
        }
        return unique;
    }
}