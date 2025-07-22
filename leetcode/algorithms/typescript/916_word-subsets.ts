// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-10
// At the time of submission:
//   Runtime 25 ms Beats 100.00%
//   Memory 68.34 MB Beats 100.00%

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

const aCharCode = 'a'.charCodeAt(0);

// Build a max frequency map for letters required by all words in words2.
// Then, for each word in words1, count its letters and check if it meets
// or exceeds all required frequencies. Uses char codes for fast indexing.
// Time: O(N * L1 + M * L2), where N = words1.length, M = words2.length.
// Space: O(1) for 26-letter arrays. Efficient due to buffer reuse and tight loops.
function wordSubsets(words1: string[], words2: string[]): string[] {
    const tempFreq = new Array(26).fill(0); // Temporary letter frequency
    const maxFreq = new Array(26).fill(0);  // Final required max frequencies

    // Step 1: Build max frequency requirement from all words in words2
    for (const word of words2) {
        tempFreq.fill(0); // Reset buffer for each word
        for (let i = 0; i < word.length; i++) {
            const index = word.charCodeAt(i) - aCharCode;
            tempFreq[index]++;
            if (tempFreq[index] > maxFreq[index]) {
                maxFreq[index] = tempFreq[index];
            }
        }
    }

    // Step 2: Helper to check if a word meets all max frequency requirements
    function isUniversal(word: string): boolean {
        const wordFreq = new Array(26).fill(0);
        for (let i = 0; i < word.length; i++) {
            wordFreq[word.charCodeAt(i) - aCharCode]++;
        }

        for (let i = 0; i < 26; i++) {
            if (wordFreq[i] < maxFreq[i]) return false;
        }

        return true;
    }

    // Step 3: Filter and return universal words from words1
    return words1.filter(isUniversal);
}
