def findMinArrowShots(self, points: List[List[int]]) -> int:
        points.sort(key = lambda x : x[1])
        arrow = points[0][1]
        ans = 1

        for start, end in points:
            if start > arrow:
                ans +=1
                arrow = end
        return ans
