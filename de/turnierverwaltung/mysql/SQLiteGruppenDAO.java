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

import de.turnierverwaltung.model.Gruppe;

public class SQLiteGruppenDAO implements GruppenDAO {
	private Connection dbConnect;

	public SQLiteGruppenDAO() {
		this.dbConnect = null;

		this.dbConnect = SQLiteDAOFactory.createConnection();
	}

	@Override
	public void createGruppenTable() {
		String sql = "CREATE TABLE gruppen (idGruppe INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " TurnierId INTEGER, Gruppenname VARCHAR)";

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
	public boolean deleteGruppe(int id) {
		boolean ok = false;
		String sql = "delete from gruppen where idGruppe=?";
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
	public Gruppe findGruppe(int id) {
		String sql = "Select Gruppenname, TurnierId " + "from gruppen "
				+ "where idGruppe=" + id;

		Gruppe gruppe = null;

		Statement stmt;
		if (this.dbConnect != null) {
			try {
				stmt = this.dbConnect.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {

					String gruppenName = rs.getString("Gruppenname");
					gruppe = new Gruppe(id, gruppenName);

				}
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());

			}
		}
		return gruppe;
	}

	@Override
	public int insertGruppe(String gruppenName, int turnierId) {
		String sql;
		int id = -1;

		sql = "Insert into gruppen (Gruppenname, TurnierId) values (?,?)";
		id = -1;
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);

				preStm.setString(1, gruppenName);
				preStm.setInt(2, turnierId);

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
	public ArrayList<Gruppe> selectAllGruppen(int idTurnier) {
		String sql = "Select * from gruppen where TurnierId=" + idTurnier;
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
	public boolean updateGruppe(Gruppe gruppe) {
		boolean ok = false;
		String sql = "update gruppen set Gruppenname = ? where idGruppe="
				+ gruppe.getGruppeId();
		if (this.dbConnect != null) {
			try {
				PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
				preStm.setString(1, gruppe.getGruppenName());
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
}
