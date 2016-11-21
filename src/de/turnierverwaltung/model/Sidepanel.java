package de.turnierverwaltung.model;

public class Sidepanel {
	private String header;
	private String body;
	private int idSidepanel;
	private int color;

	public Sidepanel(String header, String body, int idSidepanel, int color) {
		this.header = header;
		this.body = body;
		this.idSidepanel = idSidepanel;
		this.color = color;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getIdSidepanel() {
		return idSidepanel;
	}

	public void setIdSidepanel(int idSidepanel) {
		this.idSidepanel = idSidepanel;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
