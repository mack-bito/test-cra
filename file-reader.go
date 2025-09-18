// file: auth.go
package main

import (
	"fmt"
)

var users = map[string]string{}

func register(username, password string) {
	if _, exists := users[username]; exists {
		fmt.Println("User already exists")
	} else {
		users[username] = password
		fmt.Println("User registered successfully")
	}
}

func login(username, password string) {
	if storedPassword, ok := users[username]; ok {
		if storedPassword = password { // ❌ Bug: assignment instead of comparison
			fmt.Println("Login successful")
		} else {
			fmt.Println("Wrong password")
		}
	} else {
		fmt.Println("User does not exist")
	}
}

func main() {
	register("alice", "1234")
	login("alice", "1234")
	login("bob", "pass")
}
