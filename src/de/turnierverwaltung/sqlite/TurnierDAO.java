package de.turnierverwaltung.sqlite;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.model.Tournament;

public interface TurnierDAO {
	public void createTurnierTable() throws SQLException;

	public boolean deleteTurnier(int id) throws SQLException;

	public Tournament findTurnier(int id, PropertiesControl prop) throws SQLException;

	public int insertTurnier(String turnierName, int datumId) throws SQLException;

	public ArrayList<Tournament> selectAllTurnier(PropertiesControl prop) throws SQLException;

	public boolean updateTurnier(Tournament turnier) throws SQLException;

}
