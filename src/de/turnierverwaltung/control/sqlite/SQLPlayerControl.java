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
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.rating.DWZData;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.SpielerDAO;
import de.turnierverwaltung.sqlite.Turnier_has_SpielerDAO;

public class SQLPlayerControl {
	private Tournament turnier;
	private MainControl mainControl;
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;
	private DWZDataDAO mySQLDWZDataDAO;
	private int spielerId[];
	private PropertiesControl prop;
	private ELODataDAO mySQLELODataDAO;

	public SQLPlayerControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
		mySQLDWZDataDAO = daoFactory.getDWZDataDAO();
		mySQLELODataDAO = daoFactory.getELODataDAO();
		prop = mainControl.getPropertiesControl();
		int cutForename = Integer.parseInt(prop.getCutForename());
		int cutSurname = Integer.parseInt(prop.getCutSurname());
		Player.cutFname = cutForename;
		Player.cutSname = cutSurname;

	}

	public ArrayList<Player> getAllSpieler() throws SQLException {
		ArrayList<Player> spieler;

		spieler = mySQLSpielerDAO.getAllSpieler();

		return spieler;

	}

	public ArrayList<Player> getAllSpielerOrderByZPS() throws SQLException {
		ArrayList<Player> spieler;

		spieler = mySQLSpielerDAO.getAllSpieler();

		return spieler;

	}

	public void getSpieler() throws SQLException {
		this.turnier = this.mainControl.getTournament();

		ArrayList<Player> spieler = new ArrayList<Player>();
		for (int i = 0; i < this.turnier.getAnzahlGruppen(); i++) {
			spieler = mySQLSpielerDAO.selectAllSpieler(turnier.getGruppe()[i].getGruppeId());
			if (spieler.size() % 2 == 1) {
				Player spielfrei = new Player(TournamentConstants.SPIELFREI_ID,
						Messages.getString("SpielerTableControl.0"), Messages.getString("SpielerTableControl.1"), "0",
						0, "", "", -1);
				spieler.add(spielfrei);
			}

			Player[] gamers = new Player[spieler.size()];
			for (int y = 0; y < spieler.size(); y++) {
				Player temp = spieler.get(y);

				temp.setDwzData(mySQLDWZDataDAO.getDWZData(temp.getSpielerId()));

				gamers[y] = temp;
			}
			this.turnier.getGruppe()[i].setSpieler(gamers);
			this.turnier.getGruppe()[i].setSpielerAnzahl(gamers.length);

		}

	}

	public int insertOneSpieler(Player spieler) throws SQLException {
		this.turnier = mainControl.getTournament();

		int spielerId = -1;
		spielerId = mySQLSpielerDAO.insertSpieler(spieler);
		spieler.setSpielerId(spielerId);
		if (spielerId >= 0) {

			// mySQLDWZDataDAO.insertDWZ(spieler.getDwzData());
			if (spieler.getDwzData().getCsvZPS().length() > 0) {
				spieler.setName(spieler.getDwzData().getCsvSpielername());
				mySQLDWZDataDAO.insertDWZ(spieler.getDwzData());
			} else if (spieler.getDwz().length() > 0) {

				int dwzNumber = 0;
				try {
					dwzNumber = Integer.parseInt(spieler.getDwz());
					DWZData dwzData = new DWZData();
					dwzData.setSpielerId(spielerId);
					dwzData.setCsvDWZ(dwzNumber);
					dwzData.setAge(spieler.getAge());
					dwzData.setCsvSpielername(spieler.getName());
					spieler.setDwzData(dwzData);
					mySQLDWZDataDAO.insertDWZ(dwzData);
				} catch (NumberFormatException e) {
					dwzNumber = 0;
				}

			}
			if (spieler.getEloData().getFideid() > 0) {
				spieler.setName(spieler.getEloData().getName());
				mySQLELODataDAO.insertELO(spieler.getEloData());
			} else {

				if (spieler.getDwzData().getCsvFIDE_ID() > 0) {
					spieler.setName(spieler.getDwzData().getCsvSpielername());
					spieler.copyDWZDataToELOData();
					mySQLELODataDAO.insertELO(spieler.getEloData());
				}
			}
		}

		return spielerId;
	}

	public boolean insertSpieler(int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		this.turnier = mainControl.getTournament();

		turnier.getTurnierId();
		int spielerAnzahl = turnier.getGruppe()[gruppe].getSpielerAnzahl();
		spielerId = new int[spielerAnzahl];
		for (int y = 0; y < spielerAnzahl; y++) {
			if (turnier.getGruppe()[gruppe].getSpieler()[y].getSpielerId() == -1) {
				Player temp = turnier.getGruppe()[gruppe].getSpieler()[y];
				spielerId[y] = mySQLSpielerDAO.insertSpieler(temp);
				temp.setSpielerId(spielerId[y]);
				if (!temp.getDwzData().getCsvZPS().equals("")) {
					mySQLDWZDataDAO.insertDWZ(temp.getDwzData());
				}
				if (temp.getEloData().getFideid() > 0) {
					mySQLELODataDAO.insertELO(temp.getEloData());
				} else {
					if (temp.getDwzData().getCsvFIDE_ID() > 0) {
						temp.copyDWZDataToELOData();
						mySQLELODataDAO.insertELO(temp.getEloData());
					}
				}
				turnier.getGruppe()[gruppe].getSpieler()[y].setSpielerId(spielerId[y]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean loescheSpieler(Player spieler) throws SQLException {
		boolean geloescht = false;
		Turnier_has_SpielerDAO turnier_has_spielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		ArrayList<Integer> tId = turnier_has_spielerDAO.findSpielerisinTurnier_has_Spieler(spieler);
		int abfrage = 0;
		if (tId.size() > 0) {
			JOptionPane.showMessageDialog(mainControl,
					"Spieler " + spieler.getName() + "\n" + Messages.getString("SpielerTableControl.5") //$NON-NLS-2$ //$NON-NLS-3$
							+ spieler.getName() + " \n" + Messages.getString("SpielerTableControl.7") + tId.size() //$NON-NLS-1$ //$NON-NLS-2$
							+ Messages.getString("SpielerTableControl.8")); //$NON-NLS-1$

			abfrage = -1;
		} else {
			Object[] options = { Messages.getString("SpielerTableControl.9"), //$NON-NLS-1$
					Messages.getString("SpielerTableControl.10") }; //$NON-NLS-1$
			abfrage = JOptionPane.showOptionDialog(mainControl,
					Messages.getString("SpielerTableControl.11") + Messages.getString("SpielerTableControl.12") //$NON-NLS-1$ //$NON-NLS-2$
							+ Messages.getString("SpielerTableControl.13") //$NON-NLS-1$
							+ spieler.getName() + Messages.getString("SpielerTableControl.14"), //$NON-NLS-1$
					Messages.getString("SpielerTableControl.15"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		}
		if (abfrage == 0) {
			// geloescht =
			// turnier_has_spielerDAO.deleteTurnier_has_Spieler(tId);
			geloescht = mySQLSpielerDAO.deleteSpieler(spieler.getSpielerId());
			if (!spieler.getDwzData().getCsvZPS().equals("")) {
				mySQLDWZDataDAO.deleteDWZ(spieler.getSpielerId());
			}
			if (spieler.getEloData().getFideid() > 0) {
				mySQLELODataDAO.deleteELO(spieler.getSpielerId());
			}
		}

		return geloescht;
	}

	public boolean playerExist(DWZData tmp) {
		boolean exist = false;

		exist = mySQLDWZDataDAO.playerExist(tmp);

		return exist;
	}

	public Boolean playerExist(Player neuerSpieler) throws SQLException {
		boolean exist = false;

		exist = mySQLDWZDataDAO.playerExist(neuerSpieler.getDwzData());

		return exist;
	}

	public Boolean playerFideExist(int fideId) {
		Boolean exist = false;
		exist = mySQLDWZDataDAO.playerFideExist(fideId);

		if (exist == false) {
			exist = mySQLELODataDAO.playerExist(fideId);
		}
		return exist;
	}

	public void updateOneSpieler(Player spieler) throws SQLException {

		mySQLSpielerDAO.updateSpieler(spieler);
		if (spieler.getDwzData().getCsvZPS().length() > 0) {
			mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
		}
		if (spieler.getEloData().getFideid() > 0) {
			mySQLELODataDAO.updateELO(spieler.getEloData());
		} else {
			if (spieler.getDwzData().getCsvFIDE_ID() > 0) {
				spieler.copyDWZDataToELOData();
				mySQLELODataDAO.insertELO(spieler.getEloData());
			}
		}
	}

	public boolean updateSpieler(int gruppe) throws SQLException {
		this.turnier = mainControl.getTournament();

		boolean saved = false;
		for (int i = 0; i < turnier.getGruppe()[gruppe].getSpielerAnzahl(); i++) {
			Player spieler = turnier.getGruppe()[gruppe].getSpieler()[i];
			saved = mySQLSpielerDAO.updateSpieler(spieler);
			// mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
			if (!spieler.getDwzData().getCsvZPS().equals("")) {
				mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
			}
			if (spieler.getEloData().getFideid() > 0) {
				mySQLELODataDAO.updateELO(spieler.getEloData());
			} else {
				if (spieler.getDwzData().getCsvFIDE_ID() > 0) {
					spieler.copyDWZDataToELOData();
					mySQLELODataDAO.insertELO(spieler.getEloData());
				}
			}
		}
		return saved;
	}
}