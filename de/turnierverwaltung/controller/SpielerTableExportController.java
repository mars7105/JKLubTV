package de.turnierverwaltung.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.mysql.SpielerDAO;

public class SpielerTableExportController {
	private MainControl mainControl;
	private SpielerTableControl spielerTableControl;
	private ArrayList<Spieler> spieler;
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;

	public SpielerTableExportController(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void exportSpielerTable() {

		spielerTableControl = new SpielerTableControl(this.mainControl);
		spieler = spielerTableControl.getAllSpieler();

		String filename = SQLiteDAOFactory.getDB_PATH();
		if (filename != null) {
			BufferedWriter writer;

			try {
				int positionEXT = filename.lastIndexOf('.');
				String newFile = "";
				if (positionEXT > 0) {
					newFile = filename.substring(0, positionEXT) + ".spl";
				}
				File savefile = new File(newFile);
				writer = new BufferedWriter(new FileWriter(savefile.getAbsolutePath()));
				writer.write("");
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
				JOptionPane.showMessageDialog(null, "Datei wurde gespeichert.\n Dateiname:\n" + newFile, "File Saved",
						JOptionPane.INFORMATION_MESSAGE);
				SQLiteDAOFactory.setDB_PATH(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
