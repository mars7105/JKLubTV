package de.turnierverwaltung.mysql;

import java.nio.charset.Charset;
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

import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;

public class SQLiteSpielerDAO implements SpielerDAO {
	private Connection dbConnect;

	public SQLiteSpielerDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createSpielerTable() {
		String sql = "CREATE TABLE spieler (idSpieler INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " Name VARCHAR, Kuerzel VARCHAR, DWZ VARCHAR, Age INTEGER)" + ";";

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
	public boolean deleteSpieler(int id) {
		boolean ok = false;
		String sql = "delete from spieler where idSpieler=?" + ";";
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setInt(1, id);
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				preStm.close();
				ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

		}
		return ok;
	}

	@Override
	public ArrayList<Gruppe> findSpieler(int id) {
		String sql = "Select * from turnier_has_spieler, spieler where Gruppe_idGruppe =" + id
				+ " AND Spieler_idSpieler = idSpieler" + ";";
		ArrayList<Gruppe> gruppenListe = new ArrayList<Gruppe>();

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					int idGruppe = rs.getInt("idGruppe");
					String gruppenName = rs.getString("Gruppenname");
					gruppenListe.add(new Gruppe(idGruppe, gruppenName));
				}
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return gruppenListe;
	}

	@Override
	public ArrayList<Spieler> getAllSpieler() {
		String sql = "Select * from spieler" + ";";
		ArrayList<Spieler> spielerListe = new ArrayList<Spieler>();

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {

					int idSpieler = rs.getInt("idSpieler");
					String name = rs.getString("Name");
					String kuerzel = rs.getString("kuerzel");
					String dwz = rs.getString("dwz");
					int age = 2;
					try {
						age = rs.getInt("Age");
					} catch (SQLException e) {

						alterTableAge();

					}
					// test
					Charset UTF8_CHARSET = Charset.forName("UTF-8");	
					byte[] bname = name.getBytes();
					String nameUTF8 = new String(bname, UTF8_CHARSET);
					byte[] bkuerzel = kuerzel.getBytes();
					String kuerzelUTF8 = new String(bkuerzel, UTF8_CHARSET);				
					// Test
					spielerListe.add(new Spieler(idSpieler, nameUTF8, kuerzelUTF8, dwz, age));
				}
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return spielerListe;

	}

	@Override
	public int insertSpieler(String name, String dwz, String kuerzel, int age) {

		String sql;
		int id = -1;

		sql = "Insert into spieler (DWZ, Kuerzel, Name, Age) values (?,?,?,?)" + ";";

		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				preStm.setString(1, dwz);
				preStm.setString(2, kuerzel);
				preStm.setString(3, name);
				try {
					preStm.setInt(4, age);
				} catch (SQLException e) {
					alterTableAge();
					preStm.setInt(4, age);
				}
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
	public ArrayList<Spieler> selectAllSpieler(int idGruppe) {
		String sql = "Select * from turnier_has_spieler, spieler where Gruppe_idGruppe = " + idGruppe
				+ " AND Spieler_idSpieler = idSpieler" + ";";
		ArrayList<Spieler> spielerListe = new ArrayList<Spieler>();

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					int idSpieler = rs.getInt("idSpieler");
					String name = rs.getString("Name");
					String kuerzel = rs.getString("kuerzel");
					String dwz = rs.getString("dwz");
					int age = 2;
					try {
						age = rs.getInt("Age");
					} catch (SQLException e) {
						alterTableAge();
					}
					spielerListe.add(new Spieler(idSpieler, name, kuerzel, dwz, age));
				}
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return spielerListe;

	}

	@Override
	public boolean updateSpieler(Spieler spieler) {
		boolean ok = false;
		String sql = "update spieler set Name = ?, Kuerzel = ?" + ", DWZ = ?, Age = ? where idSpieler="
				+ spieler.getSpielerId() + ";";
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setString(1, spieler.getName());
				preStm.setString(2, spieler.getKuerzel());
				preStm.setString(3, spieler.getDwz());
				try {
					preStm.setInt(4, spieler.getAge());
				} catch (SQLException e) {
					alterTableAge();
				}
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				preStm.close();
				ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return ok;
	}

	private void alterTableAge() {
		

		String sql = "ALTER TABLE spieler ADD Age INTEGER  DEFAULT(2)" + ";";
		Statement stmt;
		if (this.dbConnect != null) {
			try {
				// create a database connection
				stmt = this.dbConnect.createStatement();
				stmt.setQueryTimeout(30); // set timeout to 30 sec.
				stmt.executeUpdate(sql);
				stmt.close();

			} catch (SQLException e) {

			}
		}
	}

}
