package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TableContent;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.WebMainContentDAO;
import de.turnierverwaltung.view.ColorSelectorView;
import de.turnierverwaltung.view.FrontendSidePanelView;
import de.turnierverwaltung.view.WebsiteConfigView;

public class FrontendSidePanelControl implements ActionListener {
	private FrontendSidePanelView sidePanel;
	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JButton okButton;
	private String headerText;
	private String bodyText;
	private ArrayList<Sidepanel> sideP;
	private int selectedIndex;
	private MainControl mainControl;
	private SidePanelControl dynTree;
	private ColorSelectorView colorSelector;
	private FrontendTableTextControl[] ftC;
	private String[] crossHeader;
	private String[] crossBody;
	private String[] meetingHeader;
	private int[] crossColor;
	private String[] meetingBody;
	private int[] meetingColor;
	private WebMainContentDAO webMainContent;

	public FrontendSidePanelControl(MainControl mainControl) {
		this.mainControl = mainControl;

		dynTree = new SidePanelControl(mainControl);

		sidePanel = new FrontendSidePanelView(sideP, dynTree);
		this.mainControl.setFrontendSidePanelView(sidePanel);
		// jsonDialog = sidePanel.getJsonDialog();

		headerTextField = sidePanel.getHeaderTextField();
		bodyTextArea = sidePanel.getBodyTextArea();
		colorSelector = sidePanel.getColorSelectorPanel();

		// okButton.addActionListener(this);

		headerText = "";
		bodyText = "";
		// int anzahlgruppen = mainControl.getTurnier().getAnzahlGruppen();
		DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		try {

			webMainContent = daoFactory.getWebMainContentDAO();
		} catch (SQLException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

		ftC = mainControl.getFrontendTableTextControl();
	}

	public void makeDialog() {
		sidePanel.makeDialog();

		WebsiteConfigView webconfigView = null;
		if (mainControl.getWebconfigView() == null) {
			webconfigView = new WebsiteConfigView();
			mainControl.setWebconfigView(webconfigView);
		} else {
			webconfigView = mainControl.getWebconfigView();
		}

		jsonDialog = webconfigView.getDialog();
		okButton = webconfigView.getOkButton();
		okButton.addActionListener(this);
		// webconfigView.getTabbedPane().addTab("Right Content",
		// sidePanel.getMainPanel());
		webconfigView.getTabbedPane().insertTab("Right Content", null, sidePanel.getMainPanel(), null, 0);
		webconfigView.getTabbedPane().setSelectedIndex(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == okButton) {

			jsonDialog.dispose();
			int anzahlgruppen = mainControl.getTurnier().getAnzahlGruppen();
			crossHeader = new String[anzahlgruppen];
			crossBody = new String[anzahlgruppen];
			crossColor = new int[anzahlgruppen];
			meetingHeader = new String[anzahlgruppen];
			meetingBody = new String[anzahlgruppen];
			meetingColor = new int[anzahlgruppen];

			for (int i = 0; i < anzahlgruppen; i++) {

				int groupID = mainControl.getTurnier().getGruppe()[i].getGruppeId();
				crossHeader[i] = ftC[i].getCrossHeader().getText();
				crossBody[i] = ftC[i].getCrossBody().getText();
				crossColor[i] = ftC[i].getCrossColorSelector().getSelectedIndex();
				meetingHeader[i] = ftC[i].getMeetingHeader().getText();
				meetingBody[i] = ftC[i].getMeetingBody().getText();
				meetingColor[i] = ftC[i].getMeetingColorSelector().getSelectedIndex();

				TableContent tableCrossContent = new TableContent(crossHeader[i], crossBody[i], 0,
						TournamentConstants.CROSSTABLETYPE, groupID, crossColor[i]);
				TableContent tableMeetingContent = new TableContent(meetingHeader[i], meetingBody[i], 0,
						TournamentConstants.MEETINGTABLETYPE, groupID, meetingColor[i]);

				Boolean updatetableCrossContent = false;
				Boolean updatetableMeetingContent = false;

				ArrayList<TableContent> tableContentItems = new ArrayList<TableContent>();

				try {
					tableContentItems = webMainContent
							.selectAllTableContent(this.mainControl.getTurnier().getGruppe()[i].getGruppeId());

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(mainControl, "Database error");
				}
				for (TableContent tableContent : tableContentItems) {

					if (tableContent.getIdGroup() == tableCrossContent.getIdGroup()
							&& tableContent.getTableType() == tableCrossContent.getTableType()) {
						tableCrossContent.setIdTableContent(tableContent.getIdTableContent());
						try {
							webMainContent.updateTableContent(tableCrossContent);

						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(mainControl, "Database error");
							e1.printStackTrace();
						}
						updatetableCrossContent = true;
					}
					if (tableContent.getIdGroup() == tableMeetingContent.getIdGroup()
							&& tableContent.getTableType() == tableMeetingContent.getTableType()) {
						tableMeetingContent.setIdTableContent(tableContent.getIdTableContent());
						try {
							webMainContent.updateTableContent(tableMeetingContent);

						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(mainControl, "Database error");
							e1.printStackTrace();
						}
						updatetableMeetingContent = true;
					}
				}

				if (updatetableCrossContent == false) {
					try {
						tableCrossContent
								.setIdTableContent(webMainContent.insertTableContent(tableCrossContent, groupID));
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(mainControl, "Database error");
						e1.printStackTrace();
					}
				}
				if (updatetableMeetingContent == false) {
					try {

						tableCrossContent
								.setIdTableContent(webMainContent.insertTableContent(tableMeetingContent, groupID));
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(mainControl, "Database error");
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

	public ColorSelectorView getColorSelector() {
		return colorSelector;
	}

	public void setColorSelector(ColorSelectorView colorSelector) {
		this.colorSelector = colorSelector;
	}

	public JDialog getJsonDialog() {
		return jsonDialog;
	}

	public void setJsonDialog(JDialog jsonDialog) {
		this.jsonDialog = jsonDialog;
	}

}
