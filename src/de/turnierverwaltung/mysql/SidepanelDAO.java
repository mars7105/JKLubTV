package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.Sidepanel;

public interface SidepanelDAO {
	public void createSidepanelTable() throws SQLException;

	public boolean deleteSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> findSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> getAllSidepanel() throws SQLException;

	public int insertSidepanel(Sidepanel sitepanel, int turnierId) throws SQLException;

	public ArrayList<Sidepanel> selectAllSidepanel(int idSidepanel) throws SQLException;

	public boolean updateSidepanel(int idSidepanel, Sidepanel sitepanel) throws SQLException;

}
