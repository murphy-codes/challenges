// Source: https://leetcode.com/problems/1-bit-and-2-bit-characters/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-17
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.70 MB Beats 24.86%

/****************************************
* 
* We have two special characters:
* • The first character can be represented by one bit `0`.
* • The second character can be represented by two bits (`10` or `11`).
* Given a binary array `bits` that ends with `0`, return `true` if the last
* _ character must be a one-bit character.
*
* Example 1:
* Input: bits = [1,0,0]
* Output: true
* Explanation: The only way to decode it is two-bit character and one-bit character.
* So the last character is one-bit character.
*
* Example 2:
* Input: bits = [1,1,1,0]
* Output: false
* Explanation: The only way to decode it is two-bit character and two-bit character.
* So the last character is not one-bit character.
*
* Constraints:
* • `1 <= bits.length <= 1000`
* • `bits[i]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // We decode bit stream from left to right, jumping by 1 for a 
    // '0' (one-bit char) or 2 for a '1' (two-bit char). We then 
    // check if we end with our pointer indicating a one or two bit char.
    // This runs in O(n) time with O(1) extra space.
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        // if (bits[n-1]==1) return false;
        int i = 0;
        while (i < n-1){
            if (bits[i]==1) i++;
            i++;
        }
        if (i == n) return false;
        return true;
    }
}