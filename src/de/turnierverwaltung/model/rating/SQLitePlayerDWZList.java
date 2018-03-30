package de.turnierverwaltung.model.rating;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class SQLitePlayerDWZList {

	public ArrayList<DWZData> getPlayerOfVerein(String filename, String zps) {
		ArrayList<DWZData> dwzDataArray = null;
		if (filename.length() > 0 && zps.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzDataArray = dwzDataDAO.getPlayerOfVerein(zps);
			} catch (SQLException e) {
				dwzDataArray = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzDataArray;

	}

	public ArrayList<DWZData> getPlayersByFideId(String pathToPlayersCSV, int eingabe) {
		ArrayList<DWZData> dwzPlayers = null;
		if (pathToPlayersCSV.length() > 0 && eingabe > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getDWZDataByFideId(eingabe);
			} catch (SQLException e) {
				dwzPlayers = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzPlayers;
	}

	public ArrayList<DWZData> getPlayersByName(String pathToPlayersCSV, String eingabe) {
		ArrayList<DWZData> dwzPlayers = null;
		if (pathToPlayersCSV.length() > 0 && eingabe.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getDWZDataByName(eingabe);
			} catch (SQLException e) {
				dwzPlayers = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return dwzPlayers;

	}

	public Player getPlayer(String pathToPlayersCSV, final String zps, final String mgl) {
		ArrayList<DWZData> dwzPlayers = null;
		Player player = new Player();
		if (pathToPlayersCSV.length() > 0) {
			DAOFactory daoFactory;
			DWZDataDAO dwzDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			dwzDataDAO = daoFactory.getDWZDataDAO();

			try {
				dwzPlayers = dwzDataDAO.getPlayerByZPSMGL(zps, mgl);

				if (dwzPlayers.size() == 1) {
					player.setDwzData(dwzPlayers.get(0));
				}

			} catch (SQLException e) {
				dwzPlayers = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return player;

	}
}
