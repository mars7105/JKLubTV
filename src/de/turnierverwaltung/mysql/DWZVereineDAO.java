package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.DWZData;

public interface DWZVereineDAO {
	public void createVereineTable() throws SQLException;

	public int insertDWZ(DWZData dwzData) throws SQLException;

	public boolean updateDWZ(DWZData dwzData) throws SQLException;

	public DWZData findPlayer(int id) throws SQLException;

	boolean deleteDWZ(String zps) throws SQLException;
}