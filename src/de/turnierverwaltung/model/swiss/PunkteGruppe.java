package de.turnierverwaltung.model.swiss;

import java.util.ArrayList;

public class PunkteGruppe {
	private ArrayList<SwissPlayer> swissPlayers;
	private UnterGruppe s1;
	private UnterGruppe s2;

	public PunkteGruppe() {
		super();
		swissPlayers = new ArrayList<SwissPlayer>();
		s1 = new UnterGruppe();
		s2 = new UnterGruppe();
	}

	public PunkteGruppe(ArrayList<SwissPlayer> swissPlayers) {
		super();
		this.swissPlayers = swissPlayers;
		s1 = new UnterGruppe();
		s2 = new UnterGruppe();
	}

	public ArrayList<SwissPlayer> getSwissPlayers() {
		return swissPlayers;
	}

	public void setSwissPlayers(ArrayList<SwissPlayer> swissPlayers) {
		this.swissPlayers = swissPlayers;
	}

	public UnterGruppe getS1() {
		return s1;
	}

	public void setS1(UnterGruppe s1) {
		this.s1 = s1;
	}

	public UnterGruppe getS2() {
		return s2;
	}

	public void setS2(UnterGruppe s2) {
		this.s2 = s2;
	}

}
