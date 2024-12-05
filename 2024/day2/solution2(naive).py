def parse_reports(data : str) -> list[list[int]]:
    reports = data.split("\n")
    parsed_reports = [
        [int(level) for level in report.split()] for report in reports
    ]
    return parsed_reports

def check_report(report : list[int]) -> bool:
    """Checks if a report is valid"""
    direction = None
    for i in range(len(report) - 1):
        lvl1 = report[i]
        lvl2 = report[i + 1]
        diff = lvl2-lvl1
        old_direction = direction
        if not direction:
            if diff > 0:
                direction = 1
            else:
                direction = -1
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
    return safe

def main():
    with open("reports.txt", "r") as file:
        reports_str = file.read()
    
    reports = parse_reports(reports_str)
    safe_count = 0
    for report in reports:
        if check_report(report):
            safe_count += 1
        else:
            for i in range(len(report)):
                report_copy = report.copy()
                report_copy.pop(i)
                if check_report(report_copy):
                    safe_count += 1
                    break
    

    print(safe_count)

if __name__ == "__main__":
    main()