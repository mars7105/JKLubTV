package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TableContent;

public interface WebsiteContentDAO {
	public void createSidepanelTable() throws SQLException;

	public boolean deleteSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> findSidepanel(int id) throws SQLException;

	public ArrayList<Sidepanel> getAllSidepanel() throws SQLException;

	public int insertSidepanel(Sidepanel sitepanel, int turnierId) throws SQLException;

	public ArrayList<Sidepanel> selectAllSidepanel(int idTurnier) throws SQLException;

	public boolean updateSidepanel(Sidepanel sitepanel) throws SQLException;

	public void createTableContentTable() throws SQLException;

	public boolean deleteTableContent(int idTableContent) throws SQLException;

	public ArrayList<TableContent> findTableContent( int groupId) throws SQLException;

	public ArrayList<TableContent> getAllTableContent() throws SQLException;

	public int insertTableContent(TableContent tableContent, int groupId) throws SQLException;

	public ArrayList<TableContent> selectAllTableContent(int groupId) throws SQLException;

	public boolean updateTableContent(TableContent tableContent) throws SQLException;

}
