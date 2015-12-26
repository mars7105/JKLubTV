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
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.turnierverwaltung.model.Spieler;

public class SpielerDewisView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private JPanel centerPane;

	int spielerAnzahl;
	private DefaultListModel<Object> listModel;
	private JList<Object> list;

	public SpielerDewisView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(Messages.getString("SpielerDewisView.0")); //$NON-NLS-1$
		JLabel hilfeLabel = new JLabel(Messages.getString("SpielerDewisView.1")); //$NON-NLS-1$
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlepanel.add(titleLabel);
		JPanel hilfepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hilfepanel.add(hilfeLabel);
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
		mainPane.add(titlepanel);
		mainPane.add(hilfepanel);

		add(mainPane, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		contentPanel.add(centerPane, BorderLayout.NORTH);
		listModel = new DefaultListModel<Object>();

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		add(scrollPane, BorderLayout.CENTER);

	}

	public void makeSpielerZeile(Spieler spieler) {

		listModel.addElement(spieler.getName() + Messages.getString("SpielerDewisView.2") + spieler.getDwz()); //$NON-NLS-1$

	}

	public void makeList() {
		list = new JList<Object>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		contentPanel.add(list);
		contentPanel.updateUI();
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public JList<Object> getList() {
		return list;
	}

	public void setList(JList<Object> list) {
		this.list = list;
	}
	
}
