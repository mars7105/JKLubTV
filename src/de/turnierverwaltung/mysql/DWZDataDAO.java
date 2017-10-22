package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.DWZData;
import de.turnierverwaltung.model.Player;

public interface DWZDataDAO {
	public void createDWZTable() throws SQLException;

	public void deleteDWZ(int spielerId) throws SQLException;

	public void updateDWZ(DWZData dwzData) throws SQLException;

	public DWZData getDWZData(int spielerId) throws SQLException;

	public void insertDWZ(DWZData dwzData) throws SQLException;

	public boolean playerExist(DWZData dwzData);
}
