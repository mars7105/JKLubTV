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

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;

public class SQLiteSpielerDAO implements SpielerDAO {
	private Connection dbConnect;

	public SQLiteSpielerDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
		alterTableAge();
	}

	@Override
	public void createSpielerTable() throws SQLException {
		String sql = "CREATE TABLE spieler (idSpieler INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " Name VARCHAR, Kuerzel VARCHAR, DWZ VARCHAR, Age INTEGER, Fidetitle VARCHAR)" + ";";

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
	public boolean deleteSpieler(int id) throws SQLException {
		boolean ok = false;
		String sql = "delete from spieler where idSpieler=?" + ";";
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
	public ArrayList<Group> findSpieler(int id) throws SQLException {
		String sql = "Select * from turnier_has_spieler, spieler where Gruppe_idGruppe =" + id
				+ " AND Spieler_idSpieler = idSpieler" + ";";
		ArrayList<Group> gruppenListe = new ArrayList<Group>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int idGruppe = rs.getInt("idGruppe");
				String gruppenName = rs.getString("Gruppenname");
				gruppenListe.add(new Group(idGruppe, gruppenName));
			}
			stmt.close();

		}
		return gruppenListe;
	}

	@Override
	public ArrayList<Player> getAllSpieler() throws SQLException {
		String sql = "Select * from spieler ORDER BY dwz ASC;";
		ArrayList<Player> spielerListe = new ArrayList<Player>();

		Statement stmt;
		if (this.dbConnect != null) {

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
				String fideTitle = "";
				try {
					fideTitle = rs.getString("Fidetitle");
				} catch (SQLException e) {

					alterTableFideTitle();

				}

				spielerListe.add(new Player(idSpieler, name, kuerzel, dwz, age, fideTitle));
			}
			stmt.close();

		}

		return spielerListe;

	}

	@Override
	public int insertSpieler(Player spieler) throws SQLException {

		String sql;
		int id = -1;

		sql = "Insert into spieler (DWZ, Kuerzel, Name, Age, Fidetitle) values (?,?,?,?,?)" + ";";

		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preStm.setString(1, spieler.getDwz());
			preStm.setString(2, spieler.getKuerzel());
			preStm.setString(3, spieler.getName());

			preStm.setInt(4, spieler.getAge());
			preStm.setString(5, spieler.getFideTitle());
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
	public ArrayList<Player> selectAllSpieler(int idGruppe) throws SQLException {
		String sql = "Select * from turnier_has_spieler, spieler where Gruppe_idGruppe = " + idGruppe
				+ " AND Spieler_idSpieler = idSpieler" + " ORDER BY dwz ASC;";
		ArrayList<Player> spielerListe = new ArrayList<Player>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int idSpieler = rs.getInt("idSpieler");
				String name = rs.getString("Name");
				String kuerzel = rs.getString("kuerzel");
				String dwz = rs.getString("dwz");
				int age = 2;

				age = rs.getInt("Age");
				String fideTtle = "";
				fideTtle = rs.getString("Fidetitle");

				spielerListe.add(new Player(idSpieler, name, kuerzel, dwz, age, fideTtle));
			}
			stmt.close();

		}
		return spielerListe;

	}

	@Override
	public boolean updateSpieler(Player spieler) throws SQLException {
		boolean ok = false;
		String sql = "update spieler set Name = ?, Kuerzel = ?" + ", DWZ = ?, Age = ?, Fidetitle = ? where idSpieler="
				+ spieler.getSpielerId() + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, spieler.getName());
			preStm.setString(2, spieler.getKuerzel());
			preStm.setString(3, spieler.getDwz());
			try {
				preStm.setInt(4, spieler.getAge());
			} catch (SQLException e) {
				alterTableAge();
			}
			try {
				preStm.setString(5, spieler.getFideTitle());
			} catch (SQLException e) {
				alterTableFideTitle();

			}
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	private void alterTableAge() {

		String sql = "ALTER TABLE spieler ADD Age INTEGER DEFAULT(2)" + ";";
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

	private void alterTableFideTitle() {

		String sql = "ALTER TABLE spieler ADD Fidetitle VARCHAR" + ";";
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
