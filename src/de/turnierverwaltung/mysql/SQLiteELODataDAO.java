package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.ELOData;

public class SQLiteELODataDAO implements ELODataDAO {

	@Override
	public void createELOTable() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteELO(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertELO(ELOData eloData) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateELO(ELOData eloData) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ELOData findPlayer(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
