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

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.mysql.*;

public class TurnierTableControl {
	private MainControl mainControl;
	private Turnier turnier;
	private DAOFactory daoFactory;
	private TurnierDAO mySQLTurnierDao;
	private DatumDAO mySQLDatumDAO;
	int turnierId;

	public TurnierTableControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(3);
		mySQLTurnierDao = daoFactory.getTurnierDAO();
		mySQLDatumDAO = daoFactory.getDatumDAO();
	}

	public void getTurnier(int tID) {

		turnier = mySQLTurnierDao.findTurnier(tID);
		mainControl.setTurnier(turnier);
		mainControl.getGruppenTableControl().getGruppe();

	}

	public boolean insertTurnier() {
		boolean eintragGespeichert = false;
		this.turnier = mainControl.getTurnier();
		if (turnier.getTurnierId() == -1) {
			String turnierName = turnier.getTurnierName();
			String startDatum = turnier.getStartDatum();
			String endDatum = turnier.getEndDatum();
			int datumId = mySQLDatumDAO.insertDatum(startDatum, endDatum);
			turnierId = mySQLTurnierDao.insertTurnier(turnierName, datumId);
			turnier.setTurnierId(turnierId);
			eintragGespeichert = true;
		}
		return eintragGespeichert;
	}

	public ArrayList<Turnier> loadTurnierListe() {
		ArrayList<Turnier> turnierListe;
		turnierListe = mySQLTurnierDao.selectAllTurnier();
		return turnierListe;

	}

	public boolean loescheTurnier(Turnier turnier) {
		boolean geloescht = false;

		Object[] options = { "Ja", "Abbrechen" };
		int abfrage = JOptionPane.showOptionDialog(null,
				"Wollen Sie wirklich das Turnier \n" + turnier.getTurnierName() + "\n" + "löschen?", "Turnier löschen?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		if (abfrage == 0) {
			geloescht = (mySQLTurnierDao.deleteTurnier(turnier.getTurnierId()));

		}

		return geloescht;
	}

	public void updateTurnier(Turnier turnier) {
		mySQLTurnierDao.updateTurnier(turnier);
	}
}
