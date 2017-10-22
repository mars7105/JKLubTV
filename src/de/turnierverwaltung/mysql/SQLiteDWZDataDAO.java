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
				+ "  'FIDE_Land'          char(3)               default NULL,\n"
				+ "  idSpieler            INTEGER NOT NULL,\n"
				+ "  idDWZData INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL\n" + ");";
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
	public void deleteDWZ(int spielerId) throws SQLException {
		String sql = "delete from dwz_spieler where idSpieler=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setInt(1, spielerId);
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();

		}

	}

	@Override
	public void insertDWZ(DWZData dwzData) throws SQLException {
		String sql;

		sql = "Insert into dwz_spieler (ZPS, Mgl_Nr, Status, Spielername, Geschlecht, Spielberechtigung, Geburtsjahr, Letzte_Auswertung, DWZ, DWZ_Index, FIDE_Elo, FIDE_Titel, FIDE_ID, FIDE_Land, idSpieler) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
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
			preStm.setInt(15, dwzData.getSpielerId());
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			// ResultSet rs = preStm.getGeneratedKeys();

			preStm.close();

		}

	}

	@Override
	public void updateDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public DWZData getDWZData(int spielerId) throws SQLException {
		String sql = "Select * from dwz_spieler WHERE idSpieler=" + spielerId + ";";
		DWZData dwzData = new DWZData();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dwzData.setSpielerId(spielerId);
				dwzData.setCsvZPS(rs.getString("ZPS"));
				dwzData.setCsvMgl_Nr(rs.getString("Mgl_Nr"));
				dwzData.setCsvStatus(rs.getString("Status"));
				dwzData.setCsvSpielername(rs.getString("Spielername"));
				dwzData.setCsvGeschlecht(rs.getString("Geschlecht"));
				dwzData.setCsvSpielberechtigung(rs.getString("Spielberechtigung"));
				dwzData.setCsvGeburtsjahr(rs.getInt("Geburtsjahr"));
				dwzData.setCsvLetzte_Auswertung(rs.getInt("Letzte_Auswertung"));
				dwzData.setCsvDWZ(rs.getInt("DWZ"));
				dwzData.setCsvIndex(rs.getInt("DWZ_Index"));
				dwzData.setCsvFIDE_Elo(rs.getInt("FIDE_Elo"));
				dwzData.setCsvFIDE_Titel(rs.getString("FIDE_Titel"));
				dwzData.setCsvFIDE_ID(rs.getInt("FIDE_ID"));
				dwzData.setCsvFIDE_Land(rs.getString("FIDE_Land"));

			}

			stmt.close();

		}
		return dwzData;
	}
	@Override
	public boolean playerExist(DWZData dwzData) {
		String sql = "Select * from dwz_spieler where ZPS LIKE '" + dwzData.getCsvZPS()
				+ "' AND Mgl_Nr LIKE '%" + dwzData.getCsvMgl_Nr() + "';";

		int id = -1;
		Statement stmt;
		if (this.dbConnect != null) {

			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					id = rs.getInt("idSpieler");

				}
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Boolean returnStatement = false;
		if (id >= 0) {
			returnStatement = true;
		}

		return returnStatement;
	}
}
