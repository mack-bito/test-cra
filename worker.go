package main

import (
	"fmt"
	"time"
)

func worker(jobs <-chan int) {
	for job := range jobs {
		fmt.Println("Worker started job", job)
		time.Sleep(2 * time.Second) // slow work
		fmt.Println("Worker finished job", job)
	}
}

func main() {
	jobs := make(chan int, 3) // buffer size = 3

	go worker(jobs)

	for i := 1; i <= 6; i++ {
		fmt.Println("Sending job", i)
		jobs <- i
		fmt.Println("Sent job", i)
	}

	close(jobs)
	time.Sleep(15 * time.Second)
}

