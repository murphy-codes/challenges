// Source: https://leetcode.com/problems/balance-a-binary-search-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-08
// At the time of submission:
//   Runtime 3 ms Beats 18.28%
//   Memory 48.39 MB Beats 39.08%

/****************************************
* 
* Given the `root` of a binary search tree, return a balanced binary search tree
* _ with the same node values. If there is more than one answer, return any of them.
* A binary search tree is balanced if the depth of the two subtrees of every node
* _ never differs by more than `1`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/08/10/balance1-tree.jpg]
* Input: root = [1,null,2,null,3,null,4,null,null]
* Output: [2,1,3,null,null,null,4]
* Explanation: This is not the only correct answer, [3,1,4,null,2] is also correct.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/08/10/balanced2-tree.jpg]
* Input: root = [2,1,3]
* Output: [2,1,3]
*
* Constraints:
* • The number of nodes in the tree is in the range `[1, 10^4]`.
* • `1 <= Node.val <= 10^5`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Perform in-order traversal to collect BST values in sorted order.
    // Recursively build a balanced BST by choosing the middle value as root.
    // This guarantees subtree heights differ by at most one.
    // Each node is processed once for O(n) time.
    // Extra space is O(n) for the list and recursion stack.
    
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> vals = new ArrayList<>();
        inorder(root, vals);
        return build(vals, 0, vals.size() - 1);
    }

    private void inorder(TreeNode node, List<Integer> vals) {
        if (node == null) return;
        inorder(node.left, vals);
        vals.add(node.val);
        inorder(node.right, vals);
    }

    private TreeNode build(List<Integer> vals, int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(vals.get(mid));
        node.left = build(vals, left, mid - 1);
        node.right = build(vals, mid + 1, right);
        return node;
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