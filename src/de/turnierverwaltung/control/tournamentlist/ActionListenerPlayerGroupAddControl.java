package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;

public class ActionListenerPlayerGroupAddControl implements ActionListener {
	private final MainControl mainControl;
	private final EditTournamentView editTournamentView;
	private final JButton addGroupButton;
	private final Tournament tournament;

	public ActionListenerPlayerGroupAddControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		editTournamentView = mainControl.getActionListenerTournamentItemsControl().getTurnierEditierenView();
		addGroupButton = editTournamentView.getAddGroupButton();
		tournament = mainControl.getActionListenerTournamentItemsControl().getTurnierEdit();
		addGroupButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

	}

}
