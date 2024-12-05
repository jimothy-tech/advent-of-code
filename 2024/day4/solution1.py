VALID_SEQUENCE = "XMAS"

def parse_input(input_file : str) -> list[list[str]]:
    with open(input_file, "r") as f:
        return [list(line.strip()) for line in f.readlines()]

def check_surrounding(grid, x, y) -> int:
    """
    returns number of valid char sequences from given position in grid
    """
    count = 0
    if grid[y][x] == VALID_SEQUENCE[0]:
        occurences : dict[str, int] = {"up-left": 0, 
                                       "up-right": 0, 
                                       "up": 0, 
                                       "down": 0, 
                                       "down-left": 0, 
                                       "down-right": 0,
                                       "left": 0,
                                       "right": 0} # dict to count occurences of each char in sequence
        
        for level, char in enumerate(VALID_SEQUENCE):

            if x - level >= 0 and y - level >= 0 and grid[y - level][x - level] == char: # check upper left corner
                occurences["up-left"] += 1
            if x + level < len(grid[0]) and y - level >= 0 and grid[y - level][x + level] == char: # check upper right corner
                occurences["up-right"] += 1
            if y - level >= 0 and grid[y - level][x] == char: # check up
                occurences["up"] += 1
            if y + level < len(grid) and grid[y + level][x] == char: # check down
                occurences["down"] += 1
            if x - level >= 0 and y + level < len(grid) and grid[y + level][x - level] == char: # check down left corner
                occurences["down-left"] += 1
            if x + level < len(grid[0]) and y + level < len(grid) and grid[y + level][x + level] == char: # check down right corner
                occurences["down-right"] += 1
            if x - level >= 0 and grid[y][x - level] == char: # check left
                occurences["left"] += 1
            if x + level < len(grid[0]) and grid[y][x + level] == char: # check right
                occurences["right"] += 1

        for occurence in occurences.values():
            if occurence == 4:
                count += 1
    return count


def main():
    count = 0
    grid = parse_input("input.txt")
    for y in range(len(grid)):
        for x in range(len(grid[0])):
            count += check_surrounding(grid, x, y)
    print(f"Total count: {count}")


if __name__ == "__main__":
    main()