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

import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class SQLitePartienDAO implements PartienDAO {
	private Connection dbConnect;

	public SQLitePartienDAO() {
		dbConnect = null;
		dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createPartienTable() throws SQLException {
		final String sql = "CREATE TABLE partien (idPartie INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " idSpielerWeiss INTEGER NOT NULL , idSpielerSchwarz INTEGER, Runde INTEGER, Spieldatum VARCHAR, idGruppe INTEGER, Ergebnis INTEGER)"
				+ ";";

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
	public boolean deletePartien(final int id) throws SQLException {
		boolean ok = false;
		final String sql = "delete from partien where idPartie=?" + ";";
		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql);
			preStm.setInt(1, id);
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
	public String[] findPartien(final int id) throws SQLException {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public String getErgebnis(final int SpielerIDWeiss, final int SpielerIDSchwarz, final int idGruppe)
			throws SQLException {
		final String sql = "Select Ergebnis " + "from partien where idGruppe=" + idGruppe + " AND idSpielerWeiss="
				+ SpielerIDWeiss + " AND idSpielerSchwarz=" + SpielerIDSchwarz + ";";
		Statement stmt;
		int ergebnis = -1;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ergebnis = rs.getInt("Ergebnis");

			}
			stmt.close();

		}
		String ergebnisString = "";
		if (ergebnis == TournamentConstants.MYSQL_KEIN_ERGEBNIS) {
			ergebnisString = TournamentConstants.KEIN_ERGEBNIS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_WEISS) {
			ergebnisString = TournamentConstants.GEWINN;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			ergebnisString = TournamentConstants.VERLUST;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_REMIS) {
			ergebnisString = TournamentConstants.REMIS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			ergebnisString = TournamentConstants.GEWINN_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			ergebnisString = TournamentConstants.VERLUST_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE) {
			ergebnisString = TournamentConstants.VERLUST_KAMPFLOS_BEIDE;
		}
		return ergebnisString;
	}

	@Override
	public int insertPartien(final int idGruppe, final String spielDatum, final int Runde, final int ergebnis,
			final int spielerIdweiss, final int spielerIdschwarz) throws SQLException {

		String sql;
		int id = -1;

		sql = "Insert into partien (idGruppe, Spieldatum, Runde, Ergebnis, idSpielerWeiss"
				+ ", idSpielerSchwarz) values (?,?,?,?,?,?)" + ";";

		if (dbConnect != null) {

			final PreparedStatement preStm = dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			final EventDate event = new EventDate(spielDatum);
			preStm.setInt(1, idGruppe);
			preStm.setString(2, event.getDateString());
			preStm.setInt(3, Runde);
			preStm.setInt(4, ergebnis);
			preStm.setInt(5, spielerIdweiss);
			preStm.setInt(6, spielerIdschwarz);

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
	public ArrayList<Game> selectAllPartien(final int idGruppe) throws SQLException {
		final String sql = "Select idPartie,idSpielerWeiss," + "idSpielerSchwarz, Runde, Spieldatum, Ergebnis  "
				+ "from partien where idGruppe=" + idGruppe + " ORDER BY Runde ASC;";
		final ArrayList<Game> partieListe = new ArrayList<Game>();
		Statement stmt;
		if (dbConnect != null) {

			stmt = dbConnect.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final int idPartie = rs.getInt("idPartie");
				final int idSpielerWeiss = rs.getInt("idSpielerWeiss");
				final int idSpielerSchwarz = rs.getInt("idSpielerSchwarz");
				final int runde = rs.getInt("Runde");
				final String spielDatum = rs.getString("Spieldatum");
				final int ergebnis = rs.getInt("Ergebnis");
				final Player spielerWeiss = new Player();
				spielerWeiss.setSpielerId(idSpielerWeiss);
				final Player spielerSchwarz = new Player();
				spielerSchwarz.setSpielerId(idSpielerSchwarz);
				final EventDate event = new EventDate(spielDatum);
				partieListe
						.add(new Game(idPartie, event.getDateString(), ergebnis, runde, spielerWeiss, spielerSchwarz));

			}
			stmt.close();

		}
		return partieListe;
	}

	@Override
	public boolean updatePartien(final ArrayList<Game> partien) throws SQLException {
		Game partie;
		boolean ok = false;
		PreparedStatement preStm = null;

		if (dbConnect != null) {

			dbConnect.setAutoCommit(false);

			final String sql = "update partien set idSpielerWeiss = ?, idSpielerSchwarz = ?"
					+ ", Runde = ?, Ergebnis = ?, Spieldatum = ? where idPartie = ?;";
			// + "COMMIT;";

			preStm = dbConnect.prepareStatement(sql);
			for (final Game ausgabe : partien) {
				partie = ausgabe;

				preStm.setInt(1, partie.getSpielerWeiss().getSpielerId());
				preStm.setInt(2, partie.getSpielerSchwarz().getSpielerId());
				preStm.setInt(3, partie.getRunde());
				preStm.setInt(4, partie.getErgebnis());
				final EventDate event = new EventDate(partie.getSpielDatum());
				preStm.setString(5, event.getDateString());
				preStm.setInt(6, partie.getPartieId());
				preStm.addBatch();

			}

			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	@Override
	public boolean updatePartien(final Game[] partien) throws SQLException {
		Game partie;
		boolean ok = false;
		PreparedStatement preStm = null;

		if (dbConnect != null) {

			dbConnect.setAutoCommit(false);

			final String sql = "update partien set idSpielerWeiss = ?, idSpielerSchwarz = ?"
					+ ", Runde = ?, Ergebnis = ?, Spieldatum = ? where idPartie = ?;";
			// + "COMMIT;";

			preStm = dbConnect.prepareStatement(sql);
			for (int i = 0; i < partien.length; i++) {
				partie = partien[i];

				preStm.setInt(1, partie.getSpielerWeiss().getSpielerId());
				preStm.setInt(2, partie.getSpielerSchwarz().getSpielerId());
				preStm.setInt(3, partie.getRunde());
				preStm.setInt(4, partie.getErgebnis());
				final EventDate event = new EventDate(partie.getSpielDatum());
				preStm.setString(5, event.getDateString());
				preStm.setInt(6, partie.getPartieId());
				preStm.addBatch();

			}

			preStm.executeBatch();
			dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

}
