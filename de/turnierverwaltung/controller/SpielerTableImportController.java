package de.turnierverwaltung.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	private Object mainControl;

	public SpielerTableImportController(MainControl mainControl) {
		this.mainControl = mainControl;
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
