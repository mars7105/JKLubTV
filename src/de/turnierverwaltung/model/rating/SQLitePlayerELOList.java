package de.turnierverwaltung.model.rating;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.InfoDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class SQLitePlayerELOList {

	public ELOData getPlayer(final String filename, final int fideId) {
		ELOData eloData = null;
		if (filename.length() > 0 && fideId > 0) {
			DAOFactory daoFactory;
			ELODataDAO eloDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			eloDataDAO = daoFactory.getELODataDAO();

			try {
				eloData = eloDataDAO.getELODataByFideId(fideId);
			} catch (final SQLException e) {
				eloData = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return eloData;
	}

	public ArrayList<ELOData> getPlayersByName(final String filename, final String eingabe) {
		ArrayList<ELOData> eloPlayers = null;
		if (filename.length() > 0 && eingabe.length() > 0) {
			DAOFactory daoFactory;
			ELODataDAO eloDataDAO;

			final String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			eloDataDAO = daoFactory.getELODataDAO();
			try {
				eloPlayers = eloDataDAO.getELODataByName(eingabe);
			} catch (final SQLException e) {
				eloPlayers = null;
				final ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return eloPlayers;
	}

	public Boolean checkDatabase(final String pathToPlayersCSV) {
		ArrayList<ELOData> dwzPlayers = null;
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

	public String getELODate(final String filename) {
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
					if (info.getInfonotice().equals("date") == true) {
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
