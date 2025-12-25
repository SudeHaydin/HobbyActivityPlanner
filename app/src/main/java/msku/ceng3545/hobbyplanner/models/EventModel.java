package msku.ceng3545.hobbyplanner.models;

public class EventModel {
    private String title;
    private String details;
    private String participantStatus;
    private String category;

    public EventModel(String title, String details, String participantStatus, String category) {
        this.title = title;
        this.details = details;
        this.participantStatus = participantStatus;
        this.category = category;
    }

    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public String getParticipantStatus() { return participantStatus; }
    public String getCategory() { return category; } // Getter
}