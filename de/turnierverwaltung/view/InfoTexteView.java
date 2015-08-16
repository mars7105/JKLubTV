package de.turnierverwaltung.view;

public class InfoTexteView {

	public InfoTexteView() {
		
	}
	
	public String getLizenzText() {
		return "Dieses Programm nutzt verschiedene freie Bibliotheken:\n"
				+ "1. JDatePicker -> jdatepicker-1.3.4.jar\n"
				+ "  für die Auswahl des Datum.\n"
				+ "  http://jdatepicker.org/\n"
				+ "2. SQLite JDBC -> sqlite-jdbc-3.8.11.1.jar\n"
				+ "  für die Speicherung im SQLite Format.\n"
				+ "  https://bitbucket.org/xerial/sqlite-jdbc/downloads\n"
				+ "3. MySQL Connector -> mysql-connector-java-5.1.35-bin.jar\n"
				+ "  für zukünftige Änderungen.\n"
				+ "  http://mvnrepository.com/artifact/mysql/mysql-connector-java\n"
				+ "4. Bilder: Open Icon Library\n"
				+ "  http://sourceforge.net/projects/openiconlibrary/\n"
				+ "";
	}
	public String getHelpText() {
		return null;
		
	}
}
