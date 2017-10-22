package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.turnierverwaltung.model.ELOData;

public class SQLiteELODataDAO implements ELODataDAO {
	private Connection dbConnect;

	public SQLiteELODataDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createELOTable() throws SQLException {
//		String sql = "CREATE TABLE spieler (idSpieler INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
//				+ " Name VARCHAR, Forename VARCHAR, Surname VARCHAR, Kuerzel VARCHAR, DWZ VARCHAR, ZPS VARCHAR, MGL VARCHAR, DWZIndex INTEGER, Age INTEGER)"
//				+ ") TYPE=MyISAM;";
//
//		Statement stmt;
//		if (this.dbConnect != null) {
//
//			// create a database connection
//			stmt = this.dbConnect.createStatement();
//			stmt.setQueryTimeout(30); // set timeout to 30 sec.
//			stmt.executeUpdate(sql);
//			stmt.close();
//
//		}

	}

	@Override
	public boolean deleteELO(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertELO(ELOData eloData) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateELO(ELOData eloData) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ELOData findPlayer(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
