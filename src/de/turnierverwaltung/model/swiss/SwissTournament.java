package de.turnierverwaltung.model.swiss;

import de.turnierverwaltung.model.Tournament;

public class SwissTournament extends Tournament {
	

	public SwissTournament(int turnierId, String turnierName, String startDatum, String endDatum, Boolean onlyTables,
			Boolean noDWZCalc, Boolean noFolgeDWZCalc, Boolean sortMeetingTable) {
		super(turnierId, turnierName, startDatum, endDatum, onlyTables, noDWZCalc, noFolgeDWZCalc, sortMeetingTable);

	}

	public SwissTournament(Boolean onlyTables, Boolean noDWZCalc, Boolean noFolgeDWZCalc, Boolean sortMeetingTable) {
		super(onlyTables, noDWZCalc, noFolgeDWZCalc, sortMeetingTable);
		// TODO Auto-generated constructor stub
	}

	public void createNewSwissTournament() {

	}
}
