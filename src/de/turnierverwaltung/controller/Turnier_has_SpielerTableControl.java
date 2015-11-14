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
import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.Turnier_has_SpielerDAO;

public class Turnier_has_SpielerTableControl {
	private MainControl mainControl;
	private Turnier turnier;
	private Gruppe[] gruppe;
	DAOFactory daoFactory;
	int turnierId;
	int turnier_has_Spieler;

	public Turnier_has_SpielerTableControl(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void insertTurnier_has_Spieler(int gruppenNr) {
		int gruppeId = 0;
		int spielerId = 0;
		this.turnier = mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
		Turnier_has_SpielerDAO turnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		gruppeId = gruppe[gruppenNr].getGruppeId();
		Spieler[] spieler = gruppe[gruppenNr].getSpieler();
		for (int i = 0; i < gruppe[gruppenNr].getSpielerAnzahl(); i++) {
			spielerId = spieler[i].getSpielerId();
			turnier_has_Spieler = turnier_has_SpielerDAO.insertTurnier_has_Spieler(gruppeId, spielerId);
		}

	}

	public void updateTurnier_has_Spieler(int gruppe) {
		daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
		Turnier_has_SpielerDAO mySQLTurnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		for (int i = 0; i < turnier.getGruppe()[gruppe].getSpielerAnzahl(); i++) {
			mySQLTurnier_has_SpielerDAO.updateTurnier_has_Spieler(turnier);
		}

	}
}
