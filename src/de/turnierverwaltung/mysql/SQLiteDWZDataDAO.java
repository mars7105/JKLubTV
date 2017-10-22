package de.turnierverwaltung.mysql;

import java.sql.Connection;
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
	public void createDWZTable() {
		String sql = "CREATE TABLE 'dwz_spieler' (\n" + "  'ZPS'                varchar(5)   NOT NULL default '',\n"
				+ "  'Mgl_Nr'             char(4)      NOT NULL default '',\n"
				+ "  'Status'             char(1)               default NULL,\n"
				+ "  'Spielername'        varchar(40)  NOT NULL default '',\n"
				+ "  'Spielername_G'      varchar(40)  NOT NULL default '',\n"
				+ "  'Geschlecht'         char(1)               default NULL,\n"
				+ "  'Spielberechtigung'  char(1)      NOT NULL default '',\n"
				+ "  'Geburtsjahr'        year(4)      NOT NULL default '0000',\n"
				+ "  'Letzte_Auswertung'  INTEGER unsigned default NULL,\n"
				+ "  'DWZ'                INTEGER  unsigned default NULL,\n"
				+ "  'DWZ_Index'          INTEGER  unsigned default NULL,\n"
				+ "  'FIDE_Elo'           INTEGER  unsigned default NULL,\n"
				+ "  'FIDE_Titel'         char(2)               default NULL,\n"
				+ "  'FIDE_ID'            INTEGER       unsigned default NULL,\n"
				+ "  'FIDE_Land'          char(3)               default NULL,\n" 
				+ "  PRIMARY KEY  ('ZPS','Mgl_Nr')\n"
				+  ");";
		Statement stmt;
		if (this.dbConnect != null) {

			// create a database connection
			try {
				stmt = this.dbConnect.createStatement();

				stmt.setQueryTimeout(30); // set timeout to 30 sec.
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
