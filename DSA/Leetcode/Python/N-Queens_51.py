def solveNQueens(self, n: int) -> List[List[str]]:
  def backtrack(row, cols, diag1, diag2):
    if row == n:
        result.append(["".join(row) for row in board])
        return

    for j in range(n):
        if j in cols or (row - j) in diag1 or (row + j) in diag2:
            continue

        board[row][j] = 'Q'
        backtrack(row + 1, cols | {j}, diag1 | {row - j}, diag2 | {row + j})
        board[row][j] = '.' 

result = []
board = [['.' for _ in range(n)] for _ in range(n)]
backtrack(0, set(), set(), set())
return result
