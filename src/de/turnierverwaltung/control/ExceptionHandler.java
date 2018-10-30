package de.turnierverwaltung.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JOptionPane;

public class ExceptionHandler {
	private MainControl mainControl;
	private PropertiesControl propertiesControl;

	public ExceptionHandler(MainControl mainControl) {
		super();

		this.mainControl = mainControl;
		if (mainControl != null) {
			propertiesControl = this.mainControl.getPropertiesControl();
		}
	}

	/**
	 * 
	 */
	public void fileSQLError(String error) {
		Boolean ok = true;
		if (mainControl != null) {
			writeErrorLog(error);
			propertiesControl.setPathToDatabase("");
			propertiesControl.checkProperties();
			ok = propertiesControl.writeProperties();
			if (ok) {
				JOptionPane.showMessageDialog(null, Messages.getString("MainControl.11"));

			} else {
				JOptionPane.showMessageDialog(null, Messages.getString("MainControl.12"));

			}

			mainControl.resetApp();
		}
	}

	private void writeErrorLog(String error) {
		if (error != null) {
			Boolean fexist = true;
			File errorFile = null;
			int errornumber = 0;
			while (fexist) {
				if (propertiesControl.getDefaultPath().length() == 0) {
					errorFile = new File("error-" + errornumber + ".log");

				} else {
					errorFile = new File(propertiesControl.getDefaultPath() + "/error-" + errornumber + ".log");
				}

				if (errorFile.exists()) {
					errornumber++;
				} else {
					fexist = false;
				}
			}

			Writer writer;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(errorFile), "UTF8");
				writer.write(error);
				writer.flush();
				writer.close();
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
