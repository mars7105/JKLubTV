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
import java.util.ListIterator;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SpielerDAO;
import de.turnierverwaltung.mysql.Turnier_has_SpielerDAO;

public class SQLPlayerControl {
	private Tournament turnier;
	private MainControl mainControl;
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;
	int turnierId;
	int spielerId[];
	private PropertiesControl prop;

	public SQLPlayerControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
		prop = mainControl.getPropertiesControl();
		Boolean dbUpdated = prop.getDatabaseUpdated();
		if (dbUpdated == false) {
			mySQLSpielerDAO.alterTables();
			prop.setDatabaseUpdated(true);
			prop.writeProperties();
		}
	}

	public ArrayList<Player> getAllSpieler() throws SQLException {
		ArrayList<Player> spieler;

		spieler = mySQLSpielerDAO.getAllSpieler();

		int cutForename = Integer.parseInt(prop.getCutForename());
		int cutSurname = Integer.parseInt(prop.getCutSurname());
		ListIterator<Player> li = spieler.listIterator();

		while (li.hasNext()) {
			Player temp = li.next();
			temp.cutForename(cutForename);
			temp.cutSurname(cutSurname);
			temp.extractForenameAndSurenameToName();
		}

		return spieler;

	}

	public void getSpieler() throws SQLException {
		this.turnier = this.mainControl.getTurnier();

		ArrayList<Player> spieler = new ArrayList<Player>();
		for (int i = 0; i < this.turnier.getAnzahlGruppen(); i++) {
			spieler = mySQLSpielerDAO.selectAllSpieler(turnier.getGruppe()[i].getGruppeId());
			if (spieler.size() % 2 == 1) {
				Player spielfrei = new Player(TournamentConstants.SPIELFREI_ID,
						Messages.getString("SpielerTableControl.0"), Messages.getString("SpielerTableControl.1"), "0",
						0, "", "");
				spieler.add(spielfrei);
			}
			int cutForename = Integer.parseInt(prop.getCutForename());
			int cutSurname = Integer.parseInt(prop.getCutSurname());

			Player[] gamers = new Player[spieler.size()];
			for (int y = 0; y < spieler.size(); y++) {
				Player temp = spieler.get(y);
				temp.cutForename(cutForename);
				temp.cutSurname(cutSurname);
				temp.extractForenameAndSurenameToName();
				gamers[y] = spieler.get(y);
			}
			this.turnier.getGruppe()[i].setSpieler(gamers);
			this.turnier.getGruppe()[i].setSpielerAnzahl(gamers.length);

		}

	}

	public int insertOneSpieler(Player spieler) throws SQLException {
		this.turnier = mainControl.getTurnier();
		String spielerName = spieler.getName();
		String spielerForeName = spieler.getForename();
		String spielerSurName = spieler.getSurname();
		String spielerDWZ = spieler.getDwz();
		String spielerKuerzel = spieler.getKuerzel();
		String spielerZPS = spieler.getDsbZPSNumber();
		String spielerMGL = spieler.getDsbMGLNumber();
		int age = spieler.getAge();

		int spielerId = -1;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();

		spielerId = mySQLSpielerDAO.insertSpieler(spielerName, spielerForeName, spielerSurName, spielerDWZ,
				spielerKuerzel, spielerZPS, spielerMGL, age);

		return spielerId;
	}

	public boolean insertSpieler(int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		this.turnier = mainControl.getTurnier();
		String[] spielerName = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerForeName = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerSurName = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerDWZ = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerKuerzel = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerZPS = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerMGL = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		int[] spielerAge = new int[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		turnierId = turnier.getTurnierId();
		int spielerAnzahl = turnier.getGruppe()[gruppe].getSpielerAnzahl();
		spielerId = new int[spielerAnzahl];
		for (int y = 0; y < spielerAnzahl; y++) {
			if (turnier.getGruppe()[gruppe].getSpieler()[y].getSpielerId() == -1) {
				spielerName[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getName();
				spielerName[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getForename();
				spielerName[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getSurname();
				spielerDWZ[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getDwz();
				spielerKuerzel[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getKuerzel();
				spielerZPS[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getDsbZPSNumber();
				spielerMGL[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getDsbMGLNumber();
				spielerAge[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getAge();
				spielerId[y] = mySQLSpielerDAO.insertSpieler(spielerName[y], spielerForeName[y], spielerSurName[y],
						spielerDWZ[y], spielerKuerzel[y], spielerZPS[y], spielerMGL[y], spielerAge[y]);
				turnier.getGruppe()[gruppe].getSpieler()[y].setSpielerId(spielerId[y]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean loescheSpieler(Player spieler) throws SQLException {
		boolean geloescht = false;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
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
		}

		return geloescht;
	}

	public void updateOneSpieler(Player spieler) throws SQLException {
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
		if (!spieler.getName().equals("") && spieler.getSurname().equals("")) {
			spieler.extractNameToForenameAndSurename();
		}
		if (spieler.getName().equals("") && !spieler.getSurname().equals("")) {
			spieler.extractForenameAndSurenameToName();
		}
		mySQLSpielerDAO.updateSpieler(spieler);
	}

	public boolean updateSpieler(int gruppe) throws SQLException {
		this.turnier = mainControl.getTurnier();

		boolean saved = false;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
		for (int i = 0; i < turnier.getGruppe()[gruppe].getSpielerAnzahl(); i++) {
			saved = mySQLSpielerDAO.updateSpieler(turnier.getGruppe()[gruppe].getSpieler()[i]);
		}
		return saved;
	}

}
