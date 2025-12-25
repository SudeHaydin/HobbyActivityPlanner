package msku.ceng3545.hobbyplanner.models;

public class HobbyModel {
    private String name;
    private int progress;

    public HobbyModel(String name, int progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() { return name; }
    public int getProgress() { return progress; }
}