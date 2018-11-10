package de.turnierverwaltung.control.tournamentlist;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.TabbedPaneViewControl;
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
	private final ArrayList<Player> alleSpieler;
	private HashMap<Integer, ArrayList<Player>> playerOfGroupList;
	private Boolean[] readyToSave;
	private final ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private final JTabbedPane hauptPanel;

	public NewTournamentPlayerIncludeControl(MainControl mainControl) throws SQLException {
		super();
		this.mainControl = mainControl;
		final int windowWidth = TournamentConstants.WINDOW_WIDTH - 25;
		final int windowHeight = TournamentConstants.WINDOW_HEIGHT - 75;
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		alleSpieler = spielerTableControl.getAllSpieler();
		playerOfGroupList = new HashMap<Integer, ArrayList<Player>>();
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
//		mainControl.getHauptPanel();
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (Player player:alleSpieler) {
			playerList.add(player);
		}
		playerOfGroupList.put(index, playerList);
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

	class PlayerListActionPopup implements ActionListener {
		private PlayerLineView playerLineView;
		private PlayerTournamentEditView playerListView;
		private int index;

		public PlayerListActionPopup(PlayerLineView playerLineView, int index) {
			super();
			this.index = index;
			this.playerLineView = playerLineView;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			newPlayer();
		}

		private void newPlayer() {

//			Boolean remove = false;
//			SQLPlayerControl group;

			final PlayerListGroupAddTable playerListTable = new PlayerListGroupAddTable(playerOfGroupList.get(index));
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

					final Player player = playerOfGroupList.get(index).get(modelRow);


					playerLineView.setSpielerID(player.getSpielerId());
					playerLineView.getDwzTextfield().setText(player.getDwz());
					playerLineView.getForenameTextfield().setText(player.getForename());
					playerLineView.getSurnameTextfield().setText(player.getSurname());
					playerLineView.getKuerzelTextfield().setText(player.getKuerzel());
					playerOfGroupList.get(index).remove(modelRow);
					playerListView.dispose();
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
