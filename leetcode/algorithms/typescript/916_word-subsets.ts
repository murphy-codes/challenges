// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-10

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

// We'll solve this problem using frequency arrays of size 26 to represent lowercase English letters.
// First, we define a helper function to compute a frequency array for a given word.
// Next, we create a "max frequency map" from words2, representing the maximum count of each letter required across all words in words2.
// For each string in words1, we compute its frequency array and compare it against the max frequency map.
// If a string satisfies all the requirements, it's added to the result list.
// This approach runs in O((n1 + n2) * m) time, where n1 and n2 are the lengths of words1 and words2, 
// and m is the average string length. Space complexity is O(26) or O(1), as the frequency arrays have a constant size.
function wordSubsets(words1: string[], words2: string[]): string[] {
    // Helper function to calculate frequency map for a word
    const getFrequency = (word: string): number[] => {
        const freq = new Array(26).fill(0);
        for (const char of word) {
            freq[char.charCodeAt(0) - 'a'.charCodeAt(0)]++;
        }
        return freq;
    };

    // Step 1: Create the max frequency map for words2
    const maxFreq = new Array(26).fill(0);
    for (const word of words2) {
        const freq = getFrequency(word);
        for (let i = 0; i < 26; i++) {
            maxFreq[i] = Math.max(maxFreq[i], freq[i]);
        }
    }

    // Step 2: Filter words1 to find universal words
    const result: string[] = [];
    for (const word of words1) {
        const freq = getFrequency(word);
        let isUniversal = true;
        for (let i = 0; i < 26; i++) {
            if (freq[i] < maxFreq[i]) {
                isUniversal = false;
                break;
            }
        }
        if (isUniversal) {
            result.push(word);
        }
    }

    return result;
}
