package de.turnierverwaltung.control.ratingdialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.Version;
import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.rating.DWZData;
import de.turnierverwaltung.model.rating.DWZDataArrayList;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.InfoDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class DWZListToSQLITEControl {
	public static final String INFONAME = Version.getString("version.0");
	public static final String VERSION = Version.getString("version.1");
	public static final String INFONOTICE = Version.getString("version.2");
	public static final String INFONAME_EXPORT_PLAYERLIST = Version.getString("version.5");
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
				InfoDAO infoDataDao = daoFactory.getInfoDAO();
				infoDataDao.createInfoTable();
				infoDataDao.insertInfo(new Info(INFONAME_EXPORT_PLAYERLIST, VERSION, INFONOTICE, getDate(), 0));
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
		} catch (NullPointerException e) {
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}

	}

	private String getDate() {
		Date dt = new Date();
		// Festlegung des Formats:
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		df.setTimeZone(TimeZone.getDefault());
		// nicht mehr unbedingt notwendig seit JDK 1.2
		// Formatierung zu String:
		String date = df.format(dt);
		// z.B. '2001-01-26 19:03:56.731'
		return date;
	}
}
