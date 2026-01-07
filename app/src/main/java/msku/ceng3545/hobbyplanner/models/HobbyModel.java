//SUDE HAYDIN

package msku.ceng3545.hobbyplanner.models;

public class HobbyModel {
    private String name;
    private int percent;

    public HobbyModel(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }


    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}