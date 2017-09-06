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
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.DatumDAO;
import de.turnierverwaltung.mysql.TurnierDAO;

public class SQLTournamentControl {
	private MainControl mainControl;
	private Tournament turnier;
	private DAOFactory daoFactory;
	private TurnierDAO mySQLTurnierDao;
	private DatumDAO mySQLDatumDAO;
	int turnierId;

	public SQLTournamentControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(3);
		if (daoFactory == null) {
			this.mainControl.setPropertiesControl(new PropertiesControl(
					this.mainControl));
			this.mainControl.getPropertiesControl().writeProperties();

		} else {
			mySQLTurnierDao = daoFactory.getTurnierDAO();
			mySQLDatumDAO = daoFactory.getDatumDAO();
		}
	}

	public void getTurnier(int tID) throws SQLException {

		turnier = mySQLTurnierDao.findTurnier(tID,
				mainControl.getPropertiesControl());
		mainControl.setTurnier(turnier);
		mainControl.getGruppenTableControl().getGruppe();

	}

	public int insertTurnier() throws SQLException {
		this.turnier = mainControl.getTurnier();
		turnierId = -1;
		if (turnier.getTurnierId() == -1) {
			String turnierName = turnier.getTurnierName();
			String startDatum = turnier.getStartDatum();
			String endDatum = turnier.getEndDatum();
			int datumId = mySQLDatumDAO.insertDatum(startDatum, endDatum);
			turnierId = mySQLTurnierDao.insertTurnier(turnierName, datumId);
			turnier.setTurnierId(turnierId);
		}
		return turnierId;
	}

	public ArrayList<Tournament> loadTurnierListe() throws SQLException {
		ArrayList<Tournament> turnierListe;
		turnierListe = mySQLTurnierDao.selectAllTurnier(mainControl
				.getPropertiesControl());

		return turnierListe;

	}

	public boolean loescheTurnier(Tournament turnier) throws SQLException {
		boolean geloescht = false;

		Object[] options = {
				Messages.getString("TurnierTableControl.0"), Messages.getString("TurnierTableControl.1") }; //$NON-NLS-1$ //$NON-NLS-2$
		int abfrage = JOptionPane
				.showOptionDialog(
						mainControl,
						Messages.getString("TurnierTableControl.2") + turnier.getTurnierName() //$NON-NLS-1$
								+ "\n" + Messages.getString("TurnierTableControl.4"), Messages.getString("TurnierTableControl.5"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		if (abfrage == 0) {
			geloescht = (mySQLTurnierDao.deleteTurnier(turnier.getTurnierId()));

		}

		return geloescht;
	}

	public void updateTurnier(Tournament turnier) throws SQLException {
		mySQLTurnierDao.updateTurnier(turnier);
	}
}
