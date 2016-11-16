package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SidepanelDAO;
import de.turnierverwaltung.view.JSONConfigView;

public class JSONConfigControl implements ActionListener {
	private JSONConfigView jsonView;
	private JButton okButton;
	private JButton cancelButton;
	private JButton sidePanelsButton;
	private String menuName;
	private JTextField menuNameTextField;

	private String uploadURL;
	private JButton connectionTestButton;
	private JTextField uploadURLTextField;
	private MainControl mainControl;
	private FrontendSidePanelControl sidePanel;
	private SidepanelDAO sidepanelDAO;
	private ArrayList<JButton> groupButtons;
	private String[] crossHeader;
	private String[] crossBody;
	private String[] meetingHeader;
	private String[] meetingBody;
	private FrontendTableTextControl[] ftC;

	public JSONConfigControl(MainControl mainControl) {
		this.mainControl = mainControl;

		jsonView = new JSONConfigView();

		okButton = jsonView.getOkButton();
		okButton.addActionListener(this);
		cancelButton = jsonView.getCancelButton();
		cancelButton.addActionListener(this);
		sidePanelsButton = jsonView.getSidePanelsButton();
		sidePanelsButton.addActionListener(this);
		menuNameTextField = jsonView.getMenuNameTextField();
		uploadURLTextField = jsonView.getUploadURLTextField();
		connectionTestButton = jsonView.getConnectionTestButton();
		connectionTestButton.addActionListener(this);
		menuName = mainControl.getPropertiesControl().getFrontendMenuname();

		uploadURL = mainControl.getPropertiesControl().getFrontendURL();

		menuNameTextField.setText(menuName);
		uploadURLTextField.setText(uploadURL);
		int gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		crossHeader = new String[gruppenAnzahl];
		crossBody = new String[gruppenAnzahl];
		meetingHeader = new String[gruppenAnzahl];
		meetingBody = new String[gruppenAnzahl];
		ftC = new FrontendTableTextControl[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			jsonView.makeGroupButtons(this.mainControl.getTurnier().getGruppe()[i].getGruppenName());
			crossHeader[i] = "";
			crossBody[i] = "";
			meetingHeader[i] = "";
			meetingBody[i] = "";
			ftC[i] = new FrontendTableTextControl();
		}
		groupButtons = jsonView.getGroupButtons();
		for (JButton groupButton : groupButtons) {
			groupButton.addActionListener(this);
		}
		DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		sidepanelDAO = daoFactory.getSidepanelDAO();
	}

	public void makeDialog() {

		jsonView.makePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = 0;
		for (JButton groupButton : groupButtons) {
			if (e.getSource() == groupButton) {
				// System.out.println(groupButton.getText());

				ftC[index].makeDialog();
				crossHeader[index] = ftC[index].getCrossHeader().getText();
				crossBody[index] = ftC[index].getCrossBody().getText();
				meetingHeader[index] = ftC[index].getMeetingHeader().getText();
				meetingBody[index] = ftC[index].getMeetingBody().getText();

			}
			index++;
		}
		if (e.getSource() == okButton) {
			menuName = jsonView.getMenuNameTextField().getText();
			uploadURL = jsonView.getUploadURLTextField().getText();
			jsonView.getJsonDialog().dispose();
			JSONSaveControl json = new JSONSaveControl(this.mainControl);

			// this.turnier = this.mainControl.getTurnier();
			ArrayList<Sidepanel> sidepanelItems = new ArrayList<Sidepanel>();

			try {
				sidepanelItems = sidepanelDAO.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
			} catch (SQLException e1) {
				// TODO Automatisch generierter Erfassungsblock
				e1.printStackTrace();
			}

			try {
				json.uploadJSONFile(uploadURL, menuName, sidepanelItems, crossHeader, crossBody, meetingHeader,
						meetingBody);
				mainControl.getPropertiesControl().setFrontendMenuname(menuName);
				mainControl.getPropertiesControl().setFrontendURL(uploadURL);
				mainControl.getPropertiesControl().writeProperties();
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.0"));
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
			}

		}
		if (e.getSource() == sidePanelsButton) {
			sidePanel = new FrontendSidePanelControl(mainControl);

			// JSONSaveControl json = new JSONSaveControl(this.mainControl);

			// this.turnier = this.mainControl.getTurnier();

			sidePanel.makeDialog();

		}
		if (e.getSource() == connectionTestButton) {
			String url = uploadURLTextField.getText();
			JSON jsonCross = new JSON(url + "/receiveJSON.php");
			try {
				Boolean testConnection = jsonCross.testConnection();
				if (testConnection == true) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.1"));
				} else {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
			}
		}
		if (e.getSource() == cancelButton) {
			jsonView.getJsonDialog().dispose();

		}

	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUploadURL() {
		return uploadURL;
	}

	public void setUploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
	}

	public FrontendSidePanelControl getSidePanel() {
		return sidePanel;
	}

	public void setSidePanel(FrontendSidePanelControl sidePanel) {
		this.sidePanel = sidePanel;
	}

	public String[] getCrossHeader() {
		return crossHeader;
	}

	public void setCrossHeader(String[] crossHeader) {
		this.crossHeader = crossHeader;
	}

	public String[] getCrossBody() {
		return crossBody;
	}

	public void setCrossBody(String[] crossBody) {
		this.crossBody = crossBody;
	}

	public String[] getMeetingHeader() {
		return meetingHeader;
	}

	public void setMeetingHeader(String[] meetingHeader) {
		this.meetingHeader = meetingHeader;
	}

	public String[] getMeetingBody() {
		return meetingBody;
	}

	public void setMeetingBody(String[] meetingBody) {
		this.meetingBody = meetingBody;
	}

}
