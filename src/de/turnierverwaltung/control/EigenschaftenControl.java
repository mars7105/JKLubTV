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

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenControl {
	private MainControl mainControl;
	private EigenschaftenView eigenschaftenView;
	private ImageIcon eigenschaftenIcon = new ImageIcon(Toolkit
			.getDefaultToolkit().getImage(
					getClass().getResource("/images/configure-2.png"))); //$NON-NLS-1$
	private int maxWidth = 0;;
	private int columnWidht = 0;
	private EigenschaftenActionListenerControl actionListenerControl;
	private EigenschaftenItemListenerControl itemListenerControl;

	/**
	 * @param mainControl
	 */
	public EigenschaftenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new EigenschaftenView();
		actionListenerControl = new EigenschaftenActionListenerControl(
				this.mainControl, this);
		itemListenerControl = new EigenschaftenItemListenerControl(
				this.mainControl, this);

	}

	/**
	 * 
	 */
	public void makeeigenschaftenPanel() {
		PropertiesControl ppC = mainControl.getPropertiesControl();
		JTabbedPane hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel
				.addTab(Messages.getString("EigenschaftenControl.1"), eigenschaftenIcon, eigenschaftenView); //$NON-NLS-1$
		if (mainControl.getPropertiesControl() == null) {
			mainControl
					.setPropertiesControl(new PropertiesControl(mainControl));
			mainControl.getPropertiesControl().readProperties();
		}
		eigenschaftenView.getCheckBoxHeaderFooter().setSelected(
				mainControl.getPropertiesControl().getOnlyTables());
		eigenschaftenView.getCheckBoxohneDWZ().setSelected(
				mainControl.getPropertiesControl().getNoDWZ());
		eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(
				mainControl.getPropertiesControl().getNoFolgeDWZ());
		eigenschaftenView.getSpielerListeAuswahlBox().setSelectedIndex(
				mainControl.getPropertiesControl().getSpielerProTab());
		eigenschaftenView.getTurnierListeAuswahlBox().setSelectedIndex(
				mainControl.getPropertiesControl().getTurniereProTab());
		if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
			eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
			mainControl.getPropertiesControl().setNoFolgeDWZ(true);
		}
		if (mainControl.getPropertiesControl().getLanguage().equals("german")) { //$NON-NLS-1$
			eigenschaftenView.getGermanLanguageCheckBox().setSelected(true);
			eigenschaftenView.getEnglishLanguageCheckBox().setSelected(false);
			mainControl.getLanguagePropertiesControl().setLanguageToGerman();
		} else if (mainControl.getPropertiesControl().getLanguage()
				.equals("english")) { //$NON-NLS-1$
			eigenschaftenView.getGermanLanguageCheckBox().setSelected(false);
			eigenschaftenView.getEnglishLanguageCheckBox().setSelected(true);
			mainControl.getLanguagePropertiesControl().setLanguageToEnglish();
		}
		eigenschaftenView.setOpenDefaultPathLabel(mainControl
				.getPropertiesControl().getDefaultPath());

		eigenschaftenView.getWhiteTextField()
				.setText(ppC.getTableComumnWhite());
		eigenschaftenView.getBlackTextField()
				.setText(ppC.getTableComumnBlack());
		eigenschaftenView.getMeetingTextField().setText(
				ppC.getTableComumnMeeting());
		eigenschaftenView.getOldDWZTextField().setText(
				ppC.getTableComumnOldDWZ());
		eigenschaftenView.getNewDWZTextField().setText(
				ppC.getTableComumnNewDWZ());
		eigenschaftenView.getPlayerTextField().setText(
				ppC.getTableComumnPlayer());
		eigenschaftenView.getPointsTextField().setText(
				ppC.getTableComumnPoints());
		eigenschaftenView.getRankingTextField().setText(
				ppC.getTableComumnRanking());
		eigenschaftenView.getSbbTextField().setText(
				ppC.getTableComumnSonnebornBerger());
		eigenschaftenView.getResultTextField().setText(
				ppC.getTableComumnResult());
		actionListenerControl.addActionListeners();
		itemListenerControl.addItemListeners();

		hauptPanel.updateUI();

	}

	public int getColumnWidht() {
		return columnWidht;
	}

	public void setColumnWidht(int columnWidht) {
		this.columnWidht = columnWidht;
	}

	public void setColumnWidhtToZero() {
		this.columnWidht = 0;
	}

	public EigenschaftenView getEigenschaftenView() {
		return eigenschaftenView;
	}

	public void setEigenschaftenView(EigenschaftenView eigenschaftenView) {
		this.eigenschaftenView = eigenschaftenView;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

}
