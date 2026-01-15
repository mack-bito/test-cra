package main

import (
	"log"
	"net/http"
	"os"
	"time"
)

func main() {
	addr := env("ADDR", ":8080")
	baseURL := env("BASE_URL", "http://localhost"+addr)

	s := NewServer(baseURL)

	srv := &http.Server{
		Addr:              addr,
		Handler:           s.Routes(),
		ReadHeaderTimeout: 5 * time.Second,
	}

	log.Printf("tiny-url listening on %s (base=%s)", addr, baseURL)
	log.Fatal(srv.ListenAndServe())
}

