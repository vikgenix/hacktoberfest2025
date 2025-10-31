def judgePoint24(self, cards: List[int]) -> bool:
        
        nums = [float(c) for c in cards]
        return self.solve(nums)

    def solve(self, nums: List[float]) -> bool:
        if len(nums) == 1:
            return abs(nums[0] - 24.0) < 1e-6

        n = len(nums)
        for i in range(n):
            for j in range(n):
                if i == j:
                    continue

                next_nums = [nums[k] for k in range(n) if k != i and k != j]
                a, b = nums[i], nums[j]

                results = [a + b, a - b, b - a, a * b]
                if abs(b) > 1e-6:
                    results.append(a / b)
                if abs(a) > 1e-6:
                    results.append(b / a)

                for val in results:
                    if self.solve(next_nums + [val]):
                        return True
        return False
