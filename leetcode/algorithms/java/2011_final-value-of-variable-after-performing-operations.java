// Source: https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-19
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.14 MB Beats 14.72%

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
    // Loop through each operation and check its middle char ('+' or '-').
    // If it's '+', increment x; otherwise, decrement x. Both ++X and X++
    // yield the same effect (+1), as do --X and X-- (-1). Simple scan.
    // Time Complexity: O(n), where n is operations.length.
    // Space Complexity: O(1), using constant extra space.
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for (int i = 0; i < operations.length; i++) {
            if (operations[i].charAt(1) == '+') x++;
            else x--;
        }
        return x;
    }
}