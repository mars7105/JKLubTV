package de.turnierverwaltung.control.sqlite;

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
import java.util.ArrayList;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.PartienDAO;

public class SQLGamesControl {
	private Tournament turnier;
	private final MainControl mainControl;
	private DAOFactory daoFactory;
	int turnierId;
	int partienId[];

	public SQLGamesControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		turnier = this.mainControl.getTournament();
	}

	public void getPartien(final int gruppenID) throws SQLException {
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		turnier = mainControl.getTournament();
		ArrayList<Game> partie = new ArrayList<Game>();

		partie = mySQLPartienDAO.selectAllPartien(turnier.getGruppe()[gruppenID].getGruppeId());
		final Game[] p = new Game[partie.size()];
		turnier.getGruppe()[gruppenID].setPartienAnzahl(partie.size());
		Player spielerWeiss;
		Player spielerSchwarz;
		for (int s = 0; s < turnier.getGruppe()[gruppenID].getSpielerAnzahl(); s++) {
			spielerWeiss = turnier.getGruppe()[gruppenID].getSpieler()[s];
			spielerSchwarz = turnier.getGruppe()[gruppenID].getSpieler()[s];
			for (int i = 0; i < partie.size(); i++) {
				p[i] = partie.get(i);
				if (p[i].getSpielerWeiss().getSpielerId() == spielerWeiss.getSpielerId()) {

					p[i].setSpielerWeiss(spielerWeiss);
				}
				if (p[i].getSpielerSchwarz().getSpielerId() == spielerSchwarz.getSpielerId()) {
					p[i].setSpielerSchwarz(spielerSchwarz);
				}

			}
		}

		turnier.getGruppe()[gruppenID].setPartien(p);

	}

	public boolean insertPartien(final int gruppe) throws SQLException {
		boolean eintragGespeichert = false;
		turnierId = turnier.getTurnierId();
		final int idGruppe = turnier.getGruppe()[gruppe].getGruppeId();
		final int anzahlPartien = turnier.getGruppe()[gruppe].getPartienAnzahl();
		partienId = new int[anzahlPartien];
		final String[] spielDatum = new String[anzahlPartien];
		final int[] runde = new int[anzahlPartien];
		final int[] ergebnis = new int[anzahlPartien];
		int spielerIdweiss;
		int spielerIdschwarz;

		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		for (int i = 0; i < anzahlPartien; i++) {
			if (turnier.getGruppe()[gruppe].getPartien()[i].getPartieId() == -1) {
				spielerIdweiss = turnier.getGruppe()[gruppe].getPartien()[i].getSpielerWeiss().getSpielerId();
				spielerIdschwarz = turnier.getGruppe()[gruppe].getPartien()[i].getSpielerSchwarz().getSpielerId();
				spielDatum[i] = turnier.getGruppe()[gruppe].getPartien()[i].getSpielDatum();
				runde[i] = turnier.getGruppe()[gruppe].getPartien()[i].getRunde();
				ergebnis[i] = turnier.getGruppe()[gruppe].getPartien()[i].getErgebnis();
				partienId[i] = mySQLPartienDAO.insertPartien(idGruppe, spielDatum[i], runde[i], ergebnis[i],
						spielerIdweiss, spielerIdschwarz);
				turnier.getGruppe()[gruppe].getPartien()[i].setPartieId(partienId[i]);
				eintragGespeichert = true;
			}
		}
		return eintragGespeichert;
	}

	public boolean updatePartien(final int gruppe) throws SQLException {
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		// for (int i = 0; i < turnier.getGruppe()[gruppe].getPartienAnzahl();
		// i++) {
		saved = mySQLPartienDAO.updatePartien(turnier.getGruppe()[gruppe].getPartien());
		// }
		return saved;
	}

	public boolean updatePartien(final Group group) throws SQLException {
		boolean saved = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		turnier = mainControl.getTournament();
		saved = mySQLPartienDAO.updatePartien(group.getPartien());
		// }
		return saved;
	}

	public boolean deletePartienFromPlayer(final Player player, final int groupId) throws SQLException {
		final boolean deleted = false;
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		final PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();
		final ArrayList<Game> games = mySQLPartienDAO.selectAllPartien(groupId);
		int count = 0;
		int rounds = 0;
		for (final Game game : games) {
			if (game.getSpielerWeiss().getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
				count++;
			}
			if (game.getSpielerSchwarz().getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
				count++;
			}
			if (game.getRunde() > rounds) {
				rounds = game.getRunde();
			}
		}
		final int anzahlSpielfrei = count / rounds;
		final int spielfreiId = TournamentConstants.SPIELFREI_ID - anzahlSpielfrei;
		final Player spielfrei = new Player();
		spielfrei.setSpielerId(spielfreiId);
		spielfrei.setSurname(TournamentConstants.SPIELFREI);
		final ArrayList<Game> spielfreiGames = new ArrayList<Game>();
		for (final Game game : games) {
			if (game.getSpielerWeiss().getSpielerId() == player.getSpielerId()) {
				// System.out.println(game.getSpielerWeiss().getName() + " - " +
				// game.getSpielerSchwarz().getName());
				game.setSpielerWeiss(spielfrei);
				game.setErgebnis(TournamentConstants.MYSQL_KEIN_ERGEBNIS);
				spielfreiGames.add(game);
			}
			if (game.getSpielerSchwarz().getSpielerId() == player.getSpielerId()) {
				// System.out.println(game.getSpielerWeiss().getName() + " - " +
				// game.getSpielerSchwarz().getName());
				game.setSpielerSchwarz(spielfrei);
				game.setErgebnis(TournamentConstants.MYSQL_KEIN_ERGEBNIS);

				spielfreiGames.add(game);
			}

		}
		mySQLPartienDAO.updatePartien(spielfreiGames);
		return deleted;
	}
}
