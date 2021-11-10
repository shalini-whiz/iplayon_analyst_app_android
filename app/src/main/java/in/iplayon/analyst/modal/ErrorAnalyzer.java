package in.iplayon.analyst.modal;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class ErrorAnalyzer {
	
	private String fromDestination;
	private String strokeHand;
	private String shotsPlayed;
	private String totalShotsPlayed;
	private ArrayList<JSONArray> dataSet;
	public String getFromDestination() {
		return fromDestination;
	}
	public void setFromDestination(String fromDestination) {
		this.fromDestination = fromDestination;
	}
	public String getStrokeHand() {
		return strokeHand;
	}
	public void setStrokeHand(String strokeHand) {
		this.strokeHand = strokeHand;
	}
	public String getShotsPlayed() {
		return shotsPlayed;
	}
	public void setShotsPlayed(String shotsPlayed) {
		this.shotsPlayed = shotsPlayed;
	}
	public String getTotalShotsPlayed() {
		return totalShotsPlayed;
	}
	public void setTotalShotsPlayed(String totalShotsPlayed) {
		this.totalShotsPlayed = totalShotsPlayed;
	}
	public ArrayList<JSONArray> getDataSet() {
		return dataSet;
	}
	public void setDataSet(ArrayList<JSONArray> dataSet) {
		this.dataSet = dataSet;
	}
	

	
}
