# JKLubTV
JKLubTV
Schach Rundenturniere verwalten mit JKlubTV

Derzeit arbeite ich an einer Java Applikation zur Erstellung und Verwaltung eines Schach Rundenturniers. Diese Applikation speichert die zum Turnier gehörenden Daten in einer MySQL Datenbank oder als Datei auf der Festplatte.
Sie ist für Webmaster gedacht die auf einfache Weise ein Klubturnier im eigenen Schachklub verwalten möchten. Die HTML Tabellen, die für die Webseite veröffentlicht werden sollen, werden ohne großen Aufwand durch die Applikation erstellt. Sie berrechnet automatisch die Gesamtpunkte und Sonnebornberger Punkte der einzelnen Spieler und sortiert die HTML Tabelle nach der errechneten Rangliste.

Die Software ist kostenlos (Opensource).

benötigte Java Librarys:

JDatePicker 1.3.4
mysql-connector-java 5.1.35
sqlite-jdbc 3.7.15 Snapshot 

Dieses Programm nutzt verschiedene freie Bibliotheken:
	1. JDatePicker -> jdatepicker-1.3.4.jar
		für die Auswahl des Datum.
		http://jdatepicker.org/
		
	2. SQLite JDBC -> sqlite-jdbc-3.7.15-SNAPSHOT.jar
		für die Speicherung im SQLite Format.
		https://bitbucket.org/xerial/sqlite-jdbc/downloads
		
	3. MySQL Connector -> mysql-connector-java-5.1.35-bin.jar
		für zukünftige Änderungen.
		http://mvnrepository.com/artifact/mysql/mysql-connector-java
		
	4. Bilder: Open Icon Library
		http://sourceforge.net/projects/openiconlibrary/
				