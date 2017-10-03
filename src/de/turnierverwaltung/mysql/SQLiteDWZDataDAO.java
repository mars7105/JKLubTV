package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.DWZData;

public class SQLiteDWZDataDAO implements DWZDataDAO {

	@Override
	public void createDWZTable() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteDWZ(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateDWZ(DWZData dwzData) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DWZData findPlayer(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
