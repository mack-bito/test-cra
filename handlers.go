package main

import (
	"encoding/json"
	"net/http"
)

type shortenReq struct {
	URL string `json:"url"`
}

type shortenResp struct {
	Code     string `json:"code"`
	ShortURL string `json:"short_url"`
	URL      string `json:"url"`
}

func (s *Server) handleShorten(w http.ResponseWriter, r *http.Request) {
	var req shortenReq
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid_json", "body must be valid JSON")
		return
	}
	req.URL = trim(req.URL)
	if req.URL == "" {
		writeError(w, http.StatusBadRequest, "invalid_url", "url is required")
		return
	}
	if !looksLikeURL(req.URL) {
		writeError(w, http.StatusBadRequest, "invalid_url", "url must start with http:// or https://")
		return
	}

	e, err := s.store.Shorten(req.URL)
	if err != nil {
		writeError(w, http.StatusInternalServerError, "shorten_failed", err.Error())
		return
	}

	resp := shortenResp{
		Code:     e.Code,
		ShortURL: s.baseURL + "/" + e.Code,
		URL:      e.URL,
	}
	writeJSON(w, http.StatusCreated, resp)
}

func (s *Server) handleRedirect(w http.ResponseWriter, r *http.Request) {
	code := r.PathValue("code")
	code = trim(code)
	if code == "" {
		writeError(w, http.StatusBadRequest, "invalid_code", "code is required")
		return
	}

	e, err := s.store.Click(code)
	if err == ErrNotFound {
		writeError(w, http.StatusNotFound, "not_found", "unknown short code")
		return
	}
	if err != nil {
		writeError(w, http.StatusInternalServerError, "lookup_failed", err.Error())
		return
	}

	http.Redirect(w, r, e.URL, http.StatusFound)
}

func (s *Server) handleStats(w http.ResponseWriter, r *http.Request) {
	code := r.PathValue("code")
	code = trim(code)
	if code == "" {
		writeError(w, http.StatusBadRequest, "invalid_code", "code is required")
		return
	}

	e, err := s.store.Get(code)
	if err == ErrNotFound {
		writeError(w, http.StatusNotFound, "not_found", "unknown short code")
		return
	}
	if err != nil {
		writeError(w, http.StatusInternalServerError, "lookup_failed", err.Error())
		return
	}

	writeJSON(w, http.StatusOK, e)
}

