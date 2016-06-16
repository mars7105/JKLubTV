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

import de.turnierverwaltung.model.Partie;

public interface PartienDAO {
	public void createPartienTable();

	public boolean deletePartien(int id);

	public String[] findPartien(int id);

	public int insertPartien(int idGruppe, String spielDatum, int Runde, int ergebnis, int spielerIdweiss,
			int spielerIdschwarz);

	public ArrayList<Partie> selectAllPartien(int idGruppe);

	public boolean updatePartien(Partie[] parties);

	public boolean updatePartien(ArrayList<Partie> changedPartien);

	public String getErgebnis(int SpielerIDWeiss, int SpielerIDSchwarz, int idGruppe);
}
