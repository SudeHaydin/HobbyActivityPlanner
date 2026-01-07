package msku.ceng3545.hobbyplanner.models;

public class EventModel {
    private String title;
    private String details;
    private String category;

    // YENİ: Sayısal değerler (Int)
    private int current;
    private int max;

    // YENİ: Firebase ID'si
    private String docId;

    // Kurucu Metot (Constructor)
    public EventModel(String title, String details, String category, int current, int max) {
        this.title = title;
        this.details = details;
        this.category = category;
        this.current = current;
        this.max = max;
    }

    // --- GETTER VE SETTER METOTLARI ---

    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public String getCategory() { return category; }

    // Sayı İşlemleri
    public int getCurrent() { return current; }
    public void setCurrent(int current) { this.current = current; } // Anlık güncelleme için şart

    public int getMax() { return max; }

    // ID İşlemleri (Hatayı çözen kısım burası)
    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    // Ekranda "10 / 50" göstermek için yardımcı metod
    public String getStatusText() {
        return current + " / " + max;
    }
}