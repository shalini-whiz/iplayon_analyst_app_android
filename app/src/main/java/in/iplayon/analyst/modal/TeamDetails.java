package in.iplayon.analyst.modal;

import java.util.ArrayList;

public class TeamDetails {

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    private String matchStatus;
    private String matchName;
    private String matchType;
    private String t1p1Name;
    private String t1p1Id;
    private String t1p2Name;
    private String t1p2Id;
    private String t2p1Name;
    private String t2p1Id;
    private String t2p2Name;
    private String t2p2Id;
    private String type = "";

    public String getT1p1Id() {
        return t1p1Id;
    }

    public void setT1p1Id(String t1p1Id) {
        this.t1p1Id = t1p1Id;
    }

    public String getT1p2Id() {
        return t1p2Id;
    }

    public void setT1p2Id(String t1p2Id) {
        this.t1p2Id = t1p2Id;
    }

    public String getT2p1Id() {
        return t2p1Id;
    }

    public void setT2p1Id(String t2p1Id) {
        this.t2p1Id = t2p1Id;
    }

    public String getT2p2Id() {
        return t2p2Id;
    }

    public void setT2p2Id(String t2p2Id) {
        this.t2p2Id = t2p2Id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getT1p2Name() {
        return t1p2Name;
    }

    public void setT1p2Name(String t1p2Name) {
        this.t1p2Name = t1p2Name;
    }

    public String getT2p1Name() {
        return t2p1Name;
    }

    public void setT2p1Name(String t2p1Name) {
        this.t2p1Name = t2p1Name;
    }

    public String getT2p2Name() {
        return t2p2Name;
    }

    public void setT2p2Name(String t2p2Name) {
        this.t2p2Name = t2p2Name;
    }

    private ArrayList<Integer> setScoresA;
    private ArrayList<Integer> setScoresB;
    private String winnerName;
    private String winnerID;


    public String getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(String winnerID) {
        this.winnerID = winnerID;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getT1p1Name() {
        return t1p1Name;
    }

    public void setT1p1Name(String t1p1Name) {
        this.t1p1Name = t1p1Name;
    }



    public ArrayList<Integer> getSetScoresA() {
        return setScoresA;
    }

    public void setSetScoresA(ArrayList<Integer> setScoresA) {
        System.out.println("setScores A "+setScoresA.toString());
        this.setScoresA = setScoresA;
    }

    public ArrayList<Integer> getSetScoresB() {
        return setScoresB;
    }

    public void setSetScoresB(ArrayList<Integer> setScoresB) {
        this.setScoresB = setScoresB;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;


    }
}
