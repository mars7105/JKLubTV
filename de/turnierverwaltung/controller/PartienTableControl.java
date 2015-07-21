package de.turnierverwaltung.controller;
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
import java.util.ArrayList;

import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.PartienDAO;

public class PartienTableControl {
	private Turnier turnier;
	private MainControl mainControl;
	private DAOFactory daoFactory;
	int turnierId;
	int partienId[];

	public PartienTableControl(MainControl mainControl) {
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
	}

	public void getPartien(int gruppenID) {
		daoFactory = DAOFactory.getDAOFactory();
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		this.turnier = this.mainControl.getTurnier();
		ArrayList<Partie> partie = new ArrayList<Partie>();

		partie = mySQLPartienDAO
				.selectAllPartien(turnier.getGruppe()[gruppenID].getGruppeId());
		Partie[] p = new Partie[partie.size()];
		turnier.getGruppe()[gruppenID].setPartienAnzahl(partie.size());
		Spieler spielerWeiss;
		Spieler spielerSchwarz;
		for (int s = 0; s < turnier.getGruppe()[gruppenID].getSpielerAnzahl(); s++) {
			spielerWeiss = turnier.getGruppe()[gruppenID].getSpieler()[s];
			spielerSchwarz = turnier.getGruppe()[gruppenID].getSpieler()[s];
			for (int i = 0; i < partie.size(); i++) {
				p[i] = partie.get(i);
				if (p[i].getSpielerWeiss().getSpielerId() == spielerWeiss
						.getSpielerId()) {

					p[i].setSpielerWeiss(spielerWeiss);
				}
				if (p[i].getSpielerSchwarz().getSpielerId() == spielerSchwarz
						.getSpielerId()) {
					p[i].setSpielerSchwarz(spielerSchwarz);
				}

			}
		}

		turnier.getGruppe()[gruppenID].setPartien(p);

	}

	public boolean insertPartien(int gruppe) {
		boolean eintragGespeichert = false;
		turnierId = turnier.getTurnierId();
		int idGruppe = turnier.getGruppe()[gruppe].getGruppeId();
		int anzahlPartien = turnier.getGruppe()[gruppe].getPartienAnzahl();
		partienId = new int[anzahlPartien];
		String[] spielDatum = new String[anzahlPartien];
		int[] runde = new int[anzahlPartien];
		int[] ergebnis = new int[anzahlPartien];
		int spielerIdweiss;
		int spielerIdschwarz;

		daoFactory = DAOFactory.getDAOFactory();
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		for (int i = 0; i < anzahlPartien; i++) {
			if (turnier.getGruppe()[gruppe].getPartien()[i].getPartieId() == -1) {
				spielerIdweiss = turnier.getGruppe()[gruppe].getPartien()[i]
						.getSpielerWeiss().getSpielerId();
				spielerIdschwarz = turnier.getGruppe()[gruppe].getPartien()[i]
						.getSpielerSchwarz().getSpielerId();
				spielDatum[i] = turnier.getGruppe()[gruppe].getPartien()[i]
						.getSpielDatum();
				runde[i] = turnier.getGruppe()[gruppe].getPartien()[i]
						.getRunde();
				ergebnis[i] = turnier.getGruppe()[gruppe].getPartien()[i]
						.getErgebnis();
				partienId[i] = mySQLPartienDAO.insertPartien(idGruppe,
						spielDatum[i], runde[i], ergebnis[i], spielerIdweiss,
						spielerIdschwarz);
				turnier.getGruppe()[gruppe].getPartien()[i]
						.setPartieId(partienId[i]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean updatePartien(int gruppe) {
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory();
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		for (int i = 0; i < turnier.getGruppe()[gruppe].getPartienAnzahl(); i++) {
			saved = mySQLPartienDAO.updatePartien(turnier.getGruppe()[gruppe]
					.getPartien()[i]);
		}
		return saved;
	}
}
