package in.iplayon.analyst.modal;

import java.util.ArrayList;

/**
 * Created by shalinibr on 11/29/17.
 */


public class SequenceDetail {

    /*

    {"_id":"XoMxHAwvPTsjb6qg6",
            "player1Name":"player60","player1Id":"ExG5ebMvqM9nNRMxy",
            "player2Name":"player3","player2Id":"Z3e5bHxjTjFEJEsts"
            ,"playerA":{"set":"5","points":"8"},"playerB":{"set":"2","points":"3"},
        "matchDate":"2017-11-23T18:30:00.000Z","rallyType":"complete",

            "strokesPlayed":[{"strokePlayed":"Z3e5bHxjTjFEJEsts","strokeHand":"FH-SSS","strokeDestination":"FHC"},
        {"strokePlayed":"ExG5ebMvqM9nNRMxy","strokeHand":"BH-BF","strokeDestination":"FHC","previousDestination":"FHC"},

        {"strokePlayed":"Z3e5bHxjTjFEJEsts","strokeHand":"FH-BLK","strokeDestination":"FHC","previousDestination":"FHC"},
        {"strokePlayed":"ExG5ebMvqM9nNRMxy","strokeHand":"BH-D","strokeDestination":"CL","previousDestination":"FHC"},
        {"strokePlayed":"Z3e5bHxjTjFEJEsts","strokeHand":"FH-D","strokeDestination":"CS","previousDestination":"CL"},
        {"strokePlayed":"ExG5ebMvqM9nNRMxy","strokeHand":"FH-LOB","strokeDestination":"FHC","previousDestination":"CS"}],
        "sequenceRecordDate":"2017-11-24T11:09:26.302Z","serviceBy":"Z3e5bHxjTjFEJEsts","winner":"ExG5ebMvqM9nNRMxy"}

    */


    private String _id;
    private String player1Name;
    private String player2Name;
    private String player1Id;
    private String player2Id;
    private ScorePoint playerA;
    private ScorePoint playerB;
    private String matchDate;
    private String rallyType;
    private ArrayList<StrokesPlayed> strokesPlayed;
    private String sequenceRecordDate;
    private String serviceBy;
    private String winner;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public ScorePoint getPlayerA() {
        return playerA;
    }

    public void setPlayerA(ScorePoint playerA) {
        this.playerA = playerA;
    }

    public ScorePoint getPlayerB() {
        return playerB;
    }

    public void setPlayerB(ScorePoint playerB) {
        this.playerB = playerB;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getRallyType() {
        return rallyType;
    }

    public void setRallyType(String rallyType) {
        this.rallyType = rallyType;
    }

    public ArrayList<StrokesPlayed> getStrokesPlayed() {
        return strokesPlayed;
    }

    public void setStrokesPlayed(ArrayList<StrokesPlayed> strokesPlayed) {
        this.strokesPlayed = strokesPlayed;
    }

    public String getSequenceRecordDate() {
        return sequenceRecordDate;
    }

    public void setSequenceRecordDate(String sequenceRecordDate) {
        this.sequenceRecordDate = sequenceRecordDate;
    }

    public String getServiceBy() {
        return serviceBy;
    }

    public void setServiceBy(String serviceBy) {
        this.serviceBy = serviceBy;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
