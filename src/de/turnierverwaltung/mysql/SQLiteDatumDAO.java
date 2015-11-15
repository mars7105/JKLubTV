package de.turnierverwaltung.mysql;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SQLiteDatumDAO implements DatumDAO {
	private Connection dbConnect;

	public SQLiteDatumDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createDatumTable() {
		String sql = "CREATE TABLE datum (idDatum INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
				+ "Startdatum VARCHAR, Enddatum VARCHAR);";

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

	@Override
	public boolean deleteDatum(int id) {
		boolean ok = false;
		String sql = "delete from datum where idDatum=?;"; 
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setInt(1, id);
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);				preStm.close();
				ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

		}
		return ok;
	}

	@Override
	public String[] findDatum(int id) {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertDatum(String startDatum, String endDatum) {
		String sql;
		sql = "Insert into datum (Startdatum,Enddatum) values (?,?);";
		int id = -1;
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preStm.setString(1, startDatum);
				preStm.setString(2, endDatum);
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				ResultSet rs = preStm.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);

				}
				preStm.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());

			}

		}
		return id;
	}

	@Override
	public ArrayList<String> selectAllDatum() {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public boolean updateDatum(int idDatum, String startDatum, String endDatum) {
		boolean ok = false;
		String sql = "update datum set Startdatum = ?, Enddatum = ? where idDatum="
				+ idDatum + ";";
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setString(1, startDatum);
				preStm.setString(2, endDatum);
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);				preStm.close();
				ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return ok;
	}

}
