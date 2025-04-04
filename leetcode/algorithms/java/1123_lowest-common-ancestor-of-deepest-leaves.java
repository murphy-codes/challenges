// Source: https://leetcode.com/lowest-common-ancestor-of-deepest-leaves/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-04
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.28 MB Beats 66.84%

/****************************************
* 
* Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.
* Recall that:
* • The node of a binary tree is a leaf if and only if it has no children
* • The depth of the root of the tree is `0`. if the depth of a node is `d`,
* _ the depth of each of its children is `d + 1`.
* • The lowest common ancestor of a set `S` of nodes, is the node `A` with the
* _ largest depth such that every node in `S` is in the subtree with root `A`.
*
* Example 1:
* Input: root = [3,5,1,6,2,0,8,null,null,7,4]
* Output: [2,7,4]
* Explanation: We return the node with value 2, colored in yellow in the diagram.
* The nodes coloured in blue are the deepest leaf-nodes of the tree.
* Note that nodes 6, 0, and 8 are also leaf nodes, but the depth of them is 2, but the depth of nodes 7 and 4 is 3.
*
* Example 2:
* Input: root = [1]
* Output: [1]
* Explanation: The root is the deepest node in the tree, and it's the lca of itself.
*
* Example 3:
* Input: root = [0,1,3,null,2]
* Output: [2]
* Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.
*
* Constraints:
* • The number of nodes in the tree will be in the range `[1, 1000]`.
* • 0 <= Node.val <= 1000
* • The values of the nodes in the tree are unique.
*
* Note: This question is the same as 865: https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
* 
****************************************/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    // This solution uses DFS to find the depth of each subtree and return the LCA
    // of the deepest leaves. If both subtrees have the same depth, the current node
    // is the LCA. Otherwise, we return the deeper subtree's LCA. This approach runs
    // in O(n) time and O(h) space, where h is the tree height (O(n) worst case).
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return dfs(root).node;
    }

    private Pair dfs(TreeNode node) {
        if (node == null) return new Pair(null, 0);

        Pair left = dfs(node.left);
        Pair right = dfs(node.right);

        if (left.depth > right.depth) return new Pair(left.node, left.depth + 1);
        if (left.depth < right.depth) return new Pair(right.node, right.depth + 1);

        return new Pair(node, left.depth + 1);
    }

    private static class Pair {
        TreeNode node;
        int depth;
        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
