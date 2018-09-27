package de.turnierverwaltung.model.rating;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.InfoDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class SQLitePlayerDWZList {

	public ArrayList<DWZData> getPlayerOfVerein(final String filename, final String zps) {
		ArrayList<DWZData> dwzDataArray = null;
		if (filename.length() > 0 && zps.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzDataArray = dwzDataDAO.getPlayerOfVerein(zps);
			} catch (final SQLException e) {
				dwzDataArray = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzDataArray;

	}

	public ArrayList<DWZData> getPlayersByFideId(final String pathToPlayersCSV, final int eingabe) {
		ArrayList<DWZData> dwzPlayers = null;
		if (pathToPlayersCSV.length() > 0 && eingabe > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getDWZDataByFideId(eingabe);
			} catch (final SQLException e) {
				dwzPlayers = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzPlayers;
	}

	public ArrayList<DWZData> getPlayersByName(final String pathToPlayersCSV, final String eingabe) {
		ArrayList<DWZData> dwzPlayers = null;
		if (pathToPlayersCSV.length() > 0 && eingabe.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getDWZDataByName(eingabe);
			} catch (final SQLException e) {
				dwzPlayers = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzPlayers;

	}

	public Player getPlayer(final String pathToPlayersCSV, final String zps, final String mgl) {
		ArrayList<DWZData> dwzPlayers = null;
		final Player player = new Player();
		if (pathToPlayersCSV.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getPlayerByZPSMGL(zps, mgl);

				if (dwzPlayers.size() == 1) {
					player.setDwzData(dwzPlayers.get(0));
				}

			} catch (final SQLException e) {
				dwzPlayers = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return player;

	}

	public Boolean checkDatabase(final String pathToPlayersCSV) {
		ArrayList<DWZData> dwzPlayers = null;
		dwzPlayers = getPlayersByName(pathToPlayersCSV, "Schm");
		if (dwzPlayers == null) {
			return false;
		} else {
			if (dwzPlayers.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	public String getDWZDate(final String filename) {
		ArrayList<Info> infos = null;
		if (filename.length() > 0) {
			DAOFactory daoFactory;
			InfoDAO infoDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			infoDAO = daoFactory.getInfoDAO();
			try {
				infos = infoDAO.getAllInfos();
				for (final Info info : infos) {
					if (info.getInfonotice().equals("Create tables") == true) {
						return info.getDatum();
					}
				}
			} catch (final SQLException e) {

				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}

		return "";
	}
}
