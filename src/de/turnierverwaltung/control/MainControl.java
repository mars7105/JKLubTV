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

import de.turnierverwaltung.model.PairingsTables;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.MeetingTableModel;
import de.turnierverwaltung.model.CrossTableModel;
import de.turnierverwaltung.model.MeetingTable;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.NewTournamentGroupsView;
import de.turnierverwaltung.view.MainView;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.PairingsView;
import de.turnierverwaltung.view.MeetingTableView;
import de.turnierverwaltung.view.ButtonTabComponent;
import de.turnierverwaltung.view.CrossTableView;
import de.turnierverwaltung.view.FrontendSidePanelView;
import de.turnierverwaltung.view.NewTournamentPlayerCountlView;
import de.turnierverwaltung.view.NewTournamentPlayerInputView;
import de.turnierverwaltung.view.StandardView;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.TitleLabelView;
import de.turnierverwaltung.view.TournamentListView;
import de.turnierverwaltung.view.NewTournamentView;

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
	private MainView mainView;
	private JTabbedPane hauptPanel;
	private FrontendSidePanelView frontendSidePanelView;
	private NewTournamentControl turnierControl;
	private NewTournamentView turnierView;
	private NewTournamentGroupsView gruppenView;
	private NewTournamentGroupsControl gruppenControl;
	private NewTournamentPlayerCountlView spielerAnzahlView;
	private NewTournamentPlayerCountControl spielerAnzahlControl;
	private NewTournamentPlayerInputView[] spielerEingabeView;
	private NewTournamentPlayerInputControl spielerEingabeControl;
	private TabbedPaneViewControl tabAnzeigeControl;
	private TabbedPaneView tabAnzeigeView;
	private TabbedPaneView[] tabAnzeigeView2;
	private Tournament turnier;
	private SQLTournamentControl turnierTableControl;
	private SQLPlayerControl spielerTableControl;
	private SQLGroupsControl gruppenTableControl;
	private SQLGamesControl partienTableControl;
	private SQLTournament_has_PlayerControl turnier_has_SpielerTableControl;
	private CrossTable[] turnierTabelle;
	private MeetingTable[] terminTabelle;
	private PairingsTables paarungsTafeln;
	private CrossTableModel[] simpleTableModel;
	private CrossTableView[] simpleTableView;
	private SaveTournamentControl saveTurnierControl;
	private CrossTableControl turnierTabelleControl;
	private MeetingTableControl terminTabelleControl;
	private PairingsControl rundenEingabeFormularControl;
	private PairingsView[] rundenEingabeFormularView;
	private MeetingTableModel[] simpleTerminTabelle;
	private MeetingTableView[] simpleTerminTabelleView;
	private TournamentListControl turnierListeLadenControl;
	private TournamentListView turnierListeLadenView;
	private PlayerListControl spielerLadenControl;
	private StandardView standardView;
	private NaviView naviView;
	private NaviControl naviController;
	private InfoControl infoController;
	private TitleLabelView titleView;
	private Boolean neuesTurnier;
	private ArrayList<Game> changedPartien;
	private PropertiesControl propertiesControl;
	private JPanel mainPanel;
	private SettingsControl eigenschaftenControl;
	private LanguagePropertiesControl languagePropertiesControl;
	private ButtonTabComponent buttonTabComponent;

	public MainControl() {
		windowWidth = TournamentConstants.WINDOW_WIDTH;
		windowHeight = TournamentConstants.WINDOW_HEIGHT;
		setBounds(TournamentConstants.WINDOW_BOUNDS_X, TournamentConstants.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth / 2, windowHeight / 2));

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
		setNeuesTurnier(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.hauptPanel = new JTabbedPane();
		standardView = new StandardView();
		titleView = new TitleLabelView("JKlubTV");

		naviController = new NaviControl(this);

		setContentPane(mainPanel);

		standardView.add(titleView, BorderLayout.NORTH);
		mainPanel.add(standardView, BorderLayout.NORTH);
		mainPanel.add(hauptPanel, BorderLayout.CENTER);
		hauptPanel.updateUI();
		buttonTabComponent = new ButtonTabComponent(hauptPanel, this, null, false);
		mainPanel.updateUI();
		setEnabled(true);
		setVisible(true);
		this.setInfoController(new InfoControl(this));

		this.eigenschaftenControl = new SettingsControl(this);
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

			if (this.getSpielerEditierenControl() != null) {
				// mainControl.getSpielerEditierenControl().makePanel();
			} else {
				this.setSpielerEditierenControl(new PlayerListControl(this));
				try {
					this.getSpielerEditierenControl().updateSpielerListe();
				} catch (SQLException e) {
					fileSQLError();
				}
			}
			this.setNeuesTurnier(false);
			// this.getNaviView().getTabellenPanel().setVisible(false);
			if (this.getTurnierTableControl() == null) {
				this.setTurnierTableControl(new SQLTournamentControl(this));
				// this.getTurnierTableControl().loadTurnierListe();
				this.setTurnierListeLadenControl(new TournamentListControl(this));
				try {
					this.getTurnierListeLadenControl().loadTurnierListe();
				} catch (SQLException e) {
					fileSQLError();
				}
				naviView.setPathToDatabase(new JLabel(path));

			} else {
				this.resetApp();
				this.setTurnierTableControl(new SQLTournamentControl(this));
				// this.getTurnierTableControl().loadTurnierListe();
				this.setTurnierListeLadenControl(new TournamentListControl(this));
				try {
					this.getTurnierListeLadenControl().loadTurnierListe();
				} catch (SQLException e) {
					fileSQLError();
				}
				naviView.setPathToDatabase(new JLabel(path));
			}
			naviView.getTurnierListePanel().setVisible(false);
			naviView.getSpielerListePanel().setVisible(false);
			hauptPanel.addChangeListener(naviController.getTurnierAnsicht());
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
		mainView = null;
		hauptPanel = null;

		turnierControl = null;
		turnierView = null;
		gruppenView = null;
		gruppenControl = null;
		spielerAnzahlView = null;
		spielerAnzahlControl = null;
		spielerEingabeView = null;
		spielerEingabeControl = null;
		tabAnzeigeControl = null;
		tabAnzeigeView = null;
		tabAnzeigeView2 = null;
		turnier = null;
		turnierTableControl = null;
		spielerTableControl = null;
		gruppenTableControl = null;
		partienTableControl = null;
		turnier_has_SpielerTableControl = null;
		turnierTabelle = null;
		terminTabelle = null;
		paarungsTafeln = null;
		simpleTableModel = null;
		simpleTableView = null;
		saveTurnierControl = null;
		turnierTabelleControl = null;
		terminTabelleControl = null;
		simpleTerminTabelleView = null;
		turnierListeLadenControl = null;
		turnierListeLadenView = null;
		saveTurnierControl = null;
		spielerLadenControl = null;
		setNeuesTurnier(false);
		System.gc();
		init();

	}

	/**
	 * 
	 */
	public void fileSQLError() {
		propertiesControl.setPathToDatabase("");
		propertiesControl.checkProperties();
		Boolean ok = propertiesControl.writeProperties();
		if (ok) {
			JOptionPane.showMessageDialog(null, Messages.getString("MainControl.11"));

		} else {
			JOptionPane.showMessageDialog(null, Messages.getString("MainControl.12"));

		}
		resetApp();
	}

	public FrontendSidePanelView getFrontendSidePanelView() {
		return frontendSidePanelView;
	}

	public void setFrontendSidePanelView(FrontendSidePanelView frontendSidePanelView) {
		this.frontendSidePanelView = frontendSidePanelView;
	}

	public ButtonTabComponent getButtonTabComponent() {
		return buttonTabComponent;
	}

	public void setButtonTabComponent(ButtonTabComponent buttonTabComponent) {
		this.buttonTabComponent = buttonTabComponent;
	}

	public LanguagePropertiesControl getLanguagePropertiesControl() {
		return languagePropertiesControl;
	}

	public void setLanguagePropertiesControl(LanguagePropertiesControl languagePropertiesControl) {
		this.languagePropertiesControl = languagePropertiesControl;
	}

	public PlayerListControl getSpielerLadenControl() {
		return spielerLadenControl;
	}

	public void setSpielerLadenControl(PlayerListControl spielerLadenControl) {
		this.spielerLadenControl = spielerLadenControl;
	}

	public ArrayList<Game> getChangedPartien() {
		return changedPartien;
	}

	public void setChangedPartien(ArrayList<Game> changedPartien) {
		this.changedPartien = changedPartien;
	}

	public Boolean getNeuesTurnier() {
		return neuesTurnier;
	}

	public void setNeuesTurnier(Boolean neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

	public PairingsView[] getRundenEingabeFormularView() {
		return rundenEingabeFormularView;
	}

	public void setRundenEingabeFormularView(PairingsView[] rundenEingabeFormularView) {
		this.rundenEingabeFormularView = rundenEingabeFormularView;
	}

	public NewTournamentGroupsControl getGruppenControl() {
		return gruppenControl;
	}

	public SQLGroupsControl getGruppenTableControl() {
		return gruppenTableControl;
	}

	public NewTournamentGroupsView getGruppenView() {
		return gruppenView;
	}

	public JTabbedPane getHauptPanel() {
		return hauptPanel;
	}

	public MainView getMainView() {
		return mainView;
	}

	public PairingsTables getPaarungsTafeln() {
		return paarungsTafeln;
	}

	public SQLGamesControl getPartienTableControl() {
		return partienTableControl;
	}

	public SaveTournamentControl getSaveTurnierControl() {
		return saveTurnierControl;
	}

	public InfoControl getInfoController() {
		return infoController;
	}

	public void setInfoController(InfoControl infoController) {
		this.infoController = infoController;
	}

	public CrossTableModel[] getSimpleTableModel() {
		return simpleTableModel;
	}

	public CrossTableView[] getSimpleTableView() {
		return simpleTableView;
	}

	public MeetingTableModel[] getSimpleTerminTabelle() {
		return simpleTerminTabelle;
	}

	public MeetingTableView[] getSimpleTerminTabelleView() {
		return simpleTerminTabelleView;
	}

	public NewTournamentPlayerCountControl getSpielerAnzahlControl() {
		return spielerAnzahlControl;
	}

	public NewTournamentPlayerCountlView getSpielerAnzahlView() {
		return spielerAnzahlView;
	}

	public PlayerListControl getSpielerEditierenControl() {
		return spielerLadenControl;
	}

	public NewTournamentPlayerInputControl getSpielerEingabeControl() {
		return spielerEingabeControl;
	}

	public NewTournamentPlayerInputView[] getSpielerEingabeView() {
		return spielerEingabeView;
	}

	public SQLPlayerControl getSpielerTableControl() {
		return spielerTableControl;
	}

	public TabbedPaneViewControl getTabAnzeigeControl() {
		return tabAnzeigeControl;
	}

	public TabbedPaneView getTabAnzeigeView() {
		return tabAnzeigeView;
	}

	public TabbedPaneView[] getTabAnzeigeView2() {
		return tabAnzeigeView2;
	}

	public MeetingTable[] getTerminTabelle() {
		return terminTabelle;
	}

	public MeetingTableControl getTerminTabelleControl() {
		return terminTabelleControl;
	}

	public Tournament getTurnier() {
		return turnier;
	}

	public SQLTournament_has_PlayerControl getTurnier_has_SpielerTableControl() {
		return turnier_has_SpielerTableControl;
	}

	public NewTournamentControl getTurnierControl() {
		return turnierControl;
	}

	public TournamentListControl getTurnierListeLadenControl() {
		return turnierListeLadenControl;
	}

	public TournamentListView getTurnierListeLadenView() {
		return turnierListeLadenView;
	}

	public CrossTable[] getTurnierTabelle() {
		return turnierTabelle;
	}

	public CrossTableControl getTurnierTabelleControl() {
		return turnierTabelleControl;
	}

	public SQLTournamentControl getTurnierTableControl() {
		return turnierTableControl;
	}

	public NewTournamentView getTurnierView() {
		return turnierView;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public PropertiesControl getPropertiesControl() {
		return propertiesControl;
	}

	public void setPropertiesControl(PropertiesControl propertiesControl) {
		this.propertiesControl = propertiesControl;
	}

	public void setGruppenControl(NewTournamentGroupsControl gruppenControl) {
		this.gruppenControl = gruppenControl;
	}

	public void setGruppenTableControl(SQLGroupsControl gruppenTableControl) {
		this.gruppenTableControl = gruppenTableControl;
	}

	public void setGruppenView(NewTournamentGroupsView gruppenView) {
		this.gruppenView = gruppenView;
	}

	public void setHauptPanel(JTabbedPane hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public void setPaarungsTafeln(PairingsTables paarungsTafeln) {
		this.paarungsTafeln = paarungsTafeln;
	}

	public void setPartienTableControl(SQLGamesControl partienTableControl) {
		this.partienTableControl = partienTableControl;
	}

	public void setSaveTurnierControl(SaveTournamentControl saveTurnierControl) {
		this.saveTurnierControl = saveTurnierControl;
	}

	public void setSimpleTableModel(CrossTableModel[] simpleTableModel) {
		this.simpleTableModel = simpleTableModel;
	}

	public void setSimpleTableView(CrossTableView[] simpleTableView) {
		this.simpleTableView = simpleTableView;
	}

	public void setSimpleTerminTabelle(MeetingTableModel[] simpleTerminTabelle) {
		this.simpleTerminTabelle = simpleTerminTabelle;
	}

	public void setSimpleTerminTabelleView(MeetingTableView[] simpleTerminTabelleView) {
		this.simpleTerminTabelleView = simpleTerminTabelleView;
	}

	public void setSpielerAnzahlControl(NewTournamentPlayerCountControl spielerAnzahlControl) {
		this.spielerAnzahlControl = spielerAnzahlControl;
	}

	public void setSpielerAnzahlView(NewTournamentPlayerCountlView spielerAnzahlView) {
		this.spielerAnzahlView = spielerAnzahlView;
	}

	public void setSpielerEditierenControl(PlayerListControl spielerLadenControl) {
		this.spielerLadenControl = spielerLadenControl;
	}

	public void setSpielerEingabeControl(NewTournamentPlayerInputControl spielerEingabeControl) {
		this.spielerEingabeControl = spielerEingabeControl;
	}

	public void setSpielerEingabeView(NewTournamentPlayerInputView[] spielerEingabeView2) {
		this.spielerEingabeView = spielerEingabeView2;
	}

	public void setSpielerTableControl(SQLPlayerControl spielerTableControl) {
		this.spielerTableControl = spielerTableControl;
	}

	public void setTabAnzeigeControl(TabbedPaneViewControl tabAnzeigeControl) {
		this.tabAnzeigeControl = tabAnzeigeControl;
	}

	public void setTabAnzeigeView(TabbedPaneView tabAnzeigeView) {
		this.tabAnzeigeView = tabAnzeigeView;
	}

	public void setTabAnzeigeView2(TabbedPaneView[] tabAnzeigeView2) {
		this.tabAnzeigeView2 = tabAnzeigeView2;
	}

	public void setTerminTabelle(MeetingTable[] terminTabelle2) {
		this.terminTabelle = terminTabelle2;
	}

	public void setTerminTabelleControl(MeetingTableControl terminTabelleControl) {
		this.terminTabelleControl = terminTabelleControl;
	}

	public void setTurnier(Tournament turnier) {
		this.turnier = turnier;
	}

	public void setTurnier_has_SpielerTableControl(SQLTournament_has_PlayerControl turnier_has_SpielerTableControl) {
		this.turnier_has_SpielerTableControl = turnier_has_SpielerTableControl;
	}

	public void setTurnierControl(NewTournamentControl turnierControl) {
		this.turnierControl = turnierControl;
	}

	public void setTurnierListeLadenControl(TournamentListControl turnierListeLadenControl) {
		this.turnierListeLadenControl = turnierListeLadenControl;
	}

	public void setTurnierListeLadenView(TournamentListView turnierListeLadenView) {
		this.turnierListeLadenView = turnierListeLadenView;
	}

	public void setTurnierTabelle(CrossTable[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	public void setTurnierTabelleControl(CrossTableControl turnierTabelleControl) {
		this.turnierTabelleControl = turnierTabelleControl;
	}

	public void setTurnierTableControl(SQLTournamentControl turnierTableControl) {
		this.turnierTableControl = turnierTableControl;
	}

	public void setTurnierView(NewTournamentView turnierView) {
		this.turnierView = turnierView;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
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

	public PairingsControl getRundenEingabeFormularControl() {
		return rundenEingabeFormularControl;
	}

	public void setRundenEingabeFormularControl(PairingsControl rundenEingabeFormularControl) {
		this.rundenEingabeFormularControl = rundenEingabeFormularControl;
	}

	public void setEigenschaftenControl(SettingsControl eigenschaftenControl) {
		this.eigenschaftenControl = eigenschaftenControl;
	}

	public SettingsControl getEigenschaftenControl() {
		return eigenschaftenControl;
	}

}
