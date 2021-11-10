package in.iplayon.analyst.modal;

import java.util.ArrayList;

public class ShotInfo {
	
	private String shotHand;
	private String shotDestination;
	private String shotPlayed;
	private String shotWin;
	private String shotLoss;
	private String shotEfficiency;
	private String winShot;
	private String winDestination;
	private String combinedShotKey;
	private String totalShotPlayed;
	private ArrayList<ShotAnalyzer> shotAnalyzerList;
	
	
	
	public ArrayList<ShotAnalyzer> getShotAnalyzerList() {
		return shotAnalyzerList;
	}
	public void setShotAnalyzerList(ArrayList<ShotAnalyzer> shotAnalyzerList) {
		this.shotAnalyzerList = shotAnalyzerList;
	}
	public String getTotalShotPlayed() {
		return totalShotPlayed;
	}
	public void setTotalShotPlayed(String totalShotPlayed) {
		this.totalShotPlayed = totalShotPlayed;
	}
	public String getCombinedShotKey() {
		return combinedShotKey;
	}
	public void setCombinedShotKey(String combinedShotKey) {
		this.combinedShotKey = combinedShotKey;
	}
	public String getWinShot() {
		return winShot;
	}
	public void setWinShot(String winShot) {
		this.winShot = winShot;
	}
	public String getWinDestination() {
		return winDestination;
	}
	public void setWinDestination(String winDestination) {
		this.winDestination = winDestination;
	}
	public String getShotHand() {
		return shotHand;
	}
	public void setShotHand(String shotHand) {
		this.shotHand = shotHand;
	}
	public String getShotDestination() {
		return shotDestination;
	}
	public String getShotPlayed() {
		return shotPlayed;
	}
	public void setShotDestination(String shotDestination) {
		this.shotDestination = shotDestination;
	}
	public String win1() {
		return shotPlayed;
	}
	public void setShotPlayed(String shotPlayed) {
		this.shotPlayed = shotPlayed;
	}
	public String getShotWin() {
		return shotWin;
	}
	public void setShotWin(String shotWin) {
		this.shotWin = shotWin;
	}
	public String getShotLoss() {
		return shotLoss;
	}
	public void setShotLoss(String shotLoss) {
		this.shotLoss = shotLoss;
	}
	public String getShotEfficiency() {
		return shotEfficiency;
	}
	public void setShotEfficiency(String shotEfficiency) {
		this.shotEfficiency = shotEfficiency;
	}
	

}
