package de.turnierverwaltung.control.sqlite;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.turnierverwaltung.control.Version;
import de.turnierverwaltung.model.Info;
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
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.DWZDataDAO;
import de.turnierverwaltung.sqlite.DWZVerbandDAO;
import de.turnierverwaltung.sqlite.DWZVereineDAO;
import de.turnierverwaltung.sqlite.DatumDAO;
import de.turnierverwaltung.sqlite.ELODataDAO;
import de.turnierverwaltung.sqlite.GruppenDAO;
import de.turnierverwaltung.sqlite.InfoDAO;
import de.turnierverwaltung.sqlite.PartienDAO;
import de.turnierverwaltung.sqlite.SpielerDAO;
import de.turnierverwaltung.sqlite.TurnierDAO;
import de.turnierverwaltung.sqlite.Turnier_has_SpielerDAO;

public class SQLControl {
	public static final String INFONAME = Version.getString("version.0");
	public static final String VERSION = Version.getString("version.1");
	public static final String INFONOTICE = Version.getString("version.2");
	public static final String INFONAME_EXPORT_PLAYERLIST = Version.getString("version.4");
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;
	private TurnierDAO mySQLTurnierDAO;
	private DatumDAO mySQLDatumDAO;
	private PartienDAO mySQLPartienDAO;
	private GruppenDAO mySQLGruppenDAO;
	private Turnier_has_SpielerDAO mySQLTurnier_has_SpielerDAO;
	private DWZDataDAO mySQLDWZDataDAO;
	private ELODataDAO mySQLELODataDAO;
	private DWZVerbandDAO mySQLVerbandDAO;
	private DWZVereineDAO mySQLVereineDAO;
	private InfoDAO mySQLInfoDataDAO;

	public SQLControl() {

		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLTurnierDAO = daoFactory.getTurnierDAO();
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
		mySQLDatumDAO = daoFactory.getDatumDAO();
		mySQLPartienDAO = daoFactory.getPartienDAO();
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		mySQLTurnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		mySQLDWZDataDAO = daoFactory.getDWZDataDAO();
		mySQLVerbandDAO = daoFactory.getDWZVerbandDAO();
		mySQLVereineDAO = daoFactory.getDWZVereineDAO();
		mySQLELODataDAO = daoFactory.getELODataDAO();
		mySQLInfoDataDAO = daoFactory.getInfoDAO();

	}

	public void createAllTables() throws SQLException {
		mySQLTurnierDAO.createTurnierTable();
		mySQLSpielerDAO.createSpielerTable();
		mySQLDatumDAO.createDatumTable();
		mySQLPartienDAO.createPartienTable();
		mySQLGruppenDAO.createGruppenTable();
		mySQLTurnier_has_SpielerDAO.createTurnier_has_SpielerTable();
		mySQLDWZDataDAO.createDWZTable();
		mySQLVerbandDAO.createVerbandTable();
		mySQLVereineDAO.createVereineTable();
		mySQLELODataDAO.createELOTable();
		mySQLInfoDataDAO.createInfoTable();
		Info info = new Info(INFONAME, VERSION, INFONOTICE, getDate(), 0);
		mySQLInfoDataDAO.insertInfo(info);
	}

	public void createSpielerTables() throws SQLException {
		mySQLSpielerDAO.createSpielerTable();
		mySQLDWZDataDAO.createDWZTable();
		mySQLELODataDAO.createELOTable();
		mySQLInfoDataDAO.createInfoTable();
		Info info = new Info(INFONAME_EXPORT_PLAYERLIST, VERSION, INFONOTICE, getDate(), 0);
		mySQLInfoDataDAO.insertInfo(info);
	}

	private String getDate() {
		Date dt = new Date();
		// Festlegung des Formats:
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		df.setTimeZone(TimeZone.getDefault());
		// nicht mehr unbedingt notwendig seit JDK 1.2
		// Formatierung zu String:
		String date = df.format(dt);
		// z.B. '2001-01-26 19:03:56.731'
		return date;
	}
}
