package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import de.turnierverwaltung.model.ELOData;

public interface ELODataDAO {
	public void createELOTable() throws SQLException;

	public boolean deleteELO(int id) throws SQLException;

	public int insertELO(ELOData eloData) throws SQLException;

	public boolean updateELO(ELOData eloData) throws SQLException;

	public ELOData findPlayer(int id) throws SQLException;

}
