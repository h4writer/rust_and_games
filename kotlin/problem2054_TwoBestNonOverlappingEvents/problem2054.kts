class Solution {
    fun save_max(max_tree: IntArray, event_tree: Array<IntArray>, start: Int, end: Int): Int {
        var root = (start + end) / 2;
        var max = event_tree[root][2];
        var maxR = 0;
        var maxL = 0;

        if (start < root) {
            // Left
            maxL = save_max(max_tree, event_tree, start, root);
        }
        if (root + 1 < end) {
            // Right
            maxR = save_max(max_tree, event_tree, root + 1, end);
        }

        max_tree[root] = maxOf(max, maxR);
        return maxOf(maxR, maxL, max);
    }

    fun find_max_after(max_tree: IntArray, event_tree: Array<IntArray>, start: Int, end: Int, time: Int): Int {
        var root = (start + end) / 2;
        var current = event_tree[root];

        println("test: ${current[0]} <= ${time}");
        if (current[0] <= time) {
            // We only want nodes after "time", so visit right.
            if (root + 1 < end) {
                val max = find_max_after(max_tree, event_tree, root + 1, end, time);
                println("find ${start} $end $time = $max");
                return max;
            }
            println("find ${start} $end $time = 0");
            return 0;
        }

        // max(right, find_max_after(left, time));
        var maxR = max_tree[root];

        var maxL = 0;
        if (start < root) {
            maxL = find_max_after(max_tree, event_tree, start, root, time);
        }
        println("find ${start} $end $time = max($maxR, $maxL)");
        return maxOf(maxR, maxL);
    }

    fun maxTwoEvents(events: Array<IntArray>): Int {
        // O(n log n)

        // events[0]
        // 1-3: 2
        // 4-5: 2
        // 2-4: 3

        // Sort for balanced tree.
        events.sortBy { it[0] };

        // Save Max value while iterating tree.
        var max_tree = IntArray(events.size) { 0 };
        save_max(max_tree, events, 0, events.size);

        for (e in events) {
            print("[s:${e[0]} e:${e[1]} v:${e[2]}], ");
        }
        println();
        println(max_tree.toList());

        var max = 0;
        for (i in 0..<events.size) {
            if (events[i][2] > max) {
                max = events[i][2];
            }

            val found = find_max_after(max_tree, events, 0, events.size, events[i][1]) + events[i][2];
            if (found > max) {
                println("f: " + found + " (" + events[i][2] + ", " + (found - events[i][2]) + ")");
                max = found;
            }
            println("---");
        }
        return max;
    }

    fun test_every_two(events: Array<IntArray>): Int {
        // O(n2)
        var max = 0;
        for (i in 0..<events.size) {
            if (events[i][2] > max) {
                max = events[i][2];
            }

            for (j in i + 1..<events.size) {
                var a = events[i];
                var b = events[j];
                if (events[j][0] < events[i][0]) {
                    a = events[j];
                    b = events[i];
                }

                if (events[i][1] < events[j][0]) {
                    //println("${events[i].toList()} ${events[j].toList()}");
                    val value = events[i][2] + events[j][2];
                    if (value > max) {
                        println("actual: ${events[i][2]} + ${events[j][2]}");
                        max = value;
                    }
                }
            }
        }

        println(max);
        return max;
    }
}

val sol = Solution();
var result: Int;
var schedule: Array<IntArray>;

result = sol.maxTwoEvents(arrayOf(intArrayOf(1, 3, 2), intArrayOf(4, 5, 2), intArrayOf(2, 4, 3)));
assert(result == 4);

result = sol.maxTwoEvents(arrayOf(intArrayOf(1, 3, 2), intArrayOf(4, 5, 2), intArrayOf(1, 5, 5)));
assert(result == 5);

result = sol.maxTwoEvents(arrayOf(intArrayOf(1, 5, 3), intArrayOf(1, 5, 1), intArrayOf(6, 6, 5)));
assert(result == 8);

schedule = arrayOf(intArrayOf(1, 5, 3), intArrayOf(1, 5, 1), intArrayOf(6, 6, 5));
result = sol.maxTwoEvents(schedule);
assert(result == sol.test_every_two(schedule));

schedule = arrayOf(
    intArrayOf(28, 81, 48),
    intArrayOf(27, 90, 94),
    intArrayOf(97, 99, 79),
    intArrayOf(5, 35, 81),
    intArrayOf(65, 94, 84),
    intArrayOf(65, 83, 58),
    intArrayOf(94, 94, 31),
    intArrayOf(39, 52, 73)
);
result = sol.maxTwoEvents(schedule);
assert(result == sol.test_every_two(schedule));
assert(result == 173);

schedule = arrayOf(
    intArrayOf(20, 88, 78),
    intArrayOf(94, 96, 23),
    intArrayOf(14, 71, 33),
    intArrayOf(40, 73, 85),
    intArrayOf(84, 95, 86),
    intArrayOf(16, 63, 86),
    intArrayOf(6, 94, 73),
    intArrayOf(74, 76, 11),
    intArrayOf(26, 98, 63),
    intArrayOf(61, 82, 45),
    intArrayOf(14, 78, 37)
);
result = sol.maxTwoEvents(schedule);
println(sol.test_every_two(schedule));
assert(result == sol.test_every_two(schedule));
assert(result == 172);

schedule = arrayOf(
    intArrayOf(57, 82, 36),
    intArrayOf(67, 76, 81),
    intArrayOf(11, 99, 66),
    intArrayOf(53, 76, 91),
    intArrayOf(2, 56, 74),
    intArrayOf(77, 82, 66),
    intArrayOf(1, 86, 41),
    intArrayOf(42, 50, 93),
    intArrayOf(66, 78, 14),
    intArrayOf(97, 100, 11),
    intArrayOf(49, 63, 11),
    intArrayOf(95, 97, 19),
    intArrayOf(3, 31, 21),
    intArrayOf(96, 98, 38),
    intArrayOf(44, 69, 35),
    intArrayOf(65, 65, 40),
    intArrayOf(51, 82, 55),
    intArrayOf(100, 100, 50),
    intArrayOf(11, 68, 72),
    intArrayOf(72, 95, 8),
    intArrayOf(94, 98, 42),
    intArrayOf(4, 26, 22),
    intArrayOf(59, 88, 93),
    intArrayOf(28, 71, 92),
    intArrayOf(19, 89, 9),
    intArrayOf(20, 88, 78),
    intArrayOf(76, 82, 54),
    intArrayOf(81, 91, 100),
    intArrayOf(51, 98, 88),
    intArrayOf(13, 93, 49),
    intArrayOf(94, 96, 23),
    intArrayOf(56, 72, 72),
    intArrayOf(14, 71, 33),
    intArrayOf(40, 73, 85),
    intArrayOf(84, 95, 86),
    intArrayOf(16, 63, 86),
    intArrayOf(6, 94, 73),
    intArrayOf(74, 76, 11),
    intArrayOf(26, 98, 63),
    intArrayOf(61, 82, 45),
    intArrayOf(14, 78, 37)
);
result = sol.maxTwoEvents(schedule);
assert(result == sol.test_every_two(schedule));
assert(result == 193);
