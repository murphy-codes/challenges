// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-15
// At the time of submission:
//   Runtime 15 ms Beats 97.56%
//   Memory 47.45 MB Beats 17.07%

/****************************************
* 
* You are given a string array `words`, and an array `groups`,
* _ both arrays having length `n`.
* The hamming distance between two strings of equal length is the number of
* _ positions at which the corresponding characters are different.
* You need to select the longest subsequence from an array of indices
* _ `[0, 1, ..., n - 1]`, such that for the subsequence denoted as
* _ `[i_0, i_1, ..., i_k-1]` having length `k`, the following holds:
* • For adjacent indices in the subsequence, their corresponding groups are
* _ unequal, i.e., groups[ij] != groups[ij+1], for each j where 0 < j + 1 < k.
* • `words[i_j]` and `words[i_j+1]` are equal in length, and the hamming distance
* _ between them is `1`, where `0 < j + 1 < k`, for all indices in the subsequence.
* Return a string array containing the words corresponding to the indices
* _ (in order) in the selected subsequence. If there are multiple answers,
* _ return any of them.
* Note: strings in `words` may be unequal in length.
*
* Example 1:
* Input: words = ["bab","dab","cab"], groups = [1,2,2]
* Output: ["bab","cab"]
* Explanation: A subsequence that can be selected is [0,2].
* groups[0] != groups[2]
* words[0].length == words[2].length, and the hamming distance between them is 1.
* So, a valid answer is [words[0],words[2]] = ["bab","cab"].
* Another subsequence that can be selected is [0,1].
* groups[0] != groups[1]
* words[0].length == words[1].length, and the hamming distance between them is 1.
* So, another valid answer is [words[0],words[1]] = ["bab","dab"].
* It can be shown that the length of the longest subsequence of indices that satisfies the conditions is 2.
*
* Example 2:
* Input: words = ["a","b","c","d"], groups = [1,2,3,4]
* Output: ["a","b","c","d"]
* Explanation: We can select the subsequence [0,1,2,3].
* It satisfies both conditions.
* Hence, the answer is [words[0],words[1],words[2],words[3]] = ["a","b","c","d"].
* It has the longest length among all subsequences of indices that satisfy the conditions.
* Hence, it is the only answer.
*
* Constraints:
* • 1 <= n == words.length == groups.length <= 1000
* • 1 <= words[i].length <= 10
* • 1 <= groups[i] <= n
* • `words` consists of distinct strings.
* • `words[i]` consists of lowercase English letters.
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    // Builds the longest subsequence of words such that:
    //   - Each word differs from the next by exactly one character (Hamming dist = 1)
    //   - Adjacent words have different group values
    // Uses bitmask encoding (5 bits per char) to efficiently compare Hamming-1 words
    // Time complexity: O(n * L) where L is max word length (<=10)
    // Space complexity: O(n * L) due to map and dp arrays
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;

        // Map from bitmask with one bit flipped to list of indices
        Map<Long, List<Integer>> bitmaskToIndices = new HashMap<>();

        int[] nextIndex = new int[n];   // To reconstruct the path
        int[] dp = new int[n];          // dp[i] = length of longest valid subsequence starting at i
        Arrays.fill(nextIndex, n);      // n means end of subsequence

        int maxStartIdx = 0;

        // Traverse from end to beginning to build valid subsequences
        for (int i = n - 1; i >= 0; i--) {
            String word = words[i];
            int len = word.length();
            long fullMask = 0L;

            // Store bitmasks for each character position
            long[] charMasks = new long[len];
            for (int j = 0; j < len; j++) {
                // Use 5 bits per character: (a = 1, b = 2, ..., z = 26)
                charMasks[j] = (long)(word.charAt(j) - 'a' + 1) << (5 * j);
                fullMask |= charMasks[j];
            }

            int maxLen = 1;
            for (int j = 0; j < len; j++) {
                // Flip the j-th character to create a Hamming-1 mask
                long hammingMask = fullMask ^ charMasks[j];
                List<Integer> candidates = bitmaskToIndices.getOrDefault(hammingMask, new ArrayList<>());

                for (int idx : candidates) {
                    if (groups[i] == groups[idx]) continue; // Must have different group
                    if (dp[idx] + 1 > maxLen) {
                        maxLen = dp[idx] + 1;
                        nextIndex[i] = idx;
                    }
                }

                // Store current index for future matching
                bitmaskToIndices.computeIfAbsent(hammingMask, k -> new ArrayList<>()).add(i);
            }

            dp[i] = maxLen;
            if (dp[i] > dp[maxStartIdx]) {
                maxStartIdx = i;
            }
        }

        // Reconstruct result path
        List<String> result = new ArrayList<>();
        for (int i = maxStartIdx; i < n; i = nextIndex[i]) {
            result.add(words[i]);
        }

        return result;
    }
}