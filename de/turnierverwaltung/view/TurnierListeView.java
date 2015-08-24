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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class TurnierListeView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel centerPane;
	private JScrollPane scrollPane;
	private JButton okButton;

	public TurnierListeView() {

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setPreferredSize(new Dimension(800, 500));
		add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		centerPane = new JPanel();
		contentPanel.add(centerPane);
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);

	}

	public void makeTurnierListe() {

	}
}
