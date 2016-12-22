package de.turnierverwaltung.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

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
	private ArrayList<Group> gruppe;
	private int turnierId;
	private Boolean onlyTables;
	private Boolean noDWZCalc;
	private Boolean noFolgeDWZCalc;
	private String md5Sum;
	private Boolean sortMeetingTable;
	private int turnierType;

	/**
	 * 
	 * @param onlyTables
	 * @param noDWZCalc
	 * @param noFolgeDWZCalc
	 */
	public Tournament(Boolean onlyTables, Boolean noDWZCalc, Boolean noFolgeDWZCalc, Boolean sortMeetingTable) {
		turnierId = -1;
		this.onlyTables = onlyTables;
		this.noDWZCalc = noDWZCalc;
		this.noFolgeDWZCalc = noFolgeDWZCalc;
		this.sortMeetingTable = sortMeetingTable;
		md5Sum = "";
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
	public Tournament(int turnierId, String turnierName, String startDatum, String endDatum, Boolean onlyTables,
			Boolean noDWZCalc, Boolean noFolgeDWZCalc, Boolean sortMeetingTable) {
		super();
		this.turnierName = turnierName;
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.turnierId = turnierId;
		this.onlyTables = onlyTables;
		this.noDWZCalc = noDWZCalc;
		this.noFolgeDWZCalc = noFolgeDWZCalc;
		this.sortMeetingTable = sortMeetingTable;
		md5Sum = "";
	}

	public void createMD5SUM() {

		String md5String = turnierName + startDatum + endDatum + turnierId + System.currentTimeMillis() / 1000;
		byte[] bytesOfMessage = null;
		try {
			bytesOfMessage = md5String.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] md5SumByte = md.digest(bytesOfMessage);
		// md5Sum = md.digest(bytesOfMessage);

		md5Sum = new String(Hex.encodeHex(md5SumByte));

	}

	public Boolean getOnlyTables() {
		return onlyTables;
	}

	public void setOnlyTables(Boolean onlyTables) {
		this.onlyTables = onlyTables;
	}

	public Boolean getNoDWZCalc() {
		return noDWZCalc;
	}

	public void setNoDWZCalc(Boolean noDWZCalc) {
		this.noDWZCalc = noDWZCalc;
	}

	public Boolean getNoFolgeDWZCalc() {
		return noFolgeDWZCalc;
	}

	public void setNoFolgeDWZCalc(Boolean noFolgeDWZCalc) {
		this.noFolgeDWZCalc = noFolgeDWZCalc;
	}

	public int getAnzahlGruppen() {
		return gruppe.size();
	}

	public String getEndDatum() {
		return endDatum;
	}

	public ArrayList<Group> getGruppe() {
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

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public void setGruppe(ArrayList<Group> gruppe) {
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

	public String getMd5Sum() {

		return this.md5Sum;
	}

	public void setMd5Sum(String md5Sum) {
		this.md5Sum = md5Sum;
	}

	public Boolean getSortMeetingTable() {
		return sortMeetingTable;
	}

	public void setSortMeetingTable(Boolean sortMeetingTable) {
		this.sortMeetingTable = sortMeetingTable;
	}

	public void setTurnierType(int turnierType) {
		this.turnierType = turnierType;

	}

	public int getTurnierType() {
		return turnierType;

	}

}
