import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
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

    private List<Article> load(List<Article> list) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFile))
                .withCSVParser(new RFC4180ParserBuilder().build())
                .build()) {

            String[] line;
            boolean isHeader = true;

            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                int id = Integer.parseInt(line[0].trim());
                String title = line[1].trim();
                String abstractText = line[2].trim();
                int computer = Integer.parseInt(line[3].trim());
                int physics = Integer.parseInt(line[4].trim());
                int mathematics = Integer.parseInt(line[5].trim());
                int statistics = Integer.parseInt(line[6].trim());
                int quantitativeBiology = Integer.parseInt(line[7].trim());
                int quantitativeFinance = Integer.parseInt(line[8].trim());

                Article article = new Article(
                        id,
                        title,
                        abstractText,
                        computer,
                        physics,
                        mathematics,
                        statistics,
                        quantitativeBiology,
                        quantitativeFinance
                );

                list.add(article);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        
        System.out.println("Total of " + list.size() + " lines has been loaded.");
        return list;
    }
}