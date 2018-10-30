package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

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
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.PlayerListGroupAddTable;
import de.turnierverwaltung.model.table.PlayerListGroupTable;
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
	private ArrayList<Player> allPlayerList;
	private PlayerTournamentEditView playerListView;

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
		// group.getPartien();
		mainControl.setSqlTournament_has_PlayerControl(new SQLTournament_has_PlayerControl(mainControl));
		final PlayerListGroupTable playerListTable = new PlayerListGroupTable(group);
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
				if (name.equals("<"+TournamentConstants.SPIELFREI+">")) {
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
		int anzahl = 0;
		for (Player pl : group.getSpieler()) {
			if (pl.getSpielerId() >= 0) {
				anzahl++;
			}
		}
		if (anzahl > 3) {
			final int option = JOptionPane.showConfirmDialog(null,
					"Delete Player " + player.getName() + " of Group " + group.getGruppenName() + "?");
			if (option == JOptionPane.YES_OPTION) {
				mainControl.getSqlGamesControl().deletePartienFromPlayer(player, group.getGruppeId());
				mainControl.getSqlTournament_has_PlayerControl().deletePlayerOfGroup(player.getSpielerId(),
						group.getGruppeId());
				playerTorunamentEditView.dispose();
				loadGroup(groupId);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Weniger als 3 Spieler nicht möglich!", "Hinweis",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void loadGroupId(final int groupId) {
		tournament = mainControl.getActionListenerTournamentItemsControl().getTurnierEdit();
		mainControl.setTournament(tournament);
		mainControl.setSqlGroupsControl(new SQLGroupsControl(mainControl));
		try {
			mainControl.getSqlGroupsControl().getGruppe();
		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final Group[] grps = tournament.getGruppe();
		int index = 0;
		for (final Group grp : grps) {
			if (grp.getGruppeId() == groupId) {
				loadGroup(index);
			}
			index++;
		}

	}

	private void newPlayer() {
		ArrayList<Player> allPlayerList2 = null;
		allPlayerList = new ArrayList<Player>();
		try {
			allPlayerList2 = mainControl.getSqlPlayerControl().getAllSpieler();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean remove = false;
		for (int i = 0; i < allPlayerList2.size(); i++) {
			remove = false;
			for (final Player groupPlayer : group.getSpieler()) {
				if (allPlayerList2.get(i).getSpielerId() == groupPlayer.getSpielerId()) {
					remove = true;
				}
			}
			if (remove == false) {
				allPlayerList.add(allPlayerList2.get(i));
			}

		}
		final PlayerListGroupAddTable playerListTable = new PlayerListGroupAddTable(allPlayerList);
		final PlayerListTableModel playerListTableModel = new PlayerListTableModel(playerListTable.getPlayerMatrix(),
				playerListTable.getColumnNames());

		final Action newPlayerAction = new AbstractAction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final JTable table = (JTable) e.getSource();
				final int modelRow = Integer.valueOf(e.getActionCommand());

				// final String name = (String) table.getModel().getValueAt(modelRow, 0);
				final Player player = allPlayerList.get(modelRow);
				final Game partien[] = group.getPartien();

				// final int playerCount = group.getSpielerAnzahl();
				// final int rounds = group.getRundenAnzahl();
				// final int gamesCount = group.getPartienAnzahl();
				int index = 0;
				// if ((gamesCount * 2 / rounds) - playerCount == 1) {
				final Hashtable<Integer, Boolean> htable = new Hashtable<Integer, Boolean>();
				final Hashtable<Integer, Boolean> hplayerid = new Hashtable<Integer, Boolean>();
				int spielfreiId = 0;
				for (final Game partie : partien) {
					final int runde = partie.getRunde();
					final Player schwarz = partie.getSpielerSchwarz();
					final Player weiss = partie.getSpielerWeiss();
					if (spielfreiId == 0) {
						if (schwarz.getSpielerId() <= TournamentConstants.SPIELFREI_ID) {

							spielfreiId = schwarz.getSpielerId();

						}
						if (weiss.getSpielerId() <= TournamentConstants.SPIELFREI_ID) {

							spielfreiId = weiss.getSpielerId();

						}
					}
					if (schwarz.getSpielerId() == spielfreiId) {
						if (htable.getOrDefault(runde, false) == false) {
							if (hplayerid.getOrDefault(weiss.getSpielerId(), false) == false) {
								htable.put(runde, true);
								hplayerid.put(weiss.getSpielerId(), true);

								partien[index].setSpielerSchwarz(player);

							}
						}
					}
					if (weiss.getSpielerId() == spielfreiId) {
						if (htable.getOrDefault(runde, false) == false) {
							if (hplayerid.getOrDefault(schwarz.getSpielerId(), false) == false) {
								htable.put(runde, true);

								hplayerid.put(schwarz.getSpielerId(), true);

								partien[index].setSpielerWeiss(player);

							}
						}
					}
					index++;
				}
				// }
				// group.setPartien(partien);
				try {
					mainControl.getSqlGamesControl().updatePartien(group);
					mainControl.getSqlTournament_has_PlayerControl()
							.deletePlayerOfGroup(TournamentConstants.SPIELFREI_ID, group.getGruppeId());
					mainControl.getSqlTournament_has_PlayerControl().insertTurnier_has_Spieler(player.getSpielerId(),
							group.getGruppeId());
				} catch (final SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					playerListView.dispose();
					playerTorunamentEditView.dispose();
					loadGroupId(group.getGruppeId());
				}
			}

		};
		playerListView = new PlayerTournamentEditView(playerListTableModel, "Add Player to Group", newPlayerAction);
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
