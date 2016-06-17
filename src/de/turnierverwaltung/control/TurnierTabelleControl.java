package de.turnierverwaltung.control;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.SimpleTurnierTabelle;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.model.TurnierTabelle;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class TurnierTabelleControl {

	private MainControl mainControl;
	private TurnierTabelle[] turnierTabelle;
	private SimpleTurnierTabelleView[] simpleTableView;
	private SaveTurnierControl saveTurnierControl;
	private JTabbedPane hauptPanel;
	private TabAnzeigeView tabAnzeigeView;
	private TabAnzeigeView[] tabAnzeigeView2;
	private Turnier turnier;
	private MyTableModelListener tml[];
	private Dimension dimension[];
	private int[] spielerAnzahl;
	private ArrayList<Partie> changedPartien;
	private ImageIcon turniertabelleIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/x-office-spreadsheet.png"))); //$NON-NLS-1$
	private int abstand;

	public TurnierTabelleControl(MainControl mainControl) {

		this.mainControl = mainControl;

		hauptPanel = this.mainControl.getHauptPanel();

		if (this.mainControl.getTabAnzeigeView() == null) {
			this.mainControl.setTabAnzeigeView(new TabAnzeigeView(mainControl));
		}
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		// tabAnzeigeView.setBackground(new Color(249, 222, 112));
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		tabAnzeigeView2 = new TabAnzeigeView[anzahlGruppen];
		dimension = new Dimension[anzahlGruppen];
		for (int i = 0; i < anzahlGruppen; i++) {
			tabAnzeigeView2[i] = new TabAnzeigeView(mainControl);
			dimension[i] = tabAnzeigeView2[i].getPreferredSize();
		}
		this.mainControl.setTabAnzeigeView2(tabAnzeigeView2);
		simpleTableView = new SimpleTurnierTabelleView[anzahlGruppen];
		tml = new MyTableModelListener[anzahlGruppen];
		this.turnierTabelle = new TurnierTabelle[anzahlGruppen];
		this.mainControl.setTurnierTabelle(turnierTabelle);
		this.saveTurnierControl = new SaveTurnierControl(this.mainControl);
		this.mainControl.setSaveTurnierControl(saveTurnierControl);
		spielerAnzahl = new int[anzahlGruppen];
		if (mainControl.getNeuesTurnier() == false) {
			this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
		}
		if (this.mainControl.getChangedPartien() == null) {
			changedPartien = new ArrayList<Partie>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedPartien();

		}

	}

	public MyTableModelListener[] getTml() {
		return tml;
	}

	public TurnierTabelle[] getTurnierTabelle() {
		return turnierTabelle;
	}

	public void makeSimpleTableView(int gruppenNummer) {

		// mainControl.getMenueControl().setWarnHinweis(true);
		turnier = mainControl.getTurnier();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}
		Arrays.sort(turnier.getGruppe()[gruppenNummer].getPartien());
		spielerAnzahl[gruppenNummer] = turnier.getGruppe()[gruppenNummer].getSpielerAnzahl();
		if (turnier.getGruppe()[gruppenNummer].getSpieler()[spielerAnzahl[gruppenNummer] - 1].getSpielerId() == -2) {
			spielerAnzahl[gruppenNummer]--;
		}
		this.turnierTabelle[gruppenNummer] = new TurnierTabelle(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		PropertiesControl ppC = mainControl.getPropertiesControl();
		String playerColumnName = ppC.getTableComumnPlayer();
		String oldDWZColumnName = ppC.getTableComumnOldDWZ();
		String newDWZColumnName = ppC.getTableComumnNewDWZ();
		String poinsColumnName = ppC.getTableComumnPoints();
		String sbbColumnName = ppC.getTableComumnSonnebornBerger();
		String rankingColumnName = ppC.getTableComumnRanking();
		turnierTabelle[gruppenNummer].createMatrix(playerColumnName, oldDWZColumnName, newDWZColumnName,
				poinsColumnName, sbbColumnName, rankingColumnName, mainControl.getPropertiesControl().getNoDWZ(),
				mainControl.getPropertiesControl().getNoFolgeDWZ());

		abstand = mainControl.getPropertiesControl().getTabellenAbstand();

		simpleTableView[gruppenNummer] = new SimpleTurnierTabelleView(
				new SimpleTurnierTabelle(this.turnierTabelle[gruppenNummer], abstand), abstand);

		simpleTableView[gruppenNummer].getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		updatePunkteCol(this.turnierTabelle[gruppenNummer].getSpalte() - 3, gruppenNummer);
		updateSoBergCol(this.turnierTabelle[gruppenNummer].getSpalte() - 2, gruppenNummer);
		updatePlatzCol(this.turnierTabelle[gruppenNummer].getSpalte() - 1, gruppenNummer);

		simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);
		simpleTableView[gruppenNummer].setPreferredSize(dimension[gruppenNummer]);
		if (tabAnzeigeView2[gruppenNummer].getTabCount() < 1) {
			tabAnzeigeView2[gruppenNummer].insertTab(Messages.getString("TurnierTabelleControl.1"), turniertabelleIcon, //$NON-NLS-1$
					simpleTableView[gruppenNummer], null, 0);

		} else {

			tabAnzeigeView2[gruppenNummer].setComponentAt(0, simpleTableView[gruppenNummer]);
		}

		mainControl.setSimpleTableView(simpleTableView);
		if (tabAnzeigeView.getTabCount() < 1) {
			tabAnzeigeView.insertTab(turnier.getGruppe()[gruppenNummer].getGruppenName(), null,
					tabAnzeigeView2[gruppenNummer], null, gruppenNummer);

		} else {

			tabAnzeigeView.setComponentAt(gruppenNummer, tabAnzeigeView2[gruppenNummer]);
		}

		hauptPanel.updateUI();
		turnier.setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		turnier.setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		// checkDWZVisible(gruppenNummer);
		berechneFolgeDWZ(gruppenNummer);
		simpleTableView[gruppenNummer].getTable().doLayout();
		simpleTableView[gruppenNummer].getTable().updateUI();
	}

	// public void checkDWZVisible(int i) {

	// simpleTableView[i]
	// .getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.2")).setMinWidth(0);
	// //$NON-NLS-1$
	//
	// simpleTableView[i]
	// .getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.3")).setMaxWidth(0);
	// //$NON-NLS-1$
	// if (mainControl.getPropertiesControl().getNoDWZ() == true) {
	// simpleTableView[i]
	// .getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.4")).setMinWidth(0);
	// //$NON-NLS-1$
	//
	// simpleTableView[i].getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.5")) //$NON-NLS-1$
	// .setMaxWidth(0);
	//
	// simpleTableView[i].getTable().updateUI();
	// }
	// if (mainControl.getPropertiesControl().getNoFolgeDWZ() == true) {
	//
	// simpleTableView[i]
	// .getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.6")).setMinWidth(0);
	// //$NON-NLS-1$
	//
	// simpleTableView[i].getTable()
	// .getColumn(Messages.getString("TurnierTabelleControl.7")) //$NON-NLS-1$
	// .setMaxWidth(0);
	//
	// simpleTableView[i].getTable().updateUI();
	// }

	// turnier.setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
	// turnier.setNoFolgeDWZCalc(mainControl.getPropertiesControl()
	// .getNoFolgeDWZ());

	// }

	public void berechneFolgeDWZ(int gruppenNummer) {
		FolgeDWZControl folgeDWZ = new FolgeDWZControl(mainControl.getTurnier(),
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		folgeDWZ.caculateDWZ();
		// Zweimal ausführen falls DWZ-lose Spieler dabei sind
		folgeDWZ.caculateDWZ();

	}

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

	public void readDataFromDatabase(int tID) {
		mainControl.getTurnierTableControl().getTurnier(tID);
		turnier = mainControl.getTurnier();

	}

	public void setTml(MyTableModelListener[] tml) {
		this.tml = tml;
	}

	public void setTurnierTabelle(TurnierTabelle[] turnierTabelle) {
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

	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;

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
			int spielerx = col - abstand;
			int invertRow = spielerx;
			int invertCol = spielery + abstand;
			String ergebniss = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row, col);

			String invertErgebniss = " "; //$NON-NLS-1$
			int invertErgebnissInt = 0;
			int ergebnissInt = 0;

			if (ergebniss == TurnierKonstanten.KEIN_ERGEBNIS) {
				invertErgebniss = TurnierKonstanten.KEIN_ERGEBNIS;
				ergebnissInt = TurnierKonstanten.MYSQL_KEIN_ERGEBNIS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_KEIN_ERGEBNIS;
			}
			if (ergebniss == TurnierKonstanten.VERLUST) {
				invertErgebniss = TurnierKonstanten.GEWINN;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS;

			}
			if (ergebniss == TurnierKonstanten.REMIS) {
				invertErgebniss = TurnierKonstanten.REMIS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_REMIS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_REMIS;
			}
			if (ergebniss == TurnierKonstanten.GEWINN) {
				invertErgebniss = TurnierKonstanten.VERLUST;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ;
			}
			if (ergebniss == TurnierKonstanten.VERLUST_KAMPFLOS) {
				invertErgebniss = TurnierKonstanten.GEWINN_KAMPFLOS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
			}
			if (ergebniss == TurnierKonstanten.GEWINN_KAMPFLOS) {
				invertErgebniss = TurnierKonstanten.VERLUST_KAMPFLOS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
			}
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(invertErgebniss, invertRow, invertCol);
			simpleTableView[gruppenNummer].getTable().getModel().setValueAt(ergebniss, row, col);
			Spieler spy = turnier.getGruppe()[gruppenNummer].getSpieler()[spielery];
			Spieler spx = turnier.getGruppe()[gruppenNummer].getSpieler()[spielerx];
			if ((spielerx >= 0 && spielerx < turnier.getGruppe()[gruppenNummer].getSpielerAnzahl())
					&& (spielery >= 0 && spielery < turnier.getGruppe()[gruppenNummer].getSpielerAnzahl())
					&& (spielerx != spielery)) {

				for (int i = 0; i < turnier.getGruppe()[gruppenNummer].getPartienAnzahl(); i++) {
					if (turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerWeiss() == spx
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerSchwarz() == spy) {

						turnier.getGruppe()[gruppenNummer].getPartien()[i].setErgebnis(invertErgebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						changedPartien.add(turnier.getGruppe()[gruppenNummer].getPartien()[i]);

					}

					if (turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerWeiss() == spy
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i].getSpielerSchwarz() == spx) {
						turnier.getGruppe()[gruppenNummer].getPartien()[i].setErgebnis(ergebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						updatePlatzCol(colCount - 1);
						changedPartien.add(turnier.getGruppe()[gruppenNummer].getPartien()[i]);

					}
				}
			}
			mainControl.getTerminTabelleControl().makeSimpleTableView(gruppenNummer);
			simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);

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
}
