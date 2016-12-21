package de.turnierverwaltung.model.swiss;

public class Floater {
	private Boolean up;
	private Boolean down;

	public Floater(Boolean up, Boolean down) {
		super();
		this.up = up;
		this.down = down;
	}

	public Boolean getUp() {
		return up;
	}

	public void setUp(Boolean up) {
		this.up = up;
	}

	public Boolean getDown() {
		return down;
	}

	public void setDown(Boolean down) {
		this.down = down;
	}

}
