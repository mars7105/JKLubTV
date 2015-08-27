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

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;

public interface Turnier_has_SpielerDAO {
	public void createTurnier_has_SpielerTable();

	public boolean deleteTurnier_has_Spieler(ArrayList<Integer> id);

	public ArrayList<Integer> findSpielerisinTurnier_has_Spieler(Spieler spieler);

	public String findTurnier_has_Spieler(int id);

	public int insertTurnier_has_Spieler(int idGruppe, int idSpieler);

	public ArrayList<String> selectAllTurnier_has_Spieler();

	public boolean updateTurnier_has_Spieler(Turnier turnier);
}
