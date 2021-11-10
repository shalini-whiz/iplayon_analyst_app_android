package in.iplayon.analyst.modal;

import java.io.Serializable;

public class EventFee implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String tournamentId;
	private String[] events;
	private String[] eventFees;
	private String[] singleEvents;
	private String[] singleEventFees;
	private String[] teamEvents;
	private String[]  teamEventFees;
	private String[]  teamEventFeeStructure;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String[] getEvents() {
		return events;
	}

	public void setEvents(String[] events) {
		this.events = events;
	}

	public String[] getEventFees() {
		return eventFees;
	}

	public void setEventFees(String[] eventFees) {
		this.eventFees = eventFees;
	}

	public String[] getSingleEvents() {
		return singleEvents;
	}

	public void setSingleEvents(String[] singleEvents) {
		this.singleEvents = singleEvents;
	}

	public String[] getSingleEventFees() {
		return singleEventFees;
	}

	public void setSingleEventFees(String[] singleEventFees) {
		this.singleEventFees = singleEventFees;
	}

	public String[]  getTeamEvents() {
		return teamEvents;
	}

	public void setTeamEvents(String[]  teamEvents) {
		this.teamEvents = teamEvents;
	}

	public String[]  getTeamEventFees() {
		return teamEventFees;
	}

	public void setTeamEventFees(String[]  teamEventFees) {
		this.teamEventFees = teamEventFees;
	}

	public String[]  getTeamEventFeeStructure() {
		return teamEventFeeStructure;
	}

	public void setTeamEventFeeStructure(String[]  teamEventFeeStructure) {
		this.teamEventFeeStructure = teamEventFeeStructure;
	}
}
