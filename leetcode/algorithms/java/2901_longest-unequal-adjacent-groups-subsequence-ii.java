// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-15
// At the time of submission:
//   Runtime 41 ms Beats 82.93%
//   Memory 45.12 MB Beats 36.59%

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
import java.util.List;

class Solution {
    // Time Complexity: O(n^2 * l), where n = words.length, l = max word length
    // For each pair (i, j), we check equal length, group mismatch, and Hamming dist
    // Space Complexity: O(n) for dp and prev arrays to reconstruct the result
    // Efficient for n <= 1000 and word length <= 10, as guaranteed by constraints
    // This uses dynamic programming to find the longest valid subsequence

    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        int[] dp = new int[n];        // dp[i] = length of longest valid subsequence ending at i
        int[] prev = new int[n];      // prev[i] = previous index in the subsequence
        for (int i = 0; i < n; i++) {
            dp[i] = 1;     // Minimum subsequence is the word itself
            prev[i] = -1;  // Initialize with no previous
            for (int j = 0; j < i; j++) {
                if (groups[i] != groups[j] &&
                    words[i].length() == words[j].length() &&
                    hammingDistance(words[i], words[j]) == 1) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        prev[i] = j;
                    }
                }
            }
        }

        // Find index of maximum dp[i]
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i;
            }
        }

        // Reconstruct the sequence
        List<String> result = new ArrayList<>();
        while (maxIndex != -1) {
            result.add(0, words[maxIndex]);  // prepend to result
            maxIndex = prev[maxIndex];
        }

        return result;
    }

    private int hammingDistance(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) count++;
            if (count > 1) return count; // Early exit
        }
        return count;
    }
}
