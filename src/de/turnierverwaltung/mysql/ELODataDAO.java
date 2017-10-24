package de.turnierverwaltung.mysql;

import java.sql.SQLException;

import de.turnierverwaltung.model.ELOData;

public interface ELODataDAO {
	public void createELOTable() throws SQLException;

	public void deleteELO(int id) throws SQLException;

	public int insertELO(ELOData eloData) throws SQLException;

	public void updateELO(ELOData eloData) throws SQLException;

	public ELOData getELOData(int id) throws SQLException;

	public boolean playerExist(int fideId);

}
