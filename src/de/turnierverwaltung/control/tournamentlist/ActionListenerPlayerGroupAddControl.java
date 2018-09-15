package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;

public class ActionListenerPlayerGroupAddControl implements ActionListener {
	private final MainControl mainControl;
	private final EditTournamentView editTournamentView;
	private final JButton addGroupButton;
	private final Tournament tournament;
	private final int maxGroups = TournamentConstants.MAX_GROUPS;
	// private final ImageIcon turnierIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));
	// //$NON-NLS-1$

	public ActionListenerPlayerGroupAddControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		editTournamentView = this.mainControl.getActionListenerTournamentItemsControl().getTurnierEditierenView();
		addGroupButton = editTournamentView.getAddGroupButton();
		tournament = mainControl.getActionListenerTournamentItemsControl().getTurnierEdit();

	}

	public void init() {
		addGroupButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		final int groupCount = tournament.getAnzahlGruppen();
		if (groupCount >= maxGroups) {
			JOptionPane.showMessageDialog(null, Messages.getString("ActionListenerPlayerGroupAddControl.1"),
					Messages.getString("ActionListenerPlayerGroupAddControl.0"), JOptionPane.WARNING_MESSAGE);

		} else {
			System.out.println(groupCount);
			mainControl.setTournament(tournament);
			mainControl.setNewTournament(true);
			mainControl.getNaviView().getTabellenPanel().setVisible(false);
			mainControl.getNaviView().getPairingsPanel().setVisible(false);
			mainControl.getActionListenerPairingsMenuControl().setPairingIsActive(false);
			final JTabbedPane hauptPanel = mainControl.getHauptPanel();
			tournament.setAnzahlGruppen(tournament.getAnzahlGruppen() + 1);
			// final ButtonTabComponent buttonTabComponent =
			// mainControl.getButtonTabComponent();
			if (mainControl.getTournament() != null) {
				if (hauptPanel.getTabCount() - 1 == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
					hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

				}

			}
			mainControl.setNewTournamentGroupsControl(new NewTournamentGroupsControl(mainControl));

			final int selectIndex = hauptPanel.getTabCount() - 1;
			hauptPanel.setSelectedIndex(selectIndex);

		}
	}

}
