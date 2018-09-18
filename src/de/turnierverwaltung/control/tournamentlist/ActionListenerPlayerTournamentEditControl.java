package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;

public class ActionListenerPlayerTournamentEditControl implements ActionListener {
	private final MainControl mainControl;
	private final EditTournamentView editTournamentView;
	private final JButton[] playerOfGroupButtons;

	public ActionListenerPlayerTournamentEditControl(final MainControl mainControl, final Tournament turnierEdit) {
		super();
		this.mainControl = mainControl;
		editTournamentView = mainControl.getActionListenerTournamentItemsControl().getTurnierEditierenView();
		playerOfGroupButtons = editTournamentView.getPlayerOfGroupButtons();

	}

	public void init() {

		for (final JButton button : playerOfGroupButtons) {
			button.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final JButton playerOfGroupButton = (JButton) e.getSource();
		int i = 0;
		int groupId = 0;
		for (final JButton button : playerOfGroupButtons) {
			if (button == playerOfGroupButton) {
				System.out.println(i);
				groupId = i;
			}
			i++;
		}
		final Tournament tournament = mainControl.getActionListenerTournamentItemsControl().getTurnierEdit();
		final Group group = tournament.getGruppe()[groupId];
		final int option = JOptionPane.showConfirmDialog(null, group.getGruppenName());
		if (option == JOptionPane.YES_OPTION) {
			System.out.println("Yes");

		}
		if (option == JOptionPane.NO_OPTION) {
			System.out.println("No");
		}
		if (option == JOptionPane.CANCEL_OPTION) {
			System.out.println("Cancel");
		}
	}

}
