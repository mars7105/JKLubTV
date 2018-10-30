package de.turnierverwaltung.sqlite;

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
public abstract class DAOFactory {
	public static final int MYSQL = 1;
	public static final int ORACLE = 2;
	public static final int SQLITE = 3;

	public static DAOFactory getDAOFactory(int database) {
		switch (SQLITE) {
		case DAOFactory.MYSQL:
			return null;
		case DAOFactory.ORACLE:
			return null;
		case DAOFactory.SQLITE:
			return new SQLiteDAOFactory();

		default:
			return null;
		}
	}

	public abstract DatumDAO getDatumDAO();

	public abstract DWZDataDAO getDWZDataDAO();

	public abstract DWZVerbandDAO getDWZVerbandDAO();

	public abstract DWZVereineDAO getDWZVereineDAO();

	public abstract ELODataDAO getELODataDAO();

	public abstract GruppenDAO getGruppenDAO();

	public abstract InfoDAO getInfoDAO();

	public abstract PartienDAO getPartienDAO();

	public abstract SpielerDAO getSpielerDAO();

	public abstract Turnier_has_SpielerDAO getTurnier_has_SpielerDAO();

	public abstract TurnierDAO getTurnierDAO();
}
