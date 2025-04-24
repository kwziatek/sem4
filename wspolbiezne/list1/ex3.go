package main

import (
	"fmt"
	"time"
	"math/rand"
	"sync"
)

const(
	number_of_travelers = 3
	min_steps 			= 10
	max_steps 			= 100
	min_delay 			= 1000 * time.Millisecond
	max_delay 			= 5000 * time.Millisecond
	board_width 		= 2
	board_height 		= 2

)

type Event struct{
	timestamp	int64
	id 			int
	x			int
	y 			int
	symbol		string

}

type Field struct {
	x			int
	y			int
	mut 		sync.Mutex
	isEmpty 	bool
	ch			chan bool
	access_ch 	chan bool
}

func occupyField(oldField *Field, newField *Field, i int) {
	newField.mut.Lock()
	defer newField.mut.Unlock()
	if i != 0 {
		go oldField.leaveField()
	}
	
	// fmt.Println("in method, field:", oldField.x, oldField.y, newField.x, newField.y)
	newField.ch <- true
	<- newField.access_ch
	//zmodyfikować occupyField na funkcje, ktora dostaje jako parametr
	//pole w ktorym obecnie jest podroznik
	//pierwsza rzecza po zalockowaniu mutexa jest wywolanie funkcji leaveField()
	//dla starego pola
	
}

func (f* Field) leaveField() {
	f.access_ch <- true
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
	
	fields := make([][]Field, board_width)
	for i := 0; i < board_width; i++ {
		fields[i] = make([]Field, board_height)
		for j := 0 ; j < board_height; j++ {
			chs := []chan bool {make(chan bool), make(chan bool)}
			fields[i][j] = Field{x : i, y : j, isEmpty : false, ch : chs[0], access_ch : chs[1]}
		}
	}

	fmt.Printf("-1 %d %d %d\n",
				number_of_travelers, 
				board_width,
				board_height)

	
	
	join_point := make(chan bool)
	
	for i := 0; i < number_of_travelers; i++ {
		go traveler(i, ch, join_point, fields)
	}

	for i := 0; i < number_of_travelers; i++ {
		<- join_point
	}
	close(ch);
}

func traveler(id int, ch chan <- Event, join_point chan <- bool, fields [][]Field) {

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

		oldField := fields[x][y]

		dir := directions[r.Intn(len(directions))]
		dx, dy := dir[0], dir[1]

		x = (x + dx + board_width) % board_width
		y = (y + dy + board_height) % board_height

		// fmt.Println("new x & y", x, y)

		newField := fields[x][y]

		deadlock := false
		stopper := time.After(min_delay + time.Millisecond)
		go occupyField(&oldField, &newField, i)

		select {
		case <- newField.ch:
			
		case <- stopper:
			fmt.Println("select deadlock")
			deadlock = true
		}
		ch <- Event{
			timestamp : time.Now().UnixNano(),
			id : id,
			x : x,
			y : y,
			symbol : symbol,
		}
		if(deadlock) {
			fmt.Println("deadlock")
			break
		}
	}
	join_point <- true
}

