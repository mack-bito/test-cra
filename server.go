package main

import "net/http"

type Server struct {
	baseURL string
	store   *Store
}

func NewServer(baseURL string) *Server {
	return &Server{
		baseURL: baseURL,
		store:   NewStore(),
	}
}

func (s *Server) Routes() http.Handler {
	mux := http.NewServeMux()

	mux.HandleFunc("POST /shorten", s.handleShorten)
	mux.HandleFunc("GET /stats/{code}", s.handleStats)
	mux.HandleFunc("GET /{code}", s.handleRedirect)
	mux.HandleFunc("GET /healthz", func(w http.ResponseWriter, r *http.Request) {
		writeJSON(w, http.StatusOK, map[string]any{"ok": true})
	})

	return logging(mux)
}

