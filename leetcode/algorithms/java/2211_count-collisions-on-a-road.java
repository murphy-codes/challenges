// Source: https://leetcode.com/problems/count-collisions-on-a-road/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-03
// At the time of submission:
//   Runtime 8 ms Beats 100.00%
//   Memory 47.22 MB Beats 44.35%

/****************************************
* 
* There are `n` cars on an infinitely long road. The cars are numbered from
* _ `0` to `n - 1` from left to right and each car is present at a unique point.
* You are given a 0-indexed string `directions` of length `n`. `directions[i]`
* _ can be either `'L'`, `'R'`, or `'S'` denoting whether the `i^th` car is
* _ moving towards the left, towards the right, or staying at its current point
* _ respectively. Each moving car has the same speed.
* The number of collisions can be calculated as follows:
* • When two cars moving in opposite directions collide with each other, the
* _ number of collisions increases by `2`.
* • When a moving car collides with a stationary car, the number of collisions
* _ increases by `1`.
* After a collision, the cars involved can no longer move and will stay at the
* _ point where they collided. Other than that, cars cannot change their state
* _ or direction of motion.
* Return the total number of collisions that will happen on the road.
*
* Example 1:
* Input: directions = "RLRSLL"
* Output: 5
* Explanation:
* The collisions that will happen on the road are:
* - Cars 0 and 1 will collide with each other. Since they are moving in opposite directions, the number of collisions becomes 0 + 2 = 2.
* - Cars 2 and 3 will collide with each other. Since car 3 is stationary, the number of collisions becomes 2 + 1 = 3.
* - Cars 3 and 4 will collide with each other. Since car 3 is stationary, the number of collisions becomes 3 + 1 = 4.
* - Cars 4 and 5 will collide with each other. After car 4 collides with car 3, it will stay at the point of collision and get hit by car 5. The number of collisions becomes 4 + 1 = 5.
* Thus, the total number of collisions that will happen on the road is 5.
*
* Example 2:
* Input: directions = "LLRR"
* Output: 0
* Explanation:
* No cars will collide with each other. Thus, the total number of collisions that will happen on the road is 0.
*
* Constraints:
* • `1 <= directions.length <= 10^5`
* • `directions[i]` is either `'L'`, `'R'`, or `'S'`.
* 
****************************************/

class Solution {
    // Cars that never collide are leading 'L's on the far left and trailing 'R's
    // on the far right, so we trim those away. Every remaining 'L' or 'R' between
    // the trimmed boundaries must eventually collide, while 'S' cars cause no
    // additional collisions themselves. We count all non-'S' cars in this region.
    // Runs in O(n) time and O(1) extra space by performing only linear scans.
    public int countCollisions(String directions) {
        int collisions = 0;
        char[] arr = directions.toCharArray();
        int n = arr.length;
        // Skip leading 'L's (cars that move left off the road and never collide)
        int left = 0;
        while (left < n && arr[left] == 'L') {
            left++;
        }
        // Skip trailing 'R's (cars that move right off the road and never collide)
        int right = n - 1;
        while (right >= 0 && arr[right] == 'R') {
            right--;
        }
        // All remaining 'L' or 'R' cars in the middle zone will definitely collide.
        // Only 'S' cars do NOT directly add collisions.
        for (int i = left; i <= right; i++) {
            if (arr[i] != 'S') {
                collisions++;
            }
        }
        return collisions;
    }
}