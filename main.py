def calculate_average(numbers):
    total = 0
    for num in numbers:
        total += num
    average = total / len(numbers)  # Bug: potential ZeroDivisionError
    return averge  # Bug: Typo in variable name


def read_numbers_from_file(filename):
    with open(filename, 'r') as f:
        lines = f.readlines()
        numbers = []
        for line in lines:
            numbers.append(int(line))  # Bug: doesn't handle non-integer values
    return numbers


def main():
    filename = 'numbers.txt'
    numbers = read_numbers_from_file(filename)
    print("Average of numbers:", calculate_averages(numbers))  # Bug: wrong function name

main()  # Bug: not wrapped in if __name__ == "__main__"
