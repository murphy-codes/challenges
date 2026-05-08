// Source: https://leetcode.com/problems/minimum-jumps-to-reach-end-via-prime-teleportation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-06
// At the time of submission:
//   Runtime 73 ms Beats 99.54%
//   Memory 123.89 MB Beats 100.00%

/****************************************
* 
* You are given an integer array `nums` of length `n`.
* You start at index 0, and your goal is to reach index `n - 1`.
* From any index `i`, you may perform one of the following operations:
* • Adjacent Step: Jump to index `i + 1` or `i - 1`, if the index is within bounds.
* • Prime Teleportation: If `nums[I]` is a prime number `p`, you may instantly
* __ jump to any index `j != i` such that `nums[j] % p == 0`.
* Return the minimum number of jumps required to reach index `n - 1`.
*
* Example 1:
* Input: nums = [1,2,4,6]
* Output: 2
* Explanation:
* One optimal sequence of jumps is:
* • Start at index i = 0. Take an adjacent step to index 1.
* • At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
* Thus, the answer is 2.
*
* Example 2:
* Input: nums = [2,3,4,7,9]
* Output: 2
* Explanation:
* One optimal sequence of jumps is:
* • Start at index i = 0. Take an adjacent step to index i = 1.
* • At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
* Thus, the answer is 2.
*
* Example 3:
* Input: nums = [4,6,5,8]
* Output: 3
* Explanation:
* • Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
*
* Constraints:
* • `1 <= n == nums.length <= 10^5`
* • `1 <= nums[i] <= 10^6`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Use BFS with adjacent moves and prime-based teleportation.
    // Build value buckets via array-linked lists for O(1) traversal.
    // Process each prime and divisible-value bucket only once.
    // Time: O(n + M log log M), Space: O(n + M)
    // Though TC could be described: O(n + M log log M + M/2 + M/3 + ...)
    public int minJumps(int[] nums) {
        int n = nums.length;

        if (n <= 1) return 0;

        // Find largest value for sieve/bucket sizing
        int maxValue = 0;
        for (int num : nums) {
            maxValue = Math.max(maxValue, num);
        }

        // Sieve: isComposite[x] == false means x is prime
        boolean[] isComposite = new boolean[maxValue + 1];
        if (maxValue >= 0) isComposite[0] = true;
        if (maxValue >= 1) isComposite[1] = true;

        for (int prime = 2; prime * prime <= maxValue; prime++) {
            if (!isComposite[prime]) {
                for (int multiple = prime * prime;
                     multiple <= maxValue;
                     multiple += prime) {

                    isComposite[multiple] = true;
                }
            }
        }

        // Build value -> linked list of indices
        int[] headIndex = new int[maxValue + 1];
        Arrays.fill(headIndex, -1);

        int[] nextIndex = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            nextIndex[i] = headIndex[nums[i]];
            headIndex[nums[i]] = i;
        }

        // BFS setup
        int[] distance = new int[n];
        Arrays.fill(distance, -1);

        int[] queue = new int[n];
        int front = 0;
        int rear = 0;

        queue[rear++] = 0;
        distance[0] = 0;

        // Prevent repeated teleport processing per prime
        boolean[] usedPrime = new boolean[maxValue + 1];

        while (front < rear) {
            int index = queue[front++];

            if (index == n - 1) {
                return distance[index];
            }

            // Move left
            if (index - 1 >= 0 && distance[index - 1] == -1) {
                distance[index - 1] = distance[index] + 1;

                if (index - 1 == n - 1) {
                    return distance[index - 1];
                }

                queue[rear++] = index - 1;
            }

            // Move right
            if (index + 1 < n && distance[index + 1] == -1) {
                distance[index + 1] = distance[index] + 1;

                if (index + 1 == n - 1) {
                    return distance[index + 1];
                }

                queue[rear++] = index + 1;
            }

            // Prime teleportation
            int prime = nums[index];

            if (!isComposite[prime] && !usedPrime[prime]) {
                usedPrime[prime] = true;

                // Visit all indices whose values are divisible by prime
                for (int multiple = prime;
                     multiple <= maxValue;
                     multiple += prime) {

                    int next = headIndex[multiple];

                    while (next != -1) {
                        if (distance[next] == -1) {
                            distance[next] = distance[index] + 1;

                            if (next == n - 1) {
                                return distance[next];
                            }

                            queue[rear++] = next;
                        }

                        next = nextIndex[next];
                    }

                    // Clear processed bucket
                    headIndex[multiple] = -1;
                }
            }
        }

        return -1;
    }
}