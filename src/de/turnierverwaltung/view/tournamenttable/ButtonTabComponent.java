package de.turnierverwaltung.view.tournamenttable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.Messages;

/**
 * Component to be used as tabComponent; Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to
 */
public class ButtonTabComponent extends JPanel {
	private class TabButton extends JButton implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4709316560468565156L;

		public TabButton() {
			int size = 22;
			setPreferredSize(new Dimension(size, size));

			setToolTipText("close this tab");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			// setBorder(BorderFactory.createEtchedBorder());
			Border blackline = BorderFactory.createLineBorder(Color.black);
			setBorder(blackline);
			setBorderPainted(false);

			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(ButtonTabComponent.this);
			if (i == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {

				int abfrage = checkForChangedGames();
				if (abfrage == 0 || abfrage == 1) {
					pane.remove(i);
					mainControl.setNewTournament(false);
					mainControl.getActionListenerTournamentItemsControl().setLoadedTurnierID(-1);

				}

			}
			if (i == 0) {
				pane.remove(i);
			}
		}

		private int checkForChangedGames() {
			ArrayList<Game> changedPartien = mainControl.getChangedGames();
			int abfrage = 0;
			if (changedPartien != null) {
				if (changedPartien.size() > 0) {
					// Custom button text
					Object[] options = { Messages.getString("ButtonTabComponent.0"), //$NON-NLS-1$
							Messages.getString("ButtonTabComponent.1") }; //$NON-NLS-1$
					abfrage = JOptionPane.showOptionDialog(mainControl, Messages.getString("ButtonTabComponent.2") //$NON-NLS-1$
							+ Messages.getString("ButtonTabComponent.3"), //$NON-NLS-1$
							Messages.getString("ButtonTabComponent.4"), //$NON-NLS-1$
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (abfrage == 0) {
						SaveTournamentControl saveGames = new SaveTournamentControl(mainControl);
						try {
							Boolean saved = saveGames.saveChangedPartien();
							if (saved == false) {
								changedPartien.clear();
							}

						} catch (SQLException e) {
							changedPartien.clear();
							ExceptionHandler eh = new ExceptionHandler(null);
							eh.fileSQLError(e.getMessage());
						}

					}
					if (abfrage == 1) {
						changedPartien.clear();
					}
				}

			}
			return abfrage;
		}

		// paint the cross
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Color windowBorder = new Color((SystemColor.controlText).getRGB());
			Color hoover = new Color((SystemColor.controlLtHighlight).getRGB());
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(windowBorder);
			
			if (getModel().isRollover()) {
				g2.setColor(hoover);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();
		}

		// we don't want to update UI for this button
		@Override
		public void updateUI() {
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8004316423128246344L;
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
	/**
	 * 
	 */
	private final JTabbedPane pane;

	private MainControl mainControl;

	private ImageIcon image;

	public ButtonTabComponent(final JTabbedPane pane, MainControl mainControl, ImageIcon image, Boolean setButton) {
		// unset default FlowLayout' gaps

		super();
		this.mainControl = mainControl;
		this.image = image;
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.pane = pane;
		setOpaque(false);

		// make JLabel read titles from JTabbedPane
		JLabel label = new JLabel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getText() {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
		};

		add(new JLabel(image));
		add(label);
		// add more space between the label and the button
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		// tab button
		JButton button = new TabButton();
		if (setButton) {
			add(button);
		}
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
