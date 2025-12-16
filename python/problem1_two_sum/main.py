
from typing import List

class Solution:

    def withSort(self, nums, target):
        # O(n log n)
        sorted_index = sorted([i for i in range(len(nums))], key=lambda key:nums[key])

        start = 0
        end = len(nums)-1

        while True:
            sum_ = nums[sorted_index[start]] + nums[sorted_index[end]] 
            if sum_ == target:
                return [sorted_index[start], sorted_index[end]]
            if sum_ > target:
                end -= 1
            else:
                start += 1

        return None 


    def naive(self, nums, target):
        # O(n2)
        for i in range(len(nums)):
            for j in range(i+1, len(nums)):
                if nums[i] + nums[j] == target:
                    return [i,j]
        return None

    def twoSum(self, nums: List[int], target: int) -> List[int]:
        return self.withSort(nums, target);
        #return self.naive(nums, target);
        

if __name__ == '__main__':
    solver = Solution();

    sol = solver.twoSum([2,7,11,15], 9)
    assert sol == [0,1]

    sol = solver.twoSum([3,2,4], 6)
    assert sol == [1,2]

    sol = solver.twoSum([3,3], 6)
    assert sol == [0,1]
