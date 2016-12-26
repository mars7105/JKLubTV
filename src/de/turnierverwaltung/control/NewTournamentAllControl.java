package de.turnierverwaltung.control;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.NewTournamentAllView;
import de.turnierverwaltung.view.NewTournamentGroupsView;
import de.turnierverwaltung.view.NewTournamentPlayerInputView;
import de.turnierverwaltung.view.TabbedPaneView;

public class NewTournamentAllControl implements ActionListener, KeyListener {
	private MainControl mainControl;
	private JButton gruppenOKButton;
	private JButton gruppenCancelButton;
	private JTabbedPane hauptPanel;
	private int gruppenAnzahl;
	private Tournament turnier;
	private ArrayList<Group> gruppe;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));
	private NewTournamentAllView tournamentAllView;
	private ArrayList<Player> alleSpieler;
	private TabbedPaneView tabAnzeigeView;
	private int[] spielerAnzahl;
	private JButton[] okButton;
	private JButton[] cancelButton;
	private PairingsControl rundenEingabeFormularControl;
	private SwissControl swissControl;
	private Boolean[] readyToSave;

	public NewTournamentAllControl(MainControl mainControl, int gruppenAnzahl) {
		int windowWidth = TournamentConstants.WINDOW_WIDTH - 25;
		int windowHeight = TournamentConstants.WINDOW_HEIGHT - 75;
		tournamentAllView = new NewTournamentAllView();
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
		hauptPanel = this.mainControl.getHauptPanel();
		this.gruppenAnzahl = gruppenAnzahl;
		this.mainControl.setGruppenView(new NewTournamentGroupsView());

		tournamentAllView.gettGroupsView().getGruppenNameTextField()[0].grabFocus();
		tournamentAllView.gettGroupsView().getButtonPane().setVisible(false);

		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(tournamentAllView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);

		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

		SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		try {
			alleSpieler = spielerTableControl.getAllSpieler();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		this.mainControl.getHauptPanel();
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		tabAnzeigeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.mainControl.getSpielerAnzahlControl().getSpielerAnzahlView();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		spielerAnzahl = new int[gruppenAnzahl];

		okButton = new JButton[gruppenAnzahl];
		cancelButton = new JButton[gruppenAnzahl];
		rundenEingabeFormularControl = new PairingsControl(this.mainControl);
		this.mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		swissControl = new SwissControl(this.mainControl);
		this.mainControl.setSwissControl(swissControl);
		readyToSave = new Boolean[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			readyToSave[i] = false;

		}
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {


	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	private void makeGruppe() {
		gruppe = new ArrayList<Group>();

		for (int i = 0; i < gruppenAnzahl; i++) {
			gruppe.add(new Group());
			gruppe.get(i).setGruppenName(tournamentAllView.gettGroupsView().getGruppenNameTextField()[i].getText());

		}
		turnier.setGruppe(gruppe);

	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
