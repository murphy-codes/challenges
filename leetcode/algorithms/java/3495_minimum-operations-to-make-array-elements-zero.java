// Source: https://leetcode.com/problems/minimum-operations-to-make-array-elements-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-06
// At the time of submission:
//   Runtime 11 ms Beats 100.00%
//   Memory 101.28 MB Beats 41.67%

/****************************************
* 
* You are given a 2D array `queries`, where `queries[i]` is of the form `[l, r]`.
* _ Each `queries[i]` defines an array of integers `nums` consisting of elements
* _ ranging from `l to r`, both inclusive.
* In one operation, you can:
* • Select two integers `a` and `b` from the array.
* • Replace them with `floor(a / 4)` and `floor(b / 4)`.
* Your task is to determine the minimum number of operations required to reduce
* _ all elements of the array to zero for each query. Return the sum of the
* _ results for all queries.
*
* Example 1:
* Input: queries = [[1,2],[2,4]]
* Output: 3
* Explanation:
* For queries[0]:
* The initial array is nums = [1, 2].
* In the first operation, select nums[0] and nums[1]. The array becomes [0, 0].
* The minimum number of operations required is 1.
* For queries[1]:
* The initial array is nums = [2, 3, 4].
* In the first operation, select nums[0] and nums[2]. The array becomes [0, 3, 1].
* In the second operation, select nums[1] and nums[2]. The array becomes [0, 0, 0].
* The minimum number of operations required is 2.
* The output is 1 + 2 = 3.
*
* Example 2:
* Input: queries = [[2,6]]
* Output: 4
* Explanation:
* For queries[0]:
* The initial array is nums = [2, 3, 4, 5, 6].
* In the first operation, select nums[0] and nums[3]. The array becomes [0, 3, 4, 1, 6].
* In the second operation, select nums[2] and nums[4]. The array becomes [0, 3, 1, 1, 1].
* In the third operation, select nums[1] and nums[2]. The array becomes [0, 0, 0, 1, 1].
* In the fourth operation, select nums[3] and nums[4]. The array becomes [0, 0, 0, 0, 0].
* The minimum number of operations required is 4.
* The output is 4.
*
* Constraints:
* • 1 <= queries.length <= 10^5
* • queries[i].length == 2
* • queries[i] == [l, r]
* • 1 <= l < r <= 10^9
* 
****************************************/

class Solution {
    // This solution groups numbers in [l, r] by powers of 4, since all values
    // within the same "power bucket" need the same number of divisions to reach 0.
    // By counting how many numbers fall into each bucket and summing their costs,
    // we avoid iterating through every number. Each operation reduces 2 numbers,
    // so the final result is (sum + 1) / 2. Time complexity is O(log r) per query
    // due to the power-of-4 progression, and space complexity is O(1).
    public long minOperations(int[][] queries) {
        long totalOps = 0;
        for (int[] query : queries) {
            totalOps += minOperations(query);
        }
        return totalOps;
    }

    public static long minOperations(int[] query) {
        long initialSteps = 0;   // number of divisions needed to reach query[0]
        long power = 1;          // tracks powers of 4

        // Find how many steps are needed before reaching the lower bound
        while (power < query[0]) {
            initialSteps++;
            power *= 4;
        }

        long steps = initialSteps; 
        long total = 0;
        long prev = query[0];    // track range start

        // Iterate through buckets of size power-of-4 until covering query[1]
        while (power <= (long) query[1] * 4) {
            long rangeEnd = Math.min(power, query[1] + 1);
            total += steps * (rangeEnd - prev);  // count contribution
            prev = power;
            steps++;
            power *= 4;
        }

        // Divide by 2 since each operation reduces 2 numbers simultaneously
        return (total + 1) / 2;
    }
}
