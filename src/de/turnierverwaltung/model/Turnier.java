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
public class Turnier {

	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int anzahlGruppen;
	private Gruppe[] gruppe;
	private int turnierId;

	//
	// Constructors
	//
	public Turnier() {
		turnierId = -1;
	}

	public Turnier(int turnierId, String turnierName, String startDatum,
			String endDatum) {
		super();
		this.turnierName = turnierName;
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.turnierId = turnierId;
	}

	public int getAnzahlGruppen() {
		return anzahlGruppen;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public Gruppe[] getGruppe() {
		return gruppe;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public int getTurnierId() {
		return turnierId;
	}

	public String getTurnierName() {
		return turnierName;
	}

	public void setAnzahlGruppen(int anzahlGruppen) {
		this.anzahlGruppen = anzahlGruppen;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public void setGruppe(Gruppe[] gruppe) {
		this.gruppe = gruppe;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public void setTurnierId(int turnierId) {
		this.turnierId = turnierId;
	}

	public void setTurnierName(String turnierName) {
		this.turnierName = turnierName;
	}

}
