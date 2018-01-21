package de.turnierverwaltung.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.Info;

public class SQLiteInfoDAO implements InfoDAO {
	private Connection dbConnect;

	public SQLiteInfoDAO() {
		this.dbConnect = null;
		this.dbConnect = SQLiteDAOFactory.createConnection();

	}

	@Override
	public void createInfoTable() throws SQLException {
		String sql = "CREATE TABLE info (idInfo INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
				+ " Infoname VARCHAR, Version INTEGER, Infonotice VARCHAR, Datum VARCHAR);";

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
	public void deleteInfo(int id) throws SQLException {
		String sql = "delete from info where idInfo=?" + ";";
		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setInt(1, id);
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();

		}
	}

	@Override
	public Info findInfo(int id, PropertiesControl prop) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Info> getAllInfos() throws SQLException {
		String sql = "Select * from info;";
		ArrayList<Info> infoList = new ArrayList<Info>();
		Statement stmt;
		if (this.dbConnect != null) {

			stmt = this.dbConnect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String infoName = rs.getString("Infoname");
				String version = rs.getString("Version");
				String infoNotice = rs.getString("Infonotice");
				String datum = rs.getString("Datum");
				int infoId = rs.getInt("idInfo");
				Info info = new Info(infoName, version, infoNotice, datum, infoId);

				infoList.add(info);
			}

			stmt.close();

		}

		return infoList;
	}

	@Override
	public int insertInfo(Info info) throws SQLException {
		String sql;
		int id = -1;
		sql = "Insert into info (Infoname, Version, Infonotice, Datum) values (?,?,?,?);";

		if (this.dbConnect != null) {

			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, info.getInfoname());
			preStm.setString(2, info.getVersion());
			preStm.setString(3, info.getInfonotice());
			preStm.setString(4, info.getDatum());

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
	public void updateInfo(Info info) throws SQLException {
		String sql = "update info set Infoname = ?, Version = ?, Infonotice = ?, Datum = ? where idInfo = "
				+ info.getInfoId() + ";";

		if (this.dbConnect != null) {
			PreparedStatement preStm = this.dbConnect.prepareStatement(sql);
			preStm.setString(1, info.getInfoname());
			preStm.setString(2, info.getVersion());
			preStm.setString(3, info.getInfonotice());
			preStm.setString(4, info.getDatum());
			preStm.addBatch();
			this.dbConnect.setAutoCommit(false);
			preStm.executeBatch();
			this.dbConnect.setAutoCommit(true);
			preStm.close();

		}
	}

}
