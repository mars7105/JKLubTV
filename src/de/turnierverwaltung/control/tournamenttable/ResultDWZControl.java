package de.turnierverwaltung.control.tournamenttable;

import de.dwzberechnung.model.MainModel;
import de.dwzberechnung.model.OpponentModel;
import de.dwzberechnung.model.PlayerModel;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;

public class ResultDWZControl extends ResultControl implements Ratingsmethods {

	public ResultDWZControl(final Group gruppe) {
		super(gruppe);

	}

	@Override
	public void addOpponent(final Player player, final double ergebnis, double gesamtpunkte) {
		// DWZ

		if (player.getDWZ() == 0) {
			if (player.getFolgeDWZ() > 0) {
				getOpponents().add(new OpponentModel(player.getFolgeDWZ(), ergebnis));
				gesamtpunkte += ergebnis;

			}
		} else {

			getOpponents().add(new OpponentModel(player.getDWZ(), ergebnis));
			gesamtpunkte += ergebnis;
		}

	}

	@Override
	public void calcRating(final Player player, final double gesamtpunkte) {
		// DWZ
		if (getOpponents().size() > 0) {
			final PlayerModel playerdwz = new PlayerModel(player.getAge(), player.getDWZ(), getOpponents().size());
			playerdwz.setPunkte(gesamtpunkte);
			final MainModel mainModel = new MainModel(playerdwz, getOpponents());
			mainModel.calculateDWZ();
			player.setFolgeDWZ(playerdwz.getFolgeDWZ());
		}
	}

}
