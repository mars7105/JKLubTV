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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class ELOPlayerView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private DefaultListModel<ListItem> listModel;
	private JList<ListItem> list;
	private int windowWidth; 
	private int windowHeight;
	private ImageIcon inserIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
	private ImageIcon insertIcon2 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));

	private ImageIcon insertIcon3 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user.png")));
	public ELOPlayerView() {
		windowWidth = TournamentConstants.WINDOW_WIDTH;
		windowHeight = TournamentConstants.WINDOW_HEIGHT;

		setPreferredSize(new Dimension(windowWidth, windowHeight / 2));
		setMinimumSize(new Dimension(windowWidth / 4, windowHeight / 4));
		setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(Messages.getString("SpielerDewisView.0")); //$NON-NLS-1$
		JLabel hilfeLabel = new JLabel(Messages.getString("SpielerDewisView.1")); //$NON-NLS-1$
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlepanel.add(titleLabel);
		JPanel hilfepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hilfepanel.add(hilfeLabel);
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
		mainPane.add(titlepanel);
		mainPane.add(hilfepanel);

		add(mainPane, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

		listModel = new DefaultListModel<ListItem>();

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		add(scrollPane, BorderLayout.CENTER);

	}

	public void makeSpielerZeile(Player spieler, int iconnumber) {
		ListItem playerItem = null;
		if (iconnumber == 0) {
			playerItem = new ListItem(inserIcon,
					spieler.getName() + Messages.getString("SpielerDewisView.2") + spieler.getDwz());
		}
		if (iconnumber == 1) {
			playerItem = new ListItem(insertIcon2,
					spieler.getName() + Messages.getString("SpielerDewisView.2") + spieler.getDwz());
		}
		if (iconnumber == 2) {
			playerItem = new ListItem(insertIcon3,
					spieler.getName() + Messages.getString("SpielerDewisView.2") + spieler.getDwz());
			
		}
		listModel.addElement(playerItem); 
		

	}

	@SuppressWarnings("unchecked")
	public void makeList() {
		list = new JList<ListItem>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new MyCellRenderer());
		contentPanel.add(list);
		
		contentPanel.updateUI();
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public JList<ListItem> getList() {
		return list;
	}

	public void setList(JList<ListItem> list) {
		this.list = list;
	}

	public DefaultListModel<ListItem> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<ListItem> listModel) {
		this.listModel = listModel;
	}
	
	
}
