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

public class SQLiteDSBVereineDAO implements DSBVereineDAO {
	private Connection dbConnect;

	public SQLiteDSBVereineDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDSBDAOFactory.createConnection();
	}

	@Override
	public void createDSBVereineTable() {
		String sql = "CREATE TABLE dwz_vereine (" + "ZPS                varchar(5)   NOT NULL default '',"
				+ "LV                 char(1)      NOT NULL default '',"
				+ "Verband            char(3)      NOT NULL default '',"
				+ "Vereinname         varchar(40)  NOT NULL default ''," + "PRIMARY KEY (ZPS)" + ") TYPE=MyISAM";

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
