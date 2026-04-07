// Source: https://leetcode.com/problems/walking-robot-simulation-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-06
// At the time of submission:
//   Runtime 55 ms Beats 100.00%
//   Memory 56.56 MB Beats 89.51%

/****************************************
* 
* A `width x height` grid is on an XY-plane with the bottom-left cell at
* _ `(0, 0)` and the top-right cell at `(width - 1, height - 1)`. The grid is
* _ aligned with the four cardinal directions (`"North"`, `"East"`, `"South"`,
* _ and `"West"`). A robot is initially at cell `(0, 0)` facing direction `"East"`.
* The robot can be instructed to move for a specific number of steps. For
* _ each step, it does the following.
* 1. Attempts to move forward one cell in the direction it is facing.
* 2. If the cell the robot is moving to is out of bounds, the robot instead
* __ turns 90 degrees counterclockwise and retries the step.
* After the robot finishes moving the number of steps required, it stops and
* _ awaits the next instruction.
* Implement the `Robot` class:
* • `Robot(int width, int height)` Initializes the `width x height` grid
* __ with the robot at `(0, 0)` facing "East".
* • `void step(int num)` Instructs the robot to move forward `num` steps.
* • `int[] getPos()` Returns the current cell the robot is at, as an
* __ array of length 2, `[x, y]`.
* • `String getDir()` Returns the current direction of the robot,
* __ `"North"`, `"East"`, `"South"`, or `"West"`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/10/09/example-1.png]
* Input
* ["Robot", "step", "step", "getPos", "getDir", "step", "step", "step", "getPos", "getDir"]
* [[6, 3], [2], [2], [], [], [2], [1], [4], [], []]
* Output
* [null, null, null, [4, 0], "East", null, null, null, [1, 2], "West"]
*
* Explanation
* Robot robot = new Robot(6, 3); // Initialize the grid and the robot at (0, 0) facing East.
* robot.step(2);  // It moves two steps East to (2, 0), and faces East.
* robot.step(2);  // It moves two steps East to (4, 0), and faces East.
* robot.getPos(); // return [4, 0]
* robot.getDir(); // return "East"
* robot.step(2);  // It moves one step East to (5, 0), and faces East.
* // Moving the next step East would be out of bounds, so it turns and faces North.
* // Then, it moves one step North to (5, 1), and faces North.
* robot.step(1);  // It moves one step North to (5, 2), and faces North (not West).
* robot.step(4);  // Moving the next step North would be out of bounds, so it turns and faces West.
* // Then, it moves four steps West to (1, 2), and faces West.
* robot.getPos(); // return [1, 2]
* robot.getDir(); // return "West"
*
* Constraints:
* • `2 <= width, height <= 100`
* • `1 <= num <= 10^5`
* • At most `10^4` calls in total will be made to `step`, `getPos`, and `getDir`.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Robot {
    // The robot moves along the grid perimeter in a fixed cycle. We precompute
    // all positions and corresponding directions along this path. Each step
    // updates an index modulo the cycle length, allowing O(1) movement. This
    // avoids complex boundary logic and ensures correct handling of edges
    // and corners by directly referencing precomputed states.

    private final List<int[]> positions;
    private final List<Integer> directions;
    private final String[] dirMap = {"East", "North", "West", "South"};

    private int idx;
    private boolean moved;

    public Robot(int width, int height) {
        positions = new ArrayList<>();
        directions = new ArrayList<>();
        idx = 0;
        moved = false;

        // Bottom edge (East)
        for (int x = 0; x < width; x++) {
            positions.add(new int[]{x, 0});
            directions.add(0);
        }

        // Right edge (North)
        for (int y = 1; y < height; y++) {
            positions.add(new int[]{width - 1, y});
            directions.add(1);
        }

        // Top edge (West)
        for (int x = width - 2; x >= 0; x--) {
            positions.add(new int[]{x, height - 1});
            directions.add(2);
        }

        // Left edge (South)
        for (int y = height - 2; y > 0; y--) {
            positions.add(new int[]{0, y});
            directions.add(3);
        }

        // Fix origin direction after movement
        directions.set(0, 3);
    }

    public void step(int num) {
        moved = true;
        idx = (idx + num) % positions.size();
    }

    public int[] getPos() {
        return positions.get(idx);
    }

    public String getDir() {
        if (!moved) return "East";
        return dirMap[directions.get(idx)];
    }
}