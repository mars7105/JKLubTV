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

public class SQLiteDAOFactory extends DAOFactory {

	private static String classNameSQLite = "org.sqlite.JDBC";
	// private static String DB_PATH = System.getProperty("user.home") + "/"
	// + "Schachturnier.sqlite";
	private static String DB_PATH;
	private static String dbStringSQLite;

	private static Connection connection;

	public static Connection createConnection() {
		classNameSQLite = "org.sqlite.JDBC";
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
		SQLiteDAOFactory.DB_PATH = dbPath;
		SQLiteDAOFactory.dbStringSQLite = "jdbc:sqlite:" + SQLiteDAOFactory.DB_PATH;

	}

	public SQLiteDAOFactory() {

	}

	@Override
	public DatumDAO getDatumDAO() {
		DatumDAO datumDAO = new SQLiteDatumDAO();
		return datumDAO;
	}

	@Override
	public GruppenDAO getGruppenDAO() {
		GruppenDAO gruppenDAO = new SQLiteGruppenDAO();
		return gruppenDAO;
	}

	@Override
	public PartienDAO getPartienDAO() {
		PartienDAO partienDAO = new SQLitePartienDAO();
		return partienDAO;
	}

	@Override
	public SpielerDAO getSpielerDAO() {
		SpielerDAO spielerDAO = new SQLiteSpielerDAO();
		return spielerDAO;
	}

	@Override
	public Turnier_has_SpielerDAO getTurnier_has_SpielerDAO() {
		Turnier_has_SpielerDAO turnier_has_SpielerDAO = new SQLiteTurnier_has_SpielerDAO();
		return turnier_has_SpielerDAO;
	}

	@Override
	public TurnierDAO getTurnierDAO() {
		TurnierDAO turnierDAO = new SQLiteTurnierDAO();
		return turnierDAO;
	}

}
