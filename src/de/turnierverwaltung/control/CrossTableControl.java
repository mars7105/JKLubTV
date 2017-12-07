package de.turnierverwaltung.control;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.model.CrossTableModel;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.CrossTableView;
import de.turnierverwaltung.view.TabbedPaneView;

/**
 * 
 * @author mars
 *
 */
public class CrossTableControl {

	private MainControl mainControl;
	private CrossTable[] turnierTabelle;
	private CrossTableView[] simpleTableView;
	private SaveTournamentControl saveTurnierControl;
	private JTabbedPane hauptPanel;
	private TabbedPaneView tabAnzeigeView;
	private TabbedPaneView[] tabAnzeigeView2;
	private Tournament turnier;
	private MyTableModelListener tml[];
	private int[] spielerAnzahl;
	private ArrayList<Game> changedPartien;
	private ImageIcon turniertabelleIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/x-office-spreadsheet.png"))); //$NON-NLS-1$
	private int abstand;

	/**
	 * 
	 * @param mainControl
	 */
	public CrossTableControl(MainControl mainControl) {

		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();

		if (this.mainControl.getTabAnzeigeView() == null) {
			this.mainControl
					.setTabAnzeigeView(new TabbedPaneView(mainControl, mainControl.getTurnier().getTurnierName()));
		}
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();

		// tabAnzeigeView.setBackground(new Color(249, 222, 112));
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		tabAnzeigeView2 = new TabbedPaneView[anzahlGruppen];
		for (int i = 0; i < anzahlGruppen; i++) {
			tabAnzeigeView2[i] = new TabbedPaneView(mainControl, Messages.getString("TurnierTabelleControl.8"));
			tabAnzeigeView2[i].getTitleView().setFlowLayoutLeft();

		}
		this.mainControl.setTabAnzeigeView2(tabAnzeigeView2);
		simpleTableView = new CrossTableView[anzahlGruppen];
		tml = new MyTableModelListener[anzahlGruppen];
		this.turnierTabelle = new CrossTable[anzahlGruppen];
		this.mainControl.setTurnierTabelle(turnierTabelle);
		this.saveTurnierControl = new SaveTournamentControl(this.mainControl);
		this.mainControl.setSaveTurnierControl(saveTurnierControl);
		spielerAnzahl = new int[anzahlGruppen];
		if (mainControl.getNewTournament() == false) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
		}
		if (this.mainControl.getChangedGames() == null) {
			changedPartien = new ArrayList<Game>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedGames();

		}

	}

	public MyTableModelListener[] getTml() {
		return tml;
	}

	public CrossTable[] getTurnierTabelle() {
		return turnierTabelle;
	}

	/**
	 * 
	 * @param gruppenNummer
	 */
	public void makeSimpleTableView(int gruppenNummer) {

		turnier = mainControl.getTurnier();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}
		Arrays.sort(turnier.getGruppe()[gruppenNummer].getPartien());
		spielerAnzahl[gruppenNummer] = turnier.getGruppe()[gruppenNummer].getSpielerAnzahl();
		if (turnier.getGruppe()[gruppenNummer].getSpieler()[spielerAnzahl[gruppenNummer] - 1].getSpielerId() == -2) {
			spielerAnzahl[gruppenNummer]--;
		}
		this.turnierTabelle[gruppenNummer] = new CrossTable(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		PropertiesControl ppC = mainControl.getPropertiesControl();
		String playerColumnName = ppC.getTableComumnPlayer();
		String oldDWZColumnName = ppC.getTableComumnOldDWZ();
		String newDWZColumnName = ppC.getTableComumnNewDWZ();
		String oldELOColumnName = ppC.getTableComumnOldELO();
		String newELOColumnName = ppC.getTableComumnNewELO();
		String poinsColumnName = ppC.getTableComumnPoints();
		String sbbColumnName = ppC.getTableComumnSonnebornBerger();
		String rankingColumnName = ppC.getTableComumnRanking();
		turnierTabelle[gruppenNummer].createMatrix(playerColumnName, oldDWZColumnName, newDWZColumnName,
				oldELOColumnName, newELOColumnName, poinsColumnName, sbbColumnName, rankingColumnName,
				mainControl.getPropertiesControl().getNoDWZ(), mainControl.getPropertiesControl().getNoFolgeDWZ(),
				mainControl.getPropertiesControl().getNoELO(), mainControl.getPropertiesControl().getNoFolgeELO());

		abstand = mainControl.getPropertiesControl().getTabellenAbstand();
		simpleTableView[gruppenNummer] = new CrossTableView(
				new CrossTableModel(this.turnierTabelle[gruppenNummer], abstand), abstand);
		simpleTableView[gruppenNummer].getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		updatePunkteCol(this.turnierTabelle[gruppenNummer].getSpalte() - 3, gruppenNummer);
		updateSoBergCol(this.turnierTabelle[gruppenNummer].getSpalte() - 2, gruppenNummer);
		updatePlatzCol(this.turnierTabelle[gruppenNummer].getSpalte() - 1, gruppenNummer);

		simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);
		if (tabAnzeigeView2[gruppenNummer].getTabbedPane().getTabCount() < 1) {
			tabAnzeigeView2[gruppenNummer].getTabbedPane().insertTab(Messages.getString("TurnierTabelleControl.1"), //$NON-NLS-1$
					turniertabelleIcon, simpleTableView[gruppenNummer], null, 0);

		} else {

			tabAnzeigeView2[gruppenNummer].getTabbedPane().setComponentAt(0, simpleTableView[gruppenNummer]);
		}

		mainControl.setSimpleTableView(simpleTableView);
		if (tabAnzeigeView.getTabbedPane().getTabCount() < 1) {
			tabAnzeigeView.getTabbedPane().insertTab(turnier.getGruppe()[gruppenNummer].getGruppenName(), null,
					tabAnzeigeView2[gruppenNummer], null, gruppenNummer);

		} else {

			tabAnzeigeView.getTabbedPane().setComponentAt(gruppenNummer, tabAnzeigeView2[gruppenNummer]);
		}

		hauptPanel.updateUI();
		turnier.setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		turnier.setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		berechneFolgeDWZ(gruppenNummer);
		simpleTableView[gruppenNummer].getTable().doLayout();
		simpleTableView[gruppenNummer].getTable().updateUI();
		simpleTableView[gruppenNummer].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
	}

	public void berechneFolgeDWZ(int gruppenNummer) {
		if (mainControl.getNewTournament() == false) {
			ResultDWZControl folgeDWZ = new ResultDWZControl(mainControl.getTurnier(),
					mainControl.getTurnier().getGruppe()[gruppenNummer]);
			folgeDWZ.caculateDWZ();
		}
	}

	/**
	 * 
	 * @param index
	 */
	public void okAction(int index) {

		turnier.getGruppe()[index].berechnePunkte();
		if (simpleTableView[index] == null) {
			makeSimpleTableView(index);
		}
		int spalte = simpleTableView[index].getTable().getModel().getColumnCount();
		int zeile = simpleTableView[index].getTable().getModel().getRowCount();
		for (int x = 0; x < spalte; x++) {
			for (int y = 0; y < zeile; y++) {

				turnierTabelle[index].getTabellenMatrix()[x][y + 1] = (String) simpleTableView[index].getTable()
						.getValueAt(y, x);

			}
		}
		makeSimpleTableView(index);
	}

	/**
	 * 
	 * @param tID
	 * @throws SQLException
	 */
	public void readDataFromDatabase(int tID) throws SQLException {
		mainControl.getTurnierTableControl().getTurnier(tID);
		turnier = mainControl.getTurnier();

	}

	public void setTml(MyTableModelListener[] tml) {
		this.tml = tml;
	}

	public void setTurnierTabelle(CrossTable[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	private void updatePlatzCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Integer.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPlatz()), i, col);

		}
	}

	private void updatePunkteCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPunkte()), i, col);

		}
	}

	private void updateSoBergCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getSoberg()), i, col);

		}
	}

	public void updateStatus() {
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		for (int i = 0; i < anzahlGruppen; i++) {
			simpleTableView[i].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
			simpleTableView[i].getStatusLabel().setBackground(Color.ORANGE);
			// berechneFolgeDWZ(i);

		}

	}

	/**
	 * 
	 * @author mars
	 *
	 */
	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;

		/**
		 * 
		 * @param gruppenNummer
		 */
		public MyTableModelListener(int gruppenNummer) {
			this.gruppenNummer = gruppenNummer;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			abstand = mainControl.getPropertiesControl().getTabellenAbstand();

			simpleTableView[gruppenNummer].getTable().getModel().removeTableModelListener(tml[gruppenNummer]);

			int row = e.getFirstRow();
			int col = e.getColumn();

			int colCount = simpleTableView[gruppenNummer].getTable().getModel().getColumnCount();
			int spielery = row;
			int spielerx = col - abstand - 1;
			int invertRow = spielerx;
			int invertCol = spielery + abstand+1;
			String ergebniss = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row, col);

			String invertErgebniss = " ";
			int invertErgebnissInt = 0;
			int ergebnissInt = 0;

			if (ergebniss.equals(TournamentConstants.KEIN_ERGEBNIS)) {
				invertErgebniss = TournamentConstants.KEIN_ERGEBNIS;
				ergebnissInt = TournamentConstants.MYSQL_KEIN_ERGEBNIS;
				invertErgebnissInt = TournamentConstants.MYSQL_KEIN_ERGEBNIS;
			}
			if (ergebniss.equals(TournamentConstants.VERLUST)) {
				invertErgebniss = TournamentConstants.GEWINN;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_WEISS;

			}
			if (ergebniss.equals(TournamentConstants.REMIS)) {
				invertErgebniss = TournamentConstants.REMIS;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_REMIS;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_REMIS;
			}
			if (ergebniss.equals(TournamentConstants.GEWINN)) {
				invertErgebniss = TournamentConstants.VERLUST;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_WEISS;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ;
			}
			if (ergebniss.equals(TournamentConstants.VERLUST_KAMPFLOS)) {
				invertErgebniss = TournamentConstants.GEWINN_KAMPFLOS;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
			}
			if (ergebniss.equals(TournamentConstants.VERLUST_KAMPFLOS_BEIDE)) {
				invertErgebniss = TournamentConstants.VERLUST_KAMPFLOS_BEIDE;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE;
			}
			if (ergebniss.equals(TournamentConstants.GEWINN_KAMPFLOS)) {
				invertErgebniss = TournamentConstants.VERLUST_KAMPFLOS;
				ergebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
				invertErgebnissInt = TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
			}
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(invertErgebniss, invertRow, invertCol);
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(ergebniss, row, col);
			Player spy = turnier.getGruppe()[gruppenNummer].getSpieler()[spielery];
			Player spx = turnier.getGruppe()[gruppenNummer].getSpieler()[spielerx];
			if ((spielerx >= 0 && spielerx < turnier.getGruppe()[gruppenNummer].getSpielerAnzahl())
					&& (spielery >= 0 && spielery < turnier.getGruppe()[gruppenNummer].getSpielerAnzahl())
					&& (spielerx != spielery)) {

				for (int i = 0; i < turnier.getGruppe()[gruppenNummer].getPartienAnzahl(); i++) {
					if (turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerWeiss().equals(spx)
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerSchwarz().equals(spy)) {

						turnier.getGruppe()[gruppenNummer].getPartien()[i].setErgebnis(invertErgebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						changedPartien.add(turnier.getGruppe()[gruppenNummer].getPartien()[i]);

					}

					if (turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerWeiss().equals(spy)
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerSchwarz().equals(spx)) {
						turnier.getGruppe()[gruppenNummer].getPartien()[i].setErgebnis(ergebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						updatePlatzCol(colCount - 1);
						changedPartien.add(turnier.getGruppe()[gruppenNummer].getPartien()[i]);

					}

				}
			}
			// mainControl.getTerminTabelleControl().makeSimpleTableView(gruppenNummer);
			mainControl.getTerminTabelleControl().updateSimpleTableView(changedPartien.get(changedPartien.size() - 1),
					gruppenNummer);
			simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);

			mainControl.getTerminTabelleControl().updateStatus();
			if (changedPartien.size() > 0) {
				mainControl.getNaviView().getTabelleSpeichernButton().setEnabled(true);
			}
			updateStatus();
		}

		private void updatePunkteCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPunkte()), i, col);
			}
		}

		private void updateSoBergCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getSoberg()), i, col);
			}
		}

		private void updatePlatzCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Integer.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPlatz()), i, col);

			}
		}
	}

	public void berechneFolgeELO(int i) {
		// TODO Auto-generated method stub

	}
}
