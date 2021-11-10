package in.iplayon.analyst.modal;

public class Rally {
	
	//"strokesPlayed":1,"win":1,"loss":0,"strokeKey":"FH-C-FHC","efficiency":100}
	//{"played":7,"winCount":7,"lossCount":0,"sequenceLen":3,"efficiency":100}
	private String sequenceLen;
	private String played;
	private String winCount;
	private String lossCount;
	private String efficiency;
	
	
	public String getSequenceLen() {
		return sequenceLen;
	}
	public void setSequenceLen(String sequenceLen) {
		this.sequenceLen = sequenceLen;
	}
	public String getShotsPlayed() {
		return played;
	}
	public void setShotsPlayed(String shotsPlayed) {
		this.played = shotsPlayed;
	}
	public String getWinCount() {
		return winCount;
	}
	public void setWinCount(String winCount) {
		this.winCount = winCount;
	}
	public String getLossCount() {
		return lossCount;
	}
	public void setLossCount(String lossCount) {
		this.lossCount = lossCount;
	}
	public String getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}
}
