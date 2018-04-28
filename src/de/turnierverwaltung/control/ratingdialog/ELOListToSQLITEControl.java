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
import de.turnierverwaltung.model.rating.ELOPlayer;
import de.turnierverwaltung.model.rating.ELOPlayerList;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.InfoDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class ELOListToSQLITEControl {
	public static final String INFONAME = Version.getString("version.0");
	public static final String VERSION = Version.getString("version.1");
	public static final String INFONOTICE = Version.getString("version.2");
	public static final String INFONAME_EXPORT_PLAYERLIST = Version.getString("version.6");
	ELOPlayerList eloPlayerList;
	MainControl mainControl;
	PropertiesControl prop;
	private DAOFactory daoFactory;
	private ELODataDAO mySQLELODataDAO;

	public ELOListToSQLITEControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		prop = mainControl.getPropertiesControl();
		eloPlayerList = new ELOPlayerList();
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);

	}

	public void convertELOListToSQLITE() {
		try {
			String filename = prop.getPathToPlayersELO();
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
				mySQLELODataDAO = daoFactory.getELODataDAO();

				mySQLELODataDAO.createELOTable();
				eloPlayerList.readEloList(prop.getPathToPlayersELO());
				ArrayList<ELOPlayer> eloPlayer = eloPlayerList.getPlayerList();
				mySQLELODataDAO.flush(eloPlayer);

				JOptionPane.showMessageDialog(mainControl,
						Messages.getString("SpielerTableExportController.3") + newFile, //$NON-NLS-1$
						Messages.getString("SpielerTableExportController.4"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
				SQLiteDAOFactory.setDB_PATH(prop.getPathToDatabase());
				prop.setPathToPlayersELO(newFile);
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
