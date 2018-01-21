package de.turnierverwaltung.model;

import java.util.ArrayList;

public class TournamentReportFile {
	private ArrayList<String> reportString;
	private final Group group;
	private final Tournament tournament;

	public TournamentReportFile(final Tournament tournament, final Group group) {
		this.tournament = tournament;
		this.group = group;
		reportString = new ArrayList<String>();
		reportString.add("012 " + this.tournament.getTurnierName());
		reportString.add("022 " + "Hamburg");// muss noch implementirt werden
		reportString.add("032 " + "FIDE");
		reportString.add("042 " + this.tournament.getStartDatumTRF());
		reportString.add("052 " + this.tournament.getEndDatumTRF());
		reportString.add("062 " + this.group.getSpielerAnzahl());
		reportString.add("072 " + this.group.getRatedPlayersCount());
		reportString.add("082 " + "0");
		reportString.add("092 " + "Round Robin Tournament");
		reportString.add("102 " + "Auto-Generator");// Chief Arbiter
		// reportString.add("112 " + "Auto-Generator");//Deputy Chief Arbiter
		// reportString.add("122 " + "");//Allotted times per moves/game
		// reportString.add("132 " + "");//Dates of the round

	}

	public ArrayList<String> getReportString() {
		return reportString;
	}

	public void setReportString(final ArrayList<String> reportString) {
		this.reportString = reportString;
	}

}
