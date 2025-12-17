
import io

class Solution:

   def convert(self, s: str, numRows: int) -> str:
       if numRows == 1:
           return s

       cycle = (numRows-2)*2 + 2

       result = []
       for i in range(numRows):
           k = 0
           while True:
               if k+i >= len(s):
                   break
               result.append(s[k+i])
               if i != 0 and i != numRows-1:
                   if k+cycle-i >= len(s):
                       break
                   result.append(s[k+cycle-i])
               k += cycle
       return "".join(result)


if __name__ == '__main__':
    solver = Solution();

    sol = solver.convert("PAYPALISHIRING", 3)
    print(sol)
    assert sol == "PAHNAPLSIIGYIR"

    sol = solver.convert("PAYPALISHIRING", 1)
    print(sol)
    assert sol == "PAYPALISHIRING"

    sol = solver.convert("PAYPALISHIRING", 2)
    print(sol)
    assert sol == "PYAIHRNAPLSIIG"

    sol = solver.convert("", 2)
    print(sol)
    assert sol == ""

    sol = solver.convert("PAYPALISHIRING", 4)
    print(sol)
    assert sol == "PINALSIGYAHRPI"

    sol = solver.convert("A", 1)
    print(sol)
    assert sol == "A"
