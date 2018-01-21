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
import java.util.ListIterator;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;

public class SQLiteTurnier_has_SpielerDAO implements Turnier_has_SpielerDAO {
	private Connection dbConnect;

	public SQLiteTurnier_has_SpielerDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createTurnier_has_SpielerTable() throws SQLException {
		String sql = "CREATE TABLE turnier_has_spieler (idturnier_has_spieler "
				+ "INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
				+ "Gruppe_idGruppe INTEGER, Spieler_idSpieler INTEGER)" + ";";

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
	public boolean deleteTurnier_has_Spieler(ArrayList<Integer> id) throws SQLException {
		boolean ok = false;
		String sql = "delete from turnier_has_spieler where Spieler_idSpieler=" + ";";
		ListIterator<Integer> li = id.listIterator();
		int idSp = -1;
		if (id.size() > 0) {
			if (this.dbConnect != null) {

				while (li.hasNext()) {
					idSp = li.next();
					PreparedStatement preStm = this.dbConnect.prepareStatement(sql + idSp);
					preStm.addBatch();
					this.dbConnect.setAutoCommit(false);
					preStm.executeBatch();
					this.dbConnect.setAutoCommit(true);
					preStm.close();
					ok = true;
				}

			}

		}
		return ok;
	}

	@Override
	public ArrayList<Integer> findSpielerisinTurnier_has_Spieler(Player spieler) throws SQLException {
		ArrayList<Integer> findTurnier_has_Spieler = new ArrayList<Integer>();
		String sql = "Select * " + "from turnier_has_spieler " + "where Spieler_idSpieler=" + spieler.getSpielerId()
				+ ";";
		Statement stmt;
		ResultSet rs;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				findTurnier_has_Spieler.add(rs.getInt("Spieler_idSpieler"));

			}

			stmt.close();

		}
		return findTurnier_has_Spieler;
	}

	@Override
	public String findTurnier_has_Spieler(int id) {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertTurnier_has_Spieler(int idGruppe, int idSpieler) throws SQLException {
		String sql;
		sql = "Insert into turnier_has_spieler (Gruppe_idGruppe, Spieler_idSpieler) values (?,?);";
		// + "COMMIT;";
		int id = -1;
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preStm.setInt(1, idGruppe);
			preStm.setInt(2, idSpieler);
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
	public ArrayList<String> selectAllTurnier_has_Spieler() {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public boolean updateTurnier_has_Spieler(Tournament turnier) {
		// TODO Automatisch generierter Methodenstub
		return false;
	}
}
