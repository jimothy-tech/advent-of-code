
import re

def main():
    with open("input.txt", "r") as file:
        data = file.read()

    mul_calls = re.findall(r'mul\(\d+,\d+\)', data)
    mul_sum = 0
    for mul in mul_calls:
        a, b = re.findall(r'\d+', mul)
        mul_sum += int(a) * int(b)

    print(mul_sum)

if __name__ == "__main__":
    main() 