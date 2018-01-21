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

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.model.Tournament;

public class SQLiteTurnierDAO implements TurnierDAO {
	private Connection dbConnect;

	public SQLiteTurnierDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createTurnierTable() throws SQLException {
		String sql = "CREATE TABLE turnier (idTurnier INTEGER PRIMARY KEY "
				+ "AUTOINCREMENT  NOT NULL , Datum_idDatum INTEGER, Turniername VARCHAR)" + ";";

		Statement stmt;
		if (this.dbConnect != null) {

			// create a database connection
			stmt = this.dbConnect.createStatement();
			// stmt.setQueryTimeout(30); // set timeout to 30 sec.
			// stmt.executeUpdate("DROP TABLE IF EXISTS turnier;");
			stmt.executeUpdate(sql);
			stmt.close();

		}

	}

	@Override
	public boolean deleteTurnier(int id) {
		boolean ok = false;
		String datum = "Select Datum_idDatum from turnier where idTurnier=" + id + ";";
		String gruppe = "Select idGruppe from gruppen where TurnierId=" + id + ";";
		String sql = "delete from turnier where idTurnier=?" + ";";
		Statement stmt;
		int datumId = 0;
		int gruppeId = 0;
		ResultSet rs;
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);

				// Datum l�schen
				stmt = this.dbConnect.createStatement();
				rs = stmt.executeQuery(datum);
				while (rs.next()) {
					datumId = rs.getInt("Datum_idDatum");
					sql = "delete from datum where idDatum=?" + ";";
					preStm = this.dbConnect.prepareStatement(sql);
					preStm.setInt(1, datumId);
					preStm.addBatch();
					this.dbConnect.setAutoCommit(false);
					preStm.executeBatch();
					this.dbConnect.setAutoCommit(true);
					preStm.close();
				}
				stmt.close();

				// Turnier Gruppen und Partien l�schen
				stmt = this.dbConnect.createStatement();
				rs = stmt.executeQuery(gruppe);
				while (rs.next()) {
					// Partien l�schen
					gruppeId = rs.getInt("idGruppe");
					sql = "delete from partien where idGruppe=?" + ";";
					preStm = this.dbConnect.prepareStatement(sql);
					preStm.setInt(1, gruppeId);
					preStm.addBatch();
					this.dbConnect.setAutoCommit(false);
					preStm.executeBatch();
					this.dbConnect.setAutoCommit(true); // Gruppen l�schen
					sql = "delete from gruppen where idGruppe=?" + ";";
					preStm = this.dbConnect.prepareStatement(sql);
					preStm.setInt(1, gruppeId);
					preStm.addBatch();
					this.dbConnect.setAutoCommit(false);
					preStm.executeBatch();
					this.dbConnect.setAutoCommit(true); // Turnier_has_Spieler
														// l�schen
					sql = "delete from turnier_has_spieler where Gruppe_idGruppe=?" + ";";
					preStm = this.dbConnect.prepareStatement(sql);
					preStm.setInt(1, gruppeId);
					preStm.addBatch();
					this.dbConnect.setAutoCommit(false);
					preStm.executeBatch();
					this.dbConnect.setAutoCommit(true);
					preStm.close();

				}
				// Turnier l�schen
				sql = "delete from turnier where idTurnier=?" + ";";
				preStm = this.dbConnect.prepareStatement(sql);
				preStm.setInt(1, id);
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				preStm.close();
				stmt.close();
				preStm.close();
				ok = true;
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			}

		}
		return ok;
	}

	@Override
	public Tournament findTurnier(int id, PropertiesControl prop) {
		String sql = "Select Startdatum, Enddatum, Turniername, Datum_idDatum, idDatum" + "from turnier,datum "
				+ "where idTurnier=" + id + " AND Datum_idDatum = idDatum" + ";";
		Tournament turnier = null;

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					int turnierId = rs.getInt("idTurnier");
					String turnierName = rs.getString("Turniername");
					String startDatum = rs.getString("Startdatum");
					String endDatum = rs.getString("Enddatum");
					EventDate startevent = new EventDate(startDatum);
					EventDate endevent = new EventDate(endDatum);
					turnier = new Tournament(turnierId, turnierName, startevent.getDateString(),
							endevent.getDateString(), prop.getOnlyTables(), prop.getNoDWZ(), prop.getNoFolgeDWZ(),
							prop.getNoELO(), prop.getNoFolgeELO());

				}
				stmt.close();

			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());

			}
		}
		return turnier;
	}

	@Override
	public int insertTurnier(String turnierName, int datumId) {

		String sql;
		sql = "Insert into turnier (Turniername, Datum_idDatum) values (?,?);";
		int id = -1;
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				preStm.setString(1, turnierName);
				preStm.setInt(2, datumId);
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
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());

			}

		}
		return id;

	}

	@Override
	public ArrayList<Tournament> selectAllTurnier(PropertiesControl prop) throws SQLException {
		String sql = "Select * from turnier, datum where Datum_idDatum = idDatum" + " ORDER BY idTurnier DESC" + ";";
		ArrayList<Tournament> turnierListe = new ArrayList<Tournament>();

		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("idTurnier");
				String turnierName = rs.getString("Turniername");
				String startDatum = rs.getString("Startdatum");
				String endDatum = rs.getString("Enddatum");
				EventDate startevent = new EventDate(startDatum);
				EventDate endevent = new EventDate(endDatum);
				turnierListe.add(new Tournament(id, turnierName, startevent.getDateString(), endevent.getDateString(),
						prop.getOnlyTables(), prop.getNoDWZ(), prop.getNoFolgeDWZ(), prop.getNoELO(),
						prop.getNoFolgeELO()));
			}
			stmt.close();

		}
		return turnierListe;
	}

	@Override
	public boolean updateTurnier(Tournament turnier) {
		boolean ok = false;
		String datum = "Select Datum_idDatum from turnier where idTurnier=" + turnier.getTurnierId() + ";";
		int datumId = -1;
		if (this.dbConnect != null) {
			// Datum l�schen
			Statement stmt;
			ResultSet rs;

			try {
				stmt = this.dbConnect.createStatement();

				rs = stmt.executeQuery(datum);

				while (rs.next()) {
					datumId = rs.getInt("Datum_idDatum");
				}
				stmt.close();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			}
		}
		// String sql0 = "BEGIN;";
		String sql1 = "update turnier set Turniername = ?" + " where idTurnier = " + turnier.getTurnierId() + ";";
		String sql2 = "update datum set Startdatum = ?, Enddatum = ?" + " where idDatum = " + datumId + ";";

		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql1);
				preStm.setString(1, turnier.getTurnierName());
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				preStm.close();
				ok = true;
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			}
		}
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql2);

				preStm.setString(1, turnier.getStartDatum());
				preStm.setString(2, turnier.getEndDatum());
				preStm.addBatch();
				this.dbConnect.setAutoCommit(false);
				preStm.executeBatch();
				this.dbConnect.setAutoCommit(true);
				preStm.close();
				ok = true;
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(null);
				eh.fileSQLError(e.getMessage());
			}
		}
		return ok;
	}

}
