# Source: https://leetcode.com/problems/word-subsets/
# Author: Tom Murphy https://github.com/murphy-codes/
# Date: 2025-01-10
# At the time of submission:
#   Runtime 63 ms Beats 99.81%
#   Memory 21.54 MB Beats 11.70%

'''
You are given two string arrays `words1` and `words2`.
A string `b` is a subset of string `a` if every letter in `b` occurs in `a` including multiplicity.
• For example, "wrr" is a subset of "warrior" but is not a subset of "world".
A string `a` from `words1` is universal if for every string `b` in `words2`, `b` is a subset of `a`.
Return an array of all the universal strings in `words1`. You may return the answer in any order.

Example 1:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
Output: ["facebook","google","leetcode"]

Example 2:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
Output: ["apple","google","leetcode"]

Constraints:
• 1 <= words1.length, words2.length <= 10^4
• 1 <= words1[i].length, words2[i].length <= 10
• words1[i] and words2[i] consist only of lowercase English letters.
• All the strings of words1 are unique.
'''

from typing import List
from collections import defaultdict

class Solution:
    # For each word in words2, record the maximum number of times each letter
    # must appear. Then, for each word in words1, check if it meets or exceeds
    # those letter requirements using str.count(). Words that qualify are added
    # to the result list. Time: O(N * L1 * 26 + M * L2^2), Space: O(26) = O(1).
    def wordSubsets(self, words1: List[str], words2: List[str]) -> List[str]:
        result = []
        required_counts = defaultdict(int)

        # Remove duplicates from words2 to reduce redundant checks
        words2 = list(set(words2))

        # Step 1: Determine the max required frequency for each letter
        for word in words2:
            for char in word:
                char_freq = word.count(char)
                if char_freq > required_counts[char]:
                    required_counts[char] = char_freq

        # Step 2: Check each word in words1 against the required frequencies
        for word in words1:
            for char, min_count in required_counts.items():
                if word.count(char) < min_count:
                    break
            else:
                result.append(word)

        return result