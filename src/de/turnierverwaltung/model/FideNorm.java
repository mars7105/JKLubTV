package de.turnierverwaltung.model;

public class FideNorm {
	private String[] fideNorm;

	public FideNorm() {
		super();
		fideNorm = new String[9];
		fideNorm[0] = "";
		fideNorm[1] = "WCM";
		fideNorm[2] = "CM";
		fideNorm[3] = "WFM";
		fideNorm[4] = "FM";
		fideNorm[5] = "WIM";
		fideNorm[6] = "WGM";
		fideNorm[7] = "IM";
		fideNorm[8] = "GM";
	}

	public String[] getFideNorm() {
		return fideNorm;
	}

	public void setFideNorm(String[] fideNorm) {
		this.fideNorm = fideNorm;
	}

}
