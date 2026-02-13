// Source: https://leetcode.com/problems/longest-balanced-substring-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-13
// At the time of submission:
//   Runtime 72 ms Beats 100.00%
//   Memory 100.63 MB Beats 97.01%

/****************************************
* 
* You are given a string `s` consisting only of the characters `'a'`, `'b'`, and `'c'`.
* A substring of `s` is called balanced if all distinct characters in the substring
* _appear the same number of times.
* Return the length of the longest balanced substring of `s`.
*
* Example 1:
* Input: s = "abbac"
* Output: 4
* Explanation:
* The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.
*
* Example 2:
* Input: s = "aabcc"
* Output: 3
* Explanation:
* The longest balanced substring is "abc" because all distinct characters 'a', 'b' and 'c' each appear exactly 1 time.
*
* Example 3:
* Input: s = "aba"
* Output: 2
* Explanation:
* One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".
*
* Constraints:
* • `1 <= s.length <= 10^5`
* • `s contains only the characters `'a'`, `'b'`, and `'c'`.
* 
****************************************/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // We split the problem into three cases: single-char runs, two-char balance,
    // and three-char balance. Each case is solved using prefix differences to
    // detect equal-frequency substrings in linear time. Case 2 resets on invalid
    // characters, while Case 3 hashes prefix count differences. 
    // Time is O(n) and space is O(n).

    static class DiffPair {
        int abDiff;
        int acDiff;

        DiffPair(int abDiff, int acDiff) {
            this.abDiff = abDiff;
            this.acDiff = acDiff;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DiffPair)) return false;
            DiffPair p = (DiffPair) o;
            return abDiff == p.abDiff && acDiff == p.acDiff;
        }

        @Override
        public int hashCode() {
            return 31 * abDiff + acDiff;
        }
    }

    public int longestBalanced(String s) {

        char[] chars = s.toCharArray();
        int n = chars.length;
        int answer = 0;

        // Case 1: longest run of a single character
        int runLength = 1;
        for (int i = 1; i < n; i++) {
            if (chars[i] == chars[i - 1]) {
                runLength++;
            } else {
                answer = Math.max(answer, runLength);
                runLength = 1;
            }
        }
        answer = Math.max(answer, runLength);

        // Case 2: exactly two characters with equal frequency
        answer = Math.max(answer, longestTwoCharBalanced(chars, 'a', 'b'));
        answer = Math.max(answer, longestTwoCharBalanced(chars, 'a', 'c'));
        answer = Math.max(answer, longestTwoCharBalanced(chars, 'b', 'c'));

        // Case 3: all three characters with equal frequency
        int countA = 0, countB = 0, countC = 0;
        Map<DiffPair, Integer> firstSeen = new HashMap<>();

        for (int i = 0; i < n; i++) {

            if (chars[i] == 'a') countA++;
            else if (chars[i] == 'b') countB++;
            else countC++;

            // Entire prefix is balanced
            if (countA == countB && countA == countC) {
                answer = Math.max(answer, countA + countB + countC);
            }

            DiffPair key = new DiffPair(countA - countB, countA - countC);

            Integer prevIndex = firstSeen.get(key);
            if (prevIndex != null) {
                answer = Math.max(answer, i - prevIndex);
            } else {
                firstSeen.put(key, i);
            }
        }

        return answer;
    }

    private int longestTwoCharBalanced(char[] chars, char x, char y) {

        int n = chars.length;
        int maxLen = 0;

        int[] firstIndex = new int[2 * n + 1];
        Arrays.fill(firstIndex, -2);

        int lastInvalidIndex = -1;
        int diff = n; // offset to avoid negative indices
        firstIndex[diff] = -1;

        for (int i = 0; i < n; i++) {

            if (chars[i] != x && chars[i] != y) {
                lastInvalidIndex = i;
                diff = n;
                firstIndex[diff] = lastInvalidIndex;
            } else {
                diff += (chars[i] == x) ? 1 : -1;

                if (firstIndex[diff] < lastInvalidIndex) {
                    firstIndex[diff] = i;
                } else {
                    maxLen = Math.max(maxLen, i - firstIndex[diff]);
                }
            }
        }

        return maxLen;
    }
}
