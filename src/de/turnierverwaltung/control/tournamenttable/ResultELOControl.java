package de.turnierverwaltung.control.tournamenttable;

import de.dwzberechnung.model.OpponentModel;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.rating.EloRatingSystem;

public class ResultELOControl extends ResultControl {

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
			getOpponents().add(new OpponentModel(eloRating, ergebnis));

		}

	}

	@Override
	public void calcRating(final Player player, final double gesamtpunkte) {

		int eloRating = player.getEloData().getRating();
		if (eloRating <= 0) {
			eloRating = player.getDwzData().getCsvFIDE_Elo();
		}
		if (getOpponents().size() > 0 && eloRating > 0) {
			final EloRatingSystem eloSystem = new EloRatingSystem();
			int rating = player.getEloData().getRating();
			for (final OpponentModel opponent : getOpponents()) {
				final int newElo = eloSystem.getNewRating(rating, opponent.getDwz(), opponent.getErgebnis(), false);
				rating = newElo;
			}
			player.setFolgeELO(rating);
		}
	}
}
