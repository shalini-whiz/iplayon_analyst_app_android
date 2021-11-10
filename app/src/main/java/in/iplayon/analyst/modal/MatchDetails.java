package in.iplayon.analyst.modal;

import java.util.ArrayList;

public class MatchDetails {


   // "matchNumber":2,"roundNumber":1,"playerAID":"jZ4Er7Kzn6Se2Pod2",
    // "playerBID":"4W8sdX4LFjATyNpBY",
    // "status":"yetToPlay","playerAWin":0,"playerBWin":0,
    // "scoresA":["0","0","0","0","0","0","0"],"scoresB":["0","0","0","0","0","0","0"],
    // "playerAName":"CHIRUVOLU VENKATA SOURYA","playerBName":"CHINTHALA CHARAN SRI SAI"

    private String tournamentId;
    private String eventName;
    private Integer matchNumber;
    private Integer roundNumber;
    private String roundName;
    private String playerAID;
    private String playerBID;

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    private String status;
    private Integer playerAWin;
    private Integer playerBWin;
    private ArrayList<String> scoresA;
    private ArrayList<String> scoresB;
    private String playerAName;
    private String playerBName;
    private Boolean liveStatus;
    private ArrayList<TeamDetailLive> teamMatchDetails = new ArrayList<>();

    public ArrayList<TeamDetailLive> getTeamMatchDetails() {
        return teamMatchDetails;
    }

    public void setTeamMatchDetails(ArrayList<TeamDetailLive> teamMatchDetails) {
        this.teamMatchDetails = teamMatchDetails;
    }

    public Boolean getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Boolean liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getPlayerAID() {
        return playerAID;
    }

    public void setPlayerAID(String playerAID) {
        this.playerAID = playerAID;
    }

    public String getPlayerBID() {
        return playerBID;
    }

    public void setPlayerBID(String playerBID) {
        this.playerBID = playerBID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPlayerAWin() {
        return playerAWin;
    }

    public void setPlayerAWin(Integer playerAWin) {
        this.playerAWin = playerAWin;
    }

    public Integer getPlayerBWin() {
        return playerBWin;
    }

    public void setPlayerBWin(Integer playerBWin) {
        this.playerBWin = playerBWin;
    }

    public ArrayList<String> getScoresA() {
        return scoresA;
    }

    public void setScoresA(ArrayList<String> scoresA) {
        this.scoresA = scoresA;
    }

    public ArrayList<String> getScoresB() {
        return scoresB;
    }

    public void setScoresB(ArrayList<String> scoresB) {
        this.scoresB = scoresB;
    }

    public String getPlayerAName() {
        return playerAName;
    }

    public void setPlayerAName(String playerAName) {
        this.playerAName = playerAName;
    }

    public String getPlayerBName() {
        return playerBName;
    }

    public void setPlayerBName(String playerBName) {
        this.playerBName = playerBName;
    }
}
