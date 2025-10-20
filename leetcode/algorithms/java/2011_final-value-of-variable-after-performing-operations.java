// Source: https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-19
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.07 MB Beats 24.22%

/****************************************
* 
* There is a programming language with only four operations and one variable `X`:
* • `++X` and `X++` increments the value of the variable `X` by `1`.
* • `--X` and `X--` decrements the value of the variable `X` by `1`.
* Initially, the value of `X` is `0`.
* Given an array of strings `operations` containing a list of operations,
* _ return the final value of X after performing all the operations.
*
* Example 1:
* Input: operations = ["--X","X++","X++"]
* Output: 1
* Explanation: The operations are performed as follows:
* • Initially, X = 0.
* • --X: X is decremented by 1, X =  0 - 1 = -1.
* • X++: X is incremented by 1, X = -1 + 1 =  0.
* • X++: X is incremented by 1, X =  0 + 1 =  1.
*
* Example 2:
* Input: operations = ["++X","++X","X++"]
* Output: 3
* Explanation: The operations are performed as follows:
* • Initially, X = 0.
* • ++X: X is incremented by 1, X = 0 + 1 = 1.
* • ++X: X is incremented by 1, X = 1 + 1 = 2.
* • X++: X is incremented by 1, X = 2 + 1 = 3.
*
* Example 3:
* Input: operations = ["X++","++X","--X","X--"]
* Output: 0
* Explanation: The operations are performed as follows:
* • Initially, X = 0.
* • X++: X is incremented by 1, X = 0 + 1 = 1.
* • ++X: X is incremented by 1, X = 1 + 1 = 2.
* • --X: X is decremented by 1, X = 2 - 1 = 1.
* • X--: X is decremented by 1, X = 1 - 1 = 0.
*
* Constraints:
* • `1 <= operations.length <= 100`
* • `operations[i]` will be either `"++X"`, `"X++"`, `"--X"`, or `"X--"`.
* 
****************************************/

class Solution {
    // Iterate through all operations using an enhanced for-loop. 
    // For each op, +1 if the middle char is '+', else -1 if it's '-'. 
    // Compact, efficient, & functionally identical to my previous version.
    // Time Complexity: O(n), scanning all operations once.
    // Space Complexity: O(1), requiring no additional data structures.
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for (String op : operations) x += (op.charAt(1) == '+') ? 1 : -1;
        return x;
    }
}