package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.turnierverwaltung.model.PaarungsTafeln;
import de.turnierverwaltung.model.SimpleTerminTabelle;
import de.turnierverwaltung.model.SimpleTurnierTabelle;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.model.TurnierTabelle;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.GruppenView;
import de.turnierverwaltung.view.MainView;
import de.turnierverwaltung.view.MenueView;
import de.turnierverwaltung.view.NaviView;
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
	private JPanel hauptPanel;
	private MenueControl menueControl;
	private MenueView menueView;
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

	public MainControl() {
		windowWidth = TurnierKonstanten.WINDOW_WIDTH;
		windowHeight = TurnierKonstanten.WINDOW_HEIGHT;
		setBounds(TurnierKonstanten.WINDOW_BOUNDS_X, TurnierKonstanten.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth, windowHeight));
		setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setBackground(new Color(126, 201, 208));
		setTitle("Klubturnierverwaltung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		datenbankMenueView(false);
	}

	public void datenbankMenueView(Boolean enable) {
		menueControl.setDatenbankMenue(enable);
		naviView.getDatenbankPanel().setVisible(enable);
		
		naviView.setPathToDatabase(new JLabel(menueControl.getFileName()));
		naviView.updateUI();
		if (enable == true) {
			this.setTitle("Klubturnierverwaltung - Datei:" + SQLiteDAOFactory.getDB_PATH());
		} else {
			this.setTitle("Klubturnierverwaltung ");
		}
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

	public JPanel getHauptPanel() {
		return hauptPanel;
	}

	public MainView getMainView() {
		return mainView;
	}

	public MenueControl getMenueControl() {
		return menueControl;
	}

	public MenueView getMenueView() {
		return menueView;
	}

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

	private void init() {

		this.hauptPanel = new JPanel();
		this.hauptPanel.setLayout(new BorderLayout());
		standardView = new StandardView();
		titleView = new TitleView();
		
		naviController = new NaviController(this);
		menueView = new MenueView();
		menueControl = new MenueControl(this);
		setJMenuBar(menueView.getJMenuBar());
		setContentPane(hauptPanel);

		standardView.add(titleView,BorderLayout.NORTH);
		hauptPanel.add(standardView, BorderLayout.CENTER);
		hauptPanel.updateUI();
		setEnabled(true);
		setVisible(true);
	}

	public void resetApp() {
		windowWidth = 0;
		windowHeight = 0;
		mainView = null;
		hauptPanel = null;
		menueControl = null;
		menueView = null;
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

		init();
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

	public void setHauptPanel(JPanel hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public void setMenueControl(MenueControl menueControl) {
		this.menueControl = menueControl;
	}

	public void setMenueView(MenueView menueView) {
		this.menueView = menueView;
	}

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

	public void setSimpleTerminTabelleView(SimpleTerminTabelleView[] simpleTerminTabelleView) {
		this.simpleTerminTabelleView = simpleTerminTabelleView;
	}

	public void setSpielerAnzahlControl(SpielerAnzahlControl spielerAnzahlControl) {
		this.spielerAnzahlControl = spielerAnzahlControl;
	}

	public void setSpielerAnzahlView(SpielerAnzahlView spielerAnzahlView) {
		this.spielerAnzahlView = spielerAnzahlView;
	}

	public void setSpielerEditierenControl(SpielerLadenControl spielerLadenControl) {
		this.spielerLadenControl = spielerLadenControl;
	}

	public void setSpielerEingabeControl(SpielerEingabeControl spielerEingabeControl) {
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

	public void setTerminTabelleControl(TerminTabelleControl terminTabelleControl) {
		this.terminTabelleControl = terminTabelleControl;
	}

	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}

	public void setTurnier_has_SpielerTableControl(Turnier_has_SpielerTableControl turnier_has_SpielerTableControl) {
		this.turnier_has_SpielerTableControl = turnier_has_SpielerTableControl;
	}

	public void setTurnierControl(TurnierControl turnierControl) {
		this.turnierControl = turnierControl;
	}

	public void setTurnierListeLadenControl(TurnierListeLadenControl turnierListeLadenControl) {
		this.turnierListeLadenControl = turnierListeLadenControl;
	}

	public void setTurnierListeLadenView(TurnierListeLadenView turnierListeLadenView) {
		this.turnierListeLadenView = turnierListeLadenView;
	}

	public void setTurnierTabelle(TurnierTabelle[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	public void setTurnierTabelleControl(TurnierTabelleControl turnierTabelleControl) {
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

}
