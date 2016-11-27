/**
 * 
 */
package de.turnierverwaltung.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.turnierverwaltung.model.Sidepanel;

/**
 * @author schmuck
 *
 */
public class SQLiteWebRightContentDAO implements WebRightContentDAO {
	private Connection dbConnect;

	public SQLiteWebRightContentDAO() throws SQLException {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
		createSidepanelTable();
	}

	@Override
	public void createSidepanelTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS sidepanel (idSidepanel INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
				+ " TurnierId INTEGER, header VARCHAR, body TEXT, color INTEGER);";

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
	public boolean deleteSidepanel(int id) throws SQLException {

		boolean ok = false;
		String sql = "delete from sidepanel where idSidepanel=?;";
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
	public Sidepanel findSidepanel(int id) throws SQLException {
		return null;
		// Sidepanel sidepanel = null;
		// String sql = "Select * from sidepanel where idSidepanel =" + id +
		// ";";
		// Statement stmt;
		// if (this.dbConnect != null) {
		//
		// stmt = this.dbConnect.createStatement();
		// ResultSet rs = stmt.executeQuery(sql);
		// while (rs.next()) {
		// int idTableContent = rs.getInt("idSidepanel");
		// String header = rs.getString("header");
		// String body = rs.getString("body");
		// int turnierId = rs.getInt("TurnierId");
		// sidepanel = new Sidepanel(header, body, turnierId);
		//
		// }
		// stmt.close();
		//
		// }
		// return sidepanel;
	}

	@Override
	public ArrayList<Sidepanel> getAllSidepanel() throws SQLException {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertSidepanel(Sidepanel sitepanel, int turnierId) throws SQLException {
		isColorExist();
		String sql;
		sql = "Insert into sidepanel (header,body,TurnierId,color) values (?,?,?,?);";
		int id = -1;
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preStm.setString(1, sitepanel.getHeader());
			preStm.setString(2, sitepanel.getBody());
			preStm.setInt(3, turnierId);
			preStm.setInt(4, sitepanel.getColor());
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
	public ArrayList<Sidepanel> selectAllSidepanel(int idTurnier) throws SQLException {
		isColorExist();
		String sql = "Select idSidepanel, header, body, TurnierId, color " + "from sidepanel where TurnierId ="
				+ idTurnier + " ORDER BY idSidepanel ASC;";
		ArrayList<Sidepanel> sidepanelListe = new ArrayList<Sidepanel>();
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idSidepanel = rs.getInt("idSidepanel");
				String header = rs.getString("header");
				String body = rs.getString("body");
				int color = rs.getInt("color");
//				int turnierid = rs.getInt("TurnierId");

				Sidepanel sidepanel = new Sidepanel(header, body, idSidepanel, color);
				sidepanelListe.add(sidepanel);

			}
			stmt.close();

		}
		return sidepanelListe;
	}

	@Override
	public boolean updateSidepanel(Sidepanel sitepanel, int turnierID) throws SQLException {
		isColorExist();
		int idSidepanel = sitepanel.getIdSidepanel();
		boolean ok = false;
		String sql = "update sidepanel set header = ?, body = ?,TurnierId = ?, color = ? where idSidepanel="
				+ idSidepanel + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, sitepanel.getHeader());
			preStm.setString(2, sitepanel.getBody());
			preStm.setInt(3, turnierID);
			preStm.setInt(4, sitepanel.getColor());
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();
			ok = true;

		}
		return ok;
	}
	private void alterTableColor() {

		String sql = "ALTER TABLE sidepanel ADD color INTEGER DEFAULT(0)" + ";";
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

	public void isColorExist() {

		String sql = "SELECT color FROM sidepanel LIMIT 1;";
		if (this.dbConnect != null) {
			// Datum l�schen
			Statement stmt;
			ResultSet rs;

			try {
				stmt = this.dbConnect.createStatement();

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					String color = rs.getString("color");
				}
				stmt.close();

			} catch (SQLException e) {
				alterTableColor();

			}
		}

	}
	

}
