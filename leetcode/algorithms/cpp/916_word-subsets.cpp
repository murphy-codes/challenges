// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-10
// At the time of submission:
//   Runtime 56 ms Beats 56.44%
//   Memory 106.95 MB Beats 26.99%

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

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Solution {
// We'll solve this using frequency vectors of size 26 to represent lowercase English letters.
// First, we create a "max frequency map" from words2, representing the maximum count of each letter required.
// We compute the frequency of each letter for each word in words2, and for each letter, we keep track of the maximum frequency across all words in words2.
// Next, for each string in words1, we compute its frequency vector and compare it against the max frequency map.
// If a string satisfies all the requirements (i.e., it has at least the required frequency for each letter), it's added to the result list.
// This approach runs in O(n1 * m + n2 * m) time, where n1 and n2 are the lengths of words1 and words2, 
// and m is the average string length. Space complexity is O(26) or O(1), as the frequency vectors have a constant size (26 letters).
public:
    vector<string> wordSubsets(vector<string>& words1, vector<string>& words2) {
        // Step 1: Create a frequency map for words2
        vector<int> maxFreq(26, 0); // to store the maximum required frequency of each letter

        for (const string& word : words2) {
            vector<int> wordFreq(26, 0);
            for (char c : word) {
                wordFreq[c - 'a']++; // frequency of each character in the word
            }
            // Update the maximum frequency for each letter across all words in words2
            for (int i = 0; i < 26; i++) {
                maxFreq[i] = max(maxFreq[i], wordFreq[i]);
            }
        }

        // Step 2: Check each word in words1 and see if it satisfies the max frequency map
        vector<string> result;
        for (const string& word : words1) {
            vector<int> wordFreq(26, 0);
            for (char c : word) {
                wordFreq[c - 'a']++;
            }
            bool isUniversal = true;
            for (int i = 0; i < 26; i++) {
                if (wordFreq[i] < maxFreq[i]) {
                    isUniversal = false;
                    break;
                }
            }
            if (isUniversal) {
                result.push_back(word);
            }
        }
        
        return result;
    }
};