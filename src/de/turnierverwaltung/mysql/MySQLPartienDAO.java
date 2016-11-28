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

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Player;

public class MySQLPartienDAO implements PartienDAO {
	private Connection dbConnect;

	public MySQLPartienDAO() {
		this.dbConnect = null;
		this.dbConnect = MySQLDAOFactory.createConnection();

	}

	@Override
	public void createPartienTable() {
		String sql = "CREATE TABLE partien (idPartie INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " idSpielerWeiss INTEGER NOT NULL , idSpielerSchwarz INTEGER, Runde INTEGER, Spieldatum VARCHAR, idGruppe INTEGER, Ergebnis INTEGER)";

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
	public boolean deletePartien(int id) {
		boolean ok = false;
		String sql = "delete from partien where idPartie=?";
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setInt(1, id);
				preStm.executeUpdate();
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
	public String[] findPartien(int id) {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertPartien(int idGruppe, String spielDatum, int Runde, int ergebnis, int spielerIdweiss,
			int spielerIdschwarz) {

		String sql;
		int id = -1;

		sql = "Insert into partien (idGruppe, Spieldatum, Runde, Ergebnis, idSpielerWeiss"
				+ ", idSpielerSchwarz) values (?,?,?,?,?,?)";

		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				preStm.setInt(1, idGruppe);
				preStm.setString(2, spielDatum);
				preStm.setInt(3, Runde);
				preStm.setInt(4, ergebnis);
				preStm.setInt(5, spielerIdweiss);
				preStm.setInt(6, spielerIdschwarz);

				preStm.executeUpdate();
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
	public ArrayList<Game> selectAllPartien(int idGruppe, Boolean sortMeetingTable) {
		String sql = "Select idPartie,idSpielerWeiss," + "idSpielerSchwarz, Runde, Spieldatum, Ergebnis  "
				+ "from partien where idGruppe=" + idGruppe + " ORDER BY Runde ASC";
		ArrayList<Game> partieListe = new ArrayList<Game>();
		Statement stmt;
		if (this.dbConnect != null) {
			try {
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

					partieListe
							.add(new Game(idPartie, spielDatum, ergebnis, runde, spielerWeiss, spielerSchwarz, false));

				}
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

		}
		return partieListe;
	}

	@Override
	public boolean updatePartien(Game[] partien) {
		int anzahl = partien.length;
		boolean ok = false;
		Game partie;
		if (this.dbConnect != null) {
			try {
				for (int i = 0; i < anzahl; i++) {
					partie = partien[i];
					String sql = "update partien set idSpielerWeiss = ?, idSpielerSchwarz = ?"
							+ ", Runde = ?, Ergebnis = ?, Spieldatum = ? where idPartie=" + partie.getPartieId();

					PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
					preStm.setInt(1, partie.getSpielerWeiss().getSpielerId());
					preStm.setInt(2, partie.getSpielerSchwarz().getSpielerId());
					preStm.setInt(3, partie.getRunde());
					preStm.setInt(4, partie.getErgebnis());
					preStm.setString(5, partie.getSpielDatum());
					preStm.executeUpdate();
					preStm.close();
					ok = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());

			}
		}
		return ok;
	}

	@Override
	public boolean updatePartien(ArrayList<Game> changedPartien) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getErgebnis(int SpielerIDWeiss, int SpielerIDSchwarz, int idGruppe) {

		return null;
	}

}
