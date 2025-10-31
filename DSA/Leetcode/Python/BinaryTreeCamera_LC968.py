# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def minCameraCover(self, root: Optional[TreeNode]) -> int:
        
        if not root:
            return 0
        cam = 0
        cov = set()
        cov.add(None)  

        def fun(node):
            nonlocal cam 
            if not node:
                return 1 

            if node:
                left = fun(node.left)
                right = fun(node.right)

            if left == 0 or right == 0:
                cam += 1
                return 2

            if left == 2 or right == 2:
                return 1

            return 0
        return fun(root) == 0 and cam + 1 or cam
