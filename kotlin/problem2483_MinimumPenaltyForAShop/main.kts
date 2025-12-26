
class Solution {
    fun bestClosingTime(customers: String): Int {
        var penalty = 0;

        var bestTime = -1;
        var bestPenalty = 0;
        for (i in 0 ..< customers.length) {
            if (customers[i] == 'Y') {
                bestPenalty++;
            } else {
                assert(customers[i] == 'N');
                penalty++;
            }

            if (penalty < bestPenalty) {
                bestPenalty = penalty;
                bestTime = i;
            }
        }
        return bestTime+1;
    }
}

val sol = Solution();
run {
    val answer = sol.bestClosingTime("YYNY");
    assert(answer == 2);
}
run {
    val answer = sol.bestClosingTime("NNNN");
    assert(answer == 0);
}
run {
    val answer = sol.bestClosingTime("YYYY");
    assert(answer == 4);
}
run {
    val answer = sol.bestClosingTime("Y");
    assert(answer == 1);
}
run {
    val answer = sol.bestClosingTime("N");
    assert(answer == 0);
}