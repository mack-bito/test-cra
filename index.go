package main

import "fmt"

func main() {
    a := 10
    b := 0

    if b == 0 {
        fmt.Println("Error: division by zero")
        return
    } else {
        result := a / b
        fmt.Println("Result:", result)
    }
}
