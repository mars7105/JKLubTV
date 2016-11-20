package de.turnierverwaltung.control;

import java.sql.SQLException;

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
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.DatumDAO;
import de.turnierverwaltung.mysql.GruppenDAO;
import de.turnierverwaltung.mysql.PartienDAO;
import de.turnierverwaltung.mysql.WebRightContentDAO;
import de.turnierverwaltung.mysql.SpielerDAO;
import de.turnierverwaltung.mysql.TurnierDAO;
import de.turnierverwaltung.mysql.Turnier_has_SpielerDAO;

public class SQLControl {
	private DAOFactory daoFactory;
	private SpielerDAO mySQLSpielerDAO;
	private TurnierDAO mySQLTurnierDAO;
	private DatumDAO mySQLDatumDAO;
	private PartienDAO mySQLPartienDAO;
	private GruppenDAO mySQLGruppenDAO;
	private Turnier_has_SpielerDAO mySQLTurnier_has_SpielerDAO;
	private WebRightContentDAO sidePanelDAO;

	public SQLControl() {
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLTurnierDAO = daoFactory.getTurnierDAO();
		mySQLSpielerDAO = daoFactory.getSpielerDAO();
		mySQLDatumDAO = daoFactory.getDatumDAO();
		mySQLPartienDAO = daoFactory.getPartienDAO();
		mySQLGruppenDAO = daoFactory.getGruppenDAO();
		mySQLTurnier_has_SpielerDAO = daoFactory.getTurnier_has_SpielerDAO();
		sidePanelDAO = daoFactory.getWebRightContentDAO();
	}

	public void createAllTables() throws SQLException {
		mySQLTurnierDAO.createTurnierTable();
		mySQLSpielerDAO.createSpielerTable();
		mySQLDatumDAO.createDatumTable();
		mySQLPartienDAO.createPartienTable();
		mySQLGruppenDAO.createGruppenTable();
		mySQLTurnier_has_SpielerDAO.createTurnier_has_SpielerTable();
		sidePanelDAO.createSidepanelTable();
	}

	public void createSpielerTables() throws SQLException {
		mySQLSpielerDAO.createSpielerTable();

	}
}
