package de.turnierverwaltung.control.sqlite;

import java.sql.SQLException;

import de.turnierverwaltung.control.MainControl;
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
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.Turnier_has_SpielerDAO;

public class SQLTournament_has_PlayerControl {
	private final MainControl mainControl;
	private Tournament turnier;
	private Group[] gruppe;
	DAOFactory daoFactory;
	int turnierId;
	int turnier_has_Spieler;

	public SQLTournament_has_PlayerControl(final MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void insertTurnier_has_Spieler(final int gruppenNr) throws SQLException {
		int gruppeId = 0;
		int spielerId = 0;
		turnier = mainControl.getTournament();
		gruppe = turnier.getGruppe();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final Turnier_has_SpielerDAO turnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		gruppeId = gruppe[gruppenNr].getGruppeId();
		final Player[] spieler = gruppe[gruppenNr].getSpieler();
		for (int i = 0; i < gruppe[gruppenNr].getSpielerAnzahl(); i++) {
			spielerId = spieler[i].getSpielerId();
			turnier_has_Spieler = turnier_has_SpielerDAO.insertTurnier_has_Spieler(gruppeId, spielerId);
		}

	}

	public void insertTurnier_has_Spieler(final int spielerId, final int gruppeId) throws SQLException {

		turnier = mainControl.getTournament();
		gruppe = turnier.getGruppe();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final Turnier_has_SpielerDAO turnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		turnier_has_Spieler = turnier_has_SpielerDAO.insertTurnier_has_Spieler(gruppeId, spielerId);

	}

	public void deletePlayerOfGroup(final int spielerId, final int gruppeId) throws SQLException {
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final Turnier_has_SpielerDAO mySQLTurnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		mySQLTurnier_has_SpielerDAO.deleteTurnier_has_Spieler(gruppeId, spielerId);
	}
}
