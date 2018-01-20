package de.turnierverwaltung.model;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
/**
 * Class Turnier
 */
public class Tournament {

	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int anzahlGruppen;
	private Group[] gruppe;
	private int turnierId;
	private Boolean onlyTables;
	private Boolean noDWZCalc;
	private Boolean noFolgeDWZCalc;
	private Boolean noELOCalc;
	private Boolean noFolgeELOCalc;
	private EventDate eventstartDate;
	private EventDate eventendDate;

	/**
	 *
	 * @param onlyTables
	 * @param noDWZCalc
	 * @param noFolgeDWZCalc
	 */
	public Tournament(final Boolean onlyTables, final Boolean noDWZCalc, final Boolean noFolgeDWZCalc,
			final Boolean noELOCalc, final Boolean noFolgeELOCalc) {
		turnierName = "";

		eventstartDate = new EventDate();
		startDatum = "";
		eventendDate = new EventDate();
		endDatum = "";
		turnierId = -1;
		this.onlyTables = onlyTables;
		this.noDWZCalc = noDWZCalc;
		this.noFolgeDWZCalc = noFolgeDWZCalc;
		this.noELOCalc = noELOCalc;
		this.noFolgeELOCalc = noFolgeELOCalc;
	}

	/**
	 *
	 * @param turnierId
	 * @param turnierName
	 * @param startDatum
	 * @param endDatum
	 * @param onlyTables
	 * @param noDWZCalc
	 * @param noFolgeDWZCalc
	 */
	public Tournament(final int turnierId, final String turnierName, final String startDatum, final String endDatum,
			final Boolean onlyTables, final Boolean noDWZCalc, final Boolean noFolgeDWZCalc, final Boolean noELOCalc,
			final Boolean noFolgeELOCalc) {
		super();
		this.turnierName = turnierName;

		eventstartDate = new EventDate(startDatum);
		this.startDatum = eventstartDate.getDateString();
		eventendDate = new EventDate(endDatum);
		this.endDatum = eventendDate.getDateString();

		this.turnierId = turnierId;
		this.onlyTables = onlyTables;
		this.noDWZCalc = noDWZCalc;
		this.noFolgeDWZCalc = noFolgeDWZCalc;
		this.noELOCalc = noELOCalc;
		this.noFolgeELOCalc = noFolgeELOCalc;

	}

	public int getAnzahlGruppen() {
		return anzahlGruppen;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public String getEndDatumTRF() {

		return eventendDate.getEnglishFormat();
	}

	public Group[] getGruppe() {
		return gruppe;
	}

	public Boolean getNoDWZCalc() {
		return noDWZCalc;
	}

	public boolean getNoELOCalc() {
		return noELOCalc;
	}

	public Boolean getNoFolgeDWZCalc() {
		return noFolgeDWZCalc;
	}

	public boolean getNoFolgeELOCalc() {
		return noFolgeELOCalc;
	}

	public Boolean getOnlyTables() {
		return onlyTables;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public String getStartDatumTRF() {

		return eventstartDate.getEnglishFormat();
	}

	public int getTurnierId() {
		return turnierId;
	}

	public String getTurnierName() {
		return turnierName;
	}

	public void setAnzahlGruppen(final int anzahlGruppen) {
		this.anzahlGruppen = anzahlGruppen;
	}

	public void setEndDatum(final String endDatum) {

		eventendDate = new EventDate(endDatum);
		this.endDatum = eventendDate.getDateString();
	}

	public void setGruppe(final Group[] gruppe) {
		this.gruppe = gruppe;
	}

	public void setNoDWZCalc(final Boolean noDWZCalc) {
		this.noDWZCalc = noDWZCalc;
	}

	public void setNoELOCalc(final Boolean noELO) {
		noELOCalc = noELO;

	}

	public void setNoFolgeDWZCalc(final Boolean noFolgeDWZCalc) {
		this.noFolgeDWZCalc = noFolgeDWZCalc;
	}

	public void setNoFolgeELOCalc(final Boolean noELO) {
		noFolgeELOCalc = noELO;
	}

	public void setOnlyTables(final Boolean onlyTables) {
		this.onlyTables = onlyTables;
	}

	public void setStartDatum(final String startDatum) {
		eventstartDate = new EventDate(startDatum);
		this.startDatum = eventstartDate.getDateString();

	}

	public void setTurnierId(final int turnierId) {
		this.turnierId = turnierId;
	}

	public void setTurnierName(final String turnierName) {
		this.turnierName = turnierName;
	}

}
