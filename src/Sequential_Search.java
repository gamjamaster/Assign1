import java.util.Objects;
import java.util.List;

class Sequential_Search {
    public static <T> int search(List<T> list, T x) {
        for (int i = 0; i < list.size(); i++) { 
            if (Objects.equals(list.get(i), x)) // internally call list.get(i).equals(x)
                return i;
        }
        return -1;
    }
}