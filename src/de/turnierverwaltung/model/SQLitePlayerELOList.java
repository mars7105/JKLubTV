package de.turnierverwaltung.model;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.ELODataDAO;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;

public class SQLitePlayerELOList {

	public ELOData getPlayer(String filename, int fideId) {
		DAOFactory daoFactory;
		ELODataDAO eloDataDAO;

		String oldPath = SQLiteDAOFactory.getDB_PATH();

		// This is where a real application would open the file.
		SQLiteDAOFactory.setDB_PATH(filename);
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		eloDataDAO = daoFactory.getELODataDAO();
		ELOData eloData = null;
		try {
			eloData = eloDataDAO.getELODataByFideId(fideId);
		} catch (SQLException e) {
			eloData = null;
			ExceptionHandler eh = new ExceptionHandler(null);
			eh.fileSQLError(e.getMessage());
		} finally {
			SQLiteDAOFactory.setDB_PATH(oldPath);
		}

		return eloData;
	}

	public ArrayList<ELOData> getPlayersByName(String filename, String eingabe) {
		DAOFactory daoFactory;
		ELODataDAO eloDataDAO;
		ArrayList<ELOData> eloPlayers = new ArrayList<ELOData>();
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

		return eloPlayers;
	}

}
