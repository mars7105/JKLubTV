package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.turnierverwaltung.model.DWZData;

public class SQLiteDWZDataDAO implements DWZDataDAO {

	private Connection dbConnect;

	public SQLiteDWZDataDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createDWZTable() throws SQLException {
		String sql = "CREATE TABLE 'dwz_spieler' (\n" + "  'ZPS'                varchar(5)   NOT NULL default '',\n"
				+ "  'Mgl_Nr'             char(4)      NOT NULL default '',\n"
				+ "  'Status'             char(1)               default NULL,\n"
				+ "  'Spielername'        varchar(40)  NOT NULL default '',\n"
				+ "  'Geschlecht'         char(1)               default NULL,\n"
				+ "  'Spielberechtigung'  char(1)      NOT NULL default '',\n"
				+ "  'Geburtsjahr'        INTEGER      NOT NULL default '0000',\n"
				+ "  'Letzte_Auswertung'  INTEGER unsigned default NULL,\n"
				+ "  'DWZ'                INTEGER  unsigned default NULL,\n"
				+ "  'DWZ_Index'          INTEGER  unsigned default NULL,\n"
				+ "  'FIDE_Elo'           INTEGER  unsigned default NULL,\n"
				+ "  'FIDE_Titel'         char(2)               default NULL,\n"
				+ "  'FIDE_ID'            INTEGER       unsigned default NULL,\n"
				+ "  'FIDE_Land'          char(3)               default NULL,\n" + "  PRIMARY KEY  ('ZPS','Mgl_Nr')\n"
				+ ");";
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
	public boolean deleteDWZ(String zps, String mgl) throws SQLException {
		boolean ok = false;
		String sql = "delete from dwz_spieler where ZPS=? AND Mgl_Nr=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, zps);
			preStm.setString(2, mgl);
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
	public int insertDWZ(DWZData dwzData) throws SQLException {
		String sql;
		int id = -1;

		sql = "Insert into dwz_spieler (ZPS, Mgl_Nr, Status, Spielername, Geschlecht, Spielberechtigung, Geburtsjahr, Letzte_Auswertung, DWZ, DWZ_Index, FIDE_Elo, FIDE_Titel, FIDE_ID, FIDE_Land) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
				+ ";";

		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preStm.setString(1, dwzData.getCsvZPS());
			preStm.setString(2, dwzData.getCsvMgl_Nr());
			preStm.setString(3, dwzData.getCsvStatus());
			preStm.setString(4, dwzData.getCsvSpielername());
			preStm.setString(5, dwzData.getCsvGeschlecht());
			preStm.setString(6, dwzData.getCsvSpielberechtigung());
			preStm.setInt(7, dwzData.getCsvGeburtsjahr());
			preStm.setInt(8, dwzData.getCsvLetzte_Auswertung());
			preStm.setInt(9, dwzData.getCsvDWZ());
			preStm.setInt(10, dwzData.getCsvIndex());
			preStm.setInt(11, dwzData.getCsvFIDE_Elo());
			preStm.setString(12, dwzData.getCsvFIDE_Titel());
			preStm.setInt(13, dwzData.getCsvFIDE_ID());
			preStm.setString(14, dwzData.getCsvFIDE_Land());

			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			ResultSet rs = preStm.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);

			}
			preStm.close();

		}
		return id;
	}

	@Override
	public boolean updateDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DWZData getDWZData(String zps, String mgl) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
