package de.turnierverwaltung.mysql;

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

	public ArrayList<DWZData> getPlayerOfVerein(String zps) throws SQLException ;

	public void insertDWZ(DWZData dwzData) throws SQLException;

	public boolean playerExist(DWZData dwzData);

	public boolean playerFideExist(int fideId);

	public void updateDWZ(DWZData dwzData) throws SQLException;
}
