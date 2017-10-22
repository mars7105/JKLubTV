package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.DWZData;

public interface DWZVerbandDAO {
	public void createVerbandTable() throws SQLException;

	public int insertDWZ(DWZData dwzData) throws SQLException;

	public boolean updateDWZ(DWZData dwzData) throws SQLException;

	public DWZData findPlayer(int id) throws SQLException;

	boolean deleteDWZ(String verband) throws SQLException;

}
