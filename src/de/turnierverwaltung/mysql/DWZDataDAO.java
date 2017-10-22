package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.DWZData;

public interface DWZDataDAO {
	public void createDWZTable() throws SQLException;


	public int insertDWZ(DWZData dwzData) throws SQLException;

	public boolean updateDWZ(DWZData dwzData) throws SQLException;


	public boolean deleteDWZ(String zps, String mgl) throws SQLException;


	public DWZData getDWZData(String zps, String mgl) throws SQLException;
}
