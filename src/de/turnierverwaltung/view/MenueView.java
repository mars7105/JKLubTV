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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenueView extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel hauptPanel;
	private JMenuBar menuBar;
	private JMenu mnDateiMenu;
	private JMenuItem mntmNeu;
	private JMenuItem mntmLaden;
	private JMenuItem mntmSpeichern;
	private JMenuItem mntmSpielerLaden;
	private JMenuItem mntmBeenden;
	private JMenuItem mntmDBNeu;
	private JMenuItem mntmDBLaden;
	private JMenu mnEditMenu;
	private JMenuItem mntmNeueSpieler;

	public MenueView() {

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnDateiMenu = new JMenu(Messages.getString("MenueView.0")); //$NON-NLS-1$
		menuBar.add(mnDateiMenu);

		mntmDBNeu = new JMenuItem(Messages.getString("MenueView.1")); //$NON-NLS-1$
		mnDateiMenu.add(mntmDBNeu);

		mntmDBLaden = new JMenuItem(Messages.getString("MenueView.2")); //$NON-NLS-1$
		mnDateiMenu.add(mntmDBLaden);

		mntmBeenden = new JMenuItem(Messages.getString("MenueView.3")); //$NON-NLS-1$
		mnDateiMenu.add(mntmBeenden);

		mnEditMenu = new JMenu(Messages.getString("MenueView.4")); //$NON-NLS-1$
		menuBar.add(mnEditMenu);

		mntmLaden = new JMenuItem(Messages.getString("MenueView.5")); //$NON-NLS-1$
		mnEditMenu.add(mntmLaden);

		mntmSpielerLaden = new JMenuItem(Messages.getString("MenueView.6")); //$NON-NLS-1$
		mnEditMenu.add(mntmSpielerLaden);

//		mntmNeu = new JMenuItem("Neues Turnier");
//		mnEditMenu.add(mntmNeu);
//
//		mntmNeueSpieler = new JMenuItem("Neue Spieler");
//		mnEditMenu.add(mntmNeueSpieler);

		mntmSpeichern = new JMenuItem(Messages.getString("MenueView.7")); //$NON-NLS-1$
		mnEditMenu.add(mntmSpeichern);
	}

	public JPanel getHauptPanel() {
		return hauptPanel;
	}

	public JMenuBar getJMenuBar() {
		return menuBar;
	}

	public JMenu getMnDateiMenu() {
		return mnDateiMenu;
	}

	public JMenu getMnEditMenu() {
		return mnEditMenu;
	}

	public JMenuItem getMntmBeenden() {
		return mntmBeenden;
	}

	public JMenuItem getMntmDBLaden() {
		return mntmDBLaden;
	}

	public JMenuItem getMntmDBNeu() {
		return mntmDBNeu;
	}

	public JMenuItem getMntmLaden() {
		return mntmLaden;
	}

	public JMenuItem getMntmNeu() {
		return mntmNeu;
	}

	public JMenuItem getMntmNeueSpieler() {
		return mntmNeueSpieler;
	}

	public JMenuItem getMntmSpeichern() {
		return mntmSpeichern;
	}

	public JMenuItem getMntmSpielerLaden() {
		return mntmSpielerLaden;
	}

	public void setHauptPanel(JPanel hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setJMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public void setMnDateiMenu(JMenu mnNewMenu) {
		this.mnDateiMenu = mnNewMenu;
	}

	public void setMnEditMenu(JMenu mnEditMenu) {
		this.mnEditMenu = mnEditMenu;
	}

	public void setMntmBeenden(JMenuItem mntmBeenden) {
		this.mntmBeenden = mntmBeenden;
	}

	public void setMntmDBLaden(JMenuItem mntmDBLaden) {
		this.mntmDBLaden = mntmDBLaden;
	}

	public void setMntmDBNeu(JMenuItem mntmDBNeu) {
		this.mntmDBNeu = mntmDBNeu;
	}

	public void setMntmLaden(JMenuItem mntmLaden) {
		this.mntmLaden = mntmLaden;
	}

	public void setMntmNeu(JMenuItem mntmNeu) {
		this.mntmNeu = mntmNeu;
	}

	public void setMntmNeueSpieler(JMenuItem mntmNeueSpieler) {
		this.mntmNeueSpieler = mntmNeueSpieler;
	}

	public void setMntmSpeichern(JMenuItem mntmSpeichern) {
		this.mntmSpeichern = mntmSpeichern;
	}

	public void setMntmSpielerLaden(JMenuItem mntmSpielerLaden) {
		this.mntmSpielerLaden = mntmSpielerLaden;
	}

}
