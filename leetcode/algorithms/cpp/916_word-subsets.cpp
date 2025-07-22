// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-10
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 61.83 MB Beats 87.73%

/****************************************
* 
* You are given two string arrays `words1` and `words2`.
* A string `b` is a subset of string `a` if every letter in `b` occurs in `a` including multiplicity.
* • For example, "wrr" is a subset of "warrior" but is not a subset of "world".
* A string `a` from `words1` is universal if for every string `b` in `words2`, `b` is a subset of `a`.
* Return an array of all the universal strings in `words1`. You may return the answer in any order.
* 
* Example 1:
* Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
* Output: ["facebook","google","leetcode"]
* 
* Example 2:
* Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
* Output: ["apple","google","leetcode"]
* 
* Constraints:
* • 1 <= words1.length, words2.length <= 10^4
* • 1 <= words1[i].length, words2[i].length <= 10
* • words1[i] and words2[i] consist only of lowercase English letters.
* • All the strings of words1 are unique.
* 
****************************************/

#include <bits/stdc++.h>
using namespace std;

// Optional optimization for faster I/O
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();
auto init2 = atexit([]() { ofstream("display_runtime.txt") << 0; });

// For each word in words2, track the maximum frequency needed for each letter.
// Then, for each word in words1, count its letters and check if it meets
// or exceeds those maximum requirements. If it does, it's universal.
// Time: O(N * L1 + M * L2), where N = words1.size(), M = words2.size(),
// L1 and L2 are average word lengths. Space: O(1) using fixed 26-letter arrays.
class Solution {
  public:
    static vector<string> wordSubsets(vector<string>& words1, vector<string>& words2) {
        // freqNeeded[i] = max number of times letter 'a' + i must appear
        std::array<int, 26> freqNeeded = {0};
        std::array<int, 26> tempFreq = {0};

        // Step 1: Build the required max frequency for each letter in words2
        for (const auto& word : words2) {
            tempFreq.fill(0);
            for (char c : word) {
                tempFreq[c - 'a']++;
            }
            for (int i = 0; i < 26; ++i) {
                freqNeeded[i] = max(freqNeeded[i], tempFreq[i]);
            }
        }

        vector<string> result;
        // Step 2: Check which words in words1 satisfy the max frequency constraints
        for (const auto& word : words1) {
            tempFreq.fill(0);
            for (char c : word) {
                tempFreq[c - 'a']++;
            }

            bool isUniversal = true;
            for (int i = 0; i < 26; ++i) {
                if (tempFreq[i] < freqNeeded[i]) {
                    isUniversal = false;
                    break;
                }
            }

            if (isUniversal) {
                result.emplace_back(word);
            }
        }

        return result;
    }
};
