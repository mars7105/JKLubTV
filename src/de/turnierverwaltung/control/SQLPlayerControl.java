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

import de.turnierverwaltung.model.Group;
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

	public SQLPlayerControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
	}

	public ArrayList<Player> getAllSpieler() throws SQLException {
		ArrayList<Player> spieler;

		spieler = mySQLSpielerDAO.getAllSpieler();

		return spieler;

	}

	public void getSpieler() throws SQLException {
		this.turnier = this.mainControl.getTurnier();
		ArrayList<Player> spieler = new ArrayList<Player>();
		for (int i = 0; i < this.turnier.getAnzahlGruppen(); i++) {
			spieler = mySQLSpielerDAO.selectAllSpieler(turnier.getGruppe().get(i).getGruppeId());
			if (spieler.size() % 2 == 1) {
				Player spielfrei = new Player(TournamentConstants.SPIELFREI_ID,
						Messages.getString("SpielerTableControl.0"), Messages.getString("SpielerTableControl.1"), "0", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						0, "");
				spieler.add(spielfrei);
			}

			this.turnier.getGruppe().get(i).setSpieler(spieler);

		}

	}

	public int insertOneSpieler(Player spieler) throws SQLException {
		this.turnier = mainControl.getTurnier();

		int spielerId = -1;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();

		spielerId = mySQLSpielerDAO.insertSpieler(spieler);

		return spielerId;
	}

	public boolean insertSpieler(int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		Group group = turnier.getGruppe().get(gruppe);
		this.turnier = mainControl.getTurnier();
		String[] spielerName = new String[group.getSpielerAnzahl()];
		String[] spielerDWZ = new String[group.getSpielerAnzahl()];
		String[] spielerKuerzel = new String[group.getSpielerAnzahl()];
		int[] spielerAge = new int[group.getSpielerAnzahl()];
		String[] fideTitle = new String[group.getSpielerAnzahl()];
		turnierId = turnier.getTurnierId();
		int spielerAnzahl = group.getSpielerAnzahl();
		spielerId = new int[spielerAnzahl];
		for (int y = 0; y < spielerAnzahl; y++) {
			if (group.getSpieler().get(y).getSpielerId() == -1) {
				spielerName[y] = group.getSpieler().get(y).getName();
				spielerDWZ[y] = group.getSpieler().get(y).getDwz();
				spielerKuerzel[y] = group.getSpieler().get(y).getKuerzel();
				spielerAge[y] = group.getSpieler().get(y).getAge();
				fideTitle[y] = group.getSpieler().get(y).getFideTitle();
				Player player = new Player(-1, spielerName[y], spielerKuerzel[y], spielerDWZ[y], spielerAge[y],
						fideTitle[y]);
				spielerId[y] = mySQLSpielerDAO.insertSpieler(player);
				group.getSpieler().get(y).setSpielerId(spielerId[y]);
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

		mySQLSpielerDAO.updateSpieler(spieler);
	}

	public boolean updateSpieler(int gruppe) throws SQLException {
		this.turnier = mainControl.getTurnier();
		Group group = turnier.getGruppe().get(gruppe);
		boolean saved = false;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
		for (int i = 0; i < group.getSpielerAnzahl(); i++) {
			saved = mySQLSpielerDAO.updateSpieler(group.getSpieler().get(i));
		}
		return saved;
	}

}
