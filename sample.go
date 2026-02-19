package main

import (
	"fmt"
	"sync"
	"time"
)

type User struct {
	ID   int
	Name string
}

var cache = make(map[int]User)

func main() {
	var wg sync.WaitGroup

	users := []User{
		{ID: 1, Name: "Alice"},
		{ID: 2, Name: "Bob"},
		{ID: 3, Name: "Charlie"},
	}

	// Goroutine bug
	for _, u := range users {
		wg.Add(1)
		go func() {
			defer wg.Done()
			time.Sleep(100 * time.Millisecond)
			fmt.Println("Processing user:", u.Name)
			cache[u.ID] = u
		}()
	}

	wg.Wait()

	// Map iteration + delete bug
	for id := range cache {
		delete(cache, id)
	}

	fmt.Println("Final cache:", cache)

	// Slice bug
	a := []int{1, 2, 3}
	b := a[:2]
	b = append(b, 99)

	fmt.Println("Slice a:", a)
	fmt.Println("Slice b:", b)
}
