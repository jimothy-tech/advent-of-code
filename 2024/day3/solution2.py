
import re

def main():
    with open("input.txt", "r") as file:
        data = file.read()

    mul_calls = re.findall(r'mul\(\d+,\d+\)|don\'t\(\)|do\(\)', data)
    print(mul_calls)
    mul_sum = 0
    multiply = True
    for mul in mul_calls:
        if mul == "don't()":
            multiply = False
            print(mul)
        elif mul == "do()":
            multiply = True
        elif multiply:
            a, b = re.findall(r'\d+', mul)
            mul_sum += int(a) * int(b)

if __name__ == "__main__":
    main() 