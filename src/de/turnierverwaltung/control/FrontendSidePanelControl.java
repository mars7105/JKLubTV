package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SidepanelDAO;
import de.turnierverwaltung.view.FrontendSidePanelView;

public class FrontendSidePanelControl implements ActionListener {
	private FrontendSidePanelView sidePanel;
	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JButton cancelButton;
	private JButton okButton;
	private String headerText;
	private String bodyText;
	private ArrayList<Sidepanel> sideP;
	private int selectedIndex;
	private MainControl mainControl;
	private SidepanelDAO sidepanelDAO;
	private JButton addButton;
	private JButton saveButton;
	private JButton deleteButton;

	public FrontendSidePanelControl(MainControl mainControl) {
		this.mainControl = mainControl;
		DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		sidepanelDAO = daoFactory.getSidepanelDAO();
		sidePanel = new FrontendSidePanelView();
		jsonDialog = sidePanel.getJsonDialog();
		headerTextField = sidePanel.getHeaderTextField();
		bodyTextArea = sidePanel.getBodyTextArea();
		cancelButton = sidePanel.getCancelButton();
		okButton = sidePanel.getOkButton();

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		addButton = sidePanel.getAddButton();
		addButton.addActionListener(this);
		saveButton = sidePanel.getSaveButton();
		saveButton.addActionListener(this);
		deleteButton = sidePanel.getDeleteButton();
		deleteButton.addActionListener(this);
		headerText = "";
		bodyText = "";
	}

	public void makeDialog() {
		new ArrayList<Sidepanel>();

		try {
			sideP = sidepanelDAO.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
		} catch (SQLException e1) {
			// TODO Automatisch generierter Erfassungsblock
			e1.printStackTrace();
		}

		sidePanel.makeDialog(sideP);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			jsonDialog.dispose();
			headerText = null;
			bodyText = null;
		}
		if (e.getSource() == okButton) {
			headerText = headerTextField.getText();
			bodyText = bodyTextArea.getText();
			selectedIndex = sidePanel.getTreedemo().getSelectedItem();
			jsonDialog.dispose();
			String header = null;
			String body = null;
			header = getHeaderText();
			body = getBodyText();
			if (header != null) {
				if (selectedIndex < 0) {
					try {
						sidepanelDAO.insertSidepanel(new Sidepanel(header, body, 0),
								mainControl.getTurnier().getTurnierId());
					} catch (SQLException e1) {
						// TODO Automatisch generierter Erfassungsblock
						e1.printStackTrace();
					}
				} else {
					try {
						sideP.get(selectedIndex).setHeader(header);
						sideP.get(selectedIndex).setBody(body);
						sidepanelDAO.updateSidepanel(sideP.get(selectedIndex));
					} catch (SQLException e1) {
						// TODO Automatisch generierter Erfassungsblock
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {

		headerTextField.setText(headerText);
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {

		bodyTextArea.setText(bodyText);
	}

	public ArrayList<Sidepanel> getSideP() {
		return sideP;
	}

	public void setSideP(ArrayList<Sidepanel> sideP) {
		this.sideP = sideP;
	}

	public FrontendSidePanelView getSidePanel() {
		return sidePanel;
	}

	public void setSidePanel(FrontendSidePanelView sidePanel) {
		this.sidePanel = sidePanel;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

}
