package in.iplayon.analyst.modal;

public class TeamPlayer {

    //{"_id":"2sygYxorgqAjNku7T","userId":"9ABbR34M5d5tDNFzp",
      //      "userName":"Suhas","dateOfBirth":"2003-05-01T00:00:00.000Z"}

    private String _id;
    private String userId;
    private String userName;
    private String dateOfBirth;

    public TeamPlayer(){

    }

    public TeamPlayer(String userId,String userName)
    {
        this.userId = userId;
        this.userName = userName;
    }
    public TeamPlayer(String userId,Boolean check)
    {
        this.userId = userId;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean equals(Object o) {
        if (!(o instanceof TeamPlayer)) {
            return false;
        }
        TeamPlayer other = (TeamPlayer) o;
        if(userId != null && userName != null)
            return (other.userId.equalsIgnoreCase(userId) && other.userName.equalsIgnoreCase(userName));
        else if(_id != null && userName != null)
            return (other._id.equalsIgnoreCase(_id) && other.userName.equalsIgnoreCase(userName));
        else if(_id != null)
            return _id.equalsIgnoreCase(other.get_id());
        else if(userId != null)
            return userId.equalsIgnoreCase(other.getUserId());
        else if(userName != null)
            return userName.equalsIgnoreCase(other.getUserName());

        return false;
    }

    @Override
    public String toString() {
        return this.userName;
    }


    public int hashCode() {
        return userName.hashCode();
    }

}
