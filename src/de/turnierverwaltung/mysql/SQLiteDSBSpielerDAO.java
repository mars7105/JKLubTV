package de.turnierverwaltung.mysql;

//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class SQLiteDSBSpielerDAO implements DSBSpielerDAO {
	private Connection dbConnect;

	public SQLiteDSBSpielerDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDSBDAOFactory.createConnection();
	}

	@Override
	public void createDSBSpielerTable() {
		String sql = "CREATE TABLE dwz_spieler (" + "ZPS                varchar(5)   NOT NULL default '',"
				+ "Mgl_Nr             char(4)      NOT NULL default '',"
				+ "Status             char(1)               default NULL,"
				+ "Spielername        varchar(40)  NOT NULL default '',"
				+ "Spielername_G      varchar(40)  NOT NULL default '',"
				+ "Geschlecht         char(1)               default NULL,"
				+ "Spielberechtigung  char(1)      NOT NULL default '',"
				+ "Geburtsjahr        year(4)      NOT NULL default '0000',"
				+ "Letzte_Auswertung  mediumint(6) unsigned default NULL,"
				+ "DWZ                smallint(4)  unsigned default NULL,"
				+ "DWZ_Index          smallint(3)  unsigned default NULL,"
				+ "FIDE_Elo           smallint(4)  unsigned default NULL,"
				+ "FIDE_Titel         char(2)               default NULL,"
				+ "FIDE_ID            int(8)       unsigned default NULL,"
				+ "FIDE_Land          char(3)               default NULL," + "PRIMARY KEY  (ZPS,Mgl_Nr),"
				+ "KEY FIDE_ID (FIDE_ID)," + " KEY Spielername_G (Spielername_G)" + ") TYPE=MyISAM";

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				// create a database connection
				stmt = this.dbConnect.createStatement();
				stmt.setQueryTimeout(30); // set timeout to 30 sec.
				stmt.executeUpdate(sql);
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

	}

}
