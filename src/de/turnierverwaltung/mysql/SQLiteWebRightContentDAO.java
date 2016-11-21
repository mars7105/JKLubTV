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
import de.turnierverwaltung.model.TableContent;

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
				+ " TurnierId INTEGER, header VARCHAR, body TEXT);";

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
		Sidepanel sidepanel = null;
		String sql = "Select * from sidepanel where idSidepanel =" + id + ";";
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idTableContent = rs.getInt("idSidepanel");
				String header = rs.getString("header");
				String body = rs.getString("body");
				int turnierId = rs.getInt("TurnierId");
				sidepanel = new Sidepanel(header, body, turnierId);

			}
			stmt.close();

		}
		return sidepanel;
	}

	@Override
	public ArrayList<Sidepanel> getAllSidepanel() throws SQLException {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertSidepanel(Sidepanel sitepanel, int turnierId) throws SQLException {
		String sql;
		sql = "Insert into sidepanel (header,body,TurnierId) values (?,?,?);";
		int id = -1;
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preStm.setString(1, sitepanel.getHeader());
			preStm.setString(2, sitepanel.getBody());
			preStm.setInt(3, turnierId);
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
		String sql = "Select idSidepanel, header, body, idTurnier " + "from sidepanel, turnier where idTurnier ="
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
				// int SidePanelID = rs.getInt("SidePanelID");
				// int turnierid = rs.getInt("idTurnier");

				Sidepanel sidepanel = new Sidepanel(header, body, idSidepanel);
				sidepanelListe.add(sidepanel);

			}
			stmt.close();

		}
		return sidepanelListe;
	}

	@Override
	public boolean updateSidepanel(Sidepanel sitepanel) throws SQLException {
		int idSidepanel = sitepanel.getIdSidepanel();
		boolean ok = false;
		String sql = "update sidepanel set header = ?, body = ? where idSidepanel=" + idSidepanel + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, sitepanel.getHeader());
			preStm.setString(2, sitepanel.getBody());
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
