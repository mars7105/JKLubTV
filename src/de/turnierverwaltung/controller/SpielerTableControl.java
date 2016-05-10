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

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SpielerDAO;
import de.turnierverwaltung.mysql.Turnier_has_SpielerDAO;

public class SpielerTableControl {
	private Turnier turnier;
	private MainControl mainControl;
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;
	int turnierId;
	int spielerId[];

	public SpielerTableControl(MainControl mainControl) {
		this.mainControl = mainControl;
		daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
	}

	public ArrayList<Spieler> getAllSpieler() {
		ArrayList<Spieler> spieler;

		spieler = mySQLSpielerDAO.getAllSpieler();

		return spieler;

	}

	public void getSpieler() {
		this.turnier = this.mainControl.getTurnier();
		ArrayList<Spieler> spieler = new ArrayList<Spieler>();
		for (int i = 0; i < this.turnier.getAnzahlGruppen(); i++) {
			spieler = mySQLSpielerDAO.selectAllSpieler(turnier.getGruppe()[i].getGruppeId());
			if (spieler.size() % 2 == 1) {
				Spieler spielfrei = new Spieler(TurnierKonstanten.SPIELFREI_ID, Messages.getString("SpielerTableControl.0"), Messages.getString("SpielerTableControl.1"), "0", 0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				spieler.add(spielfrei);
			}

			Spieler[] gamers = new Spieler[spieler.size()];
			for (int y = 0; y < spieler.size(); y++) {
				gamers[y] = spieler.get(y);
			}
			this.turnier.getGruppe()[i].setSpieler(gamers);
			this.turnier.getGruppe()[i].setSpielerAnzahl(gamers.length);

		}

	}

	public int insertOneSpieler(Spieler spieler) {
		this.turnier = mainControl.getTurnier();
		String spielerName = spieler.getName();
		String spielerDWZ = spieler.getDwz();
		String spielerKuerzel = spieler.getKuerzel();
		int age = spieler.getAge();

		int spielerId = -1;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();

		spielerId = mySQLSpielerDAO.insertSpieler(spielerName, spielerDWZ, spielerKuerzel, age);

		return spielerId;
	}

	public boolean insertSpieler(int gruppe) {
		boolean eintragGespeichert = false;
		this.turnier = mainControl.getTurnier();
		String[] spielerName = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerDWZ = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		String[] spielerKuerzel = new String[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		int[] spielerAge = new int[turnier.getGruppe()[gruppe].getSpielerAnzahl()];
		turnierId = turnier.getTurnierId();
		int spielerAnzahl = turnier.getGruppe()[gruppe].getSpielerAnzahl();
		spielerId = new int[spielerAnzahl];
		for (int y = 0; y < spielerAnzahl; y++) {
			if (turnier.getGruppe()[gruppe].getSpieler()[y].getSpielerId() == -1) {
				spielerName[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getName();
				spielerDWZ[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getDwz();
				spielerKuerzel[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getKuerzel();
				spielerAge[y] = turnier.getGruppe()[gruppe].getSpieler()[y].getAge();
				spielerId[y] = mySQLSpielerDAO.insertSpieler(spielerName[y], spielerDWZ[y], spielerKuerzel[y], spielerAge[y]);
				turnier.getGruppe()[gruppe].getSpieler()[y].setSpielerId(spielerId[y]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean loescheSpieler(Spieler spieler) {
		boolean geloescht = false;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
		Turnier_has_SpielerDAO turnier_has_spielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		ArrayList<Integer> tId = turnier_has_spielerDAO.findSpielerisinTurnier_has_Spieler(spieler);
		int abfrage = 0;
		if (tId.size() > 0) {
			JOptionPane.showMessageDialog(mainControl,
					"Spieler " + spieler.getName() + "\n" + Messages.getString("SpielerTableControl.5") + spieler.getName() //$NON-NLS-2$ //$NON-NLS-3$
							+ " \n" + Messages.getString("SpielerTableControl.7") + tId.size() + Messages.getString("SpielerTableControl.8")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			abfrage = -1;
		} else {
			Object[] options = { Messages.getString("SpielerTableControl.9"), Messages.getString("SpielerTableControl.10") }; //$NON-NLS-1$ //$NON-NLS-2$
			abfrage = JOptionPane.showOptionDialog(mainControl,
					Messages.getString("SpielerTableControl.11") + Messages.getString("SpielerTableControl.12") + Messages.getString("SpielerTableControl.13") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ spieler.getName() + Messages.getString("SpielerTableControl.14"), //$NON-NLS-1$
					Messages.getString("SpielerTableControl.15"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, //$NON-NLS-1$
					options[1]);
		}
		if (abfrage == 0) {
//			geloescht = turnier_has_spielerDAO.deleteTurnier_has_Spieler(tId);
			geloescht = mySQLSpielerDAO.deleteSpieler(spieler.getSpielerId());
		}

		return geloescht;
	}

	public void updateOneSpieler(Spieler spieler) {
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();

		mySQLSpielerDAO.updateSpieler(spieler);
	}

	public boolean updateSpieler(int gruppe) {
		this.turnier = mainControl.getTurnier();

		boolean saved = false;
		SpielerDAO mySQLSpielerDAO = daoFactory.getSpielerDAO();
		for (int i = 0; i < turnier.getGruppe()[gruppe].getSpielerAnzahl(); i++) {
			saved = mySQLSpielerDAO.updateSpieler(turnier.getGruppe()[gruppe].getSpieler()[i]);
		}
		return saved;
	}


}
