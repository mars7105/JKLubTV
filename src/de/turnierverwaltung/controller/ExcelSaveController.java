package de.turnierverwaltung.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelSaveController {

	private MainControl mainControl;
	private Workbook wb;

	public ExcelSaveController(MainControl mainControl) {
		this.mainControl = mainControl;
	}

	public void saveExcelFile() {
		Boolean ready = mainControl.getRundenEingabeFormularControl()
				.checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier()
					.getAnzahlGruppen();
			String filename = JOptionPane
					.showInputDialog(
							mainControl,
							Messages.getString("HTMLSaveControler.0"), Messages.getString("HTMLSaveControler.1"), //$NON-NLS-1$ //$NON-NLS-2$
							JOptionPane.PLAIN_MESSAGE);
			if (filename != null) {
				JFileChooser savefile = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("XLS", "xls"); //$NON-NLS-1$ //$NON-NLS-2$
				savefile.addChoosableFileFilter(filter);
				savefile.setFileFilter(filter);
				savefile.setSelectedFile(new File(filename));
				savefile.setDialogType(JFileChooser.SAVE_DIALOG);

				int sf = savefile.showSaveDialog(null);
				if (sf == JFileChooser.APPROVE_OPTION) {
					// create a new file
					OutputStream out;

					wb = new HSSFWorkbook();
					CreationHelper createHelper = wb.getCreationHelper();
					Font font = wb.createFont();
					// set font 1 to 12 point type
					font.setFontHeightInPoints((short) 12);
					// make it blue
					font.setColor((short) 0xc);
					Sheet[] s = new Sheet[anzahlGruppen];

					CellStyle cellStyle = wb.createCellStyle();
					cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
					cellStyle.setBottomBorderColor(IndexedColors.BLACK
							.getIndex());
					cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
					cellStyle
							.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
					cellStyle.setRightBorderColor(IndexedColors.BLACK
							.getIndex());
					cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
					cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
					cellStyle.setFont(font);
					// cellStyle.setShrinkToFit(true);
					for (int i = 0; i < anzahlGruppen; i++) {
						if (this.mainControl.getTurnierTabelle()[i] == null) {
							this.mainControl.getTurnierTabelleControl()
									.makeSimpleTableView(i);
							this.mainControl.getTerminTabelleControl()
									.makeSimpleTableView(i);
						}
						this.mainControl.getTurnierTabelle()[i].createMatrix();
						int spalte = this.mainControl.getSimpleTableView()[i]
								.getTable().getModel().getColumnCount();
						int zeile = this.mainControl.getSimpleTableView()[i]
								.getTable().getModel().getRowCount();
						// create a new sheet
						s[i] = wb.createSheet(this.mainControl.getTurnier()
								.getGruppe()[i].getGruppenName());
						PrintSetup ps = s[i].getPrintSetup();

						s[i].setAutobreaks(true);

						ps.setFitHeight((short) 1);
						ps.setFitWidth((short) 1);
						// declare a row object reference
						Row r = null;
						// declare a cell object reference
						Cell c = null;
						short rownum;
						for (int y = 0; y < zeile; y++) {
							rownum = (short) y;
							// create a row
							r = s[i].createRow(rownum);

							short cellnum;

							for (int x = 0; x < spalte; x++) {
								cellnum = (short) x;
								// create a numeric cell
								c = r.createCell(cellnum);
								c.setCellStyle(cellStyle);
								this.mainControl.getTurnierTabelle()[i]
										.getTabellenMatrix()[x][y + 1] = (String) this.mainControl
										.getSimpleTableView()[i].getTable()
										.getValueAt(y, x);
								c.setCellValue(createHelper
										.createRichTextString((String) this.mainControl
												.getSimpleTableView()[i]
												.getTable().getValueAt(y, x)));
							}
							r.setHeight((short) (r.getHeight() * 2));
						}

					}
					File filename1 = new File(savefile.getCurrentDirectory()
							+ "/" //$NON-NLS-1$
							+ filename
							+ Messages.getString("HTMLSaveControler.5") //$NON-NLS-1$
							+ mainControl.getTurnier().getTurnierName()
							+ ".xls"); //$NON-NLS-1$
					try {
						out = new FileOutputStream(filename1);

						// write the workbook to the output stream
						// close our file (don't blow out our file
						// handles
						wb.write(out);
						out.close();

					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,
								Messages.getString("HTMLSaveControler.16")); //$NON-NLS-1$
					}
					JOptionPane.showMessageDialog(null,
							Messages.getString("HTMLSaveControler.18")); //$NON-NLS-1$
					// first check if Desktop is supported by
					// Platform or not
					if (!Desktop.isDesktopSupported())

					{
						JOptionPane.showMessageDialog(null,
								Messages.getString("HTMLSaveControler.19"), //$NON-NLS-1$
								Messages.getString("HTMLSaveControler.20"), //$NON-NLS-1$
								JOptionPane.INFORMATION_MESSAGE);
					} else

					{
						Desktop desktop = Desktop.getDesktop();

						try {
							desktop.open(savefile.getCurrentDirectory());
						} catch (IOException e) {
							// TODO Auto-generated catch block

						}
					}

				}
			}
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}
}
