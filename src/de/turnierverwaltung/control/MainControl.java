package de.turnierverwaltung.control;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import de.turnierverwaltung.control.navigation.ActionListenerFileMenuControl;
import de.turnierverwaltung.control.navigation.NaviControl;
import de.turnierverwaltung.control.playerlist.ActionListenerPlayerListControl;
import de.turnierverwaltung.control.playerlist.PlayerListControl;
import de.turnierverwaltung.control.settingsdialog.SettingsControl;
import de.turnierverwaltung.control.sqlite.SQLGamesControl;
import de.turnierverwaltung.control.sqlite.SQLGroupsControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.control.sqlite.SQLTournamentControl;
import de.turnierverwaltung.control.sqlite.SQLTournament_has_PlayerControl;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentEditControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentItemsControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentListControl;
import de.turnierverwaltung.control.tournamentlist.NewTournamentControl;
import de.turnierverwaltung.control.tournamentlist.NewTournamentGroupsControl;
import de.turnierverwaltung.control.tournamentlist.NewTournamentPlayerIncludeControl;
import de.turnierverwaltung.control.tournamenttable.ActionListenerPairingsMenuControl;
import de.turnierverwaltung.control.tournamenttable.CrossTableControl;
import de.turnierverwaltung.control.tournamenttable.MeetingTableControl;
import de.turnierverwaltung.control.tournamenttable.PairingsControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Parameter;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.model.table.CrossTableModel;
import de.turnierverwaltung.model.table.MeetingTable;
import de.turnierverwaltung.model.table.MeetingTableModel;
import de.turnierverwaltung.model.table.PairingsTables;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;
import de.turnierverwaltung.view.StandardView;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.TitleLabelView;
import de.turnierverwaltung.view.navigation.NaviView;
import de.turnierverwaltung.view.tournamentlist.NewTournamentGroupsView;
import de.turnierverwaltung.view.tournamentlist.NewTournamentView;
import de.turnierverwaltung.view.tournamentlist.TournamentListView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;
import de.turnierverwaltung.view.tournamenttable.CrossTableView;
import de.turnierverwaltung.view.tournamenttable.MeetingTableView;
import de.turnierverwaltung.view.tournamenttable.PairingsView;

/**
 *
 * @author mars
 *
 */
public class MainControl extends JFrame implements WindowListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private int windowWidth;
	private int windowHeight;

	private JTabbedPane hauptPanel;
	private NewTournamentControl newTournamentControl;
	private NewTournamentView newTournamentView;
	private NewTournamentGroupsView newTournamentGroupsView;

	private NewTournamentGroupsControl newTournamentGroupsControl;
//	private NewTournamentPlayerInputControl newTournamentPlayerInputControl;
	private TabbedPaneViewControl tabbedPaneViewControl;
	private TabbedPaneView tabbedPaneView;
	private TabbedPaneView[] tabbedPaneViewArray;
	private Tournament tournament;
	private SQLTournamentControl sqlTournamentControl;
	private SQLPlayerControl sqlPlayerControl;
	private SQLGroupsControl sqlGroupsControl;
	private SQLGamesControl sqlGamesControl;
	private SQLTournament_has_PlayerControl sqlTournament_has_PlayerControl;
	private CrossTable[] crossTable;
	private MeetingTable[] meetingTable;
	private PairingsTables pairingsTables;
	private CrossTableModel[] crossTableModel;
	private CrossTableView[] crossTableView;
	private SaveTournamentControl saveTournamentControl;
	private CrossTableControl crossTableControl;
	private MeetingTableControl meetingTableControl;
	private PairingsControl pairingsControl;
	private PairingsView[] pairingsView;
	private MeetingTableModel[] meetingTableModel;
	@SuppressWarnings("rawtypes")
	private MeetingTableView[] meetingTableView;
	private ActionListenerTournamentItemsControl actionListenerTournamentItemsControl;
	private TournamentListView tournamentListView;
	private PlayerListControl playerListControl;
	private StandardView standardView;
	private NaviView naviView;
	private NaviControl naviController;
	private InfoControl infoController;
	private TitleLabelView titleLabelView;
	private Boolean newTournament;
	private ArrayList<Game> changedGames;
	private PropertiesControl propertiesControl;
	private JPanel mainPanel;
	private SettingsControl settingsControl;
	private LanguagePropertiesControl languagePropertiesControl;
	private ButtonTabComponent buttonTabComponent;
	private ActionListenerFileMenuControl actionListenerFileMenuControl;
	private ActionListenerPairingsMenuControl actionListenerPairingsMenuControl;
	private ActionListenerPlayerListControl actionListenerPlayerListControl;
	private ActionListenerTournamentListControl actionListenerTournamentListControl;

	private ActionListenerTournamentEditControl actionListenerTournamentEditControl;
	private StartpageControl startpageControl;
	private final Parameter parameter;
	private	NewTournamentPlayerIncludeControl newTournamentPlayerIncludeControl;
	public MainControl(final String[] args) {

		parameter = new Parameter(args);
		windowWidth = TournamentConstants.WINDOW_WIDTH;
		windowHeight = TournamentConstants.WINDOW_HEIGHT;
		// setBounds(TournamentConstants.WINDOW_BOUNDS_X,
		// TournamentConstants.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setSize(windowWidth, windowHeight);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(windowWidth / 2, windowHeight / 2));
		// Make sure we have nice window decorations.
		setDefaultLookAndFeelDecorated(true);
		addWindowListener(this);
		// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		init();

		makeProperties();

	}

	public static void setUIFont(final java.awt.Font f) {
		final Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			final Object key = keys.nextElement();
			final Object value = UIManager.get(key);
			if (value != null && value instanceof java.awt.Font) {
				UIManager.put(key, f);
			}
		}
	}

	public StartpageControl getStartpageControl() {
		return startpageControl;
	}

	public void setStartpageControl(final StartpageControl startpageControl) {
		this.startpageControl = startpageControl;
	}

	public ActionListenerFileMenuControl getActionListenerFileMenuControl() {
		return actionListenerFileMenuControl;
	}

	public ActionListenerPairingsMenuControl getActionListenerPairingsMenuControl() {
		return actionListenerPairingsMenuControl;
	}

	public ActionListenerPlayerListControl getActionListenerPlayerListControl() {
		return actionListenerPlayerListControl;
	}

	public ActionListenerTournamentEditControl getActionListenerTournamentEditControl() {
		return actionListenerTournamentEditControl;
	}

	public ActionListenerTournamentItemsControl getActionListenerTournamentItemsControl() {
		return actionListenerTournamentItemsControl;
	}

	public ActionListenerTournamentListControl getActionListenerTournamentListControl() {
		return actionListenerTournamentListControl;
	}

	public ButtonTabComponent getButtonTabComponent() {
		return buttonTabComponent;
	}

	public ArrayList<Game> getChangedGames() {
		return changedGames;
	}

	public CrossTable[] getCrossTable() {
		return crossTable;
	}

	public CrossTableControl getCrossTableControl() {
		return crossTableControl;
	}

	public CrossTableModel[] getCrossTableModel() {
		return crossTableModel;
	}

	public CrossTableView[] getCrossTableView() {
		return crossTableView;
	}

	public JTabbedPane getHauptPanel() {
		return hauptPanel;
	}

	public InfoControl getInfoController() {
		return infoController;
	}

	public LanguagePropertiesControl getLanguagePropertiesControl() {
		return languagePropertiesControl;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public MeetingTable[] getMeetingTable() {
		return meetingTable;
	}

	public MeetingTableControl getMeetingTableControl() {
		return meetingTableControl;
	}

	public MeetingTableModel[] getMeetingTableModel() {
		return meetingTableModel;
	}

	@SuppressWarnings("rawtypes")
	public MeetingTableView[] getMeetingTableView() {
		return meetingTableView;
	}

	public NaviControl getNaviController() {
		return naviController;
	}

	public NaviView getNaviView() {
		return naviView;
	}

	public Boolean getNewTournament() {
		return newTournament;
	}

	public NewTournamentControl getNewTournamentControl() {
		return newTournamentControl;
	}

	public NewTournamentGroupsControl getNewTournamentGroupsControl() {
		return newTournamentGroupsControl;
	}

	public NewTournamentGroupsView getNewTournamentGroupsView() {
		return newTournamentGroupsView;
	}

//	public NewTournamentPlayerInputControl getNewTournamentPlayerInputControl() {
//		return newTournamentPlayerInputControl;
//	}

//	public NewTournamentPlayerInputView[] getNewTournamentPlayerInputView() {
//		return newTournamentPlayerInputView;
//	}

	public NewTournamentView getNewTournamentView() {
		return newTournamentView;
	}

	public PairingsControl getPairingsControl() {
		return pairingsControl;
	}

	public PairingsTables getPairingsTables() {
		return pairingsTables;
	}

	public PairingsView[] getPairingsView() {
		return pairingsView;
	}

	public PlayerListControl getPlayerListControl() {
		return playerListControl;
	}

	public PropertiesControl getPropertiesControl() {
		return propertiesControl;
	}

	public SaveTournamentControl getSaveTournamentControl() {
		return saveTournamentControl;
	}

	public SettingsControl getSettingsControl() {
		return settingsControl;
	}

	public SQLGamesControl getSqlGamesControl() {
		return sqlGamesControl;
	}

	public SQLGroupsControl getSqlGroupsControl() {
		return sqlGroupsControl;
	}

	public SQLPlayerControl getSqlPlayerControl() {
		return sqlPlayerControl;
	}

	public SQLTournament_has_PlayerControl getSqlTournament_has_PlayerControl() {
		return sqlTournament_has_PlayerControl;
	}

	public SQLTournamentControl getSqlTournamentControl() {
		return sqlTournamentControl;
	}

	public StandardView getStandardView() {
		return standardView;
	}

	public TabbedPaneView getTabbedPaneView() {
		return tabbedPaneView;
	}

	public TabbedPaneView[] getTabbedPaneViewArray() {
		return tabbedPaneViewArray;
	}

	public TabbedPaneViewControl getTabbedPaneViewControl() {
		return tabbedPaneViewControl;
	}

	public TitleLabelView getTitleLabelView() {
		return titleLabelView;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public TournamentListView getTournamentListView() {
		return tournamentListView;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	private void init() {
		propertiesControl = new PropertiesControl(this);
		Font font;
		font = propertiesControl.getFont();
		setUIFont(font);
		languagePropertiesControl = new LanguagePropertiesControl(this);
		if (propertiesControl.readProperties() == false) {
			if (propertiesControl.writeProperties() == false) {
				JOptionPane.showMessageDialog(this, Messages.getString("MainControl.7")); //$NON-NLS-1$
			}
		}
		languagePropertiesControl.checkLanguage();

		setTitle(Messages.getString("MainControl.0")); //$NON-NLS-1$

		newTournament = false;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		hauptPanel = new JTabbedPane();
		standardView = new StandardView();
		titleLabelView = new TitleLabelView("JKlubTV");

		naviController = new NaviControl(this);

		// setContentPane(mainPanel);

		standardView.add(titleLabelView, BorderLayout.NORTH);
		mainPanel.add(standardView, BorderLayout.NORTH);
		mainPanel.add(hauptPanel, BorderLayout.CENTER);
		hauptPanel.updateUI();
		buttonTabComponent = new ButtonTabComponent(hauptPanel, this, null, false);
		mainPanel.updateUI();

		setInfoController(new InfoControl(this));

		settingsControl = new SettingsControl(this);
		SQLiteDAOFactory.setDB_PATH("");
		setTitle(Messages.getString("MainControl.8"));
		setContentPane(mainPanel);
	}

	private void makeProperties() {
		final String wrongFilename = "file not found.";
		final String parameterPath = parameter.getTournamentPath();
		final String propertiesPath = propertiesControl.getPathToDatabase();
		TournamentConstants.setSpielfrei(propertiesControl.getSpielfrei());

		if (parameter.getHelp() == true) {
			final String helpString1 = "-f <filename> : filename of database to load.";
			final String helpString2 = "--reset : reset properties.";
			final String helpString3 = "--help : help.";
			System.out.println(helpString1 + "\n" + helpString2 + "\n" + helpString3 + "\n");
			System.exit(0);
		}
		if (parameter.resetProperties() == true) {
			propertiesControl.resetProperties();
			final String helpString = "All Properties are deleted!";
			System.out.println(helpString);
			System.exit(0);
		}
		if (parameterPath.length() > 0) {
			if (!parameterPath.equals("nf")) {
				propertiesControl.setPathToDatabase(parameterPath);
				if (propertiesControl.checkPathToDatabase() == true) {
					loadDatabase(parameterPath);
				} else {

					System.out.println(wrongFilename);
					System.exit(0);
				}
			} else {
				System.out.println(wrongFilename);
				System.exit(0);
			}
		} else if (propertiesPath.length() > 0) {
			propertiesControl.setPathToDatabase(propertiesPath);
			if (propertiesControl.checkPathToDatabase() == true) {
				loadDatabase(propertiesPath);
			} else {
				System.out.println(wrongFilename);
				System.exit(0);
			}
		} else {
			if (parameterPath.equals("nf")) {
				System.out.println(wrongFilename);
				System.exit(0);
			}
			setTitle(Messages.getString("MainControl.10"));
			setBounds(propertiesControl.getFrameX(), propertiesControl.getFrameY(), propertiesControl.getFrameWidth(),
					propertiesControl.getFrameHeight());

			setEnabled(true);
			setVisible(true);
			startpageControl = new StartpageControl(this);
			startpageControl.createStartPanels();
		}
	}

	private void loadDatabase(final String path) {

		SQLiteDAOFactory.setDB_PATH(path);
		setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
				+ SQLiteDAOFactory.getDB_PATH());

		if (getPlayerListControl() == null) {

			setPlayerListControl(new PlayerListControl(this));
			try {
				getPlayerListControl().updateSpielerListe();
				int cutForename = 20;
				int cutSurname = 20;
				try {
					cutForename = Integer.parseInt(propertiesControl.getCutForename());
					cutSurname = Integer.parseInt(propertiesControl.getCutSurname());
				} catch (final NumberFormatException e) {
					cutForename = 20;
					cutSurname = 20;
				}
				Player.cutFname = cutForename;
				Player.cutSname = cutSurname;
			} catch (final SQLException e) {
				final ExceptionHandler eh = new ExceptionHandler(this);
				eh.fileSQLError(e.getMessage());
			}
		}
		setNewTournament(false);
		// this.getNaviView().getTabellenPanel().setVisible(false);
		if (getSqlTournamentControl() == null) {
			setSqlTournamentControl(new SQLTournamentControl(this));
			// this.getTurnierTableControl().loadTurnierListe();
			setActionListenerTournamentItemsControl(new ActionListenerTournamentItemsControl(this));
			try {
				getActionListenerTournamentItemsControl().loadTurnierListe();
			} catch (final SQLException e) {
				final ExceptionHandler eh = new ExceptionHandler(this);
				eh.fileSQLError(e.getMessage());
			}
			naviView.setPathToDatabase(new JLabel(path));

		} else {
			resetApp();
			setSqlTournamentControl(new SQLTournamentControl(this));
			// this.getTurnierTableControl().loadTurnierListe();
			setActionListenerTournamentItemsControl(new ActionListenerTournamentItemsControl(this));
			try {
				getActionListenerTournamentItemsControl().loadTurnierListe();
			} catch (final SQLException e) {
				final ExceptionHandler eh = new ExceptionHandler(this);
				eh.fileSQLError(e.getMessage());
			}
			naviView.setPathToDatabase(new JLabel(path));
		}
		naviView.getTurnierListePanel().setVisible(false);
		naviView.getSpielerListePanel().setVisible(false);
		hauptPanel.addChangeListener(actionListenerFileMenuControl.getTurnierAnsicht());
		for (int i = 0; i < hauptPanel.getTabCount(); i++) {
			if (hauptPanel.getTitleAt(i).equals(Messages.getString("MainControl.9"))) { //$NON-NLS-1$
				hauptPanel.setSelectedIndex(i);
			}
		}
		setBounds(propertiesControl.getFrameX(), propertiesControl.getFrameY(), propertiesControl.getFrameWidth(),
				propertiesControl.getFrameHeight());

		setEnabled(true);
		setVisible(true);
	}

	/**
	 *
	 */
	public void resetApp() {
		windowWidth = 0;
		windowHeight = 0;
		hauptPanel = null;

		newTournamentControl = null;
		newTournamentView = null;
		newTournamentGroupsView = null;
		newTournamentGroupsControl = null;

//		newTournamentPlayerInputView = null;
		newTournamentPlayerIncludeControl = null;
		tabbedPaneViewControl = null;
		tabbedPaneView = null;
		tabbedPaneViewArray = null;
		tournament = null;
		sqlTournamentControl = null;
		sqlPlayerControl = null;
		sqlGroupsControl = null;
		sqlGamesControl = null;
		sqlTournament_has_PlayerControl = null;
		crossTable = null;
		meetingTable = null;
		pairingsTables = null;
		crossTableModel = null;
		crossTableView = null;
		saveTournamentControl = null;
		crossTableControl = null;
		meetingTableControl = null;
		meetingTableView = null;
		actionListenerTournamentItemsControl = null;
		tournamentListView = null;
		saveTournamentControl = null;
		playerListControl = null;
		actionListenerFileMenuControl = null;
		actionListenerTournamentEditControl = null;
		setNewTournament(false);
		// System.gc();
		init();

	}

	public void setActionListenerFileMenuControl(final ActionListenerFileMenuControl actionListenerFileMenuControl) {
		this.actionListenerFileMenuControl = actionListenerFileMenuControl;
	}

	public void setActionListenerPairingsMenuControl(
			final ActionListenerPairingsMenuControl actionListenerPairingsMenuControl) {
		this.actionListenerPairingsMenuControl = actionListenerPairingsMenuControl;
	}

	public void setActionListenerPlayerListControl(
			final ActionListenerPlayerListControl actionListenerPlayerListControl) {
		this.actionListenerPlayerListControl = actionListenerPlayerListControl;
	}

	public void setActionListenerTournamentEditControl(
			final ActionListenerTournamentEditControl actionListenerTournamentEditControl) {
		this.actionListenerTournamentEditControl = actionListenerTournamentEditControl;
	}

	public void setActionListenerTournamentItemsControl(
			final ActionListenerTournamentItemsControl actionListenerTournamentItemsControl) {
		this.actionListenerTournamentItemsControl = actionListenerTournamentItemsControl;
	}

	public void setActionListenerTournamentListControl(
			final ActionListenerTournamentListControl actionListenerTournamentListControl) {
		this.actionListenerTournamentListControl = actionListenerTournamentListControl;
	}

	public void setButtonTabComponent(final ButtonTabComponent buttonTabComponent) {
		this.buttonTabComponent = buttonTabComponent;
	}

	public void setChangedGames(final ArrayList<Game> changedGames) {
		this.changedGames = changedGames;
	}

	public void setCrossTable(final CrossTable[] crossTable) {
		this.crossTable = crossTable;
	}

	public void setCrossTableControl(final CrossTableControl crossTableControl) {
		this.crossTableControl = crossTableControl;
	}

	public void setCrossTableModel(final CrossTableModel[] crossTableModel) {
		this.crossTableModel = crossTableModel;
	}

	public void setCrossTableView(final CrossTableView[] crossTableView) {
		this.crossTableView = crossTableView;
	}

	public void setHauptPanel(final JTabbedPane hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setInfoController(final InfoControl infoController) {
		this.infoController = infoController;
	}

	public void setLanguagePropertiesControl(final LanguagePropertiesControl languagePropertiesControl) {
		this.languagePropertiesControl = languagePropertiesControl;
	}

	public void setMainPanel(final JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void setMeetingTable(final MeetingTable[] meetingTable) {
		this.meetingTable = meetingTable;
	}

	public void setMeetingTableControl(final MeetingTableControl meetingTableControl) {
		this.meetingTableControl = meetingTableControl;
	}

	public void setMeetingTableModel(final MeetingTableModel[] meetingTableModel) {
		this.meetingTableModel = meetingTableModel;
	}

	public void setMeetingTableView(@SuppressWarnings("rawtypes") final MeetingTableView[] meetingTableView) {
		this.meetingTableView = meetingTableView;
	}

	public void setNaviController(final NaviControl naviController) {
		this.naviController = naviController;
	}

	public void setNaviView(final NaviView naviView) {
		this.naviView = naviView;
	}

	public void setNewTournament(final Boolean neuesTurnier) {
		newTournament = neuesTurnier;
	}

	public void setNewTournamentControl(final NewTournamentControl newTournamentControl) {
		this.newTournamentControl = newTournamentControl;
	}

	public void setNewTournamentGroupsControl(final NewTournamentGroupsControl newTournamentGroupsControl) {
		this.newTournamentGroupsControl = newTournamentGroupsControl;
	}

	public void setNewTournamentGroupsView(final NewTournamentGroupsView newTournamentGroupsView) {
		this.newTournamentGroupsView = newTournamentGroupsView;
	}

//	public void setNewTournamentPlayerInputControl(
//			final NewTournamentPlayerInputControl newTournamentPlayerInputControl) {
//		this.newTournamentPlayerInputControl = newTournamentPlayerInputControl;
//	}

//	public void setNewTournamentPlayerInputView(final NewTournamentPlayerInputView[] newTournamentPlayerInputView) {
//		this.newTournamentPlayerInputView = newTournamentPlayerInputView;
//	}

	public void setNewTournamentView(final NewTournamentView newTournamentView) {
		this.newTournamentView = newTournamentView;
	}

	public void setPairingsControl(final PairingsControl pairingsControl) {
		this.pairingsControl = pairingsControl;
	}

	public void setPairingsTables(final PairingsTables pairingsTables) {
		this.pairingsTables = pairingsTables;
	}

	public void setPairingsView(final PairingsView[] pairingsView) {
		this.pairingsView = pairingsView;
	}

	public NewTournamentPlayerIncludeControl getNewTournamentPlayerIncludeControl() {
		return newTournamentPlayerIncludeControl;
	}

	public void setNewTournamentPlayerIncludeControl(NewTournamentPlayerIncludeControl newTournamentPlayerIncludeControl) {
		this.newTournamentPlayerIncludeControl = newTournamentPlayerIncludeControl;
	}

	public void setPlayerListControl(final PlayerListControl playerListControl) {
		this.playerListControl = playerListControl;
	}

	public void setPropertiesControl(final PropertiesControl propertiesControl) {
		this.propertiesControl = propertiesControl;
	}

	public void setSaveTournamentControl(final SaveTournamentControl saveTournamentControl) {
		this.saveTournamentControl = saveTournamentControl;
	}

	public void setSettingsControl(final SettingsControl settingsControl) {
		this.settingsControl = settingsControl;
	}

	public void setSqlGamesControl(final SQLGamesControl sqlGamesControl) {
		this.sqlGamesControl = sqlGamesControl;
	}

	public void setSqlGroupsControl(final SQLGroupsControl sqlGroupsControl) {
		this.sqlGroupsControl = sqlGroupsControl;
	}

	public void setSqlPlayerControl(final SQLPlayerControl sqlPlayerControl) {
		this.sqlPlayerControl = sqlPlayerControl;
	}

	public void setSqlTournament_has_PlayerControl(
			final SQLTournament_has_PlayerControl sqlTournament_has_PlayerControl) {
		this.sqlTournament_has_PlayerControl = sqlTournament_has_PlayerControl;
	}

	public void setSqlTournamentControl(final SQLTournamentControl sqlTournamentControl) {
		this.sqlTournamentControl = sqlTournamentControl;
	}

	public void setStandardView(final StandardView standardView) {
		this.standardView = standardView;
	}

	public void setTabbedPaneView(final TabbedPaneView tabbedPaneView) {
		this.tabbedPaneView = tabbedPaneView;
	}

	public void setTabbedPaneViewArray(final TabbedPaneView[] tabbedPaneViewArray) {
		this.tabbedPaneViewArray = tabbedPaneViewArray;
	}

	public void setTabbedPaneViewControl(final TabbedPaneViewControl tabbedPaneViewControl) {
		this.tabbedPaneViewControl = tabbedPaneViewControl;
	}

	public void setTitleLabelView(final TitleLabelView titleLabelView) {
		this.titleLabelView = titleLabelView;
	}

	public void setTournament(final Tournament tournament) {
		this.tournament = tournament;
	}

	public void setTournamentListView(final TournamentListView tournamentListView) {
		this.tournamentListView = tournamentListView;
	}

	public void setWindowHeight(final int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public void setWindowWidth(final int windowWidth) {
		this.windowWidth = windowWidth;
	}

	@Override
	public void windowActivated(final WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// getPropertiesControl().writeProperties();
		// System.exit(0);

	}

	@Override
	public void windowClosing(final WindowEvent e) {

		final int abfrage = beendenHinweis();
		if (abfrage > 0) {

			if (getHauptPanel().getTabCount() == TournamentConstants.TAB_ACTIVE_TOURNAMENT + 1) {
				if (newTournament == false) {

					if (getChangedGames().isEmpty() == false) {
						final SaveTournamentControl saveTournament = new SaveTournamentControl(this);
						try {
							saveTournament.saveChangedPartien();
						} catch (final SQLException e1) {
							final ExceptionHandler eh = new ExceptionHandler(this);
							eh.fileSQLError(e1.getMessage());
						}

					}
				}
			}
		}
		getPropertiesControl().setBounds();
		getPropertiesControl().writeProperties();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	private int beendenHinweis() {
		int abfrage = 0;
		if (getHauptPanel().getTabCount() == TournamentConstants.TAB_ACTIVE_TOURNAMENT + 1) {
			if (newTournament == true) {
				abfrage = JOptionPane.CANCEL_OPTION;
			} else {
				if (getChangedGames().isEmpty() == false) {

					final String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
							+ Messages.getString("NaviController.22") //$NON-NLS-1$
							+ Messages.getString("NaviController.33"); //$NON-NLS-1$

					abfrage = 1;
					// Custom button text
					final Object[] options = { Messages.getString("NaviController.24"), //$NON-NLS-1$
							Messages.getString("NaviController.25") }; //$NON-NLS-1$
					abfrage = JOptionPane.showOptionDialog(this, hinweisText, Messages.getString("NaviController.26"), //$NON-NLS-1$
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				}
			}
		}
		return abfrage;
	}

}
