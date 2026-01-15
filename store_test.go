package main

import "testing"

func TestStoreShortenIdempotent(t *testing.T) {
	s := NewStore()

	e1, err := s.Shorten("https://example.com/a")
	if err != nil {
		t.Fatal(err)
	}
	e2, err := s.Shorten("https://example.com/a")
	if err != nil {
		t.Fatal(err)
	}
	if e1.Code != e2.Code {
		t.Fatalf("expected same code, got %s vs %s", e1.Code, e2.Code)
	}
}

func TestStoreClickIncrements(t *testing.T) {
	s := NewStore()

	e, _ := s.Shorten("https://example.com")
	if e.Clicks != 0 {
		t.Fatalf("expected 0 clicks, got %d", e.Clicks)
	}

	_, _ = s.Click(e.Code)
	after, _ := s.Get(e.Code)
	if after.Clicks != 1 {
		t.Fatalf("expected 1 click, got %d", after.Clicks)
	}
}

