package in.iplayon.analyst.modal;

/**
 * Created by shalinibr on 12/5/17.
 */

public class MatchRecord {
    /*
    {"matchNumber":1,"roundNumber":1,"status":"completed","isBlank":false,"roundName":"QF",
            "status2":"","playerStatusID":"","playerPropogateID":"","getStatusColorA":"ip_input_box_type_pName",
            "getStatusColorB":"ip_input_box_type_pNameLost",
            "winner":"KRATIK SHIVRAJ GUPTA",

            "players":{"playerA":"KRATIK SHIVRAJ GUPTA()","playerB":"PLAYER2011(aca)"},
        "playersID":{"playerAId":"GcyRDzBcbgF4pPNBT","playerBId":"6NCN3KPpbYb2C2Qvp"},
        "setWins":{"playerA":0,"playerB":0},"nextMatchNumber":5,"nextSlot":"A",
            "scores":{"setScoresA":["6","6","6","0","0","0","0"],"setScoresB":["1","1","1","0","0","0","0"]},
        "winnerID":"GcyRDzBcbgF4pPNBT",
            "completedscores":["1","1","1","",""],

            "playerA":"KRATIK SHIVRAJ GUPTA","playerB":"PLAYER2011"*/

    private int matchNumber;
    private int roundNumber;
    private String status;
    private String roundName;
    private String playerA;
    private String playerB;
    private TeamJson teams;
    private String winner;
    private SetWin setWins;
    private Scores scores;
    private String winnerID;
    private PlayerIDJson playersID;
    private TeamIDJson teamsID;

    public TeamIDJson getTeamsID() {
        return teamsID;
    }

    public void setTeamsID(TeamIDJson teamsID) {
        this.teamsID = teamsID;
    }

    public PlayerIDJson getPlayersID() {
        return playersID;
    }

    public void setPlayersID(PlayerIDJson playersID) {
        this.playersID = playersID;
    }

    public String getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(String winnerID) {
        this.winnerID = winnerID;
    }

    public TeamJson getTeams() {
        return teams;
    }

    public void setTeams(TeamJson teams) {
        this.teams = teams;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public void setPlayerB(String playerB) {
        this.playerB = playerB;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public SetWin getSetWins() {
        return setWins;
    }

    public void setSetWins(SetWin setWins) {
        this.setWins = setWins;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }
}
