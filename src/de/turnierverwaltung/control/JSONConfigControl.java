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
import de.turnierverwaltung.model.JSONReceiveObject;
import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TableContent;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.WebMainContentDAO;
import de.turnierverwaltung.mysql.WebRightContentDAO;
import de.turnierverwaltung.view.JSONConfigView;
import de.turnierverwaltung.view.WebsiteConfigView;

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
	private WebRightContentDAO webRightContent;
	private WebMainContentDAO webMainContent;
	private String[] crossHeader;
	private String[] crossBody;
	private int[] crossColor;
	private String[] meetingHeader;
	private String[] meetingBody;
	private int[] meetingColor;
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
		crossColor = new int[gruppenAnzahl];
		meetingColor = new int[gruppenAnzahl];
		meetingHeader = new String[gruppenAnzahl];
		meetingBody = new String[gruppenAnzahl];

		for (int i = 0; i < gruppenAnzahl; i++) {
			crossHeader[i] = "";
			crossBody[i] = "";
			crossColor[i] = 0;
			meetingHeader[i] = "";
			meetingBody[i] = "";
			meetingColor[i] = 0;
		}

		DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		try {
			webRightContent = daoFactory.getWebRightContentDAO();

			webMainContent = daoFactory.getWebMainContentDAO();
		} catch (SQLException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

	}

	public void makeDialog() {

		jsonView.makePanel();
	}

	public void makeGroupContentView() {
		int anzahlgruppen = mainControl.getTurnier().getAnzahlGruppen();
		ftC = new FrontendTableTextControl[anzahlgruppen];

		for (int i = 0; i < anzahlgruppen; i++) {
			ArrayList<TableContent> tableContentItems = new ArrayList<TableContent>();
			ftC[i] = new FrontendTableTextControl(mainControl);
			mainControl.setFrontendTableTextControl(ftC);
			try {
				int groupID = mainControl.getTurnier().getGruppe()[i].getGruppeId();
				tableContentItems = webMainContent.selectAllTableContent(groupID);
				for (TableContent tableContent : tableContentItems) {
					if (tableContent.getTableType() == TournamentConstants.CROSSTABLETYPE) {
						ftC[i].getCrossHeader().setText(tableContent.getHeader());
						ftC[i].getCrossBody().setText(tableContent.getBody());
						ftC[i].getCrossColorSelector().setSelectedIndex(tableContent.getColor());
					}
					if (tableContent.getTableType() == TournamentConstants.MEETINGTABLETYPE) {
						ftC[i].getMeetingHeader().setText(tableContent.getHeader());
						ftC[i].getMeetingBody().setText(tableContent.getBody());
						ftC[i].getMeetingColorSelector().setSelectedIndex(tableContent.getColor());
					}
				}
			} catch (SQLException e1) {
				// TODO Automatisch generierter Erfassungsblock
				e1.printStackTrace();
			}

			ftC[i].makeDialog(mainControl.getTurnier().getGruppe()[i].getGruppenName());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == okButton) {
			// mmm
			menuName = jsonView.getMenuNameTextField().getText();
			uploadURL = jsonView.getUploadURLTextField().getText();
			username = jsonView.getUsernameTextField().getText();
			password = jsonView.getPasswordTextField().getText();
			jsonView.getJsonDialog().dispose();

			JSONSaveControl json = new JSONSaveControl(this.mainControl);

			// this.turnier = this.mainControl.getTurnier();
			ArrayList<Sidepanel> sidepanelItems = new ArrayList<Sidepanel>();

			ArrayList<TableContent> tableContentItems = new ArrayList<TableContent>();
			ArrayList<ArrayList<TableContent>> tableContentgroups = new ArrayList<ArrayList<TableContent>>();
			int anzahlgruppen = mainControl.getTurnier().getAnzahlGruppen();
			try {

				for (int i = 0; i < anzahlgruppen; i++) {
					tableContentItems = webMainContent
							.selectAllTableContent(this.mainControl.getTurnier().getGruppe()[i].getGruppeId());
					tableContentgroups.add(tableContentItems);
				}
				sidepanelItems = webRightContent.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(mainControl, "Database error");
			}

			try {
				json.uploadJSONFile(uploadURL, username, password, menuName, sidepanelItems, tableContentgroups);
				mainControl.getPropertiesControl().setFrontendMenuname(menuName);
				mainControl.getPropertiesControl().setFrontendURL(uploadURL);
				mainControl.getPropertiesControl().setUsername(username);
				mainControl.getPropertiesControl().setPassword(password);
				mainControl.getPropertiesControl().writeProperties();
				// JOptionPane.showMessageDialog(mainControl,
				// json.getJsonCross().getPostRequest().getOutput());
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(mainControl, "?:" + json.getJsonCross().getPostRequest().getOutput());
				exc.printStackTrace();
			}

		}
		if (e.getSource() == sidePanelsButton) {
			WebsiteConfigView webconfigView = new WebsiteConfigView();
			mainControl.setWebconfigView(webconfigView);

			makeGroupContentView();
			sidePanel = new FrontendSidePanelControl(mainControl);
			sidePanel.makeDialog();

			webconfigView.makeDialog();

		}
		if (e.getSource() == connectionTestButton)

		{
			String url = jsonView.getUploadURLTextField().getText();
			username = jsonView.getUsernameTextField().getText();
			password = jsonView.getPasswordTextField().getText();
			JSON jsonCross = new JSON(url, username, password);
			try {
				JSONReceiveObject testConnection = jsonCross.testConnection();
				if (testConnection.isStatusOk() == true) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.1"));
				} else {
					JOptionPane.showMessageDialog(mainControl, testConnection.getStatusCode());
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainControl, jsonCross.getPostRequest().getOutput());
			} catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(mainControl, "Wrong URL?");
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
