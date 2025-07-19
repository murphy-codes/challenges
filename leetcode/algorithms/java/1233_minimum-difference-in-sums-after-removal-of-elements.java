// Source: https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-18
// At the time of submission:
//   Runtime 20 ms Beats 99.61%
//   Memory 55.32 MB Beats 74.41%

/****************************************
* 
* Given a list of folders `folder`, return the folders after removing all
* _ sub-folders in those folders. You may return the answer in any order.
* If a `folder[i]` is located within another `folder[j]`, it is called a
* _ sub-folder of it. A sub-folder of `folder[j]` must start with `folder[j]`,
* _ followed by a `"/"`. For example, `"/a/b"` is a sub-folder of `"/a"`, but
* _ `"/b"` is not a sub-folder of `"/a/b/c"`.
* The format of a path is one or more concatenated strings of the form: `'/'`
* _ followed by one or more lowercase English letters.
* • For example, `"/leetcode"` and `"/leetcode/problems"` are valid paths while
* __ an empty string and `"/"` are not.
*
* Example 1:
* Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
* Output: ["/a","/c/d","/c/f"]
* Explanation: Folders "/a/b" is a subfolder of "/a" and "/c/d/e" is inside of folder "/c/d" in our filesystem.
*
* Example 2:
* Input: folder = ["/a","/a/b/c","/a/b/d"]
* Output: ["/a"]
* Explanation: Folders "/a/b/c" and "/a/b/d" will be removed because they are subfolders of "/a".
*
* Example 3:
* Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
* Output: ["/a/b/c","/a/b/ca","/a/b/d"]
*
* Constraints:
* • 1 <= folder.length <= 4 * 104
* • 2 <= folder[i].length <= 100
* • folder[i] contains only lowercase letters and '/'.
* • folder[i] always starts with the character '/'.
* • Each folder name is unique.
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    // This solution uses a Trie to efficiently store folder paths and check if a
    // folder is a subfolder of any previously stored path. Sorting the folders
    // by length ensures root folders are inserted first. If a folder is not a
    // subfolder of any existing folder, it's added to the result list and the
    // Trie. Time: O(n * L), Space: O(n * L), where L = average folder length.

    // TrieNode represents each character in a folder path.
    class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            this.children = new TrieNode[27]; // 0-25 for a-z, 26 for '/'
            this.isEndOfWord = false;
        }
    }

    TrieNode root;

    // Maps characters to child indices: 'a'-'z' → 0-25, '/' → 26
    private int getIndex(char ch) {
        return (ch == '/') ? 26 : ch - 'a';
    }

    // Inserts a folder path into the Trie
    private void insert(String folderPath) {
        TrieNode currentNode = root;
        for (int i = 0; i < folderPath.length(); i++) {
            int index = getIndex(folderPath.charAt(i));
            if (currentNode.children[index] == null) {
                currentNode.children[index] = new TrieNode();
            }
            currentNode = currentNode.children[index];
        }
        // Add an extra '/' node to mark end of full path
        currentNode.children[26] = new TrieNode();
        currentNode = currentNode.children[26];
        currentNode.isEndOfWord = true;
    }

    // Searches whether the given path is a subfolder of an inserted root folder
    private boolean search(String folderPath) {
        TrieNode currentNode = root;
        for (int i = 0; i < folderPath.length(); i++) {
            if (currentNode.isEndOfWord) return true;
            int index = getIndex(folderPath.charAt(i));
            if (currentNode.children[index] == null) return false;
            currentNode = currentNode.children[index];
        }
        return currentNode.isEndOfWord;
    }

    // Main logic to remove subfolders
    public List<String> removeSubfolders(String[] folder) {
        root = new TrieNode();
        Arrays.sort(folder, (a, b) -> a.length() - b.length());
        List<String> result = new ArrayList<>();

        for (String f : folder) {
            if (!search(f)) {
                insert(f);
                result.add(f);
            }
        }
        return result;
    }
}
