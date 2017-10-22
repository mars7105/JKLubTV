package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.Info;

public class SQLiteInfoDAO implements InfoDAO {
	private Connection dbConnect;

	public SQLiteInfoDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createInfoTable() throws SQLException {
		String sql = "CREATE TABLE info (idInfo INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " Infoname VARCHAR, Version INTEGER, Infonotice VARCHAR, Datum VARCHAR);";

		Statement stmt;
		if (this.dbConnect != null) {

			// create a database connection
			stmt = this.dbConnect.createStatement();
			stmt.setQueryTimeout(30); // set timeout to 30 sec.
			stmt.executeUpdate(sql);
			stmt.close();

		}

	}

	@Override
	public boolean deleteInfo(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Info findInfo(int id, PropertiesControl prop) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertInfo(String infoName, int infoId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateInfo(Info info) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Info> selectAllInfo(PropertiesControl prop) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
