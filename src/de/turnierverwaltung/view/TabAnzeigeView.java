package de.turnierverwaltung.view;

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
import javax.swing.JTabbedPane;
import de.turnierverwaltung.controller.MainControl;

public class TabAnzeigeView extends JTabbedPane {
//	private MainControl mainControl;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabAnzeigeView(MainControl mainCtrl) {
//		this.mainControl = mainCtrl;
//		ChangeListener changeListener = new ChangeListener() {
//			public void stateChanged(ChangeEvent changeEvent) {
//				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
//				
//				int index = sourceTabbedPane.getSelectedIndex();
//				String name = sourceTabbedPane.getTitleAt(index);
//				int tabellennummer = -1;
//				String tabellenname = "";
//				int aktiveGruppe = mainControl.getTabAnzeigeView().getSelectedIndex();
//				TabAnzeigeView tabAnzeigeView;
//				if (mainControl.getTabAnzeigeView2() != null) {
//					tabAnzeigeView = mainControl.getTabAnzeigeView2()[aktiveGruppe];
//					if (tabAnzeigeView.getTabCount() > 0) {
//						
//						tabellennummer = tabAnzeigeView.getSelectedIndex();
//
//						tabellenname = tabAnzeigeView.getTitleAt(tabellennummer);
//						mainControl.getNaviView().setTabellenname(tabellenname);
////						if (tabellennummer == 2) {
////							mainControl.getNaviView().getPdfSpeichernButton().setVisible(false);
////							mainControl.getNaviView().getTabelleHTMLAusgabeButton().setVisible(false);
////						} else {
////							mainControl.getNaviView().getPdfSpeichernButton().setVisible(true);
////							mainControl.getNaviView().getTabelleHTMLAusgabeButton().setVisible(true);
////						}
//					}
//				}
//				if (sourceTabbedPane == (JTabbedPane)mainControl.getTabAnzeigeView()) {
//					mainControl.getNaviView().setGruppenname(name);
//				}
//
//
//			}
//		};
//		this.addChangeListener(changeListener);
	}
}
