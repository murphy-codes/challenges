// Source: https://leetcode.com/problems/pascals-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.10 MB Beats 39.47%

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
    // Recursively builds Pascal's Triangle row-by-row.
    // Each row is built by summing adjacent elements of the previous row.
    // Time complexity: O(n²), since total elements across all rows is ~n(n+1)/2.
    // Space complexity: O(n²) for storing the final triangle.
    // Avoids factorial math by directly applying Pascal's identity.
    void buildTriangle(List<List<Integer>> triangle, int currentRow, int totalRows) {
        if (currentRow >= totalRows) return;

        List<Integer> row = new ArrayList<>();

        if (currentRow == 0) {
            row.add(1); // First row is always [1]
        } else {
            row.add(1); // First element of each row is 1
            for (int j = 1; j < currentRow; j++) {
                int left = triangle.get(currentRow - 1).get(j - 1);
                int right = triangle.get(currentRow - 1).get(j);
                row.add(left + right); // Middle elements are sum of two above
            }
            row.add(1); // Last element of each row is also 1
        }

        triangle.add(row); // Add constructed row
        buildTriangle(triangle, currentRow + 1, totalRows); // Recurse to next row
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        buildTriangle(triangle, 0, numRows);
        return triangle;
    }
}
