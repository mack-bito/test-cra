package main

import "fmt"

func main() {
    a := 10
    b := 0

    fmt.Println("About to divide by zero...")
    result := a / b  // <-- invalid: division by zero
    fmt.Println("Result:", result)
}
