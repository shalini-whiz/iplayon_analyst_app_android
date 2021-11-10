package in.iplayon.analyst.modal;

public class EventDetail {

  //  "_id":"BDkyKkppCk8rXDS3L","eventName":"Cadet Boy's Singles","projectType":1,"tournamentId":"oR38jsdbEdrCH8tg8"}
    private String _id;
    private String eventName;
    private String projectType;
    private String tournamentId;


    public EventDetail(String eventName)
    {
        this.eventName = eventName;
    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public String toString() {
        return this.eventName;
    }
}
