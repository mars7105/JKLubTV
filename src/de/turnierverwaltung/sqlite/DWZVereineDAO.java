package de.turnierverwaltung.sqlite;

import java.sql.SQLException;

import de.turnierverwaltung.model.rating.DWZData;

public interface DWZVereineDAO {
	public void createVereineTable() throws SQLException;

	boolean deleteDWZ(String zps) throws SQLException;

	public DWZData findPlayer(int id) throws SQLException;

	public int insertDWZ(DWZData dwzData) throws SQLException;

	public boolean updateDWZ(DWZData dwzData) throws SQLException;
}
