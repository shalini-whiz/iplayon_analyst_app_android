package in.iplayon.analyst.modal;

import java.io.Serializable;
import java.util.ArrayList;

public class Tournament implements Serializable{
	private String _id;
	private String eventName;
	private String subscriptionTypeHyper = "0";
	private String hyperLinkValue = "";
	private String domainName;
    private String eventStartDate;
	private String eventEndDate;
	private ArrayList<String> drawEvents;
	private String tournamentType;


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


	public Boolean getSubscribeBoolean() {
		return subscribeBoolean;
	}

	public ArrayList<String> getDrawsEvent() {
		return drawEvents;
	}

	public void setDrawsEvent(ArrayList<String> drawsEvent) {
		this.drawEvents = drawsEvent;
	}

	public void setSubscribeBoolean(Boolean subscribeBoolean) {
		this.subscribeBoolean = subscribeBoolean;

	}

	private Boolean subscribeBoolean = false;


	public String getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getTournamentType() {
		return tournamentType;
	}

	public void setTournamentType(String tournamentType) {
		this.tournamentType = tournamentType;
	}



	public String getSubscriptionTypeHyper() {
		return subscriptionTypeHyper;
	}
	public void setSubscriptionTypeHyper(String subscriptionTypeHyper) {
		this.subscriptionTypeHyper = subscriptionTypeHyper;
	}
	public String getHyperLinkValue() {
		return hyperLinkValue;
	}
	public void setHyperLinkValue(String hyperLinkValue) {
		this.hyperLinkValue = hyperLinkValue;
	}

	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	

}
