def parse_reports(data : str) -> list[list[int]]:
    reports = data.split("\n")
    parsed_reports = [
        [int(level) for level in report.split()] for report in reports
    ]
    return parsed_reports
    
def main():
    with open("reports.txt", "r") as file:
        reports_str = file.read()
    
    reports = parse_reports(reports_str)
    safe_count = 0
    for report in reports:
        direction = None
        for i in range(len(report) - 1):
            lvl1 = report[i]
            lvl2 = report[i + 1]
            diff = lvl2-lvl1
            old_direction = direction
            safe = True
            if diff > 0:
                direction = 1
            else:
                direction = -1
                
            if old_direction and direction != old_direction:
                safe = False
                break
            if abs(diff) > 3 or abs(diff) < 1:
                safe = False
                break
        if safe:
            safe_count += 1

    print(safe_count)

if __name__ == "__main__":
    main()