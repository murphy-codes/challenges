// Source: https://leetcode.com/problems/destroying-asteroids/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-30
// At the time of submission:
//   Runtime 5 ms Beats 99.34%
//   Memory 105.69 MB Beats 98.22%

/****************************************
* 
* You are given an integer `mass`, which represents the original mass of a
* _ planet. You are further given an integer array `asteroids`, where
* _ `asteroids[i]` is the mass of the `i^th` asteroid.
* You can arrange for the planet to collide with the asteroids in any arbitrary
* _ order. If the mass of the planet is greater than or equal to the mass of
* _ the asteroid, the asteroid is destroyed and the planet gains the mass of
* _ the asteroid. Otherwise, the planet is destroyed.
* Return `true` if all asteroids can be destroyed. Otherwise, return `false`.
*
* Example 1:
* Input: mass = 10, asteroids = [3,9,19,5,21]
* Output: true
* Explanation: One way to order the asteroids is [9,19,5,3,21]:
* - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
* - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
* - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
* - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
* - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
* All asteroids are destroyed.
*
* Example 2:
* Input: mass = 5, asteroids = [4,9,23,4]
* Output: false
* Explanation:
* The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
* After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
* This is less than 23, so a collision would not destroy the last asteroid.
*
* Constraints:
* • `1 <= mass <= 10^5`
* • `1 <= asteroids.length <= 10^5`
* • `1 <= asteroids[i] <= 10^5`
* 
****************************************/

class Solution {
    // Count asteroid frequencies instead of sorting, leveraging mass <= 100000.
    // Absorb all currently destroyable asteroids, then process masses ascending.
    // If currentMass < next asteroid mass, no remaining asteroid is destroyable.
    // Time: O(n + M), Space: O(M), where M = max asteroid mass. 
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        int minMass = 100000;
        int maxMass = 0;

        for (int asteroid : asteroids) {
            maxMass = Math.max(maxMass, asteroid);
            minMass = Math.min(minMass, asteroid);
        }

        int[] frequency = new int[maxMass + 1];
        long currentMass = mass;

        // Immediately absorb any asteroid we can already destroy.
        for (int asteroid : asteroids) {
            if (asteroid > currentMass) {
                frequency[asteroid]++;
            } else {
                currentMass += asteroid;
            }
        }

        if (currentMass >= maxMass) {
            return true;
        }

        for (int asteroidMass = minMass;
             asteroidMass <= maxMass;
             asteroidMass++) {

            if (currentMass < asteroidMass) {
                return false;
            }

            if (frequency[asteroidMass] != 0) {
                currentMass += (long) asteroidMass
                             * frequency[asteroidMass];
            }
        }

        return true;
    }
}