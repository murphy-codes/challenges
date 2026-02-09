// Source: https://leetcode.com/problems/balance-a-binary-search-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-08
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 48.24 MB Beats 58.33%

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

class Solution {
    // Collect all BST nodes using in-order traversal to get sorted order.
    // Rebuild a balanced BST by choosing the middle node as the root.
    // Existing TreeNode objects are reused by rewiring left/right pointers.
    // Each node is visited once for O(n) time.
    // Extra space is O(n) for the node list and recursion stack.

    // Stores nodes in sorted (in-order) sequence
    ArrayList<TreeNode> nodes = new ArrayList<>();

    public TreeNode balanceBST(TreeNode root) {
        inorder(root);
        return buildBalanced(0, nodes.size() - 1);
    }

    // In-order traversal to collect BST nodes in sorted order
    public void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        nodes.add(root);
        inorder(root.right);
    }

    // Builds a balanced BST by selecting the middle node as root
    public TreeNode buildBalanced(int left, int right) {
        if (left > right) return null;

        int mid = (left + right) / 2;
        TreeNode node = nodes.get(mid);

        node.left = buildBalanced(left, mid - 1);
        node.right = buildBalanced(mid + 1, right);

        return node;
    }
}
