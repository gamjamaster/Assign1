import java.util.*;

public class Main {
    public static void main(String[] args) {
        String csvFile = "Article.csv"; // CSV file path

        ArticleCsvLoader loader = new ArticleCsvLoader(csvFile);

        List<Article> arrayList = loader.loadToArrayList();
        List<Article> linkedList = loader.loadToLinkedList();

        int id[] = {1,2,3,4,5};

        for (int i : id) {
            Article target = new Article(i, "", "", 0,0,0,0,0,0);

            // (1) 순차 탐색 스레드 생성 + 실행
            Thread t1 = new Thread(new Sequential_Search<>(arrayList, target));
            Thread t2 = new Thread(new Sequential_Search<>(linkedList, target));

            t1.start();
            t2.start();
        }
    }
        
        // for (int i : id) {
        //     Article target = new Article(i, "", "", 0,0,0,0,0,0);
        //     int idx = Sequential_Search.search(linkedList, target);
        //     if (idx != -1) {
        //         System.out.println("File with iSequentd " + i + " exists in index " + idx + ".");
        //     } else {
        //         System.out.println("Cannot find the file with id: " + i);
        //     }
        // }

        
}
