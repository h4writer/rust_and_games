
from typing import List
import numpy as np
import random

class Solution:

    def iterator(self, nums1, nums2):
        start_1 = 0
        start_2 = 0
        for i in range(len(nums1)+len(nums2)):
            if start_1 >= len(nums1):
                yield nums2[start_2]
                start_2 += 1
            elif start_2 >= len(nums2):
                yield nums1[start_1]
                start_1 += 1
            elif nums1[start_1] < nums2[start_2]:
                yield nums1[start_1]
                start_1 += 1
            else:
                yield nums2[start_2]
                start_2 += 1

    def usingGenerator(self, nums1: List[int], nums2: List[int]) -> float:
        # O(n+m), no additional space requirements

        middle = len(nums1) + len(nums2) - 1
        single = middle % 2 == 0
        middle = int(middle/2)

        it = self.iterator(nums1, nums2)
        num = 0
        val1 = 0
        for i in it:
            if num == middle:
                val1 = i
                if single:
                    return i
            if num == middle+1:
                return (val1+i)*0.5
            num += 1

    def binary_search(self, nums1: List[int], nums2: List[int]) -> float:
        middle = len(nums1) + len(nums2) - 1
        single = middle % 2 == 0
        if not single:
            middle = int(middle/2)
            return (self.binary_search_index(middle, np.array(nums1), np.array(nums2), 0) + 
                    self.binary_search_index(middle+1, np.array(nums1), np.array(nums2), 0))/2

        middle = int(middle/2)
        return self.binary_search_index(middle, np.array(nums1), np.array(nums2), 0)

    def binary_search_index(self, key, nums1, nums2, steps) -> float:

        if nums1.size == 0:
            self.steps = steps
            return nums2[key]
        if nums2.size == 0:
            self.steps = steps
            return nums1[key]
        if key == 0:
            self.steps = steps
            return min(nums1[0], nums2[0])

        middle1 = int((nums1.size-1)/2)
        middle2 = int((nums2.size-1)/2)

        val1 = nums1[middle1]
        val2 = nums2[middle2]

        #print("nums1:", nums1, middle1)
        #print("nums2:", nums2, middle2)
        #print("key", key, "<=>", middle1+middle2)

        if middle1 + middle2 < key:
            # [... val1 ...]: middle1
            # [... val2 ...]: middle2
            # middle1 + middle2 < key
            # key_value > min(val1, val2)
            #print("key_value > min(val1, val2)")
            if val1 < val2:
                return self.binary_search_index(key - middle1 - 1, nums1[middle1+1:], nums2, steps+1)
            else:
                return self.binary_search_index(key - middle2 - 1, nums1, nums2[middle2+1:], steps+1)
        if middle1 + middle2 > key:
            # [... val1 ...]: middle1
            # [... val2 ...]: middle2
            # middle1 + middle2 > key
            # key_value < max(val1, val2)
            #print("key_value < min(val1, val2)")
            if val1 < val2:
                return self.binary_search_index(key, nums1, nums2[:middle2], steps+1)
            else:
                return self.binary_search_index(key, nums1[:middle1], nums2, steps+1)

        # [... val1 ...]: middle1
        # [... val2 ...]: middle2
        # middle1 + middle2 == key
        # key_value is in [val1, val2]
        #print("key_value = [val1, val2]")
        if val1 < val2:
            return self.binary_search_index(key - middle1, nums1[middle1:], nums2[:middle2+1], steps+1)
        else:
            return self.binary_search_index(key - middle2, nums1[:middle1+1], nums2[middle2:], steps+1)
            


    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        #return self.usingGenerator(nums1, nums2);
        return self.binary_search(nums1, nums2);


if __name__ == '__main__':
    solver = Solution();

    sol = solver.findMedianSortedArrays([1,3], [2])
    assert sol == 2

    sol = solver.findMedianSortedArrays([1,3], [])
    assert sol == 2

    sol = solver.findMedianSortedArrays([1], [])
    assert sol == 1

    sol = solver.findMedianSortedArrays([], [6])
    assert sol == 6

    sol = solver.findMedianSortedArrays([1,2], [3,4])
    assert sol == 2.5

    for _ in range(100):
        len1 = random.randint(1, 100)
        len2 = random.randint(0, 100)
        nums1 = [random.randint(0, 100) for _ in range(len1)]
        nums2 = [random.randint(0, 100) for _ in range(len2)]
        nums1.sort()
        nums2.sort()
        print (nums1, nums2)
        assert solver.usingGenerator(nums1, nums2) == solver.findMedianSortedArrays(nums1, nums2)
        print ("steps", (len1+len2)/2, "vs" , solver.steps)
