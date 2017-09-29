package de.turnierverwaltung.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.Info;

public interface InfoDAO {
	public void createInfoTable() throws SQLException;

	public boolean deleteInfo(int id) throws SQLException;

	public Info findInfo(int id, PropertiesControl prop) throws SQLException;

	public int insertInfo(String infoName, int infoId) throws SQLException;

	public boolean updateInfo(Info info) throws SQLException;

	public ArrayList<Info> selectAllInfo(PropertiesControl prop) throws SQLException;

}
