def factorial(n):
    if n == 0:
        return 0  # ❌ Bug: should return 1 for factorial of 0
    result = 1
    for i in range(1, n + 1):
        result *= i
    return results  # ❌ Bug: typo in variable name
