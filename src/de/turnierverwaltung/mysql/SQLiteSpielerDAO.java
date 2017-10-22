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

	}

	@Override
	public void createSpielerTable() throws SQLException {
		String sql = "CREATE TABLE spieler (idSpieler INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " Name VARCHAR, Forename VARCHAR, Surname VARCHAR, Kuerzel VARCHAR, DWZ VARCHAR, ZPS VARCHAR, MGL VARCHAR, DWZIndex INTEGER, Age INTEGER)"
				+ ";";

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
	public boolean playerExist(Player neuerSpieler) {
		String sql = "Select * from spieler where ZPS LIKE '" + neuerSpieler.getDwzData().getCsvZPS()
				+ "' AND MGL LIKE '%" + neuerSpieler.getDwzData().getCsvMgl_Nr() + "';";

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

	@Override
	public ArrayList<Player> getAllSpieler() throws SQLException {
		String sql = "Select * from spieler ORDER BY Surname ASC;";
		ArrayList<Player> spielerListe = new ArrayList<Player>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idSpieler = rs.getInt("idSpieler");
				String name = rs.getString("Name");
				String foreName = rs.getString("ForeName");
				String surName = rs.getString("SurName");
				String kuerzel = rs.getString("kuerzel");
				String dwz = rs.getString("dwz");
				String zps = rs.getString("ZPS");
				String mgl = rs.getString("MGL");
				int dwzindex = rs.getInt("DWZIndex");
				int age = rs.getInt("Age");

				Player player = null;

				if (foreName.length() == 0 && surName.length() == 0) {
					player = new Player(idSpieler, name, kuerzel, dwz, age, zps, mgl, dwzindex);

				} else {

					player = new Player(idSpieler, foreName, surName, kuerzel, dwz, dwzindex, age, zps, mgl);
				}
				spielerListe.add(player);
			}

			stmt.close();

		}
		return spielerListe;

	}

	@Override
	public ArrayList<Player> getAllSpielerOrderByZPS() throws SQLException {
		String sql = "Select * from spieler ORDER BY ZPS ASC;";
		ArrayList<Player> spielerListe = new ArrayList<Player>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idSpieler = rs.getInt("idSpieler");
				String name = rs.getString("Name");
				String foreName = rs.getString("ForeName");
				String surName = rs.getString("SurName");
				String kuerzel = rs.getString("kuerzel");
				String dwz = rs.getString("dwz");
				String zps = rs.getString("ZPS");
				String mgl = rs.getString("MGL");
				int dwzindex = rs.getInt("DWZIndex");
				int age = rs.getInt("Age");

				Player player = null;

				if (foreName.length() == 0 && surName.length() == 0) {
					player = new Player(idSpieler, name, kuerzel, dwz, age, zps, mgl, dwzindex);

				} else {

					player = new Player(idSpieler, foreName, surName, kuerzel, dwz, dwzindex, age, zps, mgl);
				}
				spielerListe.add(player);
			}

			stmt.close();

		}
		return spielerListe;

	}

	@Override
	public int insertSpieler(Player spieler) throws SQLException {

		String sql;
		int id = -1;

		sql = "Insert into spieler (Name, Forename, Surname, DWZ, Kuerzel, ZPS, MGL, DWZIndex, Age) values (?,?,?,?,?,?,?,?,?)"
				+ ";";

		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preStm.setString(1, spieler.getName());
			preStm.setString(2, spieler.getForename());
			preStm.setString(3, spieler.getSurname());
			preStm.setString(4, spieler.getDwz());
			preStm.setString(5, spieler.getKuerzel());
			preStm.setString(6, spieler.getDwzData().getCsvZPS());
			preStm.setString(7, spieler.getDwzData().getCsvMgl_Nr());
			preStm.setInt(8, spieler.getDwzData().getCsvIndex());
			preStm.setInt(9, spieler.getAge());
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
				+ " AND Spieler_idSpieler = idSpieler" + " ORDER BY Surname ASC;";
		ArrayList<Player> spielerListe = new ArrayList<Player>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int idSpieler = rs.getInt("idSpieler");
				String name = rs.getString("Name");
				String foreName = rs.getString("Forename");
				String surName = rs.getString("Surname");
				String kuerzel = rs.getString("kuerzel");
				String dwz = rs.getString("dwz");
				String zps = rs.getString("ZPS");
				String mgl = rs.getString("MGL");
				int dwzindex = rs.getInt("DWZIndex");

				int age = rs.getInt("Age");
				Player player = null;

				if (foreName.length() == 0 && surName.length() == 0) {
					player = new Player(idSpieler, name, kuerzel, dwz, age, zps, mgl, dwzindex);

				} else {
					player = new Player(idSpieler, foreName, surName, kuerzel, dwz, dwzindex, age, zps, mgl);
				}
				spielerListe.add(player);
			}
			stmt.close();

		}
		return spielerListe;

	}

	@Override
	public boolean updateSpieler(Player spieler) throws SQLException {
		boolean ok = false;
		String sql = "update spieler set Name = ?,Forename = ?,Surname = ?, Kuerzel = ?"
				+ ", DWZ = ?, ZPS = ?, MGL = ?, DWZIndex = ?, Age = ? where idSpieler=" + spieler.getSpielerId() + ";";
		if (this.dbConnect != null) {
			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, spieler.getName());
			preStm.setString(2, spieler.getForename());
			preStm.setString(3, spieler.getSurname());
			preStm.setString(4, spieler.getKuerzel());
			preStm.setString(5, spieler.getDwz());
			preStm.setString(6, spieler.getDwzData().getCsvZPS());
			preStm.setString(7, spieler.getDwzData().getCsvMgl_Nr());
			preStm.setInt(8, spieler.getDwzData().getCsvIndex());
			preStm.setInt(9, spieler.getAge());

			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	public void alterTables() {
		alterTableAge();
		alterTableName();
	}

	private void alterTableAge() {
		if (!isFieldExist("Age", true)) {
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

	private void alterTableName() {
		if (!isFieldExist("Forename", false)) {
			String sql1 = "ALTER TABLE spieler ADD Forename VARCHAR DEFAULT ''" + ";";
			Statement stmt1;
			if (this.dbConnect != null) {
				try {
					// create a database connection
					stmt1 = this.dbConnect.createStatement();
					stmt1.setQueryTimeout(30); // set timeout to 30 sec.
					stmt1.executeUpdate(sql1);
					stmt1.close();

				} catch (SQLException e1) {

				}
			}
		}
		if (!isFieldExist("Surname", false)) {
			String sql2 = "ALTER TABLE spieler ADD Surname VARCHAR DEFAULT ''" + ";";
			Statement stmt2;
			if (this.dbConnect != null) {
				try {
					// create a database connection
					stmt2 = this.dbConnect.createStatement();
					stmt2.setQueryTimeout(30); // set timeout to 30 sec.
					stmt2.executeUpdate(sql2);
					stmt2.close();

				} catch (SQLException e2) {

				}
			}
		}
		if (!isFieldExist("Surname", false) && !isFieldExist("Forename", false)) {
			ArrayList<Player> spielerListe = new ArrayList<Player>();

			try {
				spielerListe = getAllSpieler();
				for (Player player : spielerListe) {
					player.extractNameToForenameAndSurename();
					updateSpieler(player);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (!isFieldExist("ZPS", false)) {
			String sql1 = "ALTER TABLE spieler ADD ZPS VARCHAR DEFAULT ''" + ";";
			Statement stmt1;
			if (this.dbConnect != null) {
				try {
					// create a database connection
					stmt1 = this.dbConnect.createStatement();
					stmt1.setQueryTimeout(30); // set timeout to 30 sec.
					stmt1.executeUpdate(sql1);
					stmt1.close();

				} catch (SQLException e1) {

				}
			}
		}
		if (!isFieldExist("MGL", false)) {
			String sql1 = "ALTER TABLE spieler ADD MGL VARCHAR DEFAULT ''" + ";";
			Statement stmt1;
			if (this.dbConnect != null) {
				try {
					// create a database connection
					stmt1 = this.dbConnect.createStatement();
					stmt1.setQueryTimeout(30); // set timeout to 30 sec.
					stmt1.executeUpdate(sql1);
					stmt1.close();

				} catch (SQLException e1) {

				}
			}
		}
		if (!isFieldExist("DWZIndex", false)) {
			String sql1 = "ALTER TABLE spieler ADD DWZIndex INTEGER DEFAULT -1" + ";";
			Statement stmt1;
			if (this.dbConnect != null) {
				try {
					// create a database connection
					stmt1 = this.dbConnect.createStatement();
					stmt1.setQueryTimeout(30); // set timeout to 30 sec.
					stmt1.executeUpdate(sql1);
					stmt1.close();

				} catch (SQLException e1) {

				}
			}
		}
	}

	public boolean isFieldExist(String fieldName, Boolean type) {

		boolean isExist = true;
		String sql = "SELECT " + fieldName + " FROM spieler;";
		Statement stmt;
		if (this.dbConnect != null) {

			try {
				stmt = this.dbConnect.createStatement();

				ResultSet rs = stmt.executeQuery(sql);

				rs.next();
				if (type == false) {
					@SuppressWarnings("unused")
					String string = rs.getString(fieldName);
				} else {
					@SuppressWarnings("unused")
					int number = rs.getInt(fieldName);
				}
				stmt.close();
			} catch (SQLException e) {
				isExist = false;
			}
		}
		return isExist;
	}

}
