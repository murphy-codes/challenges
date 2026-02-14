// Source: https://leetcode.com/problems/champagne-tower/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-13
// At the time of submission:
//   Runtime 3 ms Beats 76.33%
//   Memory 47.74 MB Beats 87.99%

/****************************************
* 
* We stack glasses in a pyramid, where the first row has `1` glass, the second
* _ row has `2` glasses, and so on until the 100^th row.  Each glass holds one
* _ cup of champagne.
* Then, some champagne is poured into the first glass at the top.  When the
* _ topmost glass is full, any excess liquid poured will fall equally to the
* _ glass immediately to the left and right of it.  When those glasses become
* _ full, any excess champagne will fall equally to the left and right of those
* _ glasses, and so on.  (A glass at the bottom row has its excess champagne
* _ fall on the floor.)
* For example, after one cup of champagne is poured, the top most glass is full.
* _ After two cups of champagne are poured, the two glasses on the second row are
* _ half full.  After three cups of champagne are poured, those two cups become
* _ full - there are 3 full glasses total now.  After four cups of champagne are
* _ poured, the third row has the middle glass half full, and the two outside
* _ glasses are a quarter full, as pictured below.
* [Image: https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/09/tower.png]
* Now after pouring some non-negative integer cups of champagne, return how
* _ full the `j^th` glass in the `i^th` row is (both `i` and `j` are 0-indexed.)
*
* Example 1:
* Input: poured = 1, query_row = 1, query_glass = 1
* Output: 0.00000
* Explanation: We poured 1 cup of champange to the top glass of the tower (which is indexed as (0, 0)). There will be no excess liquid so all the glasses under the top glass will remain empty.
*
* Example 2:
* Input: poured = 2, query_row = 1, query_glass = 1
* Output: 0.50000
* Explanation: We poured 2 cups of champange to the top glass of the tower (which is indexed as (0, 0)). There is one cup of excess liquid. The glass indexed as (1, 0) and the glass indexed as (1, 1) will share the excess liquid equally, and each will get half cup of champange.
*
* Example 3:
* Input: poured = 100000009, query_row = 33, query_glass = 17
* Output: 1.00000
*
* Constraints:
* • `0 <= poured <= 10^9`
* • `0 <= query_glass <= query_row < 100`
* 
****************************************/

class Solution {
    // Simulate champagne pouring row by row using a 2D array tower[row][col].
    // Each glass holds at most 1 cup; excess spills equally to two glasses below.
    // Time complexity: O(query_row^2) since we only simulate up to query_row.
    // Space complexity: O(100*100) = O(1) constant space for the fixed-size tower.
    // The answer is the min of 1.0 and the value in tower[query_row][query_glass].
    public double champagneTower(int poured, int query_row, int query_glass) {
        // Maximum row size is 100
        double[][] tower = new double[101][101];
        tower[0][0] = poured;

        for (int row = 0; row <= query_row; row++) {
            for (int col = 0; col <= row; col++) {
                // Calculate excess champagne
                double excess = (tower[row][col] - 1.0) / 2.0;
                if (excess > 0) {
                    tower[row + 1][col] += excess;
                    tower[row + 1][col + 1] += excess;
                }
            }
        }

        // Each glass can hold at most 1 cup
        return Math.min(1.0, tower[query_row][query_glass]);
    }
}
