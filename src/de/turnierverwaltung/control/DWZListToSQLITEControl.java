package de.turnierverwaltung.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.DWZData;
import de.turnierverwaltung.model.DWZDataArrayList;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.DWZDataDAO;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;

public class DWZListToSQLITEControl {
	private DWZDataArrayList csvPlayerList;
	private MainControl mainControl;
	private PropertiesControl prop;
	private DAOFactory daoFactory;
	private DWZDataDAO mySQLDWZDataDAO;

	public DWZListToSQLITEControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		prop = mainControl.getPropertiesControl();
		csvPlayerList = new DWZDataArrayList();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);

	}

	public void convertDWZListToSQLITE() {
		try {
			String filename = prop.getPathToPlayersCSV();
			if (filename != null) {
				BufferedWriter writer;

				int positionEXT = filename.lastIndexOf('.');
				String newFile = ""; //$NON-NLS-1$
				if (positionEXT > 0) {
					newFile = filename.substring(0, positionEXT) + ".sqlite"; //$NON-NLS-1$
				}
				File savefile = new File(newFile);
				writer = new BufferedWriter(new FileWriter(savefile.getAbsolutePath()));
				writer.write(""); //$NON-NLS-1$
				writer.close();

				// true for rewrite, false for override
				SQLiteDAOFactory.setDB_PATH(newFile);
				daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);

				mySQLDWZDataDAO = daoFactory.getDWZDataDAO();

				mySQLDWZDataDAO.createDWZTable();
				csvPlayerList.loadPlayerCSVList(prop.getPathToPlayersCSV());
				ArrayList<DWZData> dwzPlayer = csvPlayerList.getAlldwzData();
				mySQLDWZDataDAO.flush(dwzPlayer);

				JOptionPane.showMessageDialog(mainControl,
						Messages.getString("SpielerTableExportController.3") + newFile, //$NON-NLS-1$
						Messages.getString("SpielerTableExportController.4"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
				SQLiteDAOFactory.setDB_PATH(prop.getPathToDatabase());
				prop.setPathToPlayersCSV(newFile);
				prop.writeProperties();
			}

		} catch (IOException e) {
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		} catch (SQLException e) {
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}

	}
}
