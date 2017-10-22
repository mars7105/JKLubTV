package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		String sql = "CREATE TABLE 'elo_data' (\n" + "  'Name'               varchar(57)  NOT NULL default '',\n"
				+ "  'Fed'                varchar(5)   NOT NULL default '',\n"
				+ "  'Sex'                varchar(3)      NOT NULL default '',\n"
				+ "  'Tit'                varchar(3)               default NULL,\n"
				+ "  'WTit'               varchar(3)               default NULL,\n"
				+ "  'OTit'               varchar(3)      NOT NULL default '',\n"
				+ "  'FOA'                varchar(3)       NOT NULL default '',\n"
				+ "  'OCT17'              INTEGER  unsigned default NULL,\n"
				+ "  'Gms'                INTEGER  unsigned default NULL,\n"
				+ "  'K'                  INTEGER  unsigned default NULL,\n"
				+ "  'B-day'              INTEGER  unsigned default NULL,\n"
				+ "  'Flag'               char(2)               default NULL,\n"
				+ "  'ID_Number'          INTEGER PRIMARY KEY NOT NULL\n" + ");";
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
	public boolean deleteELO(int id) throws SQLException {
		boolean ok = false;
		String sql = "delete from elo_data where ID_Number=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setInt(1, id);
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
	public ELOData getELOData(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
