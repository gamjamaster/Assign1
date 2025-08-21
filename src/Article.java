public class Article {
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
    public String toString() {
        return id + ", " + title + ", " + abstractText + ", " + computer + ", " + physics + ", " + mathematics
                + ", " + statistics + ", " + quantitativeBiology + ", " + quantitativeFinance;
    }
}