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

import de.turnierverwaltung.view.EigenschaftenView;

public class EigenschaftenControl {
	private MainControl mainControl;
	private EigenschaftenView eigenschaftenView;

	private EigenschaftenActionListenerControl actionListenerControl;
	private EigenschaftenItemListenerControl itemListenerControl;

	/**
	 * @param mainControl
	 */
	public EigenschaftenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		eigenschaftenView = new EigenschaftenView();
		actionListenerControl = new EigenschaftenActionListenerControl(this.mainControl, this);
		itemListenerControl = new EigenschaftenItemListenerControl(this.mainControl, this);

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
	}



	public EigenschaftenView getEigenschaftenView() {
		return eigenschaftenView;
	}

	public void setEigenschaftenView(EigenschaftenView eigenschaftenView) {
		this.eigenschaftenView = eigenschaftenView;
	}

	

	public EigenschaftenActionListenerControl getActionListenerControl() {
		return actionListenerControl;
	}

	public void setActionListenerControl(EigenschaftenActionListenerControl actionListenerControl) {
		this.actionListenerControl = actionListenerControl;
	}

	public EigenschaftenItemListenerControl getItemListenerControl() {
		return itemListenerControl;
	}

	public void setItemListenerControl(EigenschaftenItemListenerControl itemListenerControl) {
		this.itemListenerControl = itemListenerControl;
	}

}
