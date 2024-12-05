import os
import re
def main():
    file = fr'{os.path.dirname(os.path.abspath(__file__))}/input.txt'
    with open(file) as f:
        data = f.read().strip()
        d = re.findall(r'mul\([0-9]+,[0-9]+\)|do\(\)|don\'t\(\)', data)
        
        #mult = [[int(x) for x  in re.findall('[0-9]+', l)] for l in d]
        ans = 0
        #s = [x for x in re.findall('[0-9]+|do\(\)|don\'t', d)]
        s = []
        for i in d:
            if len(re.findall(r'[0-9]+', i)) == 2:
                s.append(re.findall(r'[0-9]+', i))
            else:
                s.append(re.findall(r'do\(\)|don\'t\(\)', i))
        do = True
        print(s)
        for item in s:
            # print(repr(item))
            if len(item) == 2 and do:
                ans += int(item[0])*int(item[1])
            if item == 'do()':
                do = True
            if item == "don't()":
                do = False
    #for m in mult:
    #    ans += m[0]*m[1]
    # print(ans) 
if __name__=='__main__':
    main()