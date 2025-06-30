package main

import (
	"errors"
	"fmt"
	"net/http"
	"os"
)

func getRoot(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/html")

	// Odczyt nagłówków request
	var headers string
    for name, values := range r.Header {
        for _, value := range values {
            headers += fmt.Sprintf("%s: %s<br>", name, value)
        }
	}

	fmt.Fprintf(w, `
		<!DOCTYPE html>
		<html>
		<head>
			<meta charset="UTF-8" />
			<title>Site</title>
			<link rel="stylesheet" href="/static/style.css" />
			<style>
				div {
					color: #333;
					font-size: 1.5em;
					padding: 20px;
					background-color: #fff;
					border-radius: 10px;
					box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
				}
			</style>
		</head>
		<body>
			<div>
				<p>Your request line:</p>
				<p>%s %s %s</p>
				<p>Your headers:</p>
				<p>%s<p>
			</div>
		</body>
		</html>
	`, r.Method, r.URL.Path, r.Proto, headers)
}

func main() {
	// Handlerzy dla różnych ścieżek
	http.HandleFunc("/", getRoot)

	err := http.ListenAndServe(":3001", nil)
	if errors.Is(err, http.ErrServerClosed) {
		fmt.Printf("server closed\n")
	} else if err != nil {
		fmt.Printf("error starting server: %s\n", err)
		os.Exit(1)
	}
}