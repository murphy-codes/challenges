// Source: https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-06
// At the time of submission:
//   Runtime 5 ms Beats 99.22%
//   Memory 59.24 MB Beats 85.60%

/****************************************
* 
* Given the `root` of a binary tree, the level of its root is `1`, the level of
* _ its children is `2`, and so on.
* Return the smallest level `x` such that the sum of all the values of nodes at
* _ level `x` is maximal.
*
* Example 1:
* Input: root = [1,7,0,7,-8,null,null]
* Output: 2
* Explanation:
* Level 1 sum = 1.
* Level 2 sum = 7 + 0 = 7.
* Level 3 sum = 7 + -8 = -1.
* So we return the level with the maximum sum which is level 2.
*
* Example 2:
* Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
* Output: 2
*
* Constraints:
* • The number of nodes in the tree is in the range [1, 10^4].`
* • `-10^5 <= Node.val <= 10^5`
* 
****************************************/

class Solution {
    // First compute the total sum of all nodes in the tree.
    // Then compute each subtree sum using DFS.
    // Treat each subtree as if its parent edge is cut.
    // Maximize subtreeSum * (totalSum - subtreeSum).
    // Time: O(n), Space: O(h) for recursion stack.

    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1_000_000_007;

    public int maxProduct(TreeNode root) {
        totalSum = computeTotalSum(root);
        computeSubtreeSum(root);
        return (int)(maxProduct % MOD);
    }

    // First pass: compute total sum of all nodes
    private long computeTotalSum(TreeNode node) {
        if (node == null) return 0;
        return node.val
            + computeTotalSum(node.left)
            + computeTotalSum(node.right);
    }

    // Second pass: compute subtree sums and evaluate split product
    private long computeSubtreeSum(TreeNode node) {
        if (node == null) return 0;

        long leftSum = computeSubtreeSum(node.left);
        long rightSum = computeSubtreeSum(node.right);
        long subtreeSum = node.val + leftSum + rightSum;

        long product = subtreeSum * (totalSum - subtreeSum);
        maxProduct = Math.max(maxProduct, product);

        return subtreeSum;
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