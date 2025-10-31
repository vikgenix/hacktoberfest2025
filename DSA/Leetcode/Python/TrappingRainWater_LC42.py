def trap(self, height: List[int]) -> int:
  ans = 0
  for i in range(0,len(height)-1):
      mx1 = max(height[:i+1])
      mx2 = max(height[i+1:])
      print(mx1,mx2,min(mx1,mx2) - height[i],height[i])
      if min(mx1,mx2) - height[i] >=0:
          ans += min(mx1,mx2) - height[i]
      return ans

