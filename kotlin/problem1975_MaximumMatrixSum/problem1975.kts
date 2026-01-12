import kotlin.math.abs
import kotlin.math.min

class Solution {
    fun maxMatrixSum(matrix: Array<IntArray>): Long {
        var sum = 0L;
        var min = Int.MAX_VALUE;
        var numberNegatives = 0;
        var hasZero = false;
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                var abs_val = abs(matrix[i][j]);
                sum += abs_val;
                min = min(min, abs_val);

                if (matrix[i][j] < 0) {
                    numberNegatives += 1;
                }
                if (matrix[i][j] == 0)
                    hasZero = true;
            }
        }
        if (!hasZero && numberNegatives % 2 == 1)
            sum -= 2 * min;

        /*println("hasZeros: {$hasZero}");
        println("numberNegatives: {$numberNegatives}");
        println("min: {$min}");*/
        return sum;
    }
}


val sol = Solution();
var result: Long;

result = sol.maxMatrixSum(arrayOf(intArrayOf(1, -1), intArrayOf(-1, 1)));
assert(result == 4L);

result = sol.maxMatrixSum(arrayOf(intArrayOf(1, 2, 3), intArrayOf(-1, -2, - 3), intArrayOf(1, 2, 3)));
assert(result == 16L);

result = sol.maxMatrixSum(arrayOf(intArrayOf(0, 2, 3), intArrayOf(-1, -2, - 3), intArrayOf(1, 2, 3)));
assert(result == 17L);

result = sol.maxMatrixSum(arrayOf(intArrayOf(2,9,3), intArrayOf(5,4,-4), intArrayOf(1,7,1)));
assert(result == 34L);
