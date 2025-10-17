// Source: https://leetcode.com/problems/maximize-the-number-of-partitions-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-17
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 42.39 MB Beats 92.86%

/****************************************
* 
* You are given a string `s` and an integer `k`.
* First, you are allowed to change at most one index in `s` to another lowercase
* _ English letter.
* After that, do the following partitioning operation until `s` is empty:
* • Choose the longest prefix of `s` containing at most `k` distinct characters.
* • Delete the prefix from `s` and increase the number of partitions by one.
* __ The remaining characters (if any) in `s` maintain their initial order.
* Return an integer denoting the maximum number of resulting partitions after
* _ the operations by optimally choosing at most one index to change.
*
* Example 1:
* Input: s = "accca", k = 2
* Output: 3
* Explanation:
* The optimal way is to change s[2] to something other than a and c, for example, b. then it becomes "acbca".
* Then we perform the operations:
* The longest prefix containing at most 2 distinct characters is "ac", we remove it and s becomes "bca".
* Now The longest prefix containing at most 2 distinct characters is "bc", so we remove it and s becomes "a".
* Finally, we remove "a" and s becomes empty, so the procedure ends.
* Doing the operations, the string is divided into 3 partitions, so the answer is 3.
*
* Example 2:
* Input: s = "aabaab", k = 3
* Output: 1
* Explanation:
* Initially s contains 2 distinct characters, so whichever character we change, it will contain at most 3 distinct characters, so the longest prefix with at most 3 distinct characters would always be all of it, therefore the answer is 1.
*
* Example 3:
* Input: s = "xxyz", k = 1
* Output: 4
* Explanation:
* The optimal way is to change s[0] or s[1] to something other than characters in s, for example, to change s[0] to w.
* Then s becomes "wxyz", which consists of 4 distinct characters, so as k is 1, it will divide into 4 partitions.
*
* Constraints:
* • `1 <= s.length <= 10^4`
* • `s` consists only of lowercase English letters.
* • `1 <= k <= 26`
* 
****************************************/

class Solution {
    // Uses two linear passes with bitmask tracking to count partitions and test
    // one allowed character change. The first (right→left) precomputes partitions
    // and used-letter masks; the second (left→right) simulates merges and splits.
    // Bit operations replace sets for O(1) updates. Overall time O(n), space O(n),
    // since only constant-size masks and per-index arrays are stored.

    static final int ALPHABET_SIZE = 26; // 'z' - 'a' + 1

    public int maxPartitionsAfterOperations(String s, int k) {
        if (k == ALPHABET_SIZE) {
            return 1; // whole string fits into one partition
        }

        int n = s.length();
        int[] rightPartitions = new int[n];
        int[] rightUsedMasks = new int[n];

        // ----- Pass 1: preprocess from right to left -----
        int usedMask = 0, distinctCount = 0, partitions = 1;
        for (int i = n - 1; i >= 0; --i) {
            int chBit = 1 << (s.charAt(i) - 'a');
            if ((usedMask & chBit) == 0) {
                if (distinctCount == k) {
                    // start new partition
                    distinctCount = 0;
                    usedMask = 0;
                    partitions++;
                }
                usedMask |= chBit;
                distinctCount++;
            }
            rightPartitions[i] = partitions;
            rightUsedMasks[i] = usedMask;
        }

        // ----- Pass 2: scan left to right, testing one possible change -----
        int leftPartitions = 0;
        int best = rightPartitions[0];
        for (int l = 0; l < n; ) {
            usedMask = 0;
            distinctCount = 0;
            int maskBeforeLastNew = 0;
            int repeatedBeforeLast = 0;
            int lastNewIdx = -1;
            int r = l;

            // build current partition until distincts exceed k
            while (r < n) {
                int chBit = 1 << (s.charAt(r) - 'a');
                if ((usedMask & chBit) == 0) {
                    if (distinctCount == k) break;
                    maskBeforeLastNew = usedMask;
                    lastNewIdx = r;
                    usedMask |= chBit;
                    distinctCount++;
                } else if (distinctCount < k) {
                    repeatedBeforeLast |= chBit;
                }
                r++;
            }

            // evaluate possible benefit from a single character change
            if (distinctCount == k) {
                if (lastNewIdx - l > Integer.bitCount(maskBeforeLastNew)) {
                    // change one letter before lastNewIdx to start a new partition at lastNewIdx
                    best = Math.max(best, leftPartitions + 1 + rightPartitions[lastNewIdx]);
                }
                if (lastNewIdx + 1 < r) {
                    // try changing s[lastNewIdx + 1]
                    if (lastNewIdx + 2 >= n) {
                        best = Math.max(best, leftPartitions + 2);
                    } else if (Integer.bitCount(rightUsedMasks[lastNewIdx + 2]) == k) {
                        int canUse =
                            ((1 << ALPHABET_SIZE) - 1) &
                            ~usedMask & ~rightUsedMasks[lastNewIdx + 2];
                        if (canUse > 0) {
                            best = Math.max(best, leftPartitions + 1 + 1 + rightPartitions[lastNewIdx + 2]);
                        } else {
                            best = Math.max(best, leftPartitions + 1 + rightPartitions[lastNewIdx + 2]);
                        }
                        int nextCharBit = 1 << (s.charAt(lastNewIdx + 1) - 'a');
                        if ((repeatedBeforeLast & nextCharBit) == 0) {
                            best = Math.max(best, leftPartitions + 1 + rightPartitions[lastNewIdx + 1]);
                        }
                    } else {
                        best = Math.max(best, leftPartitions + 1 + rightPartitions[lastNewIdx + 2]);
                    }
                }
            }

            l = r;          // move to next partition
            leftPartitions++;
        }

        return best;
    }
}
