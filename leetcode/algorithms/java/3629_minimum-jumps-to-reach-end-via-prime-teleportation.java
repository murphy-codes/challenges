// Source: https://leetcode.com/problems/minimum-jumps-to-reach-end-via-prime-teleportation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-06
// At the time of submission:
//   Runtime 283 ms Beats 70.78%
//   Memory 193.43 MB Beats 80.37%

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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Solution {
    // Use BFS with adjacent moves plus prime-based teleport edges.
    // Build prime -> divisible indices buckets using SPF sieve.
    // Teleports are allowed only when nums[i] itself is prime.
    // Time: O(n log M + M log log M), Space: O(n + M)
    public int minJumps(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // Smallest prime factor sieve
        int[] spf = new int[maxVal + 1];
        for (int i = 2; i <= maxVal; i++) {
            if (spf[i] == 0) {
                for (int j = i; j <= maxVal; j += i) {
                    if (spf[j] == 0) {
                        spf[j] = i;
                    }
                }
            }
        }

        // prime -> indices divisible by prime
        Map<Integer, List<Integer>> primeToIndices = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int value = nums[i];
            int temp = value;

            while (temp > 1) {
                int prime = spf[temp];

                primeToIndices
                    .computeIfAbsent(prime, k -> new ArrayList<>())
                    .add(i);

                while (temp % prime == 0) {
                    temp /= prime;
                }
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];

        queue.offer(0);
        visited[0] = true;

        int jumps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int s = 0; s < size; s++) {
                int index = queue.poll();

                if (index == n - 1) {
                    return jumps;
                }

                // Move left
                if (index > 0 && !visited[index - 1]) {
                    visited[index - 1] = true;
                    queue.offer(index - 1);
                }

                // Move right
                if (index + 1 < n && !visited[index + 1]) {
                    visited[index + 1] = true;
                    queue.offer(index + 1);
                }

                int value = nums[index];

                // Teleportation only allowed if nums[index] is prime
                if (value > 1 && spf[value] == value) {
                    List<Integer> nextIndices =
                        primeToIndices.get(value);

                    if (nextIndices != null) {
                        for (int next : nextIndices) {
                            if (!visited[next]) {
                                visited[next] = true;
                                queue.offer(next);
                            }
                        }

                        // Process each prime bucket once
                        primeToIndices.remove(value);
                    }
                }
            }

            jumps++;
        }

        return -1;
    }
}