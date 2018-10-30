package de.turnierverwaltung.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.turnierverwaltung.model.rating.DWZData;

public class SQLiteDWZDataDAO implements DWZDataDAO {

	private Connection dbConnect;

	public SQLiteDWZDataDAO() {
		dbConnect = null;
		dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createDWZTable() throws SQLException {
		final String sql = "CREATE TABLE 'dwz_spieler' (\n"
				+ "  'ZPS'                varchar(5)   NOT NULL default '',\n"
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
				+ "  'idSpieler'            INTEGER NOT NULL,\n"
				+ "  'idDWZData' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL\n" + ");";
		Statement stmt;
		if (dbConnect != null) {

			// create a database connection

			stmt = dbConnect.createStatement();

			stmt.setQueryTimeout(30); // set timeout to 30 sec.
			stmt.executeUpdate(sql);
			stmt.close();

		}

	}

	@Override
	public void deleteDWZ(final int spielerId) throws SQLException {
		final String sql = "delete from dwz_spieler where idSpieler=?" + ";";
		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql);
			preStm.setInt(1, spielerId);
			preStm.addBatch();
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();

		}

	}

	@Override
	public void flush(final ArrayList<DWZData> csvDataArray) throws SQLException {
		String sql;

		sql = "Insert into dwz_spieler (ZPS, Mgl_Nr, Status, Spielername, Geschlecht, Spielberechtigung, Geburtsjahr, Letzte_Auswertung, DWZ, DWZ_Index, FIDE_Elo, FIDE_Titel, FIDE_ID, FIDE_Land, idSpieler) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
				+ ";";
		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql);

			for (final DWZData dwzData : csvDataArray) {
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

			}
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
		}

	}

	@Override
	public DWZData getDWZData(final int spielerId) throws SQLException {
		final String sql = "Select * from dwz_spieler WHERE idSpieler=" + spielerId + ";";
		final DWZData dwzData = new DWZData();

		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
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
	public ArrayList<DWZData> getDWZDataByFideId(final int eingabe) throws SQLException {
		final String sql = "Select * from dwz_spieler WHERE FIDE_ID LIKE '" + eingabe + "' LIMIT 20;";
		final DWZData dwzData = new DWZData();
		final ArrayList<DWZData> dwzDataArray = new ArrayList<DWZData>();

		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				int sId = -1;
				final String spielerid = rs.getString("idSpieler");
				try {
					sId = Integer.parseInt(spielerid);
				} catch (final NumberFormatException e) {
					sId = -1;
				}
				dwzData.setSpielerId(sId);
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
				dwzDataArray.add(dwzData);
			}

			stmt.close();

		}
		return dwzDataArray;
	}

	@Override
	public ArrayList<DWZData> getDWZDataByName(final String eingabe) throws SQLException {
		final String sql = "Select * from dwz_spieler WHERE Spielername LIKE '%" + eingabe + "%' LIMIT 40;";

		final ArrayList<DWZData> dwzDataArray = new ArrayList<DWZData>();

		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final DWZData dwzData = new DWZData();
				int sId = -1;
				final String spielerid = rs.getString("idSpieler");
				try {
					sId = Integer.parseInt(spielerid);
				} catch (final NumberFormatException e) {
					sId = -1;
				}
				dwzData.setSpielerId(sId);
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
				dwzDataArray.add(dwzData);
			}

			stmt.close();

		}
		return dwzDataArray;
	}

	@Override
	public ArrayList<DWZData> getPlayerOfVerein(final String zps) throws SQLException {
		final String sql = "Select * from dwz_spieler WHERE ZPS LIKE '" + zps + "' ORDER BY Spielername ASC;";

		final ArrayList<DWZData> dwzDataArray = new ArrayList<DWZData>();
		Statement stmt;

		if (dbConnect != null) {

			stmt = dbConnect.createStatement();

			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final DWZData dwzData = new DWZData();
				int sId = -1;
				final String spielerid = rs.getString("idSpieler");
				try {
					sId = Integer.parseInt(spielerid);
				} catch (final NumberFormatException e) {
					sId = -1;
				}
				dwzData.setSpielerId(sId);
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
				dwzDataArray.add(dwzData);

			}
			stmt.close();

		}

		return dwzDataArray;

	}

	public ArrayList<DWZData> getPlayerByZPSMGL(final String zps, final String mgl) throws SQLException {
		final String sql = "Select * from dwz_spieler WHERE ZPS LIKE '" + zps + "' AND Mgl_Nr LIKE '" + mgl
				+ "' ORDER BY Spielername ASC;";

		final ArrayList<DWZData> dwzDataArray = new ArrayList<DWZData>();
		Statement stmt;

		if (dbConnect != null) {

			stmt = dbConnect.createStatement();

			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final DWZData dwzData = new DWZData();
				int sId = -1;
				final String spielerid = rs.getString("idSpieler");
				try {
					sId = Integer.parseInt(spielerid);
				} catch (final NumberFormatException e) {
					sId = -1;
				}
				dwzData.setSpielerId(sId);
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
				dwzDataArray.add(dwzData);

			}
			stmt.close();

		}

		return dwzDataArray;

	}

	@Override
	public void insertDWZ(final DWZData dwzData) throws SQLException {
		String sql;

		sql = "Insert into dwz_spieler (ZPS, Mgl_Nr, Status, Spielername, Geschlecht, Spielberechtigung, Geburtsjahr, Letzte_Auswertung, DWZ, DWZ_Index, FIDE_Elo, FIDE_Titel, FIDE_ID, FIDE_Land, idSpieler) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
				+ ";";

		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			// ResultSet rs = preStm.getGeneratedKeys();

			preStm.close();

		}

	}

	@Override
	public boolean playerExist(final DWZData dwzData) throws SQLException {
		final String sql = "Select idSpieler from dwz_spieler where ZPS LIKE '" + dwzData.getCsvZPS()
				+ "' AND Mgl_Nr LIKE '%" + dwzData.getCsvMgl_Nr() + "';";

		int id = -1;
		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("idSpieler");

			}
			stmt.close();

		}
		Boolean returnStatement = false;
		if (id > 0) {
			returnStatement = true;
		}

		return returnStatement;
	}

	@Override
	public boolean playerFideExist(final int fideId) throws SQLException {

		final String sql = "Select idSpieler from dwz_spieler where FIDE_ID LIKE '" + fideId + "';";

		int id = -1;
		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("idSpieler");

			}
			stmt.close();

		}
		Boolean returnStatement = false;
		if (id > 0) {
			returnStatement = true;
		}

		return returnStatement;
	}

	@Override
	public void updateDWZ(final DWZData dwzData) throws SQLException {
		final String sql = "update dwz_spieler set ZPS = ?, Mgl_Nr = ?, Status = ?, Spielername = ?, Geschlecht = ?, Spielberechtigung = ?, Geburtsjahr = ?, Letzte_Auswertung = ?, DWZ = ?, DWZ_Index = ?, FIDE_Elo = ?, FIDE_Titel = ?, FIDE_ID = ?, FIDE_Land = ? where idSpieler = "
				+ dwzData.getSpielerId() + ";";

		if (dbConnect != null) {
			final PreparedStatement preStm = dbConnect.prepareStatement(sql);
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
			// preStm.setInt(15, dwzData.getSpielerId());
			preStm.addBatch();
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
		}

	}
}
