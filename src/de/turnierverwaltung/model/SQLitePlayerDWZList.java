package de.turnierverwaltung.model;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.DWZDataDAO;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;

public class SQLitePlayerDWZList {

	public ArrayList<DWZData> getPlayerOfVerein(String filename, String zps) {
		DAOFactory daoFactory;
		DWZDataDAO dwzDataDAO;

		String oldPath = SQLiteDAOFactory.getDB_PATH();

		// This is where a real application would open the file.
		SQLiteDAOFactory.setDB_PATH(filename);
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		dwzDataDAO = daoFactory.getDWZDataDAO();
		ArrayList<DWZData> dwzDataArray = null;

		try {
			dwzDataArray = dwzDataDAO.getPlayerOfVerein(zps);
		} catch (SQLException e) {
			dwzDataArray = null;
			JOptionPane.showMessageDialog(null, Messages.getString("MainControl.11"));
		} finally {
			SQLiteDAOFactory.setDB_PATH(oldPath);
		}
		return dwzDataArray;

	}

	public ArrayList<DWZData> getPlayersByName(String pathToPlayersCSV, String eingabe) {
		DAOFactory daoFactory;
		DWZDataDAO dwzDataDAO;

		String oldPath = SQLiteDAOFactory.getDB_PATH();

		// This is where a real application would open the file.
		SQLiteDAOFactory.setDB_PATH(pathToPlayersCSV);
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		dwzDataDAO = daoFactory.getDWZDataDAO();
		ArrayList<DWZData> dwzPlayers = null;
		try {
			dwzPlayers = dwzDataDAO.getDWZDataByName(eingabe);
		} catch (SQLException e) {
			dwzPlayers = null;
			JOptionPane.showMessageDialog(null, Messages.getString("MainControl.11"));
		} finally {
			SQLiteDAOFactory.setDB_PATH(oldPath);
		}

		return dwzPlayers;
	}

}
