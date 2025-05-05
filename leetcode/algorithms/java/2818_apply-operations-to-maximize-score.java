// Source: https://leetcode.com/problems/apply-operations-to-maximize-score/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-29
// At the time of submission:
//   Runtime 28 ms Beats 99.99%
//   Memory 60.23 MB Beats 74.21%

/****************************************
* 
* You are given an array `nums` of `n` positive integers and an integer `k`.
* Initially, you start with a score of `1`. You have to maximize your score
* by applying the following operation at most `k` times:
* • Choose any non-empty subarray `nums[l, ..., r]` that you haven't chosen previously.
* • Choose an element `x` of `nums[l, ..., r]` with the highest prime score.
* If multiple such elements exist, choose the one with the smallest index.
* • Multiply your score by `x`.
* Here, `nums[l, ..., r]` denotes the subarray of `nums` starting at index `l` and
* ending at the index `r`, both ends being inclusive.
* The prime score of an integer x is equal to the number of distinct prime factors of x.
* For example, the prime score of `300` is `3` since `300 = 2 * 2 * 3 * 5 * 5`.
* Return the maximum possible score after applying at most `k` operations.
* Since the answer may be large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: nums = [8,3,9,3,8], k = 2
* Output: 81
* Explanation: To get a score of 81, we can apply the following operations:
* - Choose subarray nums[2, ..., 2]. nums[2] is the only element in this subarray. Hence, we multiply the score by nums[2]. The score becomes 1 * 9 = 9.
* - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 1, but nums[2] has the smaller index. Hence, we multiply the score by nums[2]. The score becomes 9 * 9 = 81.
* It can be proven that 81 is the highest score one can obtain.
*
* Example 2:
* Input: nums = [19,12,14,6,10,18], k = 3
* Output: 4788
* Explanation: To get a score of 4788, we can apply the following operations:
* - Choose subarray nums[0, ..., 0]. nums[0] is the only element in this subarray. Hence, we multiply the score by nums[0]. The score becomes 1 * 19 = 19.
* - Choose subarray nums[5, ..., 5]. nums[5] is the only element in this subarray. Hence, we multiply the score by nums[5]. The score becomes 19 * 18 = 342.
* - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 2, but nums[2] has the smaller index. Hence, we multipy the score by nums[2]. The score becomes 342 * 14 = 4788.
* It can be proven that 4788 is the highest score one can obtain.
*
* Constraints:
* • 1 <= nums.length == n <= 10^5
* • 1 <= nums[i] <= 10^5
* • 1 <= k <= min(n * (n + 1) / 2, 10^9)
* 
****************************************/

class Solution {
    // This solution calculates a maximum product score using contribution counts  
    // derived from a monotonic stack and a prime score-based sorting strategy.  
    // It leverages dense remapping of unique numbers for efficiency and handles  
    // both full and partial subarray score accumulation. Time: O(n log n), Space: O(n).
    private static final int MAX = 100001;
    private static final int[] primeScores = computePrimeScores();
    private static final int MOD = 1_000_000_007;

    static int[] numberToIndex = new int[MAX];
    static int[] indexToNumber = new int[MAX];

    public int maximumScore(List<Integer> nums, int k) {
        int n = nums.size();
        int totalSubarrays = n * (n + 1) / 2;

        int[] numArray = new int[n];
        BitSet uniqueNumbersBitSet = new BitSet();

        // Initialize the working array and mark unique numbers
        for (int i = 0; i < n; ++i) {
            int num = nums.get(i);
            numArray[i] = num;
            if (k != totalSubarrays) uniqueNumbersBitSet.set(num);
        }

        long[] contributionCounts = new long[n];
        int[] monoStack = new int[n];
        int top = -1;

        // Monotonic stack to calculate contribution count (span) for each element
        for (int i = 0; i < n; i++) {
            int primeFactorScore = primeScores[numArray[i]];
            while (top != -1 && primeScores[numArray[monoStack[top]]] < primeFactorScore) {
                int index = monoStack[top--];
                contributionCounts[index] *= (i - index);
            }

            int prevIndex = (top == -1) ? -1 : monoStack[top];
            contributionCounts[i] = i - prevIndex;
            monoStack[++top] = i;
        }

        // Finish calculating contribution for remaining elements
        while (top != -1) {
            int index = monoStack[top--];
            contributionCounts[index] *= (n - index);
        }

        long result = 1;

        // Shortcut: if k == total possible subarrays, just multiply all contributions
        if (k == totalSubarrays) {
            for (int i = 0; i < n; ++i) {
                result = pow(numArray[i], contributionCounts[i], result);
            }
            return (int) result;
        }

        // Remap unique numbers to dense indices for efficient sorting
        int uniqueIndex = 0;
        for (int num = uniqueNumbersBitSet.nextSetBit(0); num != -1; num = uniqueNumbersBitSet.nextSetBit(num + 1)) {
            indexToNumber[uniqueIndex] = num;
            numberToIndex[num] = uniqueIndex++;
        }

        long[] scoreCount = new long[uniqueIndex];
        for (int i = 0; i < n; ++i) {
            int mapIndex = numberToIndex[numArray[i]];
            scoreCount[mapIndex] = Math.min(k, scoreCount[mapIndex] + contributionCounts[i]);
        }

        // Iterate from largest number to smallest, greedily applying score
        for (int i = uniqueIndex - 1; i >= 0; --i) {
            long times = Math.min(k, scoreCount[i]);
            result = pow(indexToNumber[i], times, result);
            k -= times;
            if (k == 0) return (int) result;
        }

        return (int) result;
    }

    private static int[] computePrimeScores() {
        int[] scores = new int[MAX];
        for (int i = 2; i < MAX; i++) {
            if (scores[i] == 0) {
                for (int j = i; j < MAX; j += i) {
                    scores[j]++;
                }
            }
        }
        return scores;
    }

    // Modular exponentiation: result * base^exp % MOD
    private long pow(long base, long exp, long result) {
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }
}