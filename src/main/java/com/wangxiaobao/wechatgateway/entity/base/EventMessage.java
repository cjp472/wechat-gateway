package com.wangxiaobao.wechatgateway.entity.base;

public class EventMessage extends BaseWechatMessage {
	private String event;
	private String eventKey;
	private String Ticket;
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventMessage [event=");
		builder.append(event);
		builder.append(", eventKey=");
		builder.append(eventKey);
		builder.append(", Ticket=");
		builder.append(Ticket);
		builder.append("]");
		return builder.toString();
	}
}
