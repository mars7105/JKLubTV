package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.sqlite.SQLGroupsControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.table.PlayerListTable;
import de.turnierverwaltung.model.table.PlayerListTableModel;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;
import de.turnierverwaltung.view.tournamentlist.PlayerTournamentEditView;

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
		mainControl.setTournament(tournament);
		mainControl.setSqlGroupsControl(new SQLGroupsControl(mainControl));
		try {
			mainControl.getSqlGroupsControl().getGruppe();
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainControl.setSqlPlayerControl(new SQLPlayerControl(mainControl));
		try {
			mainControl.getSqlPlayerControl().getSpieler();
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final Group group = tournament.getGruppe()[groupId];
		final PlayerListTable playerListTable = new PlayerListTable(group);
		final PlayerListTableModel playerListTableModel = new PlayerListTableModel(playerListTable.getPlayerMatrix(),
				playerListTable.getColumnNames());
		final PlayerTournamentEditView playerTorunamentEditView = new PlayerTournamentEditView(playerListTableModel,
				group.getGruppenName());
		playerTorunamentEditView.showDialog();
	}

}
