// Source: https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-24
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.68 MB Beats 84.27%

/****************************************
* 
* You are given the `root` of a binary tree where each node has a value
* _ `0` or `1`. Each root-to-leaf path represents a binary number starting
* _ with the most significant bit.
* • For example, if the path is `0 -> 1 -> 1 -> 0 -> 1`, then this could
* __ represent `01101` in binary, which is `13`.
* For all leaves in the tree, consider the numbers represented by the path from
* _ the root to that leaf. Return the sum of these numbers.
* The test cases are generated so that the answer fits in a 32-bits integer.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2019/04/04/sum-of-root-to-leaf-binary-numbers.png]
* Input: root = [1,0,1,0,1,0,1]
* Output: 22
* Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
*
* Example 2:
* Input: root = [0]
* Output: 0
*
* Constraints:
* • The number of nodes in the tree is in the range `[1, 1000]`.
* • `Node.val` is `0` or `1`.
* 
****************************************/

class Solution {
    // Perform DFS while carrying the current binary value.
    // At each node, shift left and add the node's bit (0 or 1).
    // When a leaf is reached, the accumulated value is complete.
    // Each node is visited once: time O(n).
    // Space is O(h) due to recursion stack, where h is tree height.
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int current) {
        if (node == null) return 0;

        // Shift left and add current node's bit
        current = (current << 1) | node.val;

        // If leaf node, return the formed binary number
        if (node.left == null && node.right == null) {
            return current;
        }

        // Sum values from left and right subtrees
        return dfs(node.left, current) + dfs(node.right, current);
    }
}

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