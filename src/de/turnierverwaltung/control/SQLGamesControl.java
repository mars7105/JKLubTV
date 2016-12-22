package de.turnierverwaltung.control;

import java.sql.SQLException;
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
import java.util.Arrays;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.PartienDAO;

public class SQLGamesControl {
	private Tournament turnier;
	private MainControl mainControl;
	private DAOFactory daoFactory;
	int turnierId;
	int partienId[];

	public SQLGamesControl(MainControl mainControl) {
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();

	}

	public void getPartien(int gruppenID) throws SQLException {
		Game.sortMeetingTable = mainControl.getPropertiesControl().getSortMeetingTable();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		this.turnier = this.mainControl.getTurnier();
		Group group = turnier.getGruppe().get(gruppenID);
		ArrayList<Game> partie = new ArrayList<Game>();

		partie = mySQLPartienDAO.selectAllPartien(group.getGruppeId());
		Game[] p = new Game[partie.size()];
		group.setPartienAnzahl(partie.size());
		Player spielerWeiss;
		Player spielerSchwarz;
		for (int s = 0; s < group.getSpielerAnzahl(); s++) {
			spielerWeiss = group.getSpieler().get(s);
			spielerSchwarz = group.getSpieler().get(s);
			for (int i = 0; i < partie.size(); i++) {
				p[i] = partie.get(i);
				if (p[i].getSpielerWeiss().getSpielerId() == spielerWeiss.getSpielerId()) {

					p[i].setSpielerWeiss(spielerWeiss);
				}
				if (p[i].getSpielerSchwarz().getSpielerId() == spielerSchwarz.getSpielerId()) {
					p[i].setSpielerSchwarz(spielerSchwarz);
				}

			}
		}
		Arrays.sort(p);
		group.setPartien(p);

	}

	public boolean insertPartien(int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		this.turnier = this.mainControl.getTurnier();
		turnierId = turnier.getTurnierId();
		Group group = turnier.getGruppe().get(gruppe);
		int idGruppe = group.getGruppeId();
		int anzahlPartien = group.getPartienAnzahl();
		partienId = new int[anzahlPartien];
		String[] spielDatum = new String[anzahlPartien];
		int[] runde = new int[anzahlPartien];
		int[] ergebnis = new int[anzahlPartien];
		int spielerIdweiss;
		int spielerIdschwarz;

		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		for (int i = 0; i < anzahlPartien; i++) {
			if (group.getPartien()[i].getPartieId() == -1) {
				spielerIdweiss = group.getPartien()[i].getSpielerWeiss().getSpielerId();
				spielerIdschwarz = group.getPartien()[i].getSpielerSchwarz().getSpielerId();
				spielDatum[i] = group.getPartien()[i].getSpielDatum();
				runde[i] = group.getPartien()[i].getRunde();
				ergebnis[i] = group.getPartien()[i].getErgebnis();
				partienId[i] = mySQLPartienDAO.insertPartien(idGruppe, spielDatum[i], runde[i], ergebnis[i],
						spielerIdweiss, spielerIdschwarz);
				group.getPartien()[i].setPartieId(partienId[i]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean updatePartien(int gruppe) throws SQLException {
		boolean saved = false;
		this.turnier = this.mainControl.getTurnier();
		Group group = turnier.getGruppe().get(gruppe);
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		// for (int i = 0; i < turnier.getGruppe()[gruppe].getPartienAnzahl();
		// i++) {
		saved = mySQLPartienDAO.updatePartien(group.getPartien());
		// }
		return saved;
	}
}
