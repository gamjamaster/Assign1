import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArticleCsvLoader {

    private final String csvFile;

    public ArticleCsvLoader(String csvFile) {
        this.csvFile = csvFile;
    }

    public List<Article> loadToArrayList() {
        return load(new ArrayList<>());
    }

    public List<Article> loadToLinkedList() {
        return load(new LinkedList<>());
    }

    // 공통 로직
    private List<Article> load(List<Article> list) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

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

                list.add(article);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return list;
    }
}
