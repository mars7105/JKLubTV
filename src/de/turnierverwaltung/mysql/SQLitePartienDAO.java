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

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class SQLitePartienDAO implements PartienDAO {
	private Connection dbConnect;

	public SQLitePartienDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createPartienTable() throws SQLException {
		String sql = "CREATE TABLE partien (idPartie INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " idSpielerWeiss INTEGER NOT NULL , idSpielerSchwarz INTEGER, Runde INTEGER, Spieldatum VARCHAR, idGruppe INTEGER, Ergebnis INTEGER)"
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
	public boolean deletePartien(int id) throws SQLException {
		boolean ok = false;
		String sql = "delete from partien where idPartie=?" + ";";
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
	public String[] findPartien(int id) throws SQLException {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertPartien(int idGruppe, String spielDatum, int Runde, int ergebnis, int spielerIdweiss,
			int spielerIdschwarz) throws SQLException {

		String sql;
		int id = -1;

		sql = "Insert into partien (idGruppe, Spieldatum, Runde, Ergebnis, idSpielerWeiss"
				+ ", idSpielerSchwarz) values (?,?,?,?,?,?)" + ";";

		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preStm.setInt(1, idGruppe);
			preStm.setString(2, spielDatum);
			preStm.setInt(3, Runde);
			preStm.setInt(4, ergebnis);
			preStm.setInt(5, spielerIdweiss);
			preStm.setInt(6, spielerIdschwarz);

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
	public ArrayList<Game> selectAllPartien(int idGruppe) throws SQLException {
		String sql = "Select idPartie,idSpielerWeiss," + "idSpielerSchwarz, Runde, Spieldatum, Ergebnis  "
				+ "from partien where idGruppe=" + idGruppe + " ORDER BY Runde ASC;";
		ArrayList<Game> partieListe = new ArrayList<Game>();
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idPartie = rs.getInt("idPartie");
				int idSpielerWeiss = rs.getInt("idSpielerWeiss");
				int idSpielerSchwarz = rs.getInt("idSpielerSchwarz");
				int runde = rs.getInt("Runde");
				String spielDatum = rs.getString("Spieldatum");
				int ergebnis = rs.getInt("Ergebnis");
				Player spielerWeiss = new Player();
				spielerWeiss.setSpielerId(idSpielerWeiss);
				Player spielerSchwarz = new Player();
				spielerSchwarz.setSpielerId(idSpielerSchwarz);

				partieListe.add(new Game(idPartie, spielDatum, ergebnis, runde, spielerWeiss, spielerSchwarz));

			}
			stmt.close();

		}
		return partieListe;
	}

	@Override
	public boolean updatePartien(Game[] partien) throws SQLException {
		Game partie;
		boolean ok = false;
		PreparedStatement preStm = null;

		if (this.dbConnect != null) {

			this.dbConnect.setAutoCommit(false);

			String sql = "update partien set idSpielerWeiss = ?, idSpielerSchwarz = ?"
					+ ", Runde = ?, Ergebnis = ?, Spieldatum = ? where idPartie = ?;";
			// + "COMMIT;";

			preStm = this.dbConnect.prepareStatement(sql);
			for (int i = 0; i < partien.length; i++) {
				partie = partien[i];

				preStm.setInt(1, partie.getSpielerWeiss().getSpielerId());
				preStm.setInt(2, partie.getSpielerSchwarz().getSpielerId());
				preStm.setInt(3, partie.getRunde());
				preStm.setInt(4, partie.getErgebnis());
				preStm.setString(5, partie.getSpielDatum());
				preStm.setInt(6, partie.getPartieId());
				preStm.addBatch();

			}

			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	public boolean updatePartien(ArrayList<Game> partien) throws SQLException {
		Game partie;
		boolean ok = false;
		PreparedStatement preStm = null;

		if (this.dbConnect != null) {

			this.dbConnect.setAutoCommit(false);

			String sql = "update partien set idSpielerWeiss = ?, idSpielerSchwarz = ?"
					+ ", Runde = ?, Ergebnis = ?, Spieldatum = ? where idPartie = ?;";
			// + "COMMIT;";

			preStm = this.dbConnect.prepareStatement(sql);
			for (Game ausgabe : partien) {
				partie = ausgabe;

				preStm.setInt(1, partie.getSpielerWeiss().getSpielerId());
				preStm.setInt(2, partie.getSpielerSchwarz().getSpielerId());
				preStm.setInt(3, partie.getRunde());
				preStm.setInt(4, partie.getErgebnis());
				preStm.setString(5, partie.getSpielDatum());
				preStm.setInt(6, partie.getPartieId());
				preStm.addBatch();

			}

			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}

	@Override
	public String getErgebnis(int SpielerIDWeiss, int SpielerIDSchwarz, int idGruppe) throws SQLException {
		String sql = "Select Ergebnis " + "from partien where idGruppe=" + idGruppe + " AND idSpielerWeiss="
				+ SpielerIDWeiss + " AND idSpielerSchwarz=" + SpielerIDSchwarz + ";";
		Statement stmt;
		int ergebnis = -1;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
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

		return ergebnisString;
	}
}
