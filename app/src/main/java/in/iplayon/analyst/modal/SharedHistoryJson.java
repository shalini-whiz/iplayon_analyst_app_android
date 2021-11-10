package in.iplayon.analyst.modal;

public class SharedHistoryJson {

    /*"_id":"7aBEmDy9N7afxG2Py","sharedId":"Y2wNASGdpofeFaC7M",
    "player1Name":"Divya","player2Name":"Charvi","sequenceCount":1,

        "playerList":[{"userId":"qqRaBcneGr657FJZG","playerName":"Divya"},
    {"userId":"xEY8CWhYALGyj7X8E","playerName":"Charvi"}],
            "sequenceSharedDate":"2017-10-06T08:06:17.372Z",
            "sharedUser":"player2"}
            */


    private String _id;
    private String sharedId;
    private String player1Name;
    private String player2Name;
    private int sequenceCount;
    private String sequenceSharedDate;
    private String sharedUser;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSharedId() {
        return sharedId;
    }

    public void setSharedId(String sharedId) {
        this.sharedId = sharedId;
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

    public int getSequenceCount() {
        return sequenceCount;
    }

    public void setSequenceCount(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }

    public String getSequenceSharedDate() {
        return sequenceSharedDate;
    }

    public void setSequenceSharedDate(String sequenceSharedDate) {
        this.sequenceSharedDate = sequenceSharedDate;
    }

    public String getSharedUser() {
        return sharedUser;
    }

    public void setSharedUser(String sharedUser) {
        this.sharedUser = sharedUser;
    }
}
