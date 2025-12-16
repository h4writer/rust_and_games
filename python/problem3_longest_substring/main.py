
from collections import defaultdict

class Solution:

    def movingwindow(self, s: str) -> int:
        # O(n)
        if len(s) == 0:
            return 0

        num_multiple = 0;

        letters = defaultdict(int)
        length = 0

        start = 0
        end = start 

        best = 0

        while True:
            if num_multiple == 0:
                if end-start > best:
                    best = end-start
                    print("inc", best)

                if end >= len(s):
                    break

                # increase length
                letters[s[end]] += 1
                if letters[s[end]] == 2:
                    num_multiple += 1

                end += 1
            else:
                # decrease length
                letters[s[start]] -= 1
                if letters[s[start]] == 1:
                    num_multiple -= 1

                start += 1
        return best


    def naive(self, s: str) -> int:
        # O(n2)
        best = 0
        for i in range(len(s)):
            letters = {}
            for l in range(len(s)-i):
                if s[i+l] in letters:
                    break
                letters[s[i+l]] = 1
                if l+1 > best:
                    best = l+1
        return best

    def lengthOfLongestSubstring(self, s: str) -> int:
        #return self.naive(s)
        return self.movingwindow(s)


if __name__ == '__main__':
    solver = Solution();

    sol = solver.lengthOfLongestSubstring("abcabcbb")
    assert sol == 3

    sol = solver.lengthOfLongestSubstring("bbbbbb")
    assert sol == 1

    sol = solver.lengthOfLongestSubstring("pwwkew")
    assert sol == 3

    sol = solver.lengthOfLongestSubstring("abdqsfafqsvcqdaerzcsv")
    assert sol == 9

    sol = solver.lengthOfLongestSubstring("")
    assert sol == 0

    sol = solver.lengthOfLongestSubstring(" ")
    assert sol == 1

    sol = solver.lengthOfLongestSubstring("abc")
    assert sol == 3

    sol = solver.lengthOfLongestSubstring("aabc")
    assert sol == 3

    sol = solver.lengthOfLongestSubstring("abca")
    assert sol == 3
