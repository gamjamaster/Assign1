import java.util.*;

public class Main {
    public static void main(String[] args) {
        String csvFile = "ArticleS.csv"; // CSV file path

        ArticleCsvLoader loader = new ArticleCsvLoader(csvFile);

        List<Article> arrayList = loader.loadToArrayList();
        List<Article> linkedList = loader.loadToLinkedList();

        int id[] = {10581, 10681, 11220, 11000};

        for (int i : id) {
            Article target = new Article(i, "", "", 0,0,0,0,0,0);

            Thread t1 = new Thread(new Sequential_Search_ArrayList<>(arrayList, target));
            Thread t2 = new Thread(new Sequential_Search_LinkedList<>(linkedList, target));
            Thread t3 = new Thread(new Fibonacci_Search_ArrayList<>(arrayList, target));
            Thread t4 = new Thread(new Fibonacci_Search_LinkedList<>(linkedList, target));

            t1.start();
            t2.start();
            t3.start();
            t4.start();

            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
}
