package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.ELOData;
import de.turnierverwaltung.model.ELOPlayer;

public interface ELODataDAO {
	public void createELOTable() throws SQLException;

	public void deleteELO(int id) throws SQLException;

	public void insertELO(ELOData eloData) throws SQLException;

	public void updateELO(ELOData eloData) throws SQLException;

	public ELOData getELOData(int id) throws SQLException;

	public boolean playerExist(int fideId);

	public void flush(ArrayList<ELOPlayer> eloDataArray) throws SQLException;
}
