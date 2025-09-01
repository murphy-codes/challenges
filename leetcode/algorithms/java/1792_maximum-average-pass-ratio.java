// Source: https://leetcode.com/problems/maximum-average-pass-ratio/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-31
// At the time of submission:
//   Runtime 315 ms Beats 93.78%
//   Memory 95.32 MB Beats 69.43%

/****************************************
* 
* There is a school that has classes of students and each class will be having a
* _ final exam. You are given a 2D integer array `classes`, where
* _ `classes[i] = [pass_i, total_i]`. You know beforehand that in the `i^th`
* _ class, there are `total_i` total students, but only `pass_i` number of
* _ students will pass the exam.
* You are also given an integer `extraStudents`. There are another
* _ `extraStudents` brilliant students that are guaranteed to pass the exam of
* _ any class they are assigned to. You want to assign each of the
* _ `extraStudents` students to a class in a way that maximizes the average
* _ pass ratio across all the classes.
* The pass ratio of a class is equal to the number of students of the class
* _ that will pass the exam divided by the total number of students of the
* _ class. The average pass ratio is the sum of pass ratios of all the
* _ classes divided by the number of the classes.
* Return the maximum possible average pass ratio after assigning the
* _ `extraStudents` students. Answers within `10^-5` of the actual
* _ answer will be accepted.
*
* Example 1:
* Input: classes = [[1,2],[3,5],[2,2]], `extraStudents` = 2
* Output: 0.78333
* Explanation: You can assign the two extra students to the first class. The average pass ratio will be equal to (3/4 + 3/5 + 2/2) / 3 = 0.78333.
*
* Example 2:
* Input: classes = [[2,4],[3,9],[4,5],[2,10]], `extraStudents` = 4
* Output: 0.53485
*
* Constraints:
* • 1 <= classes.length <= 10^5
* • classes[i].length == 2
* • 1 <= pass_i <= total_i <= 10^5
* • 1 <= extraStudents <= 10^5
* 
****************************************/

class Solution {
    // Greedy solution using a max-heap (priority queue).
    // At each step, assign an extra student to the class that yields
    // the largest gain in average pass ratio. Gain is computed as the
    // difference between new and old ratios. This ensures maximum benefit.
    // Time complexity: O(n log n + extraStudents log n). Space: O(n).
    
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        // Max-heap based on gain (largest gain first)
        PriorityQueue<double[]> maxHeap = new PriorityQueue<>(new Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[0], a[0]); // sort by gain descending
            }
        });

        // Initialize heap with (gain, pass, total) for each class
        for (int i = 0; i < classes.length; i++) {
            double pass = classes[i][0];
            double total = classes[i][1];
            double gain = (pass + 1.0) / (total + 1.0) - pass / total;
            maxHeap.offer(new double[]{gain, pass, total});
        }

        // Assign extra students greedily to maximize average ratio
        while (extraStudents > 0) {
            double[] top = maxHeap.poll();
            double pass = top[1] + 1;
            double total = top[2] + 1;
            double gain = (pass + 1.0) / (total + 1.0) - pass / total;
            maxHeap.offer(new double[]{gain, pass, total});
            extraStudents--;
        }

        // Compute final average pass ratio
        double sum = 0.0;
        for (double[] c : maxHeap) {
            sum += c[1] / c[2];
        }

        return sum / classes.length;
    }
}
