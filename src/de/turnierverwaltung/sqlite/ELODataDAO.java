package de.turnierverwaltung.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.rating.ELOData;
import de.turnierverwaltung.model.rating.ELOPlayer;

public interface ELODataDAO {
	public void createELOTable() throws SQLException;

	public void deleteELO(int id) throws SQLException;

	public void flush(ArrayList<ELOPlayer> eloDataArray) throws SQLException;

	public ELOData getELOData(int id) throws SQLException;

	public ELOData getELODataByFideId(int id) throws SQLException;

	public ArrayList<ELOData> getELODataByName(String eingabe) throws SQLException;

	public void insertELO(ELOData eloData) throws SQLException;

	public boolean playerExist(int fideId) throws SQLException;

	public void updateELO(ELOData eloData) throws SQLException;
}
