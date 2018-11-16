package de.turnierverwaltung.control.tournamentlist;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.TabbedPaneViewControl;
import de.turnierverwaltung.control.ratingdialog.DSBDWZControl;
import de.turnierverwaltung.control.ratingdialog.ELOControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.control.tournamenttable.PairingsControl;
import de.turnierverwaltung.model.Formeln;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.PlayerListGroupAddTable;
import de.turnierverwaltung.model.table.PlayerListTableModel;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.playerlist.NewPlayerView;
import de.turnierverwaltung.view.tournamentlist.NewTournamentPlayerIncludeView;
import de.turnierverwaltung.view.tournamentlist.PlayerTournamentEditView;
import de.turnierverwaltung.view.tournamentlist.NewTournamentPlayerIncludeView.PlayerLineView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class NewTournamentPlayerIncludeControl {
	private final MainControl mainControl;
	private final HashMap<Integer, NewTournamentPlayerIncludeView> spielerEingabeView;

	private final int gruppenAnzahl;
	private final TabbedPaneView tabAnzeigeView;
	private final Tournament turnier;
	private final Group[] gruppe;
	private Player[] spieler;
	private final PairingsControl rundenEingabeFormularControl;
	private ArrayList<Player> alleSpieler;
	private ArrayList<Player> playerOfGroupList;
	private Boolean[] readyToSave;
	private final ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private final JTabbedPane hauptPanel;
	private SQLPlayerControl spielerTableControl;
	private PlayerTournamentEditView playerListView;
	private HashMap<Integer, Boolean> playerIDCheck;
	private HashMap<Integer, HashMap<Integer, Boolean>> playerIDs;

	public NewTournamentPlayerIncludeControl(MainControl mainControl) throws SQLException {
		super();
		playerIDs = new HashMap<Integer, HashMap<Integer, Boolean>>();
		this.mainControl = mainControl;
		final int windowWidth = TournamentConstants.WINDOW_WIDTH - 25;
		final int windowHeight = TournamentConstants.WINDOW_HEIGHT - 75;
		spielerTableControl = new SQLPlayerControl(mainControl);
//		alleSpieler = spielerTableControl.getAllSpieler();
		playerOfGroupList = new ArrayList<Player>();
		turnier = this.mainControl.getTournament();
		gruppe = turnier.getGruppe();
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.setTabbedPaneViewControl(new TabbedPaneViewControl(this.mainControl, "S"));
		this.mainControl
				.setTabbedPaneView(new TabbedPaneView(mainControl, Messages.getString("SpielerEingabeControl.10")));
		tabAnzeigeView = this.mainControl.getTabbedPaneView();
		tabAnzeigeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(tabAnzeigeView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		final ButtonTabComponent buttonTabComponent = mainControl.getButtonTabComponent();

		hauptPanel.setTabComponentAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, buttonTabComponent);

		gruppenAnzahl = this.mainControl.getTournament().getAnzahlGruppen();

		spielerEingabeView = new HashMap<Integer, NewTournamentPlayerIncludeView>();

		rundenEingabeFormularControl = new PairingsControl(this.mainControl);
		this.mainControl.setPairingsControl(rundenEingabeFormularControl);

		readyToSave = new Boolean[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			readyToSave[i] = false;
//			spielerEingabeView.add(new NewTournamentPlayerIncludeView(gruppe[i].getSpielerAnzahl()));
		}
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		try {
			Player.cutFname = Integer.parseInt(this.mainControl.getPropertiesControl().getCutForename());
			Player.cutSname = Integer.parseInt(this.mainControl.getPropertiesControl().getCutSurname());
		} catch (final NumberFormatException e) {
			Player.cutFname = 20;
			Player.cutSname = 20;
		}
	}

	public void makeTabbedPane(final int index) throws NumberFormatException, ZahlKleinerAlsN, ZahlGroesserAlsN {
		playerIDCheck = new HashMap<Integer, Boolean>();
		playerIDs.put(index, playerIDCheck);
		spielerEingabeView.put(index, new NewTournamentPlayerIncludeView(gruppe[index].getSpielerAnzahl()));
		tabAnzeigeView.getTabbedPane().addTab(null, spielerEingabeView.get(index));
		spielerEingabeView.get(index).getOkButton().addActionListener(new OkActionPopup(index));
		spielerEingabeView.get(index).getCancelButton().addActionListener(new CancelActionPopup());
		tabAnzeigeView.getTabbedPane().setComponentAt(index, spielerEingabeView.get(index));
		tabAnzeigeView.getTabbedPane().setTitleAt(index, gruppe[index].getGruppenName());
		tabAnzeigeView.getTabbedPane().setIconAt(index, gruppenIcon);
		for (PlayerLineView playerLineView : spielerEingabeView.get(index).getPlayerLineViews()) {
			playerLineView.getPlayerAddButton().addActionListener(new PlayerListActionPopup(playerLineView, index));
		}

	}

	public void fillPlayerList(final int index) {
		try {
			alleSpieler = spielerTableControl.getAllSpieler();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Player> playerList = new ArrayList<Player>();
		for (Player player : alleSpieler) {
			if (playerIDs.get(index).getOrDefault(player.getSpielerId(), true)) {
				playerList.add(player);
			}
		}
		playerOfGroupList = playerList;
	}

	public HashMap<Integer, NewTournamentPlayerIncludeView> getSpielerEingabeView() {
		return spielerEingabeView;
	}

	public PlayerTournamentEditView getPlayerListView() {
		return playerListView;
	}

	public void setPlayerListView(PlayerTournamentEditView playerListView) {
		this.playerListView = playerListView;
	}

	public class PlayerListActionPopup implements ActionListener {
		private PlayerLineView playerLineView;

		private int index;
//		private PlayerListActionPopup playerListActionPopup;

		public PlayerListActionPopup(PlayerLineView playerLineView, int index) {
			super();
			this.index = index;
			playerListView = null;
			this.playerLineView = playerLineView;

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			newPlayer();
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public void newPlayer() {

			fillPlayerList(index);
			final PlayerListGroupAddTable playerListTable = new PlayerListGroupAddTable(playerOfGroupList);
			final PlayerListTableModel playerListTableModel = new PlayerListTableModel(
					playerListTable.getPlayerMatrix(), playerListTable.getColumnNames());

			final Action newPlayerAction = new AbstractAction() {
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					final int modelRow = Integer.valueOf(e.getActionCommand());

					final Player player = playerOfGroupList.get(modelRow);
					int oldSpielerID = playerLineView.getSpielerID();

					playerLineView.setSpielerID(player.getSpielerId());
					playerLineView.getDwzTextfield().setText(player.getDwz());
					playerLineView.getForenameTextfield().setText(player.getForename());
					playerLineView.getSurnameTextfield().setText(player.getSurname());
					playerLineView.getKuerzelTextfield().setText(player.getKuerzel());

					playerOfGroupList.remove(modelRow);
					playerIDs.get(index).put(player.getSpielerId(), false);
					if (oldSpielerID >= 0) {
						for (Player temp : alleSpieler) {
							if (temp.getSpielerId() == oldSpielerID) {
								playerOfGroupList.add(temp);
								playerIDs.get(index).put(temp.getSpielerId(), true);
							}
						}
					}
					playerListView.dispose();
				}

			};
			playerListView = new PlayerTournamentEditView(playerListTableModel, "Add Player to Group", newPlayerAction);
			playerListView.setLineIndex(playerLineView.getLineIndex());

			final JButton okButton = playerListView.getButtonPanel().getOkButton();
			playerListView.getButtonPanel().makeELOButton();
			playerListView.getButtonPanel().makeDWZButton();
			playerListView.getButtonPanel().makeAddPlayerButton();
			final JButton eloButton = playerListView.getButtonPanel().getSpielerELOSearchButton();
			final JButton dwzButton = playerListView.getButtonPanel().getSpielerDEWISSearchButton();
			final JButton addPlayerButton = playerListView.getButtonPanel().getSpielerAddButton();
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					playerListView.dispose();

				}

			});
			eloButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					playerListView.setIndex(index);
					ELOControl eloDialogControl;
					try {
						eloDialogControl = new ELOControl(mainControl);
						eloDialogControl.makeDialog();
						eloDialogControl.makePlayerSearchList();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fillPlayerList(index);

					playerListView.dispose();
					newPlayer();

				}

			});
			dwzButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					playerListView.setIndex(index);
					DSBDWZControl dewisDialogControl = new DSBDWZControl(mainControl);
					try {
						dewisDialogControl.makeDialog();
						dewisDialogControl.makePlayerSearchList();
//						dewisDialogControl.setPlayerListView(playerListView);
//						dewisDialogControl.setPlayerListActionPopup(playerListActionPopup);
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fillPlayerList(index);

					playerListView.dispose();
					newPlayer();
				}

			});
			addPlayerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					playerListView.setIndex(index);
					NewPlayerView spielerHinzufuegenView = new NewPlayerView();

					spielerHinzufuegenView.getOkButton().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent arg0) {
							try {
								String forename = spielerHinzufuegenView.getTextFieldForeName().getText();
								String surname = spielerHinzufuegenView.getTextFieldSurName().getText();

								String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
								String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
								int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
								Player neuerSpieler = new Player();
								// neuerSpieler.setForename(forename);
								// neuerSpieler.setSurname(surname);
								neuerSpieler.setName(surname + "," + forename);
								neuerSpieler.setKuerzel(kuerzel);
								neuerSpieler.setDwz(dwz);
								neuerSpieler.setAge(age);
								SQLPlayerControl stc = new SQLPlayerControl(mainControl);

								neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));

								mainControl.getPlayerListControl().getSpieler().add(neuerSpieler);

								spielerHinzufuegenView.getTextFieldForeName().setEditable(false);
								spielerHinzufuegenView.getTextFieldSurName().setEditable(false);
								spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
								spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
								spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
								spielerHinzufuegenView.spielerPanel();
//								spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);

							} catch (SQLException e) {
								ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e.getMessage());
							}

						}

					});
					spielerHinzufuegenView.getCancelButton().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent arg0) {
							playerListView.setIndex(index);
							try {
								mainControl.getPlayerListControl().updateSpielerListe();
							} catch (SQLException e) {
								ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e.getMessage());
							}

							spielerHinzufuegenView.closeWindow();

							fillPlayerList(index);

							playerListView.dispose();
							newPlayer();
						}

					});
					spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(new FocusListener() {

						@Override
						public void focusGained(FocusEvent e) {
							String forename = spielerHinzufuegenView.getTextFieldForeName().getText();
							String surname = spielerHinzufuegenView.getTextFieldSurName().getText();
							String kuerzel = "";
							if (forename.length() > 0) {
								kuerzel = forename.substring(0, 1);
							}
							if (surname.length() > 0) {
								kuerzel += surname.substring(0, 1);
							}
							spielerHinzufuegenView.getTextFieldKuerzel().setText(kuerzel);

						}

						@Override
						public void focusLost(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}

					});
					spielerHinzufuegenView.showDialog();
				}

			});
			playerListView.showDialog();
		}

		public PlayerLineView getPlayerLineView() {
			return playerLineView;
		}

		public void setPlayerLineView(PlayerLineView playerLineView) {
			this.playerLineView = playerLineView;
		}

	}

	class OkActionPopup implements ActionListener {

		private int index;

		public OkActionPopup(int index) {
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

//			final SQLPlayerControl stc = new SQLPlayerControl(mainControl);

			int sAnzahl = gruppe[index].getSpielerAnzahl();
			Formeln formeln = new Formeln();
			gruppe[index].setRundenAnzahl(formeln.getRundenAnzahl(sAnzahl));

			readyToSave[index] = true;
			spieler = new Player[sAnzahl];
			Boolean allOk = true;

			for (int y = 0; y < sAnzahl; y++) {

				int spielerID = spielerEingabeView.get(index).getPlayerLineViews().get(y).getSpielerID();
				if (spielerID >= 0) {
					spieler[y] = new Player();

					Player temp = null;

					final ListIterator<Player> li = alleSpieler.listIterator();
					while (li.hasNext()) {
						temp = li.next();
						if (spielerID == temp.getSpielerId()) {
							spieler[y] = temp;

						}

					}

				} else {
					allOk = false;
				}
			}
			if (allOk) {
				gruppe[index].setSpieler(spieler);
				spielerEingabeView.get(index).removeAll();

				rundenEingabeFormularControl.makeTerminTabelle(index);
			} else {
				JOptionPane.showMessageDialog(null, Messages.getString("SpielerEingabeControl.11")); //$NON-NLS-1$

			}

		}

	}

	class CancelActionPopup implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Custom button text
			final Object[] options = { Messages.getString("SpielerEingabeControl.4"), //$NON-NLS-1$
					Messages.getString("SpielerEingabeControl.5") }; //$NON-NLS-1$
			final int abfrage = JOptionPane.showOptionDialog(mainControl,
					Messages.getString("SpielerEingabeControl.6") + Messages.getString("SpielerEingabeControl.7"), //$NON-NLS-1$ //$NON-NLS-2$
					Messages.getString("SpielerEingabeControl.8"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (abfrage == 0) {
				mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
			}
		}

	}

	public Boolean[] getReadyToSave() {
		return readyToSave;

	}
}
