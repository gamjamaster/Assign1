import java.util.Objects;

public class Article implements Comparable<Article> {
    private int id;
    private String title;
    private String abstractText;
    private int computer;
    private int physics;
    private int mathematics;
    private int statistics;
    private int quantitativeBiology;
    private int quantitativeFinance;

    public Article(int id, String title, String abstractText, int computer, int physics, int mathematics, int statistics, int quantitativeBiology, int quantitativeFinance) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
        this.computer = computer;
        this.physics = physics;
        this.mathematics = mathematics;
        this.statistics = statistics;
        this.quantitativeBiology = quantitativeBiology;
        this.quantitativeFinance = quantitativeFinance;
    }

    @Override
    public boolean equals(Object o) {
        // if the article obejct and o are the same object, return true.
        if (this == o){
            return true; 
        }
        // if the object is being compare to null or any other objects of differnt types, return false.
        if (o == null || getClass() != o.getClass()){
            return false; 
        }
        // cast o to an article object.
        Article article = (Article) o;
        // return true if the article object and o have the same id, otherwise false.
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // create hash code with id
    }

    @Override
    public int compareTo(Article other) {
        // Compare by id (or change logic as needed)
        return Integer.compare(this.id, other.id);
    }
}