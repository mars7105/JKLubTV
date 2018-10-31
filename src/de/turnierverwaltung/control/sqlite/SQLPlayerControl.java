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
	private final MainControl mainControl;
	private final DAOFactory daoFactory;
	private final SpielerDAO mySQLSpielerDAO;
	private final DWZDataDAO mySQLDWZDataDAO;
	private int spielerId[];
	private final PropertiesControl prop;
	private final ELODataDAO mySQLELODataDAO;

	public SQLPlayerControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
		mySQLDWZDataDAO = daoFactory.getDWZDataDAO();
		mySQLELODataDAO = daoFactory.getELODataDAO();
		prop = mainControl.getPropertiesControl();
		final int cutForename = Integer.parseInt(prop.getCutForename());
		final int cutSurname = Integer.parseInt(prop.getCutSurname());
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
		turnier = mainControl.getTournament();

		ArrayList<Player> spieler = new ArrayList<Player>();
		for (int i = 0; i < turnier.getAnzahlGruppen(); i++) {
			spieler = mySQLSpielerDAO.selectAllSpieler(turnier.getGruppe()[i].getGruppeId());
			if (spieler.size() % 2 == 1) {
				final Player spielfrei = new Player(TournamentConstants.SPIELFREI_ID,
						Messages.getString("SpielerTableControl.0"), Messages.getString("SpielerTableControl.1"), "0",
						0, "", "", -1);
				spieler.add(spielfrei);
			}

			final Player[] gamers = new Player[spieler.size()];
			for (int y = 0; y < spieler.size(); y++) {
				final Player temp = spieler.get(y);

				temp.setDwzData(mySQLDWZDataDAO.getDWZData(temp.getSpielerId()));

				gamers[y] = temp;
			}
			turnier.getGruppe()[i].setSpieler(gamers);
			turnier.getGruppe()[i].setSpielerAnzahl(gamers.length);

		}

	}

	public int insertOneSpieler(final Player spieler) throws SQLException {
		turnier = mainControl.getTournament();

		int spielerId = -1;
		spielerId = mySQLSpielerDAO.insertSpieler(spieler);
		spieler.setSpielerId(spielerId);
		if (spielerId >= 0) {

			// mySQLDWZDataDAO.insertDWZ(spieler.getDwzData());
			if (spieler.getDwzData().getCsvZPS().length() > 0) {
				spieler.setName(spieler.getDwzData().getCsvSpielername());
				mySQLDWZDataDAO.insertDWZ(spieler.getDwzData());
			} else {
				if (spieler.getDwzData().getCsvDWZ() > 0) {
					final int dwzNumber = spieler.getDWZ();
					final DWZData dwzData = new DWZData();
					dwzData.setSpielerId(spielerId);
					dwzData.setCsvDWZ(dwzNumber);
					dwzData.setAge(spieler.getAge());
					dwzData.setCsvSpielername(spieler.getName());
					spieler.setDwzData(dwzData);
					mySQLDWZDataDAO.insertDWZ(dwzData);
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

	public boolean insertSpieler(final int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		turnier = mainControl.getTournament();

		turnier.getTurnierId();
		final int spielerAnzahl = turnier.getGruppe()[gruppe].getSpielerAnzahl();
		spielerId = new int[spielerAnzahl];
		for (int y = 0; y < spielerAnzahl; y++) {
			if (turnier.getGruppe()[gruppe].getSpieler()[y].getSpielerId() == -1) {
				final Player temp = turnier.getGruppe()[gruppe].getSpieler()[y];
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

	public boolean loescheSpieler(final Player spieler) throws SQLException {
		boolean geloescht = false;
		final Turnier_has_SpielerDAO turnier_has_spielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		final ArrayList<Integer> tId = turnier_has_spielerDAO.findSpielerisinTurnier_has_Spieler(spieler);
		int abfrage = 0;
		if (tId.size() > 0) {
			JOptionPane.showMessageDialog(mainControl,
					"Spieler " + spieler.getName() + "\n" + Messages.getString("SpielerTableControl.5") //$NON-NLS-2$ //$NON-NLS-3$
							+ spieler.getName() + " \n" + Messages.getString("SpielerTableControl.7") + tId.size() //$NON-NLS-1$ //$NON-NLS-2$
							+ Messages.getString("SpielerTableControl.8")); //$NON-NLS-1$

			abfrage = -1;
		} else {
			final Object[] options = { Messages.getString("SpielerTableControl.9"), //$NON-NLS-1$
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
			if (spieler.getDwzData().getSpielerId() > 0) {
				mySQLDWZDataDAO.deleteDWZ(spieler.getSpielerId());
			}
			if (spieler.getEloData().getFideid() > 0) {
				mySQLELODataDAO.deleteELO(spieler.getSpielerId());
			}
		}

		return geloescht;
	}

	public boolean playerExist(final DWZData tmp) throws SQLException {
		boolean exist = false;

		exist = mySQLDWZDataDAO.playerExist(tmp);

		return exist;
	}

	public Boolean playerExist(final Player neuerSpieler) throws SQLException {
		boolean exist = false;

		exist = mySQLDWZDataDAO.playerExist(neuerSpieler.getDwzData());

		return exist;
	}

	public Boolean playerFideExist(final int fideId) throws SQLException {
		Boolean exist = false;
		exist = mySQLDWZDataDAO.playerFideExist(fideId);

		if (exist == false) {
			exist = mySQLELODataDAO.playerExist(fideId);
		}
		return exist;
	}

	public void updateOneSpieler(final Player spieler) throws SQLException {

		mySQLSpielerDAO.updateSpieler(spieler);
		final DWZData dbData = mySQLDWZDataDAO.getDWZData(spieler.getSpielerId());
		if (spieler.getDWZ() > 0) {
			if (dbData.getSpielerId() == spieler.getSpielerId()) {
				mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
			} 
			else {

				spieler.setDwzData(new DWZData());

				spieler.getDwzData().setSpielerId(spieler.getSpielerId());
				spieler.getDwzData().setCsvDWZ(spieler.getDWZ());
				spieler.getDwzData().setAge(spieler.getAge());
				spieler.getDwzData().setCsvSpielername(spieler.getName());

				mySQLDWZDataDAO.insertDWZ(spieler.getDwzData());
			}
		} else {
			if (dbData.getSpielerId() == spieler.getSpielerId()) {
				if (spieler.getDwzData().getCsvZPS().length() == 0) {
					mySQLDWZDataDAO.deleteDWZ(spieler.getSpielerId());

				} else {
					spieler.getDwzData().setCsvDWZ(0);
					mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
				}
			}
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

	public boolean updateSpieler(final int gruppe) throws SQLException {
		turnier = mainControl.getTournament();

		boolean saved = false;
		for (int i = 0; i < turnier.getGruppe()[gruppe].getSpielerAnzahl(); i++) {
			final Player spieler = turnier.getGruppe()[gruppe].getSpieler()[i];
			saved = mySQLSpielerDAO.updateSpieler(spieler);
			// mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
			if (!spieler.getDwzData().getCsvZPS().equals("")) {
				mySQLDWZDataDAO.updateDWZ(spieler.getDwzData());
			}
			if (spieler.getEloData() != null) {
				if (spieler.getEloData().getFideid() > 0) {
					mySQLELODataDAO.updateELO(spieler.getEloData());
				} else {
					if (spieler.getDwzData().getCsvFIDE_ID() > 0) {
						spieler.copyDWZDataToELOData();
						mySQLELODataDAO.insertELO(spieler.getEloData());
					}
				}
			}
		}
		return saved;
	}
}
