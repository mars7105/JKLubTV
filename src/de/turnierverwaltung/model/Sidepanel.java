package de.turnierverwaltung.model;

public class Sidepanel {
	private String header;
	private String body;
	private int idSidepanel;

	public Sidepanel(String header, String body, int idSidepanel) {
		this.header = header;
		this.body = body;
		this.idSidepanel = idSidepanel;
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

}
