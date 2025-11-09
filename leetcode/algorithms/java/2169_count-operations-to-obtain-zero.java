// Source: https://leetcode.com/problems/count-operations-to-obtain-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-08
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.25 MB Beats 14.07%

/****************************************
* 
* You are given two non-negative integers `num1` and `num2`.
* In one operation, if `num1 >= num2`, you must subtract `num2` from `num1`,
* _ otherwise subtract `num1` from `num2`.
* • For example, if `num1 = 5` and `num2 = 4`, subtract `num2` from `num1`,
* __ thus obtaining `num1 = 1` and `num2 = 4`. However, if `num1 = 4` and
* __ `num2 = 5`, after one operation, `num1 = 4` and `num2 = 1`.
* Return the number of operations required to make either `num1 = 0` or
* _ `num2 = 0`.
*
* Example 1:
* Input: num1 = 2, num2 = 3
* Output: 3
* Explanation:
* - Operation 1: num1 = 2, num2 = 3. Since num1 < num2, we subtract num1 from num2 and get num1 = 2, num2 = 3 - 2 = 1.
* - Operation 2: num1 = 2, num2 = 1. Since num1 > num2, we subtract num2 from num1.
* - Operation 3: num1 = 1, num2 = 1. Since num1 == num2, we subtract num2 from num1.
* Now num1 = 0 and num2 = 1. Since num1 == 0, we do not need to perform any further operations.
* So the total number of operations required is 3.
*
* Example 2:
* Input: num1 = 10, num2 = 10
* Output: 1
* Explanation:
* - Operation 1: num1 = 10, num2 = 10. Since num1 == num2, we subtract num2 from num1 and get num1 = 10 - 10 = 0.
* Now num1 = 0 and num2 = 10. Since num1 == 0, we are done.
* So the total number of operations required is 1.
*
* Constraints:
* • `0 <= num1, num2 <= 10^5`
* 
****************************************/

class Solution {
    // Uses the Euclidean-like approach to count operations until one number hits 0.
    // Instead of subtracting repeatedly, performs num1/num2 (or vice versa) steps at once.
    // Each step mimics the subtraction process described in the problem.
    // Runs in O(log(min(num1, num2))) time due to modulus reduction at each step,
    // and uses O(1) constant extra space.
    public int countOperations(int num1, int num2) {
        int ops = 0;
        while (num1 != 0 && num2 != 0) {
            if (num1 >= num2) {
                ops += num1/num2;
                num1 = num1 % num2;
            } else { // num2 > num1
                ops += num2/num1;
                num2 = num2 % num1;
            }
        }
        return ops;
    }
}