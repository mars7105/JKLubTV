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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.mysql.SpielerDAO;

public class SpielerTableExportControl {
	private MainControl mainControl;
	private SpielerTableControl spielerTableControl;
	private ArrayList<Spieler> spieler;
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;

	public SpielerTableExportControl(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void exportSpielerTable() throws SQLException {

		spielerTableControl = new SpielerTableControl(this.mainControl);
		spieler = spielerTableControl.getAllSpieler();

		String filename = SQLiteDAOFactory.getDB_PATH();
		if (filename != null) {
			BufferedWriter writer;

			try {
				int positionEXT = filename.lastIndexOf('.');
				String newFile = ""; //$NON-NLS-1$
				if (positionEXT > 0) {
					newFile = filename.substring(0, positionEXT) + ".spl"; //$NON-NLS-1$
				}
				File savefile = new File(newFile);
				writer = new BufferedWriter(new FileWriter(savefile.getAbsolutePath()));
				writer.write(""); //$NON-NLS-1$
				writer.close();

				// true for rewrite, false for override
				SQLiteDAOFactory.setDB_PATH(newFile);
				SQLiteControl sqlC = new SQLiteControl();
				daoFactory = DAOFactory.getDAOFactory(TurnierKonstanten.DATABASE_DRIVER);
				mySQLSpielerDAO = daoFactory.getSpielerDAO();
				sqlC.createSpielerTables();
				Spieler oneSpieler = null;
				ListIterator<Spieler> li = spieler.listIterator();
				while (li.hasNext()) {
					oneSpieler = li.next();
					mySQLSpielerDAO.insertSpieler(oneSpieler.getName(), oneSpieler.getDwz(), oneSpieler.getKuerzel(),
							oneSpieler.getAge());
				}
				JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerTableExportController.3") + newFile, Messages.getString("SpielerTableExportController.4"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.INFORMATION_MESSAGE);
				SQLiteDAOFactory.setDB_PATH(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
