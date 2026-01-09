// Source: https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-09
// At the time of submission:
//   Runtime 7 ms Beats 99.26%
//   Memory 46.25 MB Beats 85.93%

/****************************************
* 
* Given the root of a binary tree, the depth of each node is the shortest
* _ distance to the root.
* Return the smallest subtree such that it contains all the deepest nodes
* _ in the original tree.
* A node is called the deepest if it has the largest depth possible among any
* _ node in the entire tree.
* The subtree of a node is a tree consisting of that node, plus the set of all
* _ descendants of that node.
*
* Example 1:
* [Image: https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/01/sketch1.png]
* Input: root = [3,5,1,6,2,0,8,null,null,7,4]
* Output: [2,7,4]
* Explanation: We return the node with value 2, colored in yellow in the diagram.
* The nodes coloured in blue are the deepest nodes of the tree.
* Notice that nodes 5, 3 and 2 contain the deepest nodes in the tree but node 2 is the smallest subtree among them, so we return it.
*
* Example 2:
* Input: root = [1]
* Output: [1]
* Explanation: The root is the deepest node in the tree.
*
* Example 3:
* Input: root = [0,1,3,null,2]
* Output: [2]
* Explanation: The deepest node in the tree is 2, the valid subtrees are the subtrees of nodes 2, 1 and 0 but the subtree of node 2 is the smallest.
*
* Constraints:
* • The number of nodes in the tree will be in the range `[1, 500]`.
* • `0 <= Node.val <= 500`
* • The values of the nodes in the tree are unique.
*
* Note: This question is the same as 1123: https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
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
    // Performs a post-order DFS that returns both the deepest depth of a subtree
    // and the smallest subtree containing all deepest nodes. If left and right
    // subtrees have equal depth, the current node is their LCA. Otherwise, the
    // deeper subtree determines the result. Time O(n), space O(h).
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
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