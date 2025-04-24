package main

import (
	"fmt"
	"time"
	"math/rand"
)

const(
	number_of_travelers = 15
	min_steps 			= 10
	max_steps 			= 100
	min_delay 			= 10 * time.Millisecond
	max_delay 			= 50 * time.Millisecond
	board_width 		= 15
	board_height 		= 15

)

type Event struct{
	timestamp	int64
	id 			int
	x			int
	y 			int
	symbol		string

}

func main() {
	ch := make(chan Event)

	//odczytywanie wydarzeń z channela
	go func() {
		for event := range ch {
			fmt.Printf("%d %d %d %d %s\n",
						event.timestamp,
						event.id,
						event.x,
						event.y,
						event.symbol)
		}
	}()
	

	fmt.Printf("-1 %d %d %d\n",
				number_of_travelers, 
				board_width,
				board_height)
	
	join_point := make(chan bool)
	
	// var wg sync.WaitGroup
	for i := 0; i < 15; i++ {
		// wg.Add(1);
		go traveler(i, ch, join_point)
	}

	for i := 0; i < 15; i++ {
		<- join_point
	}
	// wg.Wait()
	close(ch);
}

func traveler(id int, ch chan <- Event, join_point chan <- bool) {
	// defer wg.Done()

	src := rand.NewSource(time.Now().UnixNano() + int64(id))
	r := rand.New(src)

	x := r.Intn(board_width)
	y := r.Intn(board_height)

	steps := min_steps + r.Intn(max_steps + 1)

	symbol := string('A' + id);

	directions := [][2] int{
			{1, 0},
		{0, -1},	{0, 1},
			{-1, 0},
	}
	for i := 0; i < steps; i++ {
		delay := min_delay + time.Duration(r.Float64()*float64(max_delay-min_delay))
		time.Sleep(delay)
		
		dir := directions[r.Intn(len(directions))]
		dx, dy := dir[0], dir[1]

		x = (x + dx + board_width) % board_width
		y = (y + dy + board_height) % board_height

		ch <- Event{
			timestamp : time.Now().UnixNano(),
			id : id,
			x : x,
			y : y,
			symbol : symbol,
		}
	}
	join_point <- true
}

