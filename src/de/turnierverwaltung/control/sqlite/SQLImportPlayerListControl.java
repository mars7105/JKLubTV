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

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;
import de.turnierverwaltung.sqlite.SpielerDAO;

public class SQLImportPlayerListControl {

	private MainControl mainControl;

	public SQLImportPlayerListControl(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void importSpielerTable() throws SQLException {

		// Create a file chooser
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter(Messages.getString("SpielerTableImportController.0"), "sqlite"); //$NON-NLS-1$ //$NON-NLS-2$
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);
		String fileName = ""; //$NON-NLS-1$
		DAOFactory daoFactory;
		SpielerDAO mySQLSpielerDAO;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fileName = SQLiteDAOFactory.getDB_PATH();
			File file = fc.getSelectedFile();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			mySQLSpielerDAO = daoFactory.getSpielerDAO();
			
			ArrayList<Player> spielerListe = mySQLSpielerDAO.getAllSpieler();
			SQLiteDAOFactory.setDB_PATH(fileName);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			mySQLSpielerDAO = daoFactory.getSpielerDAO();
			DWZDataDAO mySQLDWZDAO = daoFactory.getDWZDataDAO();
			ELODataDAO mySQLELODAO = daoFactory.getELODataDAO();
			Player oneSpieler = null;
			ListIterator<Player> li = spielerListe.listIterator();
			while (li.hasNext()) {
				oneSpieler = li.next();
				mySQLSpielerDAO.insertSpieler(oneSpieler);
				if (oneSpieler.getDwzData().getCsvDWZ() >= 0) {
					mySQLDWZDAO.insertDWZ(oneSpieler.getDwzData());
				}
				if (oneSpieler.getEloData().getFideid() > 0) {
					mySQLELODAO.insertELO(oneSpieler.getEloData());
				}
			}

		} else {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerTableImportController.3")); //$NON-NLS-1$
		}

	}

}
