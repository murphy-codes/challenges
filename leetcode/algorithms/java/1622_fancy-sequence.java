// Source: https://leetcode.com/problems/fancy-sequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-15
// At the time of submission:
//   Runtime 33 ms Beats 100.00%
//   Memory 124.96 MB Beats 34.48%

/****************************************
* 
* Write an API that generates fancy sequences using the `append`, `addAll`, and
* _ `multAll` operations.
* Implement the `Fancy` class:
* • `Fancy()` Initializes the object with an empty sequence.
* • `void append(val)` Appends an integer `val` to the end of the sequence.
* • `void addAll(inc)` Increments all existing values in the sequence by an integer `inc`.
* • `void multAll(m)` Multiplies all existing values in the sequence by an integer `m`.
* • `int getIndex(idx)` Gets the current value at index `idx` (0-indexed) of
* __ the sequence modulo `10^9 + 7`. If the index is greater or equal than the
* __ length of the sequence, return -1.
*
* Example 1:
* Input
* ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"]
* [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
* Output
* [null, null, null, null, null, 10, null, null, null, 26, 34, 20]
* Explanation
* Fancy fancy = new Fancy();
* fancy.append(2);   // fancy sequence: [2]
* fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
* fancy.append(7);   // fancy sequence: [5, 7]
* fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
* fancy.getIndex(0); // return 10
* fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
* fancy.append(10);  // fancy sequence: [13, 17, 10]
* fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
* fancy.getIndex(0); // return 26
* fancy.getIndex(1); // return 34
* fancy.getIndex(2); // return 20
*
* Constraints:
* • `1 <= val, inc, m <= 100`
* • `0 <= idx <= 10^5`
* • `At most `10^5` calls total will be made to `append`, `addAll`, `multAll`, and `getIndex`.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Fancy {
    // Maintain a global affine transform: value = stored * mul + add (mod M).
    // When appending we reverse the current transform using the inverse
    // multiplier so stored values remain normalized. multAll() updates both
    // the forward multiplier and its inverse using precomputed inverses.
    // getIndex() reconstructs the current value from the stored base.
    // Time: O(1) per operation. Space: O(n).

    private static final int MOD = 1_000_000_007;

    // Precompute modular inverses for values 0..100
    private static final int[] INV =
            IntStream.range(0, 101)
                     .map(Fancy::modInverse)
                     .toArray();

    // Extended Euclidean algorithm to compute modular inverse
    private static int modInverse(int a) {

        int m = MOD;
        int y = 0;
        int x = 1;

        while (a > 1) {

            int q = a / m;

            int temp = m;
            m = a % m;
            a = temp;

            temp = y;
            y = x - q * y;
            x = temp;
        }

        return x < 0 ? x + MOD : x;
    }

    // Global forward transform parameters
    private long mul = 1;
    private long add = 0;

    // Reverse multiplier (inverse of cumulative multiplication)
    private long invMul = 1;

    // Stored normalized values
    private final List<Integer> values = new ArrayList<>();

    public void append(int val) {

        // Reverse current transform before storing
        values.add(
            (int)(((MOD - add + val) * invMul) % MOD)
        );
    }

    public void addAll(int inc) {
        add = (add + inc) % MOD;
    }

    public void multAll(int m) {

        mul = (mul * m) % MOD;

        // Maintain inverse multiplier
        invMul = (invMul * INV[m]) % MOD;

        add = (add * m) % MOD;
    }

    public int getIndex(int idx) {

        if (idx >= values.size())
            return -1;

        return (int)(((values.get(idx) * mul) + add) % MOD);
    }
}