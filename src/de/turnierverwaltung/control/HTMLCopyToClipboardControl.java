package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.CopyToClipboard;
import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.view.HTMLToClipBoardDialogView;
import de.turnierverwaltung.view.HTMLToClipBoardView;

public class HTMLCopyToClipboardControl {

	private MainControl mainControl;

	public HTMLCopyToClipboardControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	public void copyToClipboard() {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

			String webserverPath = mainControl.getPropertiesControl().getWebserverPath();

			HTMLToClipBoardDialogView htmlToClipboardDialog = new HTMLToClipBoardDialogView();

			ArrayList<HTMLToClipBoardView> htmlToClipboardArray = new ArrayList<HTMLToClipBoardView>();

			for (int i = 0; i < anzahlGruppen; i++) {

				String wfn = mainControl.getTurnier().getTurnierName();
				wfn += Messages.getString("PDFSaveControler.2")
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf";
				String webfilename1 = wfn.replaceAll(" ", "");

				String wfn2 = mainControl.getTurnier().getTurnierName();
				wfn2 += Messages.getString("PDFSaveControler.8")
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".pdf";
				String webfilename2 = wfn2.replaceAll(" ", "");

				String ical = mainControl.getTurnier().getTurnierName()
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + ".ics";
				String webfilename3 = ical.replaceAll(" ", "");

				String crossLabel = mainControl.getTurnier().getTurnierName() + " "
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.22");
				String meetingLabel = mainControl.getTurnier().getTurnierName() + " "
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.23");
				String allLabel = mainControl.getTurnier().getTurnierName() + " "
						+ mainControl.getTurnier().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.21");
				if (this.mainControl.getTurnierTabelle()[i] == null) {
					this.mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

					this.mainControl.getTerminTabelleControl().makeSimpleTableView(i);

				}

				CrossTable turnierTabelle = mainControl.getTurnierTabelle()[i];

				int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
				int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) this.mainControl.getSimpleTableView()[i]
								.getTable().getValueAt(y, x);

					}
				}

				Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();
				Boolean showLink = mainControl.getPropertiesControl().getPDFLinks();

				HTMLToClipBoardView crosshtmlToClipboard = new HTMLToClipBoardView();
				crosshtmlToClipboard.getLabel().setText(crossLabel);
				String crosstable = this.mainControl.getTurnierTabelle()[i].getHTMLTable(ohneHeaderundFooter,
						webserverPath, webfilename1, showLink);
				crosshtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + crossLabel);
					}

				});

				htmlToClipboardArray.add(crosshtmlToClipboard);

				HTMLToClipBoardView meetinghtmlToClipboard = new HTMLToClipBoardView();
				meetinghtmlToClipboard.getLabel().setText(meetingLabel);

				String meetingtable = this.mainControl.getTerminTabelleControl().getTerminTabelle()[i]
						.getHTMLTable(ohneHeaderundFooter, webserverPath, webfilename2, webfilename3, showLink);
				meetinghtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(meetingtable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + meetingLabel);
					}

				});

				htmlToClipboardArray.add(meetinghtmlToClipboard);
				HTMLToClipBoardView allToClipboard = new HTMLToClipBoardView();
				allToClipboard.getLabel().setText(allLabel);

				
				allToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstable + "\n" + meetingtable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + allLabel);
					}

				});

				htmlToClipboardArray.add(allToClipboard);
			}
			htmlToClipboardDialog.makeDialog(htmlToClipboardArray);

			htmlToClipboardDialog.getButtonPanel().getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					htmlToClipboardDialog.dispose();

				}

			});

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}
}
