// Source: https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-11
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 60.05 MB Beats 70.19%

/****************************************
* 
* In a mystic dungeon, `n` magicians are standing in a line. Each magician has an
* _ attribute that gives you energy. Some magicians can give you negative energy,
* _ which means taking energy from you.
* You have been cursed in such a way that after absorbing energy from magician
* _ `i`, you will be instantly transported to magician `(i + k)`. This process
* _ will be repeated until you reach the magician where `(i + k)` does not exist.
* In other words, you will choose a starting point and then teleport with `k`
* _ jumps until you reach the end of the magicians' sequence, absorbing all the
* _ energy during the journey.
* You are given an array `energy` and an integer `k`. Return the maximum possible
* _ energy you can gain.
* Note that when you are reach a magician, you must take energy from them,
* _ whether it is negative or positive energy.
*
* Example 1:
* Input: energy = [5,2,-10,-5,1], k = 3
* Output: 3
* Explanation: We can gain a total energy of 3 by starting from magician 1 absorbing 2 + 1 = 3.
*
* Example 2:
* Input: energy = [-2,-3,-1], k = 2
* Output: -1
* Explanation: We can gain a total energy of -1 by starting from magician 2.
*
* Constraints:
* • `1 <= energy.length <= 10^5`
* • `-1000 <= energy[i] <= 1000`
* • `1 <= k <= energy.length - 1`
* 
****************************************/

class Solution { 
    // Iterate backward in steps of size k from each of the last k positions.  
    // Accumulate energy along each backward path and track the max total.  
    // This covers all valid paths ending at or beyond index n - k.  
    // Runs in O(n) time and O(1) space using simple running sums.  
    // Finds the highest possible energy that can be collected efficiently. 
    public int maximumEnergy(int[] energy, int k) {
        int maxE = -1000;
        // Traverse each possible starting position from the last segment
        for (int i = energy.length-k; i < energy.length; i++) {
            // Walk backwards in steps of size k
            for (int j = i, currE = 0; j > -1; j -= k) {
                currE += energy[j];
                if (maxE < currE) maxE = currE;
            }
        }
        return maxE;
    }
}


