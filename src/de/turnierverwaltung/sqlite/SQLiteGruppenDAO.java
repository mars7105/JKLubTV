package de.turnierverwaltung.sqlite;

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

public class SQLiteGruppenDAO implements GruppenDAO {
	private Connection dbConnect;

	public SQLiteGruppenDAO() {
		this.dbConnect = null;

		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createGruppenTable() throws SQLException {
		String sql = "CREATE TABLE gruppen (idGruppe INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " TurnierId INTEGER, Gruppenname VARCHAR);";

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
	public boolean deleteGruppe(int id) throws SQLException {
		boolean ok = false;
		String sql = "delete from gruppen where idGruppe=?;";
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
	public Group findGruppe(int id) throws SQLException {
		String sql = "Select Gruppenname, TurnierId " + "from gruppen " + "where idGruppe=" + id + ";";

		Group gruppe = null;

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String gruppenName = rs.getString("Gruppenname");
				gruppe = new Group(id, gruppenName);

			}
			stmt.close();

		}
		return gruppe;
	}

	@Override
	public int insertGruppe(String gruppenName, int turnierId) throws SQLException {
		String sql;
		int id = -1;

		sql = "Insert into gruppen (Gruppenname, TurnierId) values (?,?);";
		id = -1;
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preStm.setString(1, gruppenName);
			preStm.setInt(2, turnierId);

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
	public ArrayList<Group> selectAllGruppen(int idTurnier) throws SQLException {
		String sql = "Select * from gruppen where TurnierId=" + idTurnier + ";";
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
	public boolean updateGruppe(Group gruppe) throws SQLException {
		boolean ok = false;
		String sql = "update gruppen set Gruppenname = ? where idGruppe=" + gruppe.getGruppeId() + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, gruppe.getGruppenName());
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

}
