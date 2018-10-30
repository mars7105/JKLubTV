package de.turnierverwaltung.control.tournamenttable;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.model.table.CrossTableModel;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.tournamenttable.CrossTableView;

/**
 *
 * @author mars
 *
 */
public class CrossTableControl {

	/**
	 *
	 * @author mars
	 *
	 */
	class MyTableModelListener implements TableModelListener {
		private final int gruppenNummer;

		/**
		 *
		 * @param gruppenNummer
		 */
		public MyTableModelListener(final int gruppenNummer) {
			this.gruppenNummer = gruppenNummer;
		}

		@Override
		public void tableChanged(final TableModelEvent e) {
			abstand = mainControl.getPropertiesControl().getTabellenAbstand();

			simpleTableView[gruppenNummer].getTable().getModel().removeTableModelListener(tml[gruppenNummer]);

			final int row = e.getFirstRow();
			final int col = e.getColumn();

			final int colCount = simpleTableView[gruppenNummer].getTable().getModel().getColumnCount();
			final int spielery = row;
			final int spielerx = col - abstand - 1;
			final int invertRow = spielerx;
			final int invertCol = spielery + abstand + 1;
			final String ergebniss = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row, col);

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
			final Player spy = turnier.getGruppe()[gruppenNummer].getSpieler()[spielery];
			final Player spx = turnier.getGruppe()[gruppenNummer].getSpieler()[spielerx];
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
			mainControl.getMeetingTableControl().updateSimpleTableView(changedPartien.get(changedPartien.size() - 1),
					gruppenNummer);
			simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);

			mainControl.getMeetingTableControl().updateStatus();
			if (changedPartien.size() > 0) {
				mainControl.getNaviView().getTabelleSpeichernButton().setEnabled(true);
			}
			updateStatus();
		}

		private void updatePlatzCol(final int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Integer.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPlatz()), i, col);

			}
		}

		private void updatePunkteCol(final int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPunkte()), i, col);
			}
		}

		private void updateSoBergCol(final int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
						Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getSoberg()), i, col);
			}
		}
	}

	private final MainControl mainControl;
	private CrossTable[] turnierTabelle;
	private final CrossTableView[] simpleTableView;
	private final SaveTournamentControl saveTurnierControl;
	private final JTabbedPane hauptPanel;
	private final TabbedPaneView tabAnzeigeView;
	private final TabbedPaneView[] tabAnzeigeView2;
	private Tournament turnier;
	private MyTableModelListener tml[];
	private final int[] spielerAnzahl;
	private ArrayList<Game> changedPartien;
	private final ImageIcon turniertabelleIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/x-office-spreadsheet.png"))); //$NON-NLS-1$

	private int abstand;

	/**
	 *
	 * @param mainControl
	 */
	public CrossTableControl(final MainControl mainControl) {

		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();

		if (this.mainControl.getTabbedPaneView() == null) {
			this.mainControl
					.setTabbedPaneView(new TabbedPaneView(mainControl, mainControl.getTournament().getTurnierName()));
		}
		tabAnzeigeView = this.mainControl.getTabbedPaneView();

		// tabAnzeigeView.setBackground(new Color(249, 222, 112));
		final int anzahlGruppen = mainControl.getTournament().getAnzahlGruppen();
		tabAnzeigeView2 = new TabbedPaneView[anzahlGruppen];
		for (int i = 0; i < anzahlGruppen; i++) {
			tabAnzeigeView2[i] = new TabbedPaneView(mainControl, Messages.getString("TurnierTabelleControl.8"));
			tabAnzeigeView2[i].getTitleView().setFlowLayoutLeft();

		}
		this.mainControl.setTabbedPaneViewArray(tabAnzeigeView2);
		simpleTableView = new CrossTableView[anzahlGruppen];
		tml = new MyTableModelListener[anzahlGruppen];
		turnierTabelle = new CrossTable[anzahlGruppen];
		this.mainControl.setCrossTable(turnierTabelle);
		saveTurnierControl = new SaveTournamentControl(this.mainControl);
		this.mainControl.setSaveTournamentControl(saveTurnierControl);
		spielerAnzahl = new int[anzahlGruppen];
		if (mainControl.getNewTournament() == false) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
		}
		if (this.mainControl.getChangedGames() == null) {
			changedPartien = new ArrayList<Game>();
			this.mainControl.setChangedGames(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedGames();

		}

	}

	public void berechneFolgeRatings(final int gruppenNummer) {

		if (mainControl.getNewTournament() == false) {
			final Group group = mainControl.getTournament().getGruppe()[gruppenNummer];
			if (turnier.getNoFolgeDWZCalc() == false) {
				final ResultDWZControl folgeDWZ = new ResultDWZControl(group);
				folgeDWZ.caculateRatings();
			}
			if (turnier.getNoFolgeELOCalc() == false) {
				final ResultELOControl folgeELO = new ResultELOControl(group);
				folgeELO.caculateRatings();
			}
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
	public void makeSimpleTableView(final int gruppenNummer) {

		turnier = mainControl.getTournament();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}
		Arrays.sort(turnier.getGruppe()[gruppenNummer].getPartien());
		spielerAnzahl[gruppenNummer] = turnier.getGruppe()[gruppenNummer].getSpielerAnzahl();
		if (turnier.getGruppe()[gruppenNummer].getSpieler()[spielerAnzahl[gruppenNummer] - 1].getSpielerId() == -2) {
			spielerAnzahl[gruppenNummer]--;
		}
		turnierTabelle[gruppenNummer] = new CrossTable(turnier, mainControl.getTournament().getGruppe()[gruppenNummer]);
		final PropertiesControl ppC = mainControl.getPropertiesControl();
		final String playerColumnName = ppC.getTableComumnPlayer();
		final String oldDWZColumnName = ppC.getTableComumnOldDWZ();
		final String newDWZColumnName = ppC.getTableComumnNewDWZ();
		final String oldELOColumnName = ppC.getTableComumnOldELO();
		final String newELOColumnName = ppC.getTableComumnNewELO();
		final String poinsColumnName = ppC.getTableComumnPoints();
		final String sbbColumnName = ppC.getTableComumnSonnebornBerger();
		final String rankingColumnName = ppC.getTableComumnRanking();
		turnierTabelle[gruppenNummer].createMatrix(playerColumnName, oldDWZColumnName, newDWZColumnName,
				oldELOColumnName, newELOColumnName, poinsColumnName, sbbColumnName, rankingColumnName,
				mainControl.getPropertiesControl().getNoDWZ(), mainControl.getPropertiesControl().getNoFolgeDWZ(),
				mainControl.getPropertiesControl().getNoELO(), mainControl.getPropertiesControl().getNoFolgeELO());

		abstand = mainControl.getPropertiesControl().getTabellenAbstand();
		simpleTableView[gruppenNummer] = new CrossTableView(new CrossTableModel(turnierTabelle[gruppenNummer], abstand),
				abstand);
		simpleTableView[gruppenNummer].getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		updatePunkteCol(turnierTabelle[gruppenNummer].getSpalte() - 3, gruppenNummer);
		updateSoBergCol(turnierTabelle[gruppenNummer].getSpalte() - 2, gruppenNummer);
		updatePlatzCol(turnierTabelle[gruppenNummer].getSpalte() - 1, gruppenNummer);

		simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);
		if (tabAnzeigeView2[gruppenNummer].getTabbedPane().getTabCount() < 1) {
			tabAnzeigeView2[gruppenNummer].getTabbedPane().insertTab(Messages.getString("TurnierTabelleControl.1"), //$NON-NLS-1$
					turniertabelleIcon, simpleTableView[gruppenNummer], null, 0);

		} else {

			tabAnzeigeView2[gruppenNummer].getTabbedPane().setComponentAt(0, simpleTableView[gruppenNummer]);
		}

		mainControl.setCrossTableView(simpleTableView);
		if (tabAnzeigeView.getTabbedPane().getTabCount() < 1) {
			tabAnzeigeView.getTabbedPane().insertTab(turnier.getGruppe()[gruppenNummer].getGruppenName(), null,
					tabAnzeigeView2[gruppenNummer], null, gruppenNummer);

		} else {

			tabAnzeigeView.getTabbedPane().setComponentAt(gruppenNummer, tabAnzeigeView2[gruppenNummer]);
		}

		hauptPanel.updateUI();
		turnier.setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		turnier.setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		turnier.setNoELOCalc(mainControl.getPropertiesControl().getNoELO());
		turnier.setNoFolgeELOCalc(mainControl.getPropertiesControl().getNoFolgeELO());
		berechneFolgeRatings(gruppenNummer);
		simpleTableView[gruppenNummer].getTable().doLayout();
		simpleTableView[gruppenNummer].getTable().updateUI();
		simpleTableView[gruppenNummer].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
	}

	/**
	 *
	 * @param index
	 */
	public void okAction(final int index) {

		turnier.getGruppe()[index].berechnePunkte();
		if (simpleTableView[index] == null) {
			makeSimpleTableView(index);
		}
		final int spalte = simpleTableView[index].getTable().getModel().getColumnCount();
		final int zeile = simpleTableView[index].getTable().getModel().getRowCount();
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
	public void readDataFromDatabase(final int tID) throws SQLException {
		mainControl.getSqlTournamentControl().getTurnier(tID);
		turnier = mainControl.getTournament();

	}

	public void setTml(final MyTableModelListener[] tml) {
		this.tml = tml;
	}

	public void setTurnierTabelle(final CrossTable[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	private void updatePlatzCol(final int col, final int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Integer.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPlatz()), i, col);

		}
	}

	private void updatePunkteCol(final int col, final int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getPunkte()), i, col);

		}
	}

	private void updateSoBergCol(final int col, final int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(
					Double.toString(turnier.getGruppe()[gruppenNummer].getSpieler()[i].getSoberg()), i, col);

		}
	}

	public void updateStatus() {
		final int anzahlGruppen = mainControl.getTournament().getAnzahlGruppen();
		for (int i = 0; i < anzahlGruppen; i++) {
			simpleTableView[i].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
//			simpleTableView[i].getStatusLabel().setBackground(Color.ORANGE);
			// berechneFolgeDWZ(i);

		}

	}
}
