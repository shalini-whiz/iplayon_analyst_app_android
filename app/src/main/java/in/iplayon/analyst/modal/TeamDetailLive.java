package in.iplayon.analyst.modal;

import java.util.ArrayList;

public class TeamDetailLive {


    private String matchStatus;
    private String matchTitle;
    private String matchType;
    private String teamAPlayerAName;
    private String teamAPlayerAID;
    private String teamAPlayerBName;
    private String teamAPlayerBID;
    private String teamBPlayerAName;
    private String teamBPlayerAID;
    private String teamBPlayerBName;
    private String teamBPlayerBID;
    private ArrayList<String> scoresA;
    private ArrayList<String> scoresB;
    private Integer matchNumber;


    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getTeamAPlayerAName() {
        return teamAPlayerAName;
    }

    public void setTeamAPlayerAName(String teamAPlayerAName) {
        this.teamAPlayerAName = teamAPlayerAName;
    }

    public String getTeamAPlayerAID() {
        return teamAPlayerAID;
    }

    public void setTeamAPlayerAID(String teamAPlayerAID) {
        this.teamAPlayerAID = teamAPlayerAID;
    }

    public String getTeamAPlayerBName() {
        return teamAPlayerBName;
    }

    public void setTeamAPlayerBName(String teamAPlayerBName) {
        this.teamAPlayerBName = teamAPlayerBName;
    }

    public String getTeamAPlayerBID() {
        return teamAPlayerBID;
    }

    public void setTeamAPlayerBID(String teamAPlayerBID) {
        this.teamAPlayerBID = teamAPlayerBID;
    }

    public String getTeamBPlayerAName() {
        return teamBPlayerAName;
    }

    public void setTeamBPlayerAName(String teamBPlayerAName) {
        this.teamBPlayerAName = teamBPlayerAName;
    }

    public String getTeamBPlayerAID() {
        return teamBPlayerAID;
    }

    public void setTeamBPlayerAID(String teamBPlayerAID) {
        this.teamBPlayerAID = teamBPlayerAID;
    }

    public String getTeamBPlayerBName() {
        return teamBPlayerBName;
    }

    public void setTeamBPlayerBName(String teamBPlayerBName) {
        this.teamBPlayerBName = teamBPlayerBName;
    }

    public String getTeamBPlayerBID() {
        return teamBPlayerBID;
    }

    public void setTeamBPlayerBID(String teamBPlayerBID) {
        this.teamBPlayerBID = teamBPlayerBID;
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
}
