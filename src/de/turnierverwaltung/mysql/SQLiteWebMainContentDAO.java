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

import de.turnierverwaltung.model.TableContent;

/**
 * @author schmuck
 *
 */
public class SQLiteWebMainContentDAO implements WebMainContentDAO {
	private Connection dbConnect;

	public SQLiteWebMainContentDAO() throws SQLException {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();
		createTableContentTable();
	}

	@Override
	public void createTableContentTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS tableContent (idTableContent INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
				+ " GroupId INTEGER, header VARCHAR, body TEXT, tableType INTEGER, color INTEGER);";

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
	public boolean deleteTableContent(int idTableContent) throws SQLException {

		boolean ok = false;
		String sql = "delete from tableContent where idTableContent=?;";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setInt(1, idTableContent);
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
	public TableContent findTableContent(int id) throws SQLException {
		isColorExist();
		TableContent tableContent = null;
		String sql = "Select * from tableContent where idTableContent =" + id + ";";
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idTableContent = rs.getInt("idTableContent");
				String header = rs.getString("header");
				String body = rs.getString("body");
				int tableType = rs.getInt("tableType");
				int groupId = rs.getInt("GroupId");
				int color = rs.getInt("color");
				tableContent = new TableContent(header, body, idTableContent, tableType, groupId, color);

			}
			stmt.close();

		}
		return tableContent;
	}

	@Override
	public ArrayList<TableContent> getAllTableContent() throws SQLException {
		// TODO Automatisch generierter Methodenstub
		return null;
	}

	@Override
	public int insertTableContent(TableContent tableContent, int groupId) throws SQLException {
		isColorExist();
		String sql;
		sql = "Insert into tableContent (header,body,GroupId,tableType,color) values (?,?,?,?,?);";
		int id = -1;
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preStm.setString(1, tableContent.getHeader());
			preStm.setString(2, tableContent.getBody());
			preStm.setInt(3, groupId);
			preStm.setInt(4, tableContent.getTableType());
			preStm.setInt(5, tableContent.getColor());
			preStm.addBatch();
//			System.out.println(tableContent.getHeader() + '-' + tableContent.getBody() + '-' + groupId + '-'
//					+ tableContent.getTableType() + '-' + tableContent.getColor());
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
	public ArrayList<TableContent> selectAllTableContent(int groupId) throws SQLException {
		isColorExist();
		String sql = "Select * from tableContent where GroupId =" + groupId + " ORDER BY idTableContent ASC;";
		ArrayList<TableContent> tableContentListe = new ArrayList<TableContent>();
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int idTableContent = rs.getInt("idTableContent");
				String header = rs.getString("header");
				String body = rs.getString("body");
				int tableType = rs.getInt("tableType");
				groupId = rs.getInt("GroupId");
				int color = rs.getInt("color");
				TableContent tableContent = new TableContent(header, body, idTableContent, tableType, groupId, color);
				tableContentListe.add(tableContent);

			}
			stmt.close();

		}
		return tableContentListe;
	}

	@Override
	public boolean updateTableContent(TableContent tableContent) throws SQLException {
		isColorExist();
		int idtableContent = tableContent.getIdTableContent();
		boolean ok = false;
		String sql = "update tableContent set header = ?, body = ?, GroupId = ?, tableType = ?, color = ? where idTableContent="
				+ idtableContent + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, tableContent.getHeader());
			preStm.setString(2, tableContent.getBody());
			preStm.setInt(3, tableContent.getIdGroup());
			preStm.setInt(4, tableContent.getTableType());
			preStm.setInt(5, tableContent.getColor());
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

		String sql = "ALTER TABLE tableContent ADD color INTEGER DEFAULT(0)" + ";";
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

		String sql = "SELECT color FROM tableContent LIMIT 1;";
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
