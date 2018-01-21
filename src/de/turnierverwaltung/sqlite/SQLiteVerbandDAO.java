package de.turnierverwaltung.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.turnierverwaltung.model.rating.DWZData;

public class SQLiteVerbandDAO implements DWZVerbandDAO {
	private Connection dbConnect;

	public SQLiteVerbandDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createVerbandTable() throws SQLException {
		String sql = "CREATE TABLE `dwz_verbaende` (\n" + "  `Verband`            char(3)      NOT NULL default '',\n"
				+ "  `LV`                 char(1)      NOT NULL default '',\n"
				+ "  `Uebergeordnet`      char(3)      NOT NULL default '',\n"
				+ "  `Verbandname`        varchar(45)  NOT NULL default '',\n" + "  PRIMARY KEY (`Verband`)\n" + ");";

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
	public boolean deleteDWZ(String verband) throws SQLException {
		boolean ok = false;
		String sql = "delete from dwz_verbaende where Verband=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, verband);
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	@Override
	public DWZData findPlayer(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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

}
