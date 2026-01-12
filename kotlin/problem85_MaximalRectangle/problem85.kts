class Solution {
    var starts = ArrayList<Int>();
    var heights = ArrayList<Int>();

    fun find_max_area(row: IntArray): Int {
        starts.clear();
        heights.clear();

        // 1 2 3 2 1
        var area = 0;
        for (j in row.indices) {

            var nextStart = j;

            while (!heights.isEmpty() && row[j] < heights.last()) {
                var height = heights.removeLast();
                var start = starts.removeLast();
                nextStart = start;
                area = maxOf(
                    area,
                    (j - start) * height
                );
            }

            if (heights.isEmpty() || row[j] != heights.last()) {
                heights.add(row[j]);
                starts.add(nextStart);
            }
        }

        while (!heights.isEmpty()) {
            var height = heights.removeLast();
            var start = starts.removeLast();
            area = maxOf(
                area,
                (row.size - start) * height
            );
        }

        return area;
    }

    fun simple(matrix: Array<CharArray>): Int {
        var count = IntArray(matrix[0].size) { 0 };

        var max = 0;
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j] == '0') {
                    count[j] = 0
                } else {
                    count[j]++;
                }
            }

            max = maxOf(
                find_max_area(count),
                max
            )

            //println("${count.toList()}");
        }

        //println("${max}");
        return max;
    }

    fun maximalRectangle(matrix: Array<CharArray>): Int {
        return simple(matrix);
    }
}

val sol = Solution();
var result: Int;

result = sol.find_max_area(intArrayOf(1, 1));
assert(result == 2);

result = sol.find_max_area(intArrayOf(1, 2));
assert(result == 2);

result = sol.find_max_area(intArrayOf(2, 2));
assert(result == 4);

result = sol.find_max_area(intArrayOf(2, 0));
assert(result == 2);

result = sol.find_max_area(intArrayOf(2, 0, 2));
assert(result == 2);

result = sol.find_max_area(intArrayOf(2, 3, 2));
assert(result == 6);

result = sol.find_max_area(intArrayOf(2, 2, 1, 1, 1));
assert(result == 5);

result = sol.find_max_area(intArrayOf(1, 1, 2, 2, 1, 1, 1));
assert(result == 7);

result = sol.find_max_area(intArrayOf(1, 1, 2, 3, 1, 1, 1));
assert(result == 7);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1'),
        charArrayOf('1', '1'),
        charArrayOf('1', '1'),
    )
);
assert(result == 4);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('1', '1'),
        charArrayOf('0', '1'),
        charArrayOf('1', '1'),
    )
);
assert(result == 3);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1'),
        charArrayOf('0', '1'),
        charArrayOf('1', '1'),
    )
);
assert(result == 3);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('1', '1'),
        charArrayOf('1', '1'),
        charArrayOf('0', '1'),
    )
);
assert(result == 4);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1', '0'),
        charArrayOf('1', '1', '1'),
        charArrayOf('0', '1', '1'),
    )
);
assert(result == 4);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1', '0'),
        charArrayOf('0', '1', '1'),
        charArrayOf('1', '1', '1'),
    )
);
assert(result == 4);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('1', '0', '1', '0', '0'),
        charArrayOf('1', '0', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '0', '0', '1', '0')
    )
);
assert(result == 6);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('0', '1', '1', '1', '0'),
    )
);
assert(result == 8 * 3);

result = sol.maximalRectangle(
    arrayOf(
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('1', '1', '1', '1', '1'),
        charArrayOf('0', '1', '1', '1', '0'),
        charArrayOf('0', '1', '1', '1', '0'),
    )
);
assert(result == 35);