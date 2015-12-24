package de.turnierverwaltung.controller;
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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.PaarungsTafeln;
import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.SimpleTerminTabelle;
import de.turnierverwaltung.model.SimpleTurnierTabelle;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.model.TurnierTabelle;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.GruppenView;
import de.turnierverwaltung.view.MainView;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.RundenEingabeFormularView;
import de.turnierverwaltung.view.SimpleTerminTabelleView;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.SpielerAnzahlView;
import de.turnierverwaltung.view.SpielerEingabeView;
import de.turnierverwaltung.view.StandardView;
import de.turnierverwaltung.view.TabAnzeigeView;
import de.turnierverwaltung.view.TitleView;
import de.turnierverwaltung.view.TurnierListeLadenView;
import de.turnierverwaltung.view.TurnierView;

public class MainControl extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int windowWidth;
	private int windowHeight;
	private MainView mainView;
	private JTabbedPane hauptPanel;
	// private MenueControl menueControl;
	// private MenueView menueView;
	private TurnierControl turnierControl;
	private TurnierView turnierView;
	private GruppenView gruppenView;
	private GruppenControl gruppenControl;
	private SpielerAnzahlView spielerAnzahlView;
	private SpielerAnzahlControl spielerAnzahlControl;
	private SpielerEingabeView[] spielerEingabeView;
	private SpielerEingabeControl spielerEingabeControl;
	private TabAnzeigeControl tabAnzeigeControl;
	private TabAnzeigeView tabAnzeigeView;
	private TabAnzeigeView[] tabAnzeigeView2;
	private Turnier turnier;
	private TurnierTableControl turnierTableControl;
	private SpielerTableControl spielerTableControl;
	private GruppenTableControl gruppenTableControl;
	private PartienTableControl partienTableControl;
	private Turnier_has_SpielerTableControl turnier_has_SpielerTableControl;
	private TurnierTabelle[] turnierTabelle;
	private TerminTabelle[] terminTabelle;
	private PaarungsTafeln paarungsTafeln;
	private SimpleTurnierTabelle[] simpleTableModel;
	private SimpleTurnierTabelleView[] simpleTableView;
	private SaveTurnierControl saveTurnierControl;
	private TurnierTabelleControl turnierTabelleControl;
	private TerminTabelleControl terminTabelleControl;
	private RundenEingabeFormularControl rundenEingabeFormularControl;
	private RundenEingabeFormularView[] rundenEingabeFormularView;
	private SimpleTerminTabelle[] simpleTerminTabelle;
	private SimpleTerminTabelleView[] simpleTerminTabelleView;
	private TurnierListeLadenControl turnierListeLadenControl;
	private TurnierListeLadenView turnierListeLadenView;
	private SpielerLadenControl spielerLadenControl;
	private StandardView standardView;
	private NaviView naviView;
	private NaviController naviController;
	private InfoController infoController;
	private TitleView titleView;
	private Boolean neuesTurnier;
	private ArrayList<Partie> changedPartien;
	private PropertiesControl propertiesControl;
	private JPanel mainPanel;
	private EigenschaftenControl eigenschaftenControl;

	public MainControl() {
		windowWidth = TurnierKonstanten.WINDOW_WIDTH;
		windowHeight = TurnierKonstanten.WINDOW_HEIGHT;
		setBounds(TurnierKonstanten.WINDOW_BOUNDS_X,
				TurnierKonstanten.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth / 2, windowHeight / 2));

		setTitle("Klubturnierverwaltung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		makeProperties();

		setNeuesTurnier(false);
	}

	// public void datenbankMenueView(Boolean enable) {
	// menueControl.setDatenbankMenue(enable);
	// // naviView.getDatenbankPanel().setVisible(enable);
	//
	// naviView.setPathToDatabase(new JLabel(menueControl.getFileName()));
	// naviView.updateUI();
	// if (enable == true) {
	// this.setTitle("Klubturnierverwaltung - Datei:" +
	// SQLiteDAOFactory.getDB_PATH());
	// } else {
	// this.setTitle("Klubturnierverwaltung ");
	// }
	// }

	public SpielerLadenControl getSpielerLadenControl() {
		return spielerLadenControl;
	}

	public void setSpielerLadenControl(SpielerLadenControl spielerLadenControl) {
		this.spielerLadenControl = spielerLadenControl;
	}

	public ArrayList<Partie> getChangedPartien() {
		return changedPartien;
	}

	public void setChangedPartien(ArrayList<Partie> changedPartien) {
		this.changedPartien = changedPartien;
	}

	public Boolean getNeuesTurnier() {
		return neuesTurnier;
	}

	public void setNeuesTurnier(Boolean neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

	public RundenEingabeFormularView[] getRundenEingabeFormularView() {
		return rundenEingabeFormularView;
	}

	public void setRundenEingabeFormularView(
			RundenEingabeFormularView[] rundenEingabeFormularView) {
		this.rundenEingabeFormularView = rundenEingabeFormularView;
	}

	public GruppenControl getGruppenControl() {
		return gruppenControl;
	}

	public GruppenTableControl getGruppenTableControl() {
		return gruppenTableControl;
	}

	public GruppenView getGruppenView() {
		return gruppenView;
	}

	public JTabbedPane getHauptPanel() {
		return hauptPanel;
	}

	public MainView getMainView() {
		return mainView;
	}

	// public MenueControl getMenueControl() {
	// return menueControl;
	// }
	//
	// public MenueView getMenueView() {
	// return menueView;
	// }

	public PaarungsTafeln getPaarungsTafeln() {
		return paarungsTafeln;
	}

	public PartienTableControl getPartienTableControl() {
		return partienTableControl;
	}

	public SaveTurnierControl getSaveTurnierControl() {
		return saveTurnierControl;
	}

	public InfoController getInfoController() {
		return infoController;
	}

	public void setInfoController(InfoController infoController) {
		this.infoController = infoController;
	}

	public SimpleTurnierTabelle[] getSimpleTableModel() {
		return simpleTableModel;
	}

	public SimpleTurnierTabelleView[] getSimpleTableView() {
		return simpleTableView;
	}

	public SimpleTerminTabelle[] getSimpleTerminTabelle() {
		return simpleTerminTabelle;
	}

	public SimpleTerminTabelleView[] getSimpleTerminTabelleView() {
		return simpleTerminTabelleView;
	}

	public SpielerAnzahlControl getSpielerAnzahlControl() {
		return spielerAnzahlControl;
	}

	public SpielerAnzahlView getSpielerAnzahlView() {
		return spielerAnzahlView;
	}

	public SpielerLadenControl getSpielerEditierenControl() {
		return spielerLadenControl;
	}

	public SpielerEingabeControl getSpielerEingabeControl() {
		return spielerEingabeControl;
	}

	public SpielerEingabeView[] getSpielerEingabeView() {
		return spielerEingabeView;
	}

	public SpielerTableControl getSpielerTableControl() {
		return spielerTableControl;
	}

	public TabAnzeigeControl getTabAnzeigeControl() {
		return tabAnzeigeControl;
	}

	public TabAnzeigeView getTabAnzeigeView() {
		return tabAnzeigeView;
	}

	public TabAnzeigeView[] getTabAnzeigeView2() {
		return tabAnzeigeView2;
	}

	public TerminTabelle[] getTerminTabelle() {
		return terminTabelle;
	}

	public TerminTabelleControl getTerminTabelleControl() {
		return terminTabelleControl;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public Turnier_has_SpielerTableControl getTurnier_has_SpielerTableControl() {
		return turnier_has_SpielerTableControl;
	}

	public TurnierControl getTurnierControl() {
		return turnierControl;
	}

	public TurnierListeLadenControl getTurnierListeLadenControl() {
		return turnierListeLadenControl;
	}

	public TurnierListeLadenView getTurnierListeLadenView() {
		return turnierListeLadenView;
	}

	public TurnierTabelle[] getTurnierTabelle() {
		return turnierTabelle;
	}

	public TurnierTabelleControl getTurnierTabelleControl() {
		return turnierTabelleControl;
	}

	public TurnierTableControl getTurnierTableControl() {
		return turnierTableControl;
	}

	public TurnierView getTurnierView() {
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

	private void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.hauptPanel = new JTabbedPane();
		// this.hauptPanel.setLayout(new BorderLayout());
		standardView = new StandardView();
		titleView = new TitleView();

		naviController = new NaviController(this);
		// menueView = new MenueView();
		// menueControl = new MenueControl(this);
		// setJMenuBar(menueView.getJMenuBar());

		setContentPane(mainPanel);

		standardView.add(titleView, BorderLayout.NORTH);
		mainPanel.add(standardView, BorderLayout.NORTH);
		mainPanel.add(hauptPanel, BorderLayout.CENTER);
		hauptPanel.updateUI();
		mainPanel.updateUI();
		setEnabled(true);
		setVisible(true);
		if (this.getInfoController() == null) {
			this.setInfoController(new InfoController(this));
		} else {
			this.getInfoController().makeInfoPanel();
		}

	}

	private void makeProperties() {
		// datenbankMenueView(false);
		propertiesControl = new PropertiesControl();
		if (propertiesControl.readProperties() == false) {
			if (propertiesControl.writeProperties() == false) {
				JOptionPane
						.showMessageDialog(null,
								"Einstellungen des Programms können nicht gespeichert werden.");
			}
		} else {
			if (propertiesControl.checkPath() == true) {
				// datenbankMenueView(true);
				String path = propertiesControl.getPath();
				SQLiteDAOFactory.setDB_PATH(path);
				this.setTitle("Klubturnierverwaltung - Datei:"
						+ SQLiteDAOFactory.getDB_PATH());

				if (this.getSpielerEditierenControl() != null) {
					// mainControl.getSpielerEditierenControl().makePanel();
				} else {
					this.setSpielerEditierenControl(new SpielerLadenControl(
							this));
					this.getSpielerEditierenControl().updateSpielerListe();
				}
				this.setNeuesTurnier(false);
				// this.getNaviView().getTabellenPanel().setVisible(false);
				if (this.getTurnierTableControl() == null) {
					this.setTurnierTableControl(new TurnierTableControl(this));
					this.getTurnierTableControl().loadTurnierListe();
					this.setTurnierListeLadenControl(new TurnierListeLadenControl(
							this));
					this.getTurnierListeLadenControl().loadTurnierListe();
					naviView.setPathToDatabase(new JLabel(path));

				} else {
					this.resetApp();
					this.setTurnierTableControl(new TurnierTableControl(this));
					this.getTurnierTableControl().loadTurnierListe();
					this.setTurnierListeLadenControl(new TurnierListeLadenControl(
							this));
					this.getTurnierListeLadenControl().loadTurnierListe();
					naviView.setPathToDatabase(new JLabel(path));
				}
				naviView.getTurnierListePanel().setVisible(false);
				naviView.getSpielerListePanel().setVisible(false);
				hauptPanel
						.addChangeListener(naviController.getTurnierAnsicht());
				for (int i = 0; i < hauptPanel.getTabCount(); i++) {
					if (hauptPanel.getTitleAt(i).equals("Turnierliste")) {
						hauptPanel.setSelectedIndex(i);
					}
				}
			} else {
				this.setTitle("Klubturnierverwaltung ");

			}
		}
	}

	public void resetApp() {
		windowWidth = 0;
		windowHeight = 0;
		mainView = null;
		hauptPanel = null;
		// menueControl = null;
		// menueView = null;
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

	public PropertiesControl getPropertiesControl() {
		return propertiesControl;
	}

	public void setPropertiesControl(PropertiesControl propertiesControl) {
		this.propertiesControl = propertiesControl;
	}

	public void setGruppenControl(GruppenControl gruppenControl) {
		this.gruppenControl = gruppenControl;
	}

	public void setGruppenTableControl(GruppenTableControl gruppenTableControl) {
		this.gruppenTableControl = gruppenTableControl;
	}

	public void setGruppenView(GruppenView gruppenView) {
		this.gruppenView = gruppenView;
	}

	public void setHauptPanel(JTabbedPane hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	//
	// public void setMenueControl(MenueControl menueControl) {
	// this.menueControl = menueControl;
	// }
	//
	// public void setMenueView(MenueView menueView) {
	// this.menueView = menueView;
	// }

	public void setPaarungsTafeln(PaarungsTafeln paarungsTafeln) {
		this.paarungsTafeln = paarungsTafeln;
	}

	public void setPartienTableControl(PartienTableControl partienTableControl) {
		this.partienTableControl = partienTableControl;
	}

	public void setSaveTurnierControl(SaveTurnierControl saveTurnierControl) {
		this.saveTurnierControl = saveTurnierControl;
	}

	public void setSimpleTableModel(SimpleTurnierTabelle[] simpleTableModel) {
		this.simpleTableModel = simpleTableModel;
	}

	public void setSimpleTableView(SimpleTurnierTabelleView[] simpleTableView) {
		this.simpleTableView = simpleTableView;
	}

	public void setSimpleTerminTabelle(SimpleTerminTabelle[] simpleTerminTabelle) {
		this.simpleTerminTabelle = simpleTerminTabelle;
	}

	public void setSimpleTerminTabelleView(
			SimpleTerminTabelleView[] simpleTerminTabelleView) {
		this.simpleTerminTabelleView = simpleTerminTabelleView;
	}

	public void setSpielerAnzahlControl(
			SpielerAnzahlControl spielerAnzahlControl) {
		this.spielerAnzahlControl = spielerAnzahlControl;
	}

	public void setSpielerAnzahlView(SpielerAnzahlView spielerAnzahlView) {
		this.spielerAnzahlView = spielerAnzahlView;
	}

	public void setSpielerEditierenControl(
			SpielerLadenControl spielerLadenControl) {
		this.spielerLadenControl = spielerLadenControl;
	}

	public void setSpielerEingabeControl(
			SpielerEingabeControl spielerEingabeControl) {
		this.spielerEingabeControl = spielerEingabeControl;
	}

	public void setSpielerEingabeView(SpielerEingabeView[] spielerEingabeView2) {
		this.spielerEingabeView = spielerEingabeView2;
	}

	public void setSpielerTableControl(SpielerTableControl spielerTableControl) {
		this.spielerTableControl = spielerTableControl;
	}

	public void setTabAnzeigeControl(TabAnzeigeControl tabAnzeigeControl) {
		this.tabAnzeigeControl = tabAnzeigeControl;
	}

	public void setTabAnzeigeView(TabAnzeigeView tabAnzeigeView) {
		this.tabAnzeigeView = tabAnzeigeView;
	}

	public void setTabAnzeigeView2(TabAnzeigeView[] tabAnzeigeView2) {
		this.tabAnzeigeView2 = tabAnzeigeView2;
	}

	public void setTerminTabelle(TerminTabelle[] terminTabelle2) {
		this.terminTabelle = terminTabelle2;
	}

	public void setTerminTabelleControl(
			TerminTabelleControl terminTabelleControl) {
		this.terminTabelleControl = terminTabelleControl;
	}

	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}

	public void setTurnier_has_SpielerTableControl(
			Turnier_has_SpielerTableControl turnier_has_SpielerTableControl) {
		this.turnier_has_SpielerTableControl = turnier_has_SpielerTableControl;
	}

	public void setTurnierControl(TurnierControl turnierControl) {
		this.turnierControl = turnierControl;
	}

	public void setTurnierListeLadenControl(
			TurnierListeLadenControl turnierListeLadenControl) {
		this.turnierListeLadenControl = turnierListeLadenControl;
	}

	public void setTurnierListeLadenView(
			TurnierListeLadenView turnierListeLadenView) {
		this.turnierListeLadenView = turnierListeLadenView;
	}

	public void setTurnierTabelle(TurnierTabelle[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	public void setTurnierTabelleControl(
			TurnierTabelleControl turnierTabelleControl) {
		this.turnierTabelleControl = turnierTabelleControl;
	}

	public void setTurnierTableControl(TurnierTableControl turnierTableControl) {
		this.turnierTableControl = turnierTableControl;
	}

	public void setTurnierView(TurnierView turnierView) {
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

	public NaviController getNaviController() {
		return naviController;
	}

	public void setNaviController(NaviController naviController) {
		this.naviController = naviController;
	}

	public RundenEingabeFormularControl getRundenEingabeFormularControl() {
		return rundenEingabeFormularControl;
	}

	public void setRundenEingabeFormularControl(
			RundenEingabeFormularControl rundenEingabeFormularControl) {
		this.rundenEingabeFormularControl = rundenEingabeFormularControl;
	}

	public void setEigenschaftenControl(
			EigenschaftenControl eigenschaftenControl) {
		this.eigenschaftenControl = eigenschaftenControl;	
	}

	public EigenschaftenControl getEigenschaftenControl() {
		return eigenschaftenControl;
	}



}
