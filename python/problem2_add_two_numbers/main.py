from typing import Optional

# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        last = None
        root = None
        carry = 0
        while l1 or l2:
            sum_ = carry
            if l1:
                sum_ += l1.val
                l1 = l1.next
            if l2:
                sum_ += l2.val
                l2 = l2.next

            if sum_ >= 10:
                carry = int(sum_ / 10);
            else:
                carry = 0
            sum_ = sum_ % 10;

            if root == None:
                root = ListNode(sum_)
                last = root
            else:
                last.next = ListNode(sum_)
                last = last.next

        if carry:
            last.next = ListNode(carry)
        return root;

def toListNode(input_):
    root = None
    last = None
    for i in input_:
        node = ListNode(i)
        if last == None:
            last = node
            root = node
        else:
            last.next = node
            last = node
    return root

def toArray(input_):
    ret = []
    while input_:
        ret.append(input_.val)
        input_ = input_.next
    return ret


if __name__ == '__main__':
    solver = Solution();

    sol = solver.addTwoNumbers(toListNode([2,4,3]),toListNode([5,6,4]))
    print(toArray(sol))
    assert toArray(sol) == [7,0,8]

    sol = solver.addTwoNumbers(toListNode([5]),toListNode([5]))
    print(toArray(sol))
    assert toArray(sol) == [0, 1]

    sol = solver.addTwoNumbers(toListNode([0]),toListNode([0]))
    print(toArray(sol))
    assert toArray(sol) == [0]

    sol = solver.addTwoNumbers(toListNode([9,9,9,9,9,9,9]),toListNode([9,9,9,9]))
    print(toArray(sol))
    assert toArray(sol) == [8,9,9,9,0,0,0,1]
