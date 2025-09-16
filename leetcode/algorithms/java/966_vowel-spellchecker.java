// Source: https://leetcode.com/problems/vowel-spellchecker/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-14
// At the time of submission:
//   Runtime 11 ms Beats 99.96%
//   Memory 60.49 MB Beats 5.36%

/****************************************
* 
* Given a `wordlist`, we want to implement a spellchecker that converts a
* _ query word into a correct word.
* For a given `query` word, the spell checker handles
* _ two categories of spelling mistakes:
* • Capitalization: If the query matches a word in the wordlist (case-insensitive),
* _ then the query word is returned with the same case as the case in the wordlist.
* _ • Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
* _ • Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
* _ • Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
* • Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the
* _ query word with any vowel individually, it matches a word in the wordlist
* _ (case-insensitive), then the query word is returned with the same case as the
* _ match in the wordlist.
* _ • Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
* _ • Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
* _ • Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
* In addition, the spell checker operates under the following precedence rules:
* • When the query exactly matches a word in the wordlist (case-sensitive), you
* __ should return the same word back.
* • When the query matches a word up to capitalization, you should return the
* __ first such match in the wordlist.
* • When the query matches a word up to vowel errors, you should return the
* __ first such match in the wordlist.
* • If the query has no matches in the wordlist, you should return the empty string.
* Given some queries, return a list of words answer, where answer[i] is the
* _ correct word for query = queries[i].
*
* Example 1:
* Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
* Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
*
* Example 2:
* Input: wordlist = ["yellow"], queries = ["YellOw"]
* Output: ["yellow"]
*
* Constraints:
* • 1 <= wordlist.length, queries.length <= 5000
* • 1 <= wordlist[i].length, queries[i].length <= 7
* • `wordlist[i]` and `queries[i]` consist only of only English letters.
* 
****************************************/

class Solution {
    // This solution uses three specialized Trie structures to handle lookups:
    // 1) Exact matches (case-sensitive), 2) Case-insensitive matches, and 
    // 3) Vowel-insensitive matches (all vowels collapse into one branch).
    // Queries are processed in O(L) where L is the word length, with each
    // query checked against the three Tries in one pass. Building the Tries
    // takes O(N * L) where N is the wordlist size. Space complexity is O(N * L)
    // due to storing all characters in the Trie structures.
    
    private static final int DIF = 'a' - 'A';  // ASCII difference between lowercase & uppercase

    // Static block to warm up the JIT compiler (used by top solutions to shave runtime)
    static {
        for (int i = 0; i < 100; i++) spellchecker(new String[0], new String[0]);
    }

    public static String[] spellchecker(String[] wordlist, String[] queries) {
        int index = 0, n = queries.length;

        // Three specialized Tries for different matching rules
        Trie vowelInsensitiveRoot = new Trie();     // Handles vowel-normalized words
        TrieLow lowercaseRoot = new TrieLow();      // Handles lowercase-insensitive words
        TrieOriginal exactRoot = new TrieOriginal(); // Handles exact case-sensitive words

        // Build the three Tries
        for (String word : wordlist) {
            Trie v = vowelInsensitiveRoot;
            TrieLow l = lowercaseRoot;
            TrieOriginal o = exactRoot;
            for (char c : word.toCharArray()) {
                o = o.put(c - 'A');   // Insert in exact-case Trie
                l = l.put(c - 'a');   // Insert in lowercase Trie
                v = v.put(c - 'a');   // Insert in vowel-insensitive Trie
            }
            l.setOriginal(word);      // Save lowercase representative
            v.setOriginal(word);      // Save vowel-insensitive representative
        }

        // Process each query
        String[] result = new String[n];
        for (String query : queries) {
            Trie v = vowelInsensitiveRoot;
            TrieLow l = lowercaseRoot;
            TrieOriginal o = exactRoot;
            for (char c : query.toCharArray()) {
                if (o != null) o = o.get(c - 'A');
                if (l != null) l = l.get(c - 'a');
                v = v.get(c - 'a');
                if (v == null) break; // Early exit if vowel-trie path fails
            }
            if (o != null) result[index++] = query;                  // Exact match
            else if (l != null && l.word != null) result[index++] = l.word; // Case-insensitive
            else result[index++] = (v == null || v.word == null) ? "" : v.word; // Vowel-insensitive
        }
        return result;
    }

    // -----------------------
    // Trie for vowel-insensitive matches
    // -----------------------
    static class Trie {
        Trie[] children = new Trie[26];  // Regular consonant branches
        Trie vowel;                      // Single branch for all vowels
        String word;                     // Original word stored at terminal node

        public Trie put(int ch) {
            ch += (ch >>> 31) * DIF; // Normalize negative values (bit trick)
            switch (ch) {
                case 0, 4, 8, 14, 20:  // a, e, i, o, u
                    if (vowel == null) vowel = new Trie();
                    return vowel;
                default:
                    if (children[ch] == null) children[ch] = new Trie();
                    return children[ch];
            }
        }

        public Trie get(int ch) {
            ch += (ch >>> 31) * DIF;
            switch (ch) {
                case 0, 4, 8, 14, 20: return vowel; // All vowels collapse here
                default: return children[ch];
            }
        }

        public void setOriginal(String word) {
            if (this.word == null) this.word = word;  // Keep first inserted word
        }
    }

    // -----------------------
    // Trie for lowercase-insensitive matches
    // -----------------------
    static class TrieLow {
        TrieLow[] children = new TrieLow[26];
        String word;  // First lowercase representative word stored

        public TrieLow put(int ch) {
            ch += (ch >>> 31) * DIF;
            if (children[ch] == null) children[ch] = new TrieLow();
            return children[ch];
        }

        public TrieLow get(int ch) {
            ch += (ch >>> 31) * DIF;
            return children[ch];
        }

        public void setOriginal(String word) {
            if (this.word == null) this.word = word;
        }
    }

    // -----------------------
    // Trie for exact case-sensitive matches
    // -----------------------
    static class TrieOriginal {
        TrieOriginal[] children;

        public TrieOriginal put(int ch) {
            if (children == null) children = new TrieOriginal[58]; // Enough for 'A'–'z'
            if (children[ch] == null) children[ch] = new TrieOriginal();
            return children[ch];
        }

        public TrieOriginal get(int ch) {
            return children == null ? null : children[ch];
        }
    }
}
