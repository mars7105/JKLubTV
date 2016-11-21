package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.TableContent;

public interface WebMainContentDAO {

	public void createTableContentTable() throws SQLException;

	public boolean deleteTableContent(int idTableContent) throws SQLException;

	public TableContent findTableContent(int groupId) throws SQLException;

	public ArrayList<TableContent> getAllTableContent() throws SQLException;

	public int insertTableContent(TableContent tableContent, int groupId) throws SQLException;

	public ArrayList<TableContent> selectAllTableContent(int groupId) throws SQLException;

	public boolean updateTableContent(TableContent tableContent) throws SQLException;

}
