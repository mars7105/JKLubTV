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
import java.util.ArrayList;

import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;

public interface SpielerDAO {
	public void createSpielerTable();

	public boolean deleteSpieler(int id);

	public ArrayList<Gruppe> findSpieler(int id);

	public ArrayList<Spieler> getAllSpieler();

	public int insertSpieler(String spielerName, String spielerDWZ,
			String spielerKuerzel);

	public ArrayList<Spieler> selectAllSpieler(int idGruppe);

	public boolean updateSpieler(Spieler spieler);
}
