package de.turnierverwaltung.mysql;

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

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;

public interface SpielerDAO {
	public void createSpielerTable() throws SQLException;

	public boolean deleteSpieler(int id) throws SQLException;

	public ArrayList<Group> findSpieler(int id) throws SQLException;

	public ArrayList<Player> getAllSpieler() throws SQLException;

	public int insertSpieler(String name, String foreName, String surName, String spielerDWZ, String spielerKuerzel,
			String zps, String mgl, int dwzindex, int age) throws SQLException;

	public ArrayList<Player> selectAllSpieler(int idGruppe) throws SQLException;

	public boolean updateSpieler(Player spieler) throws SQLException;

	public void alterTables();
}
