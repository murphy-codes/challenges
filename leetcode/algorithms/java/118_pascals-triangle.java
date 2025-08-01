// Source: https://leetcode.com/problems/pascals-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.18 MB Beats 39.47%

/****************************************
* 
* Given an integer `numRows`, return the first numRows of Pascal's triangle.
* In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
* [Image: https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif]
*
* Example 1:
* Input: numRows = 5
* Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
*
* Example 2:
* Input: numRows = 1
* Output: [[1]]
*
* Constraints:
* • 1 <= numRows <= 30
* 
****************************************/

class Solution {
    // Builds Pascal's Triangle up to numRows using binomial coefficients.
    // Each row is generated iteratively with O(n) space per row.
    // The formula nCk = nC(k-1) * (n-k)/(k+1) avoids recomputation.
    // Time complexity: O(n²), Space complexity: O(n²) for result storage.
    // Efficient for small to moderately large triangles without memoization.
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> row = new ArrayList<>();
            int n = 1;
            for(int j=0;j<=i;j++){
                row.add(n);
                n = n*(i-j)/(j+1);
            }
            result.add(row);
        }
        return result;
    }
}
