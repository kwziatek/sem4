#include <iostream>
#include <vector>
using namespace std;


int main() {

    //INSERTION SORT
    int num;
    cin >> num;
    int* ptr = new int[num];

    for(int i = 0; i < num; i++) {
        cin >> *(ptr + i);
    }

    for(int i = 0; i < num; i++) {
        for(int j = i; j > 0; j--) {
            if(ptr[j] < ptr[j - 1]) {
                swap(ptr[j], ptr[j - 1]);
            }
        }
    }

    for(int i = 0; i < num; i++) {
        cout << *(ptr + i) << " ";
    }

    delete[] ptr;
    ptr = nullptr;
    return 0;
}

