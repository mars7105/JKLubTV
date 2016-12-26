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

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.GruppenDAO;

public class SQLGroupsControl {
	private MainControl mainControl;
	private Tournament turnier;
	private ArrayList<Group> gruppe;
	private DAOFactory daoFactory;
	private GruppenDAO mySQLGruppenDAO;
	int gruppenId;

	public SQLGroupsControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.turnier = this.mainControl.getTurnier();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
	}

	public void getGruppe() throws SQLException {
		this.turnier = this.mainControl.getTurnier();
		gruppe = mySQLGruppenDAO.selectAllGruppen(turnier.getTurnierId());

		mainControl.getTurnier().setGruppe(gruppe);
	}

	public Tournament getGruppe(Tournament turnierName) throws SQLException {
		ArrayList<Group> grp = mySQLGruppenDAO.selectAllGruppen(turnierName.getTurnierId());

		turnierName.setGruppe(grp);
		return turnierName;
	}

	public boolean insertGruppe(int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		this.turnier = this.mainControl.getTurnier();
		Group group = turnier.getGruppe().get(gruppe);
		if (group.getGruppeId() == -1) {
			String gruppenName = group.getGruppenName();
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			int turnierId = turnier.getTurnierId();
			gruppenId = mySQLGruppenDAO.insertGruppe(gruppenName, turnierId);
			group.setGruppeId(gruppenId);
			eintragGespeichert = true;
		}
		return eintragGespeichert;
	}

	public boolean updateGruppe(int gruppe) throws SQLException {
		this.turnier = mainControl.getTurnier();
		Group group = turnier.getGruppe().get(gruppe);
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		saved = mySQLGruppenDAO.updateGruppe(group);
		return saved;

	}

	public boolean updateGruppen(Tournament turnierX) throws SQLException {

		ArrayList<Group> groups = turnierX.getGruppe();
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		for (int i = 0; i < groups.size(); i++) {
			saved = mySQLGruppenDAO.updateGruppe(groups.get(i));

		}
		return saved;
	}
}
