package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.sqlite.SQLGamesControl;
import de.turnierverwaltung.control.sqlite.SQLGroupsControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.control.sqlite.SQLTournament_has_PlayerControl;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.table.PlayerListAllTable;
import de.turnierverwaltung.model.table.PlayerListTable;
import de.turnierverwaltung.model.table.PlayerListTableModel;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;
import de.turnierverwaltung.view.tournamentlist.PlayerTournamentEditView;

public class ActionListenerPlayerTournamentEditControl implements ActionListener {
	private final MainControl mainControl;
	private final EditTournamentView editTournamentView;
	private final JButton[] playerOfGroupButtons;
	private Tournament tournament;
	private Group group;
	private PlayerTournamentEditView playerTorunamentEditView;

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
				groupId = i;
			}
			i++;
		}
		loadGroup(groupId);

	}

	private void loadGroup(final int groupId) {
		tournament = mainControl.getActionListenerTournamentItemsControl().getTurnierEdit();
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
		group = tournament.getGruppe()[groupId];
		mainControl.setSqlGamesControl(new SQLGamesControl(mainControl));
		try {
			mainControl.getSqlGamesControl().getPartien(groupId);
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		group.getPartien();
		mainControl.setSqlTournament_has_PlayerControl(new SQLTournament_has_PlayerControl(mainControl));
		final PlayerListTable playerListTable = new PlayerListTable(group);
		final PlayerListTableModel playerListTableModel = new PlayerListTableModel(playerListTable.getPlayerMatrix(),
				playerListTable.getColumnNames());
		final Action deleteAction = new AbstractAction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				final JTable table = (JTable) e.getSource();
				final int modelRow = Integer.valueOf(e.getActionCommand());

				final String name = (String) table.getModel().getValueAt(modelRow, 0);
				if (name.equals("<Spielfrei>")) {
					newPlayer();
				} else {
					final Player player = group.getSpieler()[modelRow];
					try {
						deletePlayerFromGroup(player, groupId);
					} catch (final SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		};
		playerTorunamentEditView = new PlayerTournamentEditView(playerListTableModel, group.getGruppenName(),
				deleteAction);
		final JButton okButton = playerTorunamentEditView.getButtonPanel().getOkButton();
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				playerTorunamentEditView.dispose();

			}

		});

		playerTorunamentEditView.showDialog();
	}

	private void deletePlayerFromGroup(final Player player, final int groupId) throws SQLException {
		final int option = JOptionPane.showConfirmDialog(null,
				"Delete Player " + player.getName() + " of Group " + group.getGruppenName() + "?");
		if (option == JOptionPane.YES_OPTION) {
			mainControl.getSqlGamesControl().deletePartienFromPlayer(player, group.getGruppeId());
			mainControl.getSqlTournament_has_PlayerControl().deletePlayerOfGroup(player.getSpielerId(),
					group.getGruppeId());
			playerTorunamentEditView.dispose();
			loadGroup(groupId);
		}

	}

	private void newPlayer() {
		ArrayList<Player> playerList = null;
		try {
			playerList = mainControl.getSqlPlayerControl().getAllSpieler();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final PlayerListAllTable playerListTable = new PlayerListAllTable(playerList);
		final PlayerListTableModel playerListTableModel = new PlayerListTableModel(playerListTable.getPlayerMatrix(),
				playerListTable.getColumnNames());

		final Action deleteAction = new AbstractAction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final JTable table = (JTable) e.getSource();
				// final int modelRow = Integer.valueOf(e.getActionCommand());

				// final String name = (String) table.getModel().getValueAt(modelRow, 0);
				// if (name.equals("<Spielfrei>")) {
				// newPlayer();
				// } else {
				// final Player player = group.getSpieler()[modelRow];
				// try {
				// deletePlayerFromGroup(player, groupId);
				// } catch (final SQLException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// }
			}

		};

		final PlayerTournamentEditView playerListView = new PlayerTournamentEditView(playerListTableModel,
				"Add Player to Group", deleteAction);
		final JButton okButton = playerListView.getButtonPanel().getOkButton();
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				playerListView.dispose();

			}

		});
		playerListView.showDialog();
	}
}
