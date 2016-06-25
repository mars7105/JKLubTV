package de.turnierverwaltung.control;

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

	public void getGruppe() {
		this.turnier = this.mainControl.getTurnier();
		gruppe = mySQLGruppenDAO.selectAllGruppen(turnier.getTurnierId());
		Group[] group = new Group[gruppe.size()];
		for (int i = 0; i < gruppe.size(); i++) {
			group[i] = gruppe.get(i);
		}
		mainControl.getTurnier().setGruppe(group);
		mainControl.getTurnier().setAnzahlGruppen(gruppe.size());
	}

	public Tournament getGruppe(Tournament turnierName) {
		ArrayList<Group> grp = mySQLGruppenDAO.selectAllGruppen(turnierName.getTurnierId());
		Group[] group = new Group[grp.size()];
		for (int i = 0; i < grp.size(); i++) {
			group[i] = grp.get(i);
		}
		turnierName.setGruppe(group);
		turnierName.setAnzahlGruppen(group.length);
		return turnierName;
	}

	public boolean insertGruppe(int gruppe) {
		boolean eintragGespeichert = false;
		if (turnier.getGruppe()[gruppe].getGruppeId() == -1) {
			String gruppenName = turnier.getGruppe()[gruppe].getGruppenName();
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			int turnierId = turnier.getTurnierId();
			gruppenId = mySQLGruppenDAO.insertGruppe(gruppenName, turnierId);
			turnier.getGruppe()[gruppe].setGruppeId(gruppenId);
			eintragGespeichert = true;
		}
		return eintragGespeichert;
	}

	public boolean updateGruppe(int gruppe) {
		this.turnier = mainControl.getTurnier();

		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		saved = mySQLGruppenDAO.updateGruppe(turnier.getGruppe()[gruppe]);
		return saved;

	}

	public boolean updateGruppen(Tournament turnierX) {

		Group[] groups = turnierX.getGruppe();
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		for (int i = 0; i < groups.length; i++) {
			saved = mySQLGruppenDAO.updateGruppe(turnierX.getGruppe()[i]);

		}
		return saved;
	}
}
