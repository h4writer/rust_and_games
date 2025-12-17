
class Solution:
    def longestPalindrome(self, s: str) -> str:
        self.length = 1
        self.index = 0

        for i in range(len(s)):
            self.odd(s, i)

        for i in range(len(s)):
            self.even(s, i)

        return s[self.index:self.index+self.length]

    def even(self, s, i):
        l = -1
        while True:
            l += 1
            if i-l < 0 or i+1+l >= len(s):
                break
            if s[i+1+l] != s[i-l]:
                break

            if 2*(l+1) > self.length:
                self.length = 2*(l+1)
                self.index = i-l

    def odd(self, s, i):
        l = 0
        while True:
            l += 1
            if i-l < 0 or i+l >= len(s):
                break
            if s[i+l] != s[i-l]:
                break

            if 2*l+1 > self.length:
                self.length = 2*l+1
                self.index = i-l
                
        
if __name__ == '__main__':
    solver = Solution();

    sol = solver.longestPalindrome("babad")
    print(sol)
    assert sol == "bab"

    sol = solver.longestPalindrome("cbbd")
    print(sol)
    assert sol == "bb"

    sol = solver.longestPalindrome("jmjkmlklmjsfqdsjlljklkfds")
    print(sol)
    assert sol == "mlklm"
