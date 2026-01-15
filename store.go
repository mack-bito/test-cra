package main

import (
	"errors"
	"sync"
	"time"
)

var (
	ErrNotFound = errors.New("not found")
	ErrExists   = errors.New("code already exists")
)

type Entry struct {
	Code       string    `json:"code"`
	URL        string    `json:"url"`
	CreatedAt  time.Time `json:"created_at"`
	Clicks     int       `json:"clicks"`
	LastAccess time.Time `json:"last_access"`
}

type Store struct {
	mu      sync.RWMutex
	byCode  map[string]*Entry
	byURL   map[string]string
	seed    uint64
	base62  []byte
}

func NewStore() *Store {
	return &Store{
		byCode: make(map[string]*Entry),
		byURL:  make(map[string]string),
		seed:   uint64(time.Now().UnixNano()),
		base62: []byte("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	}
}

func (s *Store) Shorten(url string) (*Entry, error) {
	s.mu.Lock()
	defer s.mu.Unlock()

	// idempotent: same URL returns same code
	if code, ok := s.byURL[url]; ok {
		cp := *s.byCode[code]
		return &cp, nil
	}

	// generate a new code; very low collision chance
	for i := 0; i < 6; i++ {
		code := s.nextCode(7) // 7 chars
		if _, exists := s.byCode[code]; exists {
			continue
		}
		e := &Entry{
			Code:      code,
			URL:       url,
			CreatedAt: time.Now().UTC(),
		}
		s.byCode[code] = e
		s.byURL[url] = code

		cp := *e
		return &cp, nil
	}

	return nil, ErrExists
}

func (s *Store) Get(code string) (*Entry, error) {
	s.mu.RLock()
	e, ok := s.byCode[code]
	if !ok {
		s.mu.RUnlock()
		return nil, ErrNotFound
	}
	cp := *e
	s.mu.RUnlock()
	return &cp, nil
}

func (s *Store) Click(code string) (*Entry, error) {
	s.mu.Lock()
	defer s.mu.Unlock()

	e, ok := s.byCode[code]
	if !ok {
		return nil, ErrNotFound
	}
	e.Clicks++
	e.LastAccess = time.Now().UTC()

	cp := *e
	return &cp, nil
}

func (s *Store) nextCode(length int) string {
	// xorshift-ish
	s.seed ^= s.seed << 13
	s.seed ^= s.seed >> 7
	s.seed ^= s.seed << 17

	x := s.seed
	out := make([]byte, length)
	for i := 0; i < length; i++ {
		out[i] = s.base62[x%62]
		x = x / 62
		if x == 0 {
			// mix again if we ran out
			s.seed ^= s.seed << 13
			s.seed ^= s.seed >> 7
			s.seed ^= s.seed << 17
			x = s.seed
		}
	}
	return string(out)
}

