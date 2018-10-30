package de.turnierverwaltung.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.rating.DWZData;

public interface DWZDataDAO {
	public void createDWZTable() throws SQLException;

	public void deleteDWZ(int spielerId) throws SQLException;

	public void flush(ArrayList<DWZData> dwzPlayer) throws SQLException;

	public DWZData getDWZData(int spielerId) throws SQLException;

	public ArrayList<DWZData> getDWZDataByFideId(int eingabe) throws SQLException;

	public ArrayList<DWZData> getDWZDataByName(String eingabe) throws SQLException;

	public ArrayList<DWZData> getPlayerOfVerein(String zps) throws SQLException;

	public ArrayList<DWZData> getPlayerByZPSMGL(final String zps, final String mgl) throws SQLException;

	public void insertDWZ(DWZData dwzData) throws SQLException;

	public boolean playerExist(DWZData dwzData) throws SQLException;

	public boolean playerFideExist(int fideId) throws SQLException;

	public void updateDWZ(DWZData dwzData) throws SQLException;
}
