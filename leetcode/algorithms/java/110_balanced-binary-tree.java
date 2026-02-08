// Source: https://leetcode.com/problems/balanced-binary-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-08
// At the time of submission:
//   Runtime 18 ms Beats 100.00%
//   Memory 47.91 MB Beats 30.43%

/****************************************
* 
* Given a binary tree, determine if it is height-balanced.
* A height-balanced binary tree is a binary tree in which the depth of two
* _ subtrees of every node never differs by more than one.
*
* Example 1:
* Input: root = [3,9,20,null,null,15,7]
* Output: true
*
* Example 2:
* Input: root = [1,2,2,3,3,null,null,4,4]
* Output: false
*
* Example 3:
* Input: root = []
* Output: true
*
* Constraints:
* • The number of nodes in the tree is in the range `[0, 5000]`.
* • `-10^4 <= Node.val <= 10^4`
* 
****************************************/

class Solution {
    // Use post-order traversal to compute subtree heights.
    // If any subtree is unbalanced, return -1 immediately.
    // This avoids recomputing heights multiple times.
    // Each node is visited once for O(n) time.
    // Space complexity is O(h) due to recursion depth.
    public boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    private int height(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = height(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = height(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
