// Source: https://leetcode.com/problems/delete-duplicate-folders-in-system/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-19
// At the time of submission:
//   Runtime 92 ms Beats 68.18%
//   Memory 69.10 MB Beats 93.18%

/****************************************
* 
* Due to a bug, there are many duplicate folders in a file system. You are given
* _ a 2D array `paths`, where `paths[i]` is an array representing an absolute path
* _ to the `i^th` folder in the file system.
* • For example, `["one", "two", "three"]` represents the path `"/one/two/three"`.
* Two folders (not necessarily on the same level) are identical if they contain
* _ the same non-empty set of identical subfolders and underlying subfolder
* _ structure. The folders do not need to be at the root level to be identical.
* _ If two or more folders are identical, then mark the folders as well as all their subfolders.
* • For example, folders `"/a"` and `"/b"` in the file structure below are identical.
* __ They (as well as their subfolders) should all be marked:
* _ • `/a`
* _ • `/a/x`
* _ • `/a/x/y`
* _ • `/a/z`
* _ • `/b`
* _ • `/b/x`
* _ • `/b/x/y`
* _ • `/b/z`
* • However, if the file structure also included the path `"/b/w"`, then the folders
* _ `"/a"` and `"/b"` would not be identical. Note that `"/a/x"` and `"/b/x"` would
* _ still be considered identical even with the added folder.
* Once all the identical folders and their subfolders have been marked, the file
* _ system will delete all of them. The file system only runs the deletion once,
* _ so any folders that become identical after the initial deletion are not deleted.
* Return the 2D array `ans` containing the paths of the remaining folders after
* _ deleting all the marked folders. The paths may be returned in any order.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/19/lc-dupfolder1.jpg]
* Input: paths = [["a"],["c"],["d"],["a","b"],["c","b"],["d","a"]]
* Output: [["d"],["d","a"]]
* Explanation: The file structure is as shown.
* Folders "/a" and "/c" (and their subfolders) are marked for deletion because they both contain an empty
* folder named "b".
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/07/19/lc-dupfolder2.jpg]
* Input: paths = [["a"],["c"],["a","b"],["c","b"],["a","b","x"],["a","b","x","y"],["w"],["w","y"]]
* Output: [["c"],["c","b"],["a"],["a","b"]]
* Explanation: The file structure is as shown.
* Folders "/a/b/x" and "/w" (and their subfolders) are marked for deletion because they both contain an empty folder named "y".
* Note that folders "/a" and "/c" are identical after the deletion, but they are not deleted because they were not marked beforehand.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/07/19/lc-dupfolder3.jpg]
* Input: paths = [["a","b"],["c","d"],["c"],["a"]]
* Output: [["c"],["c","d"],["a"],["a","b"]]
* Explanation: All folders are unique in the file system.
* Note that the returned array can be in a different order as the order does not matter.
*
* Constraints:
* • 1 <= paths.length <= 2 * 10^4
* • 1 <= paths[i].length <= 500
* • 1 <= paths[i][j].length <= 10
* • 1 <= sum(paths[i][j].length) <= 2 * 10^5
* • `path[i][j]` consists of lowercase English letters.
* • No two paths lead to the same folder.
* • For any folder not at the root level, its parent folder will also be in the input.
* 
****************************************/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Map.Entry;

class Solution {
// This solution builds a trie from folder paths and serializes each subtree.
// It maps all serialized subtrees and marks all duplicate ones for deletion.
// A final DFS collects only the paths not under any duplicated subtree.
// Time: O(N * L log L) for sorting keys and traversals (N = paths, L = avg depth)
// Space: O(N * L) for trie, serialization map, and result storage.

    static class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        String name = "";
        boolean toDelete = false;
    }

    Map<String, List<TrieNode>> serialToNodes = new HashMap<>();

    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        TrieNode root = new TrieNode();

        // Step 1: Build Trie
        for (List<String> path : paths) {
            TrieNode curr = root;
            for (String folder : path) {
                curr = curr.children.computeIfAbsent(folder, k -> new TrieNode());
                curr.name = folder;
            }
        }

        // Step 2: Serialize and collect all subtrees
        serialize(root);

        // Step 3: Mark all nodes with duplicated serials
        for (List<TrieNode> nodeList : serialToNodes.values()) {
            if (nodeList.size() > 1) {
                for (TrieNode node : nodeList) {
                    node.toDelete = true;
                }
            }
        }

        // Step 4: Collect valid paths
        List<List<String>> result = new ArrayList<>();
        collect(root, new ArrayList<>(), result);
        return result;
    }

    // Serialize the subtree and map all nodes by their serialization
    private String serialize(TrieNode node) {
        if (node.children.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<>(node.children.keySet());
        Collections.sort(keys);  // Ensure consistent ordering

        for (String key : keys) {
            TrieNode child = node.children.get(key);
            sb.append("(")
              .append(key)
              .append(serialize(child))
              .append(")");
        }

        String serial = sb.toString();
        serialToNodes.computeIfAbsent(serial, k -> new ArrayList<>()).add(node);
        return serial;
    }

    // Collect all non-deleted paths
    private void collect(TrieNode node, List<String> path, List<List<String>> result) {
        for (Map.Entry<String, TrieNode> entry : node.children.entrySet()) {
            TrieNode child = entry.getValue();
            if (child.toDelete) continue;
            path.add(child.name);
            result.add(new ArrayList<>(path));
            collect(child, path, result);
            path.remove(path.size() - 1);
        }
    }
}
