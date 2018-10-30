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
import de.turnierverwaltung.model.Player;

public class SQLiteTurnier_has_SpielerDAO implements Turnier_has_SpielerDAO {
	private Connection dbConnect;

	public SQLiteTurnier_has_SpielerDAO() {
		dbConnect = null;
		dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createTurnier_has_SpielerTable() throws SQLException {
		final String sql = "CREATE TABLE turnier_has_spieler (idturnier_has_spieler "
				+ "INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
				+ "Gruppe_idGruppe INTEGER, Spieler_idSpieler INTEGER)" + ";";

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
	public boolean deleteTurnier_has_Spieler(final int idGruppe, final int idSpieler) throws SQLException {
		boolean ok = false;
		final String sql = "delete from turnier_has_spieler where Spieler_idSpieler=" + idSpieler + " and "
				+ "Gruppe_idGruppe=" + idGruppe + ";";

		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql);
			preStm.addBatch();
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;
		}

		return ok;

	}

	@Override
	public ArrayList<Integer> findSpielerisinTurnier_has_Spieler(final Player spieler) throws SQLException {
		final ArrayList<Integer> findTurnier_has_Spieler = new ArrayList<Integer>();
		final String sql = "Select * " + "from turnier_has_spieler " + "where Spieler_idSpieler="
				+ spieler.getSpielerId() + ";";
		Statement stmt;
		ResultSet rs;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				findTurnier_has_Spieler.add(rs.getInt("Spieler_idSpieler"));

			}

			stmt.close();

		}
		return findTurnier_has_Spieler;
	}

	@Override
	public String findTurnier_has_Spieler(final int id) {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertTurnier_has_Spieler(final int idGruppe, final int idSpieler) throws SQLException {
		String sql;
		sql = "Insert into turnier_has_spieler (Gruppe_idGruppe, Spieler_idSpieler) values (?,?);";
		// + "COMMIT;";
		int id = -1;
		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preStm.setInt(1, idGruppe);
			preStm.setInt(2, idSpieler);
			preStm.addBatch();
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			final ResultSet rs = preStm.getGeneratedKeys();
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
	public boolean updateTurnier_has_Spieler(final Group group, final Player player) throws SQLException {
		boolean ok = false;

		if (dbConnect != null) {

			dbConnect.setAutoCommit(false);
			final String sql = "update turnier_has_spieler set Spieler_idSpieler = ?" + " where Gruppe_idGruppe = "
					+ group.getGruppeId() + ";";

			final PreparedStatement preStm = dbConnect.prepareStatement(sql);

			preStm.setInt(1, player.getSpielerId());
			preStm.addBatch();
			dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}
}
