def parse_reports(data : str) -> list[list[int]]:
    """ Parses the reports from the data string"""
    reports = data.split("\n")
    parsed_reports = [
        [int(level) for level in report.split()] for report in reports
    ]
    return parsed_reports

def calculate_direction(report) -> int:
    """ Calculates the overall direction of the report"""
    direction = 0
    for i in range(len(report) - 1):
        if report[i + 1] - report[i] > 0:
            direction += 1
        else:
            direction -= 1
    return direction

def check_pair(num1, num2, direction) -> bool:
    """ Checks if a pair of numbers is valid in the report"""
    diff = num2-num1
    if not diff > 0 and direction > 0 or not diff < 0 and direction < 0: # if the direction is not the same as the diff
        return False
    elif diff == 0: # if the diff is 0
        return False
    elif direction == 0: # if the direction is 0 - there must be multiple offenders
        return False
    if abs(diff) > 3 or abs(diff) < 1: # if the diff is too big or too small
        return False
    return True

def check_for_valid_removal(unsafe_lvls, direction) -> bool:
    """Checks if removing one of the offenders makes the report valid"""
    previous_lvl = unsafe_lvls[0]
    offender1 = unsafe_lvls[1]
    offender2 = unsafe_lvls[2]
    next_lvl = unsafe_lvls[3]
    if not previous_lvl:
        if check_pair(offender1, next_lvl, direction):  # remove offender2
            return True
        if check_pair(offender2, next_lvl, direction):  # remove offender1
            return True
    elif not next_lvl:
        if check_pair(previous_lvl, offender1, direction):  # remove offender2
            return True
        if check_pair(previous_lvl, offender2, direction):  # remove offender1
            return True
    else:
        if check_pair(previous_lvl, offender2, direction):  # remove offender1
            return True
        if check_pair(previous_lvl, offender1, direction):  # remove offender2
            return True
    return False

def main():
    with open("reports.txt", "r") as file:
        reports_str = file.read()
    
    reports = parse_reports(reports_str)
    safe_count = 0
    unsafe_count = 0
    for report in reports:
        direction = calculate_direction(report)
        safe = True
        unsafe_lvls = []
        for i in range(len(report) - 1):
            if len(unsafe_lvls) > 1:
                break
            lvl1 = report[i]
            lvl2 = report[i + 1]

            # check if pair is not valid
            if not check_pair(lvl1, lvl2, direction):
                unsafe_lvls += [
                    (report[i - 1] if i > 0 else None, lvl1, lvl2, report[i + 2] if i < len(report) - 2 else None)
                ] # previous, current two offenders, next
                safe = False

        if len(unsafe_lvls) == 1 and check_for_valid_removal(unsafe_lvls[0], direction): #check if removing one of the offenders makes it valid
            safe = True
            print(f"Fixable report: {report} Direction: {direction}")
        elif len(unsafe_lvls) > 1:
            # print(f"Unfixable report: {report} Direction: {direction}")
            unsafe_count += 1
        if safe:
            # print(f"Safe report: {report} Direction: {direction}")
            safe_count += 1
    print(f"Safe reports: {safe_count}")
    print(f"Unsafe reports: {unsafe_count}")
if __name__ == "__main__":
    main()