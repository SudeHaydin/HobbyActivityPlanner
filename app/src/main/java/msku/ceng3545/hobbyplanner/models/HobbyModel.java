//SUDE HAYDIN

package msku.ceng3545.hobbyplanner.models;

public class HobbyModel {
    private String name;
    private int percent; // Adaptördeki isimle aynı yaptık (progress -> percent)

    public HobbyModel(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    // Adaptör bu ismi arıyor: getPercent
    public int getPercent() {
        return percent;
    }

    // Tıklayınca arttırmak için BU METOT ŞART! (Setter)
    public void setPercent(int percent) {
        this.percent = percent;
    }
}