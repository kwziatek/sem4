package main
import "fmt"

func generator(limit int, ch chan <- int) {
	for i:= 2; i < limit; i++ {
		// fmt.Println("generator:", i)
		ch <- i
	}
	close(ch)
}

func filter(src <- chan int, dst chan <- int, prime int) {
	for i := range src{
		if i % prime != 0 {
			dst <- i
		}
	}	// pętla sie wykonuje dopoki src nie zostanie zamknięty 

	close(dst)
}

func wziat(limit int) {
	ch := make(chan int)

	go generator(limit, ch)

	for {
		prime, ok := <- ch
		if !ok {
			break
		}

		ch1 := make(chan int)
		go filter(ch, ch1, prime)

		ch = ch1
		fmt.Println(prime, " ")
	}
}

func main() {
	wziat(100)
}

// dwójka trafia do wziat
// wziat tworzy nowa goroutine i nowy changel
// ta goroutine pobiera ze starego channela liczby, filtruje je
// wysyła na nowego channela tylko te nieparzyste

//co wazne ta gorutine działa do sanego konca,
//biorac wszystkie liczby ze starego channela, ktory otrzymuje je
//od generatora

//pozniej nastepuje przepisanie zmiennych, stary channel staje 
//sie nowym

//to znaczy, że pierwszy element ktory trafi do 
//starego channela w kolejnej iteracji
// będzie pierwsza (najmniejsza liczba), ktora przejdzie filtr 
//dwojkowy - bedzie to 3, a nastepnie trafia tam wszystkie
//liczby nieparzyste

//przechodzac do kolejnej iteracji, tworzymy nowy channel, który
//filtruje liczby podzielne przez 3 i po podmiance
// w kolejnej iteracji stary channel będzie otrzymywać
// liczby niepodzielne przez 2 i 3, z których pierwszą będzie 5

// zostanie stworzony nowy filtr, który usuwa liczby podzielne 
//przez 5, itd...