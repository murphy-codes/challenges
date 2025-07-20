// Source: https://leetcode.com/problems/delete-duplicate-folders-in-system/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-19
// At the time of submission:
//   Runtime 67 ms Beats 97.73%
//   Memory 76.56 MB Beats 25.00%

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

class Solution {
    // Build a trie of folder paths and serialize each subtree from bottom-up.
    // Use a hash map to detect duplicate subtree structures via serialization.
    // Mark all duplicate subtrees for deletion when a match is found.
    // Finally, collect and return only the paths that are not marked.
    // Time: O(n * k) where n = num paths, k = max path length; Space: O(n * k)

    class Node {
        Map<String, Node> children = new TreeMap<>();
        String content = "";
        boolean markedForRemoval = false;

        void markSubtreeForRemoval() {
            if (markedForRemoval) return;
            markedForRemoval = true;
            for (Node child : children.values()) {
                child.markSubtreeForRemoval();
            }
        }
    }

    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        // Sort paths by length to ensure parents are added before children
        paths.sort(Comparator.comparingInt(List::size));
        List<Node> insertedNodes = new ArrayList<>(paths.size());
        Node root = new Node();

        // Build the trie tree from folder paths
        for (List<String> path : paths) {
            Node curr = root;
            for (int i = 0; i < path.size() - 1; i++) {
                curr = curr.children.get(path.get(i));
            }
            String lastFolder = path.get(path.size() - 1);
            Node newNode = new Node();
            curr.children.put(lastFolder, newNode);
            insertedNodes.add(newNode);
        }

        // Map to track unique serialized subtree structures
        Map<String, Node> contentToNode = new HashMap<>();
        StringBuilder serialization = new StringBuilder();

        // Traverse in reverse order for post-order serialization
        for (int i = insertedNodes.size() - 1; i >= 0; i--) {
            Node node = insertedNodes.get(i);
            if (node.children.isEmpty()) continue;

            for (Map.Entry<String, Node> entry : node.children.entrySet()) {
                serialization
                    .append(entry.getKey())
                    .append('{')
                    .append(entry.getValue().content)
                    .append('}');
            }

            node.content = serialization.toString();
            serialization.setLength(0); // Clear builder for next use

            Node existing = contentToNode.putIfAbsent(node.content, node);
            if (existing != null) {
                node.markSubtreeForRemoval();
                existing.markSubtreeForRemoval();
            }
        }

        // Collect paths that haven't been marked for removal
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            if (!insertedNodes.get(i).markedForRemoval) {
                result.add(paths.get(i));
            }
        }

        return result;
    }
}
