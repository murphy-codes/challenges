# Source: https://leetcode.com/problems/word-subsets/
# Author: Tom Murphy https://github.com/murphy-codes/
# Date: 2025-01-10
# At the time of submission:
#   Runtime 339 ms Beats 64.23%
#   Memory 21.09 MB Beats 93.38%

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

from collections import Counter
from typing import List

class Solution:
    # We'll solve this using frequency counts for each letter in the strings.
    # First, we create a "max frequency map" from words2, representing the maximum count of each letter required across all words in words2.
    # Next, for each string in words1, we compute its frequency count and compare it against the max frequency map.
    # If a string satisfies all the requirements (i.e., has at least the required frequency for each letter in words2), it's added to the result list.
    # This approach runs in O(n1 * m + n2 * m) time, where n1 and n2 are the lengths of words1 and words2, 
    # and m is the average string length. Space complexity is O(1), as the frequency maps store counts for a constant number of characters (26 lowercase English letters).
    def wordSubsets(self, words1: List[str], words2: List[str]) -> List[str]:
        # Step 1: Build max frequency map for words2
        max_freq = Counter()
        for word in words2:
            word_count = Counter(word)
            for char, count in word_count.items():
                max_freq[char] = max(max_freq[char], count)
        
        # Step 2: Check universality for each word in words1
        result = []
        for word in words1:
            word_count = Counter(word)
            # Check if word_count satisfies max_freq
            if all(word_count[char] >= count for char, count in max_freq.items()):
                result.append(word)
        
        return result
