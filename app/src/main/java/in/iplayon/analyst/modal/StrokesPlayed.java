package in.iplayon.analyst.modal;

/**
 * Created by shalinibr on 11/29/17.
 */

public class StrokesPlayed {

   // {"strokePlayed":"ExG5ebMvqM9nNRMxy","strokeHand":"BH-BF","strokeDestination":"FHC","previousDestination":"FHC"}
    private String strokePlayed;
    private String strokeHand;
    private String strokeDestination;
    private String previousDestination;

    public String getStrokePlayed() {
        return strokePlayed;
    }

    public void setStrokePlayed(String strokePlayed) {
        this.strokePlayed = strokePlayed;
    }

    public String getStrokeHand() {
        return strokeHand;
    }

    public void setStrokeHand(String strokeHand) {
        this.strokeHand = strokeHand;
    }

    public String getStrokeDestination() {
        return strokeDestination;
    }

    public void setStrokeDestination(String strokeDestination) {
        this.strokeDestination = strokeDestination;
    }

    public String getPreviousDestination() {
        return previousDestination;
    }

    public void setPreviousDestination(String previousDestination) {
        this.previousDestination = previousDestination;
    }
}
