package de.turnierverwaltung.control.tournamenttable;

import java.util.ArrayList;

import de.dwzberechnung.model.OpponentModel;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.rating.ELOOpponent;
import de.turnierverwaltung.model.rating.EloRatingSystem;

public class ResultELOControl extends ResultControl implements Ratingsmethods {

	public ResultELOControl(final Group gruppe) {
		super(gruppe);

	}

	@Override
	public void addOpponent(final Player player, final double ergebnis, final double gesamtpunkte) {

		int eloRating = player.getEloData().getRating();
		if (eloRating <= 0) {
			eloRating = player.getDwzData().getCsvFIDE_Elo();
		}
		if (eloRating > 0) {
			getOpponents().add(new ELOOpponent(eloRating, ergebnis));

		}

	}

	@Override
	public void calcRating(final Player player, final double gesamtpunkte) {
		final ArrayList<OpponentModel> opp = getOpponents();
		int eloRating = player.getEloData().getRating();
		if (eloRating <= 0) {
			eloRating = player.getDwzData().getCsvFIDE_Elo();
		}
		if (getOpponents().size() > 0 && eloRating > 0) {
			final EloRatingSystem eloSystem = new EloRatingSystem();
			int rating = player.getEloData().getRating();
			for (final OpponentModel opponent : opp) {
				final ELOOpponent eloopponent = (ELOOpponent) opponent;
				final int newElo = eloSystem.getNewRating(rating, eloopponent.getElo(), eloopponent.getErgebnis(),
						false);
				rating = newElo;
			}
			player.setFolgeELO(rating);
		}
	}
}
