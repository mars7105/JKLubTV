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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDSBDAOFactory extends DAODSBFactory {

	private static String classNameSQLite = "org.sqlite.JDBC";
	// private static String DB_PATH = System.getProperty("user.home") + "/"
	// + "Schachturnier.sqlite";
	private static String DB_PATH;
	private static String dbStringSQLite;

	private static Connection connection;

	public static Connection createConnection() {

		try {
			if (connection == null || connection.isClosed()) {

				try {

					Class.forName(classNameSQLite);
					return (DriverManager.getConnection(dbStringSQLite));

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				return connection;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setDB_PATH(String dbPath) {
		SQLiteDSBDAOFactory.DB_PATH = dbPath;
		SQLiteDSBDAOFactory.dbStringSQLite = "jdbc:sqlite:" + DB_PATH;

	}

	public SQLiteDSBDAOFactory() {

	}

	@Override
	public DSBVerbaendeDAO getDSBVerbaendeDAO() {
		DSBVerbaendeDAO dsbVerbaendeDAO = new SQLiteDSBVerbaendeDAO();
		return dsbVerbaendeDAO;
	}

	@Override
	public DSBVereineDAO getDSBVereineDAO() {
		DSBVereineDAO dsbVereineDAO = new SQLiteDSBVereineDAO();
		return dsbVereineDAO;
	}

	@Override
	public DSBSpielerDAO getDSBSpielerDAO() {
		DSBSpielerDAO dsbSpielerDAO = new SQLiteDSBSpielerDAO();
		return dsbSpielerDAO;
	}

}
