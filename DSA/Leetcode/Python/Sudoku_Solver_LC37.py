def solveSudoku(self, board: List[List[str]]) -> None:
        rows = [0] * 9
        cols = [0] * 9
        boxes = [0] * 9
        empties = []

        def place(r, c, num):
            mask = 1 << num
            rows[r] |= mask
            cols[c] |= mask
            boxes[(r // 3) * 3 + (c // 3)] |= mask

        def remove(r, c, num):
            mask = ~(1 << num)
            rows[r] &= mask
            cols[c] &= mask
            boxes[(r // 3) * 3 + (c // 3)] &= mask

        def count_bits(n):
            count = 0
            while n:
                n &= n - 1
                count += 1
            return count

        def bit_position(mask):
            pos = 0
            while (1 << pos) != mask:
                pos += 1
            return pos

        # Initialize
        for i in range(9):
            for j in range(9):
                if board[i][j] == '.':
                    empties.append((i, j))
                else:
                    num = int(board[i][j]) - 1
                    place(i, j, num)

        def backtrack():
            if not empties:
                return True

            min_options, idx, mask = 10, -1, 0
            for k, (r, c) in enumerate(empties):
                b = (r // 3) * 3 + (c // 3)
                used = rows[r] | cols[c] | boxes[b]
                options = 9 - count_bits(used)
                if options < min_options:
                    min_options = options
                    idx = k
                    mask = (~used) & 0x1FF
                    if options == 1: break

            r, c = empties.pop(idx)
            while mask:
                pick = mask & -mask
                num = bit_position(pick)
                place(r, c, num)
                board[r][c] = str(num + 1)

                if backtrack(): return True

                remove(r, c, num)
                board[r][c] = '.'
                mask -= pick

            empties.insert(idx, (r, c))
            return False

        backtrack()
