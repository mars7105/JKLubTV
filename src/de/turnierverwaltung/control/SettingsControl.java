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

import de.turnierverwaltung.view.SettingsView;

public class SettingsControl {
	private MainControl mainControl;
	private SettingsView eigenschaftenView;

	private ActionListenerSettingsControl actionListenerControl;
	private ItemListenerSettingsControl itemListenerControl;

	/**
	 * @param mainControl
	 */
	public SettingsControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new SettingsView();
		actionListenerControl = new ActionListenerSettingsControl(this.mainControl, this);
		itemListenerControl = new ItemListenerSettingsControl(this.mainControl, this);

	}

	public void setTableColumns() {
		PropertiesControl ppC = mainControl.getPropertiesControl();
		eigenschaftenView.getWhiteTextField().setText(ppC.getTableComumnWhite());
		eigenschaftenView.getBlackTextField().setText(ppC.getTableComumnBlack());
		eigenschaftenView.getMeetingTextField().setText(ppC.getTableComumnMeeting());
		eigenschaftenView.getOldDWZTextField().setText(ppC.getTableComumnOldDWZ());
		eigenschaftenView.getNewDWZTextField().setText(ppC.getTableComumnNewDWZ());
		eigenschaftenView.getPlayerTextField().setText(ppC.getTableComumnPlayer());
		eigenschaftenView.getPointsTextField().setText(ppC.getTableComumnPoints());
		eigenschaftenView.getRankingTextField().setText(ppC.getTableComumnRanking());
		eigenschaftenView.getSbbTextField().setText(ppC.getTableComumnSonnebornBerger());
		eigenschaftenView.getResultTextField().setText(ppC.getTableComumnResult());
		eigenschaftenView.getRoundTextField().setText(ppC.getTableComumnRound());
		eigenschaftenView.getForenameLengthBox().setValue(ppC.getCutForename());
		eigenschaftenView.getSurnameLengthBox().setValue(ppC.getCutSurname());
	}

	public SettingsView getEigenschaftenView() {
		return eigenschaftenView;
	}

	public void setEigenschaftenView(SettingsView eigenschaftenView) {
		this.eigenschaftenView = eigenschaftenView;
	}

	public ActionListenerSettingsControl getActionListenerControl() {
		return actionListenerControl;
	}

	public void setActionListenerControl(ActionListenerSettingsControl actionListenerControl) {
		this.actionListenerControl = actionListenerControl;
	}

	public ItemListenerSettingsControl getItemListenerControl() {
		return itemListenerControl;
	}

	public void setItemListenerControl(ItemListenerSettingsControl itemListenerControl) {
		this.itemListenerControl = itemListenerControl;
	}

}
