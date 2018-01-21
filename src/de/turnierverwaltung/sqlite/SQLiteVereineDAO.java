package de.turnierverwaltung.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.turnierverwaltung.model.rating.DWZData;

public class SQLiteVereineDAO implements DWZVereineDAO {
	private Connection dbConnect;

	public SQLiteVereineDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createVereineTable() throws SQLException {
		String sql = "CREATE TABLE `dwz_vereine` (\n" + "  `ZPS`                varchar(5)   NOT NULL default '',\n"
				+ "  `LV`                 char(1)      NOT NULL default '',\n"
				+ "  `Verband`            char(3)      NOT NULL default '',\n"
				+ "  `Vereinname`         varchar(40)  NOT NULL default '',\n" + "  PRIMARY KEY (`ZPS`)\n" + ");";

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
	public boolean deleteDWZ(String zps) throws SQLException {
		boolean ok = false;
		String sql = "delete from dwz_verbaende where ZPS=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, zps);
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
