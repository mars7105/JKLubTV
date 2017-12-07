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

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.model.CrossTableModel;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.MeetingTable;
import de.turnierverwaltung.model.MeetingTableModel;
import de.turnierverwaltung.model.PairingsTables;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.ButtonTabComponent;
import de.turnierverwaltung.view.CrossTableView;
//import de.turnierverwaltung.view.MainView;
import de.turnierverwaltung.view.MeetingTableView;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.NewTournamentGroupsView;
import de.turnierverwaltung.view.NewTournamentPlayerInputView;
import de.turnierverwaltung.view.NewTournamentView;
import de.turnierverwaltung.view.PairingsView;
import de.turnierverwaltung.view.StandardView;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.TitleLabelView;
import de.turnierverwaltung.view.TournamentListView;

/**
 * 
 * @author mars
 *
 */
public class MainControl extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int windowWidth;
	private int windowHeight;
	private JTabbedPane hauptPanel;

	private NewTournamentControl newTournamentControl;
	private NewTournamentView newTournamentView;
	private NewTournamentGroupsView newTournamentGroupsView;
	private NewTournamentGroupsControl newTournamentGroupsControl;

	private NewTournamentPlayerInputView[] newTournamentPlayerInputView;
	private NewTournamentPlayerInputControl newTournamentPlayerInputControl;
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

	public MainControl() {
		windowWidth = TournamentConstants.WINDOW_WIDTH;
		windowHeight = TournamentConstants.WINDOW_HEIGHT;
		setBounds(TournamentConstants.WINDOW_BOUNDS_X, TournamentConstants.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth / 2, windowHeight / 2));
		// Make sure we have nice window decorations.
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		propertiesControl = new PropertiesControl(this);
		languagePropertiesControl = new LanguagePropertiesControl(this);
		if (propertiesControl.readProperties() == false) {
			if (propertiesControl.writeProperties() == false) {
				JOptionPane.showMessageDialog(this, Messages.getString("MainControl.7")); //$NON-NLS-1$
			}
		}
		languagePropertiesControl.checkLanguage();

		setTitle(Messages.getString("MainControl.0")); //$NON-NLS-1$

		init();
		makeProperties();

	}

	private void init() {
		setNewTournament(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.hauptPanel = new JTabbedPane();
		standardView = new StandardView();
		titleLabelView = new TitleLabelView("JKlubTV");

		naviController = new NaviControl(this);

		setContentPane(mainPanel);

		standardView.add(titleLabelView, BorderLayout.NORTH);
		mainPanel.add(standardView, BorderLayout.NORTH);
		mainPanel.add(hauptPanel, BorderLayout.CENTER);
		hauptPanel.updateUI();
		buttonTabComponent = new ButtonTabComponent(hauptPanel, this, null, false);
		mainPanel.updateUI();
		setEnabled(true);
		setVisible(true);
		this.setInfoController(new InfoControl(this));

		this.settingsControl = new SettingsControl(this);
		SQLiteDAOFactory.setDB_PATH("");
		this.setTitle(Messages.getString("MainControl.8"));
	}

	private void makeProperties() {
		// datenbankMenueView(false);

		if (propertiesControl.checkPathToDatabase() == true) {
			// datenbankMenueView(true);
			String path = propertiesControl.getPathToDatabase();
			SQLiteDAOFactory.setDB_PATH(path);
			this.setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
					+ SQLiteDAOFactory.getDB_PATH());

			if (this.getPlayerListControl() != null) {
				// mainControl.getSpielerEditierenControl().makePanel();
			} else {
				this.setPlayerListControl(new PlayerListControl(this));
				try {
					this.getPlayerListControl().updateSpielerListe();
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(this);
					eh.fileSQLError(e.getMessage());
				}
			}
			this.setNewTournament(false);
			// this.getNaviView().getTabellenPanel().setVisible(false);
			if (this.getSqlTournamentControl() == null) {
				this.setSqlTournamentControl(new SQLTournamentControl(this));
				// this.getTurnierTableControl().loadTurnierListe();
				this.setActionListenerTournamentItemsControl(new ActionListenerTournamentItemsControl(this));
				try {
					this.getActionListenerTournamentItemsControl().loadTurnierListe();
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(this);
					eh.fileSQLError(e.getMessage());
				}
				naviView.setPathToDatabase(new JLabel(path));

			} else {
				this.resetApp();
				this.setSqlTournamentControl(new SQLTournamentControl(this));
				// this.getTurnierTableControl().loadTurnierListe();
				this.setActionListenerTournamentItemsControl(new ActionListenerTournamentItemsControl(this));
				try {
					this.getActionListenerTournamentItemsControl().loadTurnierListe();
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(this);
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
		} else {
			this.setTitle(Messages.getString("MainControl.10")); //$NON-NLS-1$

		}
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

		newTournamentPlayerInputView = null;
		newTournamentPlayerInputControl = null;
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
		System.gc();
		init();

	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public JTabbedPane getHauptPanel() {
		return hauptPanel;
	}

	public void setHauptPanel(JTabbedPane hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public NewTournamentControl getNewTournamentControl() {
		return newTournamentControl;
	}

	public void setNewTournamentControl(NewTournamentControl newTournamentControl) {
		this.newTournamentControl = newTournamentControl;
	}

	public NewTournamentView getNewTournamentView() {
		return newTournamentView;
	}

	public void setNewTournamentView(NewTournamentView newTournamentView) {
		this.newTournamentView = newTournamentView;
	}

	public NewTournamentGroupsView getNewTournamentGroupsView() {
		return newTournamentGroupsView;
	}

	public void setNewTournamentGroupsView(NewTournamentGroupsView newTournamentGroupsView) {
		this.newTournamentGroupsView = newTournamentGroupsView;
	}

	public NewTournamentGroupsControl getNewTournamentGroupsControl() {
		return newTournamentGroupsControl;
	}

	public void setNewTournamentGroupsControl(NewTournamentGroupsControl newTournamentGroupsControl) {
		this.newTournamentGroupsControl = newTournamentGroupsControl;
	}

	public NewTournamentPlayerInputView[] getNewTournamentPlayerInputView() {
		return newTournamentPlayerInputView;
	}

	public void setNewTournamentPlayerInputView(NewTournamentPlayerInputView[] newTournamentPlayerInputView) {
		this.newTournamentPlayerInputView = newTournamentPlayerInputView;
	}

	public NewTournamentPlayerInputControl getNewTournamentPlayerInputControl() {
		return newTournamentPlayerInputControl;
	}

	public void setNewTournamentPlayerInputControl(NewTournamentPlayerInputControl newTournamentPlayerInputControl) {
		this.newTournamentPlayerInputControl = newTournamentPlayerInputControl;
	}

	public TabbedPaneViewControl getTabbedPaneViewControl() {
		return tabbedPaneViewControl;
	}

	public void setTabbedPaneViewControl(TabbedPaneViewControl tabbedPaneViewControl) {
		this.tabbedPaneViewControl = tabbedPaneViewControl;
	}

	public TabbedPaneView getTabbedPaneView() {
		return tabbedPaneView;
	}

	public void setTabbedPaneView(TabbedPaneView tabbedPaneView) {
		this.tabbedPaneView = tabbedPaneView;
	}

	public TabbedPaneView[] getTabbedPaneViewArray() {
		return tabbedPaneViewArray;
	}

	public void setTabbedPaneViewArray(TabbedPaneView[] tabbedPaneViewArray) {
		this.tabbedPaneViewArray = tabbedPaneViewArray;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public SQLTournamentControl getSqlTournamentControl() {
		return sqlTournamentControl;
	}

	public void setSqlTournamentControl(SQLTournamentControl sqlTournamentControl) {
		this.sqlTournamentControl = sqlTournamentControl;
	}

	public SQLPlayerControl getSqlPlayerControl() {
		return sqlPlayerControl;
	}

	public void setSqlPlayerControl(SQLPlayerControl sqlPlayerControl) {
		this.sqlPlayerControl = sqlPlayerControl;
	}

	public SQLGroupsControl getSqlGroupsControl() {
		return sqlGroupsControl;
	}

	public void setSqlGroupsControl(SQLGroupsControl sqlGroupsControl) {
		this.sqlGroupsControl = sqlGroupsControl;
	}

	public SQLGamesControl getSqlGamesControl() {
		return sqlGamesControl;
	}

	public void setSqlGamesControl(SQLGamesControl sqlGamesControl) {
		this.sqlGamesControl = sqlGamesControl;
	}

	public SQLTournament_has_PlayerControl getSqlTournament_has_PlayerControl() {
		return sqlTournament_has_PlayerControl;
	}

	public void setSqlTournament_has_PlayerControl(SQLTournament_has_PlayerControl sqlTournament_has_PlayerControl) {
		this.sqlTournament_has_PlayerControl = sqlTournament_has_PlayerControl;
	}

	public CrossTable[] getCrossTable() {
		return crossTable;
	}

	public void setCrossTable(CrossTable[] crossTable) {
		this.crossTable = crossTable;
	}

	public MeetingTable[] getMeetingTable() {
		return meetingTable;
	}

	public void setMeetingTable(MeetingTable[] meetingTable) {
		this.meetingTable = meetingTable;
	}

	public PairingsTables getPairingsTables() {
		return pairingsTables;
	}

	public void setPairingsTables(PairingsTables pairingsTables) {
		this.pairingsTables = pairingsTables;
	}

	public CrossTableModel[] getCrossTableModel() {
		return crossTableModel;
	}

	public void setCrossTableModel(CrossTableModel[] crossTableModel) {
		this.crossTableModel = crossTableModel;
	}

	public CrossTableView[] getCrossTableView() {
		return crossTableView;
	}

	public void setCrossTableView(CrossTableView[] crossTableView) {
		this.crossTableView = crossTableView;
	}

	public SaveTournamentControl getSaveTournamentControl() {
		return saveTournamentControl;
	}

	public void setSaveTournamentControl(SaveTournamentControl saveTournamentControl) {
		this.saveTournamentControl = saveTournamentControl;
	}

	public CrossTableControl getCrossTableControl() {
		return crossTableControl;
	}

	public void setCrossTableControl(CrossTableControl crossTableControl) {
		this.crossTableControl = crossTableControl;
	}

	public MeetingTableControl getMeetingTableControl() {
		return meetingTableControl;
	}

	public void setMeetingTableControl(MeetingTableControl meetingTableControl) {
		this.meetingTableControl = meetingTableControl;
	}

	public PairingsControl getPairingsControl() {
		return pairingsControl;
	}

	public void setPairingsControl(PairingsControl pairingsControl) {
		this.pairingsControl = pairingsControl;
	}

	public PairingsView[] getPairingsView() {
		return pairingsView;
	}

	public void setPairingsView(PairingsView[] pairingsView) {
		this.pairingsView = pairingsView;
	}

	public MeetingTableModel[] getMeetingTableModel() {
		return meetingTableModel;
	}

	public void setMeetingTableModel(MeetingTableModel[] meetingTableModel) {
		this.meetingTableModel = meetingTableModel;
	}

	public MeetingTableView[] getMeetingTableView() {
		return meetingTableView;
	}

	public void setMeetingTableView(MeetingTableView[] meetingTableView) {
		this.meetingTableView = meetingTableView;
	}

	public ActionListenerTournamentItemsControl getActionListenerTournamentItemsControl() {
		return actionListenerTournamentItemsControl;
	}

	public void setActionListenerTournamentItemsControl(
			ActionListenerTournamentItemsControl actionListenerTournamentItemsControl) {
		this.actionListenerTournamentItemsControl = actionListenerTournamentItemsControl;
	}

	public TournamentListView getTournamentListView() {
		return tournamentListView;
	}

	public void setTournamentListView(TournamentListView tournamentListView) {
		this.tournamentListView = tournamentListView;
	}

	public PlayerListControl getPlayerListControl() {
		return playerListControl;
	}

	public void setPlayerListControl(PlayerListControl playerListControl) {
		this.playerListControl = playerListControl;
	}

	public StandardView getStandardView() {
		return standardView;
	}

	public void setStandardView(StandardView standardView) {
		this.standardView = standardView;
	}

	public NaviView getNaviView() {
		return naviView;
	}

	public void setNaviView(NaviView naviView) {
		this.naviView = naviView;
	}

	public NaviControl getNaviController() {
		return naviController;
	}

	public void setNaviController(NaviControl naviController) {
		this.naviController = naviController;
	}

	public InfoControl getInfoController() {
		return infoController;
	}

	public void setInfoController(InfoControl infoController) {
		this.infoController = infoController;
	}

	public TitleLabelView getTitleLabelView() {
		return titleLabelView;
	}

	public void setTitleLabelView(TitleLabelView titleLabelView) {
		this.titleLabelView = titleLabelView;
	}

	public Boolean getNewTournament() {
		return newTournament;
	}

	public void setNewTournament(Boolean neuesTurnier) {
		this.newTournament = neuesTurnier;
	}

	public ArrayList<Game> getChangedGames() {
		return changedGames;
	}

	public void setChangedGames(ArrayList<Game> changedGames) {
		this.changedGames = changedGames;
	}

	public PropertiesControl getPropertiesControl() {
		return propertiesControl;
	}

	public void setPropertiesControl(PropertiesControl propertiesControl) {
		this.propertiesControl = propertiesControl;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public SettingsControl getSettingsControl() {
		return settingsControl;
	}

	public void setSettingsControl(SettingsControl settingsControl) {
		this.settingsControl = settingsControl;
	}

	public LanguagePropertiesControl getLanguagePropertiesControl() {
		return languagePropertiesControl;
	}

	public void setLanguagePropertiesControl(LanguagePropertiesControl languagePropertiesControl) {
		this.languagePropertiesControl = languagePropertiesControl;
	}

	public ButtonTabComponent getButtonTabComponent() {
		return buttonTabComponent;
	}

	public void setButtonTabComponent(ButtonTabComponent buttonTabComponent) {
		this.buttonTabComponent = buttonTabComponent;
	}

	public ActionListenerFileMenuControl getActionListenerFileMenuControl() {
		return actionListenerFileMenuControl;
	}

	public void setActionListenerFileMenuControl(ActionListenerFileMenuControl actionListenerFileMenuControl) {
		this.actionListenerFileMenuControl = actionListenerFileMenuControl;
	}

	public ActionListenerPairingsMenuControl getActionListenerPairingsMenuControl() {
		return actionListenerPairingsMenuControl;
	}

	public void setActionListenerPairingsMenuControl(
			ActionListenerPairingsMenuControl actionListenerPairingsMenuControl) {
		this.actionListenerPairingsMenuControl = actionListenerPairingsMenuControl;
	}

	public ActionListenerPlayerListControl getActionListenerPlayerListControl() {
		return actionListenerPlayerListControl;
	}

	public void setActionListenerPlayerListControl(ActionListenerPlayerListControl actionListenerPlayerListControl) {
		this.actionListenerPlayerListControl = actionListenerPlayerListControl;
	}

	public ActionListenerTournamentListControl getActionListenerTournamentListControl() {
		return actionListenerTournamentListControl;
	}

	public void setActionListenerTournamentListControl(
			ActionListenerTournamentListControl actionListenerTournamentListControl) {
		this.actionListenerTournamentListControl = actionListenerTournamentListControl;
	}

	public ActionListenerTournamentEditControl getActionListenerTournamentEditControl() {
		return actionListenerTournamentEditControl;
	}

	public void setActionListenerTournamentEditControl(
			ActionListenerTournamentEditControl actionListenerTournamentEditControl) {
		this.actionListenerTournamentEditControl = actionListenerTournamentEditControl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
