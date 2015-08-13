package de.turnierverwaltung.mysql;
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
public abstract class DAODSBFactory {
	public static final int MYSQL_DSB = 1;
	public static final int ORACLE_DSB = 2;
	private static final int SQLITE_DSB = 3;

	public static SQLiteDSBDAOFactory getDAODSBFactory(int database) {
		switch (database) {
		case DAODSBFactory.MYSQL_DSB:
			return null;
		case DAODSBFactory.ORACLE_DSB:
			return null;
		case DAODSBFactory.SQLITE_DSB:
			return new SQLiteDSBDAOFactory();
		default:
			return null;
		}
	}

	public abstract DSBVerbaendeDAO getDSBVerbaendeDAO();

	public abstract DSBVereineDAO getDSBVereineDAO();

	public abstract DSBSpielerDAO getDSBSpielerDAO();

}
