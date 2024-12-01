def calculate_distances(list1, list2):
    list1.sort()
    list2.sort()
    distance_sum = 0
    for i in range(len(list1)):
        distance_sum += abs(list1[i]-list2[i])
        
    return distance_sum
    
def main():
    with open("lists.txt", "r") as file:
        lists = file.read()

    pairs= lists.split("\n")

    list1 = []
    list2= []

    for pair in pairs:
        nums = pair.split("   ")
        list1.append(int(nums[0]))
        list2.append(int(nums[-1]))
        
    print(calculate_distances(list1, list2))


if __name__ == "__main__":
    main()