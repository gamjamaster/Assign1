import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String csvFile = "Article.csv"; // CSV file path

        List<Article> arrayList = new ArrayList<>();
        List<Article> linkedList = new LinkedList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                // Skip the header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // CSV -> Article
                Article article = new Article(
                        Integer.parseInt(line[0]), // id
                        line[1],                   // title
                        line[2],                   // abstractText
                        Integer.parseInt(line[3]), // computer
                        Integer.parseInt(line[4]), // physics
                        Integer.parseInt(line[5]), // mathematics
                        Integer.parseInt(line[6]), // statistics
                        Integer.parseInt(line[7]), // quantitativeBiology
                        Integer.parseInt(line[8])  // quantitativeFinance
                );

                // ArrayList와 LinkedList에 추가
                arrayList.add(article);
                linkedList.add(article);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        // Test
        // System.out.println("ArrayList:");
        // for (Article a : arrayList) {
        //     System.out.println(a);
        // }

        System.out.println("\nLinkedList:");
        for (Article a : linkedList) {
            System.out.println(a);
        }
    }
}
