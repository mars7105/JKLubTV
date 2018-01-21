package de.turnierverwaltung.model.rating;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;

public class SQLitePlayerELOList {

	public ELOData getPlayer(String filename, int fideId) {
		ELOData eloData = null;
		if (filename.length() > 0 && fideId > 0) {
			DAOFactory daoFactory;
			ELODataDAO eloDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			eloDataDAO = daoFactory.getELODataDAO();

			try {
				eloData = eloDataDAO.getELODataByFideId(fideId);
			} catch (SQLException e) {
				eloData = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return eloData;
	}

	public ArrayList<ELOData> getPlayersByName(String filename, String eingabe) {
		ArrayList<ELOData> eloPlayers = null;
		if (filename.length() > 0 && eingabe.length() > 0) {
			DAOFactory daoFactory;
			ELODataDAO eloDataDAO;

			String oldPath = SQLiteDAOFactory.getDB_PATH();

			// This is where a real application would open the file.
			SQLiteDAOFactory.setDB_PATH(filename);
			daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
			eloDataDAO = daoFactory.getELODataDAO();
			try {
				eloPlayers = eloDataDAO.getELODataByName(eingabe);
			} catch (SQLException e) {
				eloPlayers = null;
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			} finally {
				SQLiteDAOFactory.setDB_PATH(oldPath);
			}

		}
		return eloPlayers;
	}

}
