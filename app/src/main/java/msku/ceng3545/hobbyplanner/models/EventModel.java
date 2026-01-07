package msku.ceng3545.hobbyplanner.models;
//MEHMET EKREM ERKAN
public class EventModel {
    private String title;
    private String details;
    private String category;

    // YENİ: Sayısal değerler
    private int current;
    private int max;

    // YENİ: Güncelleme yapmak için ID şart
    private String docId;

    public EventModel(String title, String details, String category, int current, int max) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.current = current;
        this.max = max;
    }

    // Getter ve Setter'lar
    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public String getCategory() { return category; }

    public int getCurrent() { return current; }
    public void setCurrent(int current) { this.current = current; } // Anlık güncelleme için lazım

    public int getMax() { return max; }

    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    // Ekranda "10 / 50" göstermek için yardımcı metod
    public String getStatusText() {
        return current + " / " + max;
    }
}