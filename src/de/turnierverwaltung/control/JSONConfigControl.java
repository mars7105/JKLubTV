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
import de.turnierverwaltung.model.TableContent;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.WebsiteContentDAO;
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
	private WebsiteContentDAO sidepanelDAO;
	private ArrayList<JButton> groupButtons;
	private String[] crossHeader;
	private String[] crossBody;
	private String[] meetingHeader;
	private String[] meetingBody;
	private FrontendTableTextControl[] ftC;
	private String username;
	private String password;

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
		jsonView.getUsernameTextField();
		jsonView.getPasswordTextField();
		connectionTestButton = jsonView.getConnectionTestButton();
		connectionTestButton.addActionListener(this);

		menuName = mainControl.getPropertiesControl().getFrontendMenuname();

		uploadURL = mainControl.getPropertiesControl().getFrontendURL();
		username = mainControl.getPropertiesControl().getUsername();
		jsonView.getUsernameTextField().setText(username);
		password = mainControl.getPropertiesControl().getPassword();
		jsonView.getPasswordTextField().setText(password);
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
			username = jsonView.getUsernameTextField().getText();
			password = jsonView.getPasswordTextField().getText();
			jsonView.getJsonDialog().dispose();

			JSONSaveControl json = new JSONSaveControl(this.mainControl);

			// this.turnier = this.mainControl.getTurnier();
			ArrayList<Sidepanel> sidepanelItems = new ArrayList<Sidepanel>();

			ArrayList<TableContent> tableContentItems = new ArrayList<TableContent>();
			ArrayList<ArrayList> tableContentgroups = new ArrayList<ArrayList>();

			try {
				int anzahlgruppen = mainControl.getTurnier().getAnzahlGruppen();
				for (int i = 0; i < anzahlgruppen; i++) {
					tableContentItems = sidepanelDAO
							.selectAllTableContent(this.mainControl.getTurnier().getGruppe()[i].getGruppeId());
					tableContentgroups.add(tableContentItems);
				}
				sidepanelItems = sidepanelDAO.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(mainControl, "Database error");
			}

			try {
				json.uploadJSONFile(uploadURL, username, password, menuName, sidepanelItems, crossHeader, crossBody,
						meetingHeader, meetingBody);
				mainControl.getPropertiesControl().setFrontendMenuname(menuName);
				mainControl.getPropertiesControl().setFrontendURL(uploadURL);
				mainControl.getPropertiesControl().setUsername(username);
				mainControl.getPropertiesControl().setPassword(password);
				mainControl.getPropertiesControl().writeProperties();
				JOptionPane.showMessageDialog(mainControl, json.getJsonCross().getPostRequest().getOutput());
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(mainControl, json.getJsonCross().getPostRequest().getOutput());

			}

		}
		if (e.getSource() == sidePanelsButton) {
			sidePanel = new FrontendSidePanelControl(mainControl);

			sidePanel.makeDialog();

		}
		if (e.getSource() == connectionTestButton) {
			String url = jsonView.getUploadURLTextField().getText();
			username = jsonView.getUsernameTextField().getText();
			password = jsonView.getPasswordTextField().getText();
			JSON jsonCross = new JSON(url, username, password);
			try {
				Boolean testConnection = jsonCross.testConnection();
				if (testConnection == true) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.1"));
				} else {
					JOptionPane.showMessageDialog(mainControl, jsonCross.getPostRequest().getOutput());
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainControl, jsonCross.getPostRequest().getOutput());
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
