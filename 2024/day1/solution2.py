def convert_to_pylists(lists : str) -> tuple[list[int], list[int]]:
    pairs= lists.split("\n")

    list1 = []
    list2= []

    for pair in pairs:
        nums = pair.split("   ")
        list1.append(int(nums[0]))
        list2.append(int(nums[-1]))
    
    return list1, list2

def main():
    with open("lists.txt", "r") as file:
        lists = file.read()

    list1, list2 = convert_to_pylists(lists)

    score_sum = 0

    for num in list1:
        score_sum += list2.count(num) * num

    print(score_sum)


if __name__ == "__main__":
    main()

