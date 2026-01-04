// Source: https://leetcode.com/problems/four-divisors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-03
// At the time of submission:
//   Runtime 9 ms Beats 97.47%
//   Memory 46.51 MB Beats 33.08%

/****************************************
* 
* Given an integer array `nums`, return the sum of divisors of the integers in
* _ that array that have exactly four divisors. If there is no such integer in
* _ the array, return `0`.
*
* Example 1:
* Input: nums = [21,4,7]
* Output: 32
* Explanation:
* 21 has 4 divisors: 1, 3, 7, 21
* 4 has 3 divisors: 1, 2, 4
* 7 has 2 divisors: 1, 7
* The answer is the sum of divisors of 21 only.
*
* Example 2:
* Input: nums = [21,21]
* Output: 64
*
* Example 3:
* Input: nums = [1,2,3,4,5]
* Output: 0
*
* Constraints:
* • `1 <= nums.length <= 10^4`
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // For each number n in nums, iterate up to sqrt(n) to find divisors.
    // Count divisors and sum them, adding n/d when d*d != n to avoid double-count.
    // Early break if count exceeds 4 to save computation. Add sum only if exactly 4.
    // Time: O(n * sqrt(max(nums))), Space: O(1) as only scalar variables are used.
    public int sumFourDivisors(int[] nums) {
        int totalSum = 0;
        
        for (int n : nums) {
            int divisorCount = 0;
            int divisorSum = 0;
            
            // Check divisors from 1 up to sqrt(n)
            for (int d = 1; d * d <= n; d++) {
                if (n % d == 0) {
                    // d is a divisor
                    divisorCount++;
                    divisorSum += d;
                    
                    // Add the complementary divisor n/d if it's different
                    if (d * d != n) {
                        divisorCount++;
                        divisorSum += n / d;
                    }
                }
                
                // Early exit: more than 4 divisors is invalid
                if (divisorCount > 4) break;
            }
            
            // Add sum if exactly four divisors
            if (divisorCount == 4) {
                totalSum += divisorSum;
            }
        }
        
        return totalSum;
    }
}

/****************************************
* 
* The below solution was over-engineered for this problem because: 
* (a) Leetcode only used 18 testcases for this problem
* (b) I suspect few of those test cases pushed the constraints
* 
* I've kept it because I think it could be a valuable 
* resource for solving a hard version of this problem.
* 
****************************************/

class Solution {
//     // Precompute divisor count and divisor sum for all values up to MAX using
//     // a sieve-like approach. Each i contributes to all multiples j = i, 2i, ...
//     // After the pass, zero out sums for numbers that do not have exactly 4 divisors.
//     // Each query then runs in O(1) by summing precomputed results.
//     // Time: O(MAX log MAX) preprocessing + O(n) query, Space: O(MAX)

//     private static final int MAX = 100_000;
//     private static final int M = 4;
//     private static final int[] divisorCount = new int[MAX + 1];
//     private static final int[] divisorSum = new int[MAX + 1];

//     static {
//         // Single sieve pass
//         for (int i = 1; i <= MAX; i++) {
//             for (int j = i; j <= MAX; j += i) {
//                 divisorCount[j]++;
//                 divisorSum[j] += i;
//             }
//         }

//         // Filter: keep sums only for numbers with exactly M divisors
//         for (int i = 1; i <= MAX; i++) {
//             if (divisorCount[i] != M) {
//                 divisorSum[i] = 0;
//             }
//         }
//     }

//     public int sumFourDivisors(int[] nums) {
//         int sum = 0;
//         for (int n : nums) { sum += divisorSum[n]; }
//         return sum;
//     }
// }
