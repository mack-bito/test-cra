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
