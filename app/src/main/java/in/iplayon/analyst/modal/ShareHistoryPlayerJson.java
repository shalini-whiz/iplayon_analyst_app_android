package in.iplayon.analyst.modal;

public class ShareHistoryPlayerJson {
	public String playerName;
	public String userId;
	
	public ShareHistoryPlayerJson(String userId)
	{
		this.userId = userId;
	}
	
	public ShareHistoryPlayerJson(String playerName, String userId) {
		this.playerName = playerName;
		this.userId = userId;
		// TODO Auto-generated constructor stub
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof ShareHistoryPlayerJson)) {
		    return false;
		}
		ShareHistoryPlayerJson other = (ShareHistoryPlayerJson) o;
		return userId.equalsIgnoreCase(other.getUserId());
	}
	 
	@Override
    public String toString() {
        return this.playerName;
    }
	public int hashCode() {
		  return playerName.hashCode();
		}
}
