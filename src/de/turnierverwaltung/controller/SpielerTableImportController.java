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

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.mysql.SpielerDAO;

public class SpielerTableImportController {


	public SpielerTableImportController() {
	}

	public void importSpielerTable() {

		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Spieler Datenbank", "spl");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);
		String fileName = "";
		DAOFactory daoFactory;
		SpielerDAO mySQLSpielerDAO;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fileName = SQLiteDAOFactory.getDB_PATH();
			File file = fc.getSelectedFile();
			
			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
			daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
			mySQLSpielerDAO = daoFactory.getSpielerDAO();
			ArrayList<Spieler> spielerListe = mySQLSpielerDAO.getAllSpieler();
			SQLiteDAOFactory.setDB_PATH(fileName);
			daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
			mySQLSpielerDAO = daoFactory.getSpielerDAO();
			
			Spieler oneSpieler = null;
			ListIterator<Spieler> li = spielerListe.listIterator();
			while (li.hasNext()) {
				oneSpieler = li.next();
				mySQLSpielerDAO.insertSpieler(oneSpieler.getName(), oneSpieler.getDwz(), oneSpieler.getKuerzel(),
						oneSpieler.getAge());
			}

		} else {
			JOptionPane.showMessageDialog(null, "Vorgang abgebrochen!");
		}

	}

}
