package main

import (
	"bytes"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestShortenAndStats(t *testing.T) {
	s := NewServer("http://localhost:8080")
	h := s.Routes()

	body := []byte(`{"url":"https://example.com/x"}`)
	req := httptest.NewRequest("POST", "/shorten", bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	rr := httptest.NewRecorder()
	h.ServeHTTP(rr, req)

	if rr.Code != http.StatusCreated {
		t.Fatalf("expected 201, got %d, body=%s", rr.Code, rr.Body.String())
	}

	var resp map[string]any
	if err := json.Unmarshal(rr.Body.Bytes(), &resp); err != nil {
		t.Fatal(err)
	}

	code, ok := resp["code"].(string)
	if !ok || code == "" {
		t.Fatalf("expected code in response, got %v", resp)
	}

	statsReq := httptest.NewRequest("GET", "/stats/"+code, nil)
	statsRR := httptest.NewRecorder()
	h.ServeHTTP(statsRR, statsReq)

	if statsRR.Code != http.StatusOK {
		t.Fatalf("expected 200, got %d, body=%s", statsRR.Code, statsRR.Body.String())
	}
}

func TestRedirectNotFound(t *testing.T) {
	s := NewServer("http://localhost:8080")
	h := s.Routes()

	req := httptest.NewRequest("GET", "/doesnotexist", nil)
	rr := httptest.NewRecorder()
	h.ServeHTTP(rr, req)

	if rr.Code != http.StatusNotFound {
		t.Fatalf("expected 404, got %d, body=%s", rr.Code, rr.Body.String())
	}
}

