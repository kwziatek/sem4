package main

import (
	"fmt"
	"sync"
	"sync/atomic"
)

func do() int{
	var n int64
	var w sync.WaitGroup

	for i := 0; i < 1000; i++ {
		w.Add(1)
		go func() {
			defer w.Done()
			atomic.AddInt64(&n, 1)
		}()
	}
	w.Wait()
	return int(n)
}

func main() {
	fmt.Println(do())
}
