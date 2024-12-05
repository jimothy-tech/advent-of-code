def parse_input(input_file : str) -> list[list[str]]:
    with open(input_file, "r") as f:
        return [list(line.strip()) for line in f.readlines()]

def check_surrounding(grid, x, y) -> int:
    """
    returns number of valid char sequences from given position in grid
    """
    count = 0

    # check all 4 corners

    #define conditions for checking bounds
    upleft_downright_bounds_check = (x - 1 >= 0 and y - 1 >= 0 and x + 1 < len(grid[0]) and y + 1 < len(grid))
    upright_downleft_bounds_check = (x + 1 < len(grid[0]) and y - 1 >= 0 and x - 1 >= 0 and y + 1 < len(grid))

    if grid[y][x] == "A" and upleft_downright_bounds_check and upright_downleft_bounds_check:
        upleft = grid[y - 1][x - 1]
        upright = grid[y - 1][x + 1]
        downleft = grid[y + 1][x - 1]
        downright = grid[y + 1][x + 1]

        upleft_downright_check = (upleft != downright and upleft in ("M", "S") and downright in ("M", "S"))
        upright_downleft_check = (upright != downleft and upright in ("M", "S") and downleft in ("M", "S"))

        if upleft_downright_check and upright_downleft_check:
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