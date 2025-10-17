// Source: https://leetcode.com/problems/maximize-the-number-of-partitions-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-16
// At the time of submission:
//   Runtime 518 ms Beats 21.43%
//   Memory 114.95 MB Beats 7.14%

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

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Use DP + bitmasking: dfs(i, mask, canChange) = max partitions from i-th char
    // with current partition’s letters `mask` and ability to change 1 char.
    // At each step, either include current char (if mask bit count ≤ k) or break partition.
    // If change is unused, try changing to each letter. Memoize to avoid recomputation.
    // Time ≈ O(n * 2^k * 26), Space ≈ O(n * 2^k).

    private String s;
    private int n, K;
    private Map<String, Integer> memo;  // or use a better encoded key

    public int maxPartitionsAfterOperations(String s, int k) {
        this.s = s;
        this.n = s.length();
        this.K = k;
        this.memo = new HashMap<>();
        // +1 to count first partition
        return dfs(0, 0, true) + 1;
    }

    // i = current index, mask = letters used so far in current partition,
    // canChange = whether we still can change a char
    private int dfs(int i, int mask, boolean canChange) {
        if (i == n) {
            return 0;
        }
        String key = i + "," + mask + "," + (canChange ? 1 : 0);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int result = 0;

        // Option 1: use the current letter as is
        int bit = 1 << (s.charAt(i) - 'a');
        int newMask = mask | bit;
        if (Integer.bitCount(newMask) > K) {
            // must start a new partition
            result = dfs(i + 1, bit, canChange) + 1;
        } else {
            // we can continue in the same partition
            result = dfs(i + 1, newMask, canChange);
        }

        // Option 2: change the current letter (if possible)
        if (canChange) {
            for (int c = 0; c < 26; c++) {
                int changeBit = 1 << c;
                int candidateMask = mask | changeBit;
                if (Integer.bitCount(candidateMask) > K) {
                    // starting a new partition with changed letter
                    result = Math.max(result, dfs(i + 1, changeBit, false) + 1);
                } else {
                    // staying in same partition
                    result = Math.max(result, dfs(i + 1, candidateMask, false));
                }
            }
        }

        memo.put(key, result);
        return result;
    }
}
