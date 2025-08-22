import java.io.*;
import java.util.List;

class Fibonacci_Search {

    // Returns index of x if present, else returns -1
    public static <T> int search(List<T> list, T x) {
        int n = list.size();

        // initialize first three fibonacci numbers
        int a =  0, b = 1, c = 1;
 
        // iterate while c is smaller than n
        // c stores the smallest Fibonacci 
        // number greater than or equal to n
        while (c < n) {
            a = b;
            b = c;
            c = a + b;
        }
 
        // marks the eliminated range from front
        int offset = -1;
 
        // while there are elements to be inspected
        // Note that we compare arr[a] with x. 
        // When c becomes 1, a becomes 08
        while (c > 1) {

            // check if a is a valid location
            int i = Math.min(offset + a, n - 1);
 
            // if x is greater than the value at index a,
            // cut the subarray array from offset to i 
            if (list[i] < x) {
                c = b;
                b = a;
                a = c - b;
                offset = i;
            }
 
            // else if x is greater than the value at 
            // index a,cut the subarray after i+1
            else if (list[i] > x) {
                c = a;
                b = b - a;
                a = c - b;
            }
 
            // else if element found, return index
            else
                return i;
        }
 
        // comparing the last element with x
        if (b != 0 && arr[offset + 1] == x)
            return offset + 1;
 
        // element not found, return -1
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 10, 40};
        int x = 10;
        System.out.println(search(arr, x));
    }
}