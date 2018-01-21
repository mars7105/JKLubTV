package de.turnierverwaltung.control.sqlite;

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

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.Version;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DatumDAO;
import de.turnierverwaltung.sqlite.TurnierDAO;

public class SQLTournamentControl {
	//	private InfoDAO infoDao;
	public static final String INFONAME = Version.getString("version.0");
	public static final String VERSION = Version.getString("version.1");
	public static final String INFONOTICE = Version.getString("version.2");
	private MainControl mainControl;
	private Tournament turnier;
	private DAOFactory daoFactory;
private TurnierDAO mySQLTurnierDao;
	private DatumDAO mySQLDatumDAO;
	private int turnierId;

	public SQLTournamentControl(MainControl mainControl) {
//		Boolean checkDatabase = false;
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(3);
		if (daoFactory == null) {
			this.mainControl.setPropertiesControl(new PropertiesControl(this.mainControl));
			this.mainControl.getPropertiesControl().writeProperties();

		} else {
//			infoDao = daoFactory.getInfoDAO();
//			ArrayList<Info> infoArrayList = null;
//			try {
//				infoArrayList = infoDao.getAllInfos();
//			} catch (SQLException e) {
//				ExceptionHandler eh = new ExceptionHandler(mainControl);
//				eh.fileSQLError(e.getMessage());
//			}
//
//			for (Info info : infoArrayList) {
//				if (info.getInfoname().equals(INFONAME)) {
//					checkDatabase = true;
//				}
//
//			}
			mySQLTurnierDao = daoFactory.getTurnierDAO();
			mySQLDatumDAO = daoFactory.getDatumDAO();

		}
	}

	public void getTurnier(int tID) throws SQLException {

		turnier = mySQLTurnierDao.findTurnier(tID, mainControl.getPropertiesControl());
		mainControl.setTournament(turnier);
		mainControl.getSqlGroupsControl().getGruppe();

	}

	public int insertTurnier() throws SQLException {
		this.turnier = mainControl.getTournament();
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
		turnierListe = mySQLTurnierDao.selectAllTurnier(mainControl.getPropertiesControl());

		return turnierListe;

	}

	public boolean loescheTurnier(Tournament turnier) throws SQLException {
		boolean geloescht = false;

		Object[] options = { Messages.getString("TurnierTableControl.0"), Messages.getString("TurnierTableControl.1") }; //$NON-NLS-1$ //$NON-NLS-2$
		int abfrage = JOptionPane.showOptionDialog(mainControl,
				Messages.getString("TurnierTableControl.2") + turnier.getTurnierName() //$NON-NLS-1$
						+ "\n" + Messages.getString("TurnierTableControl.4"), //$NON-NLS-1$ //$NON-NLS-2$
				Messages.getString("TurnierTableControl.5"), //$NON-NLS-1$
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		if (abfrage == 0) {
			geloescht = (mySQLTurnierDao.deleteTurnier(turnier.getTurnierId()));

		}

		return geloescht;
	}

	public void updateTurnier(Tournament turnier) throws SQLException {
		mySQLTurnierDao.updateTurnier(turnier);
	}
}
