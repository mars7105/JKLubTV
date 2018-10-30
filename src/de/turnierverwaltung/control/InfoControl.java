package de.turnierverwaltung.control;

import java.awt.Dialog;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.view.InfoHTMLView;
import de.turnierverwaltung.view.InfoLicenseView;
import de.turnierverwaltung.view.InfoView;

/**
 * 
 * @author mars
 *
 */
public class InfoControl {
	private MainControl mainControl;
	private InfoView infoView;
	private JTabbedPane lizenzenPane;
	private InfoLicenseView infoTexteView;
	private InfoHTMLView infoHelpView;
	private ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png"))); //$NON-NLS-1$
	private ImageIcon lizenzenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-paragraph.png"))); //$NON-NLS-1$
	private PropertiesControl propertiesControl;
	private JDialog dialog;

	/**
	 * @param mainControl
	 */
	public InfoControl(MainControl mainControl) {
		this.mainControl = mainControl;

		propertiesControl = mainControl.getPropertiesControl();
		String lang = propertiesControl.getLanguage();
		infoHelpView = new InfoHTMLView(lang);
		lizenzenPane = new JTabbedPane();
		infoTexteView = new InfoLicenseView();
		try {
			lizenzenPane.addTab(Messages.getString("InfoController.2"), infoIcon, infoHelpView.getLizenzText()); //$NON-NLS-1$

			lizenzenPane.addTab(Messages.getString("InfoController.3"), lizenzenIcon, infoTexteView.getLizenzText()); //$NON-NLS-1$

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoView = new InfoView(lizenzenPane);
		makeInfoDialog();
		infoView.getOkButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				propertiesControl.writeInfoDialogProperties(dialog.getBounds().x, dialog.getBounds().y,
						dialog.getBounds().width, dialog.getBounds().height);
				dialog.dispose();

			}

		});

	}

	/**
	 * 
	 */
	public void makeInfoDialog() {

		mainControl.getNaviView().getInfoButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dialog == null) {
					dialog = new JDialog();
				} else {
					dialog.dispose();
					dialog = new JDialog();
				}
				
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().add(infoView);
				dialog.pack();
				dialog.setBounds(propertiesControl.getInfoDialogX(), propertiesControl.getInfoDialogY(),
						propertiesControl.getInfoDialogWidth(), propertiesControl.getInfoDialogHeight());

				dialog.setEnabled(true);
				dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
			}

		});
	}
}
