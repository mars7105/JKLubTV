package de.turnierverwaltung.control.navigation;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.model.TournamentConstants;

public class ChangeListenerTabControl implements ChangeListener {
	/**
	 * 
	 * @author mars
	 *
	 */

	private MainControl mainControl;

	/**
	 * 
	 * @param mainControl
	 */
	public ChangeListenerTabControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		int selectedTabIndex = this.mainControl.getHauptPanel().getSelectedIndex();
		if (TournamentConstants.TAB_PLAYER_LIST == selectedTabIndex) {
			this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);
			this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

		}
		if (TournamentConstants.TAB_TOURNAMENTS_LIST == selectedTabIndex) {
			this.mainControl.getNaviView().getTurnierListePanel().setVisible(true);
			this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

		}
		if (TournamentConstants.TAB_ACTIVE_TOURNAMENT == selectedTabIndex) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
			this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
			this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
		}
		if (this.mainControl.getNewTournament() == true) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
			this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		boolean pairingIsActive = this.mainControl.getActionListenerPairingsMenuControl().getPairingIsActive();
		if (e.getSource() instanceof JTabbedPane) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int selectedIndex = pane.getSelectedIndex();
			if (this.mainControl.getTournament() != null) {
				if (selectedIndex == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
					if (this.mainControl.getNewTournament() == false) {
						if (pairingIsActive == true) {
							this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
							this.mainControl.getNaviView().getPairingsPanel().setVisible(true);
						} else {
							this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
							this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
						}

					} else {
						this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
						this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
					}

				} else {
					this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
					this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
				}

			}

			if (selectedIndex == TournamentConstants.TAB_TOURNAMENTS_LIST) {
				this.mainControl.getNaviView().getTurnierListePanel().setVisible(true);

			} else {
				this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
			}
			if (selectedIndex == TournamentConstants.TAB_PLAYER_LIST) {
				this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

			} else {
				this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
			}

		}
	}

}