package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.turnierverwaltung.model.DWZData;

public class SQLiteVerbandDAO implements DWZVerbandDAO {
	private Connection dbConnect;
	
	public SQLiteVerbandDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createVerbandTable() throws SQLException {
		String sql = "CREATE TABLE `dwz_verbaende` (\n" + 
				"  `Verband`            char(3)      NOT NULL default '',\n" + 
				"  `LV`                 char(1)      NOT NULL default '',\n" + 
				"  `Uebergeordnet`      char(3)      NOT NULL default '',\n" + 
				"  `Verbandname`        varchar(45)  NOT NULL default '',\n" + 
				"  PRIMARY KEY (`Verband`)\n" + 
				");";

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
	public boolean deleteDWZ(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DWZData findPlayer(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
