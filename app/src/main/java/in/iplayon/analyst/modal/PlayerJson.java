package in.iplayon.analyst.modal;

import java.io.Serializable;

public class PlayerJson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String playerName;
	public String userId;

	public PlayerJson()
	{

	}
	public PlayerJson(String playerName)
	{
		this.playerName = playerName;
	}
	public PlayerJson(String userId, Boolean check)
	{
		this.userId = userId;
	}
	public PlayerJson(String playerName, String userId) {
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
		if (!(o instanceof PlayerJson)) {
		    return false;
		}
		PlayerJson other = (PlayerJson) o;
		if(playerName != null && userId != null)
			return (other.userId.equals(userId) && other.playerName == playerName); 
		else if(playerName != null)
			return playerName.equalsIgnoreCase(other.getPlayerName());
		else if(userId != null)
			return userId.equalsIgnoreCase(other.getUserId());
		 
		return false;
	}
	 
	@Override
    public String toString() {
        return this.playerName;
    }
	public int hashCode() {
		  return playerName.hashCode();
		}
}
