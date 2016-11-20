package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.Sidepanel;

public interface WebRightContentDAO {
	public void createSidepanelTable() throws SQLException;

	public boolean deleteSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> findSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> getAllSidepanel() throws SQLException;

	public int insertSidepanel(Sidepanel sitepanel, int turnierId) throws SQLException;

	public ArrayList<Sidepanel> selectAllSidepanel(int idTurnier) throws SQLException;

	public boolean updateSidepanel(Sidepanel sitepanel) throws SQLException;

}
