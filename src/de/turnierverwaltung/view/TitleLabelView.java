package de.turnierverwaltung.view;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;

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

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleLabelView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel titlePanel;
	private JLabel titleLabel;

	public TitleLabelView(String title) {
		Color titleColor = new Color((SystemColor.menu).getRGB());
		Color titleTextColor = new Color((SystemColor.menuText).getRGB());
		setLayout(new BorderLayout());
		// setBackground(new Color(130, 165, 195));
		titleLabel = new JLabel(title);
		titlePanel = new JPanel();
		titlePanel.setBackground(titleColor);
		titleLabel.setForeground(titleTextColor);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(titleLabel);
		add(titlePanel, BorderLayout.NORTH);
	}

	public JLabel getTitleLabel() {
		return titleLabel;
	}

	public JPanel getTitlePanel() {
		return titlePanel;
	}

	public void setFlowLayoutLeft() {
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// setPreferredSize(new Dimension(200, 30));
		this.updateUI();
	}

	public void setTitleLabel(JLabel titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void setTitlePanel(JPanel titlePanel) {
		this.titlePanel = titlePanel;
	}

}
