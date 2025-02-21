// Source: https://leetcode.com/problems/find-elements-in-a-contaminated-binary-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-21

/****************************************
* 
* Given a binary tree with the following rules:
* 
* 1. `root.val == 0`
* 2. For any `treeNode`:
*   2.1 If `treeNode.val` has a value `x` and `treeNode.left != null`, then `treeNode.left.val == 2 * x + 1`
*   2.2 If `treeNode.val` has a value `x` and `treeNode.right != null`, then `treeNode.right.val == 2 * x + 2`
* 
* Now the binary tree is contaminated, which means all `treeNode.val` have been changed to `-1`.
* 
* Implement the `FindElements` class:
* • `FindElements(TreeNode* root)` Initializes the object with a contaminated binary tree and recovers it.
* • `bool find(int target)` Returns `true` if the `target` value exists in the recovered binary tree.
*  
* Example 1:
*   [Image: https://assets.leetcode.com/uploads/2019/11/06/untitled-diagram-4-1.jpg]
* Input
* ["FindElements","find","find"]
* [[[-1,null,-1]],[1],[2]]
* Output
* [null,false,true]
* Explanation
* FindElements findElements = new FindElements([-1,null,-1]); 
* findElements.find(1); // return False 
* findElements.find(2); // return True 
* 
* Example 2:
*   [Image: https://assets.leetcode.com/uploads/2019/11/06/untitled-diagram-4.jpg]
* Input
* ["FindElements","find","find","find"]
* [[[-1,-1,-1,-1,-1]],[1],[3],[5]]
* Output
* [null,true,true,false]
* Explanation
* FindElements findElements = new FindElements([-1,-1,-1,-1,-1]);
* findElements.find(1); // return True
* findElements.find(3); // return True
* findElements.find(5); // return False
* 
* Example 3:
*   [Image: https://assets.leetcode.com/uploads/2019/11/07/untitled-diagram-4-1-1.jpg]
* Input
* ["FindElements","find","find","find","find"]
* [[[-1,null,-1,-1,null,-1]],[2],[3],[4],[5]]
* Output
* [null,true,false,false,true]
* Explanation
* FindElements findElements = new FindElements([-1,null,-1,-1,null,-1]);
* findElements.find(2); // return True
* findElements.find(3); // return False
* findElements.find(4); // return False
* findElements.find(5); // return True
* 
* Constraints:
* • TreeNode.val == -1
* • The height of the binary tree is less than or equal to `20`
* • The total number of nodes is between `[1, 10^4]`
* • Total calls of `find()` is between `[1, 10^4]`
* • 0 <= target <= 10^6
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class FindElements {
    // This solution uses DFS to recover the tree by assigning correct values
    // based on the given formula and storing them in a HashSet for O(1) lookup.
    // Recovery runs in O(n) time and space, where n is the number of nodes.
    // The find() function runs in O(1) time on average due to HashSet lookup.
    // Overall, this approach ensures efficient preprocessing and quick queries.
    private Set<Integer> values; // Store valid values for O(1) lookup

    public FindElements(TreeNode root) {
        values = new HashSet<>();
        recoverTree(root, 0); // Start recovering from the root with value 0
    }
    
    private void recoverTree(TreeNode node, int val) {
        if (node == null) return;
        node.val = val;
        values.add(val);
        recoverTree(node.left, 2 * val + 1);
        recoverTree(node.right, 2 * val + 2);
    }
    
    public boolean find(int target) {
        return values.contains(target); // O(1) lookup
    }
}
