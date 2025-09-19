# main.py

def buggy_factorial(n):
    if n == 0:
        return 0  # ❌ Bug: should return 1 for factorial of 0
    result = 1
    for i in range(1, n + 1):
        result *= i
    return results  # ❌ Bug: typo in variable name


def fixed_factorial(n):
    if n == 0:
        return 1  # ✅ Correct base case
    result = 1
    for i in range(1, n + 1):
        result *= i
    return result  # ✅ Correct variable name


def main():
    test_values = [0, 1, 5, 7]

    print("=== Buggy Factorial ===")
    for n in test_values:
        try:
            print(f"factorial({n}) = {buggy_factorial(n)}")
        except Exception as e:
            print(f"factorial({n}) raised an error: {e}")

    print("\n=== Fixed Factorial ===")
    for n in test_values:
        print(f"factorial({n}) = {fixed_factorial(n)}")

if __name__ == "__main__":
    main()

def divide_numbers(a, b):
    # Bug 1: Doesn't handle division by zero
    return a / b

def add_to_list(value, my_list=[]):
    # Bug 2: Mutable default argument (list) can cause unexpected behavior
    my_list.append(value)
    return my_list

def read_file(filename):
    # Bug 3: Forgetting to close the file (resource leak)
    f = open(filename, "r")
    data = f.read()
    return data

def calculate_area(radius):
    # Bug 4: Typo in variable name ("redius" instead of "radius")
    pi = 3.14
    return pi * redius * redius

# Main execution
print("Division:", divide_numbers(10, 0))   # Will raise ZeroDivisionError
print("List 1:", add_to_list(1))            
print("List 2:", add_to_list(2))            # Reuses same default list unintentionally
print("File data:", read_file("nonexistent.txt"))  # FileNotFoundError
print("Area:", calculate_area(5))           # NameError due to typo

