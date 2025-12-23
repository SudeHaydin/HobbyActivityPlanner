package msku.ceng3545.hobbyplanner.models;

public class EventModel {
    private String title;
    private String details;
    private  String participantStatus;
    public EventModel(String title,String details,String participantStatus){
        this.title=title;
        this.details=details;
        this.participantStatus=participantStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getParticipantStatus() {
        return participantStatus;
    }
}
