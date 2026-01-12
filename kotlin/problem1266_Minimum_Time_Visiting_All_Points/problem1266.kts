import kotlin.math.abs

class Solution {
    fun minTimeToVisitAllPoints(points: Array<IntArray>): Int {
        var seconds = 0;
        for (i in 0..<points.size - 1) {
            val start = points[i];
            val end = points[i + 1];

            var distx = abs(start[0] - end[0]);
            var disty = abs(start[1] - end[1]);

            var diag = minOf(distx, disty);
            seconds += diag;
            seconds += distx - diag;
            seconds += disty - diag;
        }
        return seconds;
    }
}

val sol = Solution();
var result: Int;

result = sol.minTimeToVisitAllPoints(arrayOf(intArrayOf(1, 1), intArrayOf(3, 4), intArrayOf(-1, 0)));
assert(result == 7);

result = sol.minTimeToVisitAllPoints(arrayOf(intArrayOf(3, 2), intArrayOf(-2, 2)));
assert(result == 5);