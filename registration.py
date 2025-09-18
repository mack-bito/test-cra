# file: register.py

users = {}

def register_user(username, password):
    if username in users:
        print("User already exists")
    else:
        users[username] = password
        print("User registered successfully")

def login_user(username, password):
    if username not in users:
        print("No such user exists")
    elif users[username] = password:  # ❌ Bug: Assignment instead of comparison
        print("Login successful")
    else:
        print("Incorrect password")

def main():
    while True:
        print("1. Register\n2. Login\n3. Quit")
        choice = input("Choose an option: ")

        if choice == "1":
            u = input("Username: ")
            p = input("Password: ")
            register_user(u, p)
        elif choice == "2":
            u = input("Username: ")
            p = input("Password: ")
            login_user(u, p)
        elif choice == "3":
            print("Bye!")
            break
        else:
            print("Invalid choice")

if __name__ == "__main__":
    main()
