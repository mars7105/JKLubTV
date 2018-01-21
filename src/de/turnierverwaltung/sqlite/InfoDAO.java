package de.turnierverwaltung.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.Info;

public interface InfoDAO {
	public void createInfoTable() throws SQLException;

	public void deleteInfo(int id) throws SQLException;

	public Info findInfo(int id, PropertiesControl prop) throws SQLException;

	public ArrayList<Info> getAllInfos() throws SQLException;

	public int insertInfo(Info info) throws SQLException;

	public void updateInfo(Info info) throws SQLException;

}
