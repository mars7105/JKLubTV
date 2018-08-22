Schach Rundenturniere verwalten mit JKlubTV

Diese Applikation speichert die zum Rundenurnier gehörenden Daten in einer SQLite Datenbank. Sie ist für Webmaster gedacht die auf einfache Weise ein Klubturnier im eigenen Schachklub verwalten möchten. Die HTML Tabellen, die für die Webseite veröffentlicht werden sollen, werden ohne großen Aufwand durch die Applikation erstellt. Sie berrechnet automatisch die Gesamtpunkte und Sonnebornberger Punkte der einzelnen Spieler, sowie deren Folge DWZ, und sortiert die HTML Tabelle nach der errechneten Rangliste. Desweiteren ist es möglich die Tabellen als PDF Datei zu speichern.

Die Software ist kostenlos (Opensource).

tested with OpenJDK 8 and Java SE 7/8

benötigte Java Librarys:
JDatePicker 1.3.4.jar http://jdatepicker.org/

sqlite-jdbc 3.8.11.1 https://bitbucket.org/xerial/sqlite-jdbc/downloads

itextpdf-5.5.6.jar http://itextpdf.com/

Open Icon Library http://sourceforge.net/projects/openiconlibrary/

dewis.jar http://www.schachbund.de/api.html

Dieses Programm nutzt verschiedene freie Bibliotheken:

1. JDatePicker -> jdatepicker-1.3.4.jar
    für die Auswahl des Datum.
    http://jdatepicker.org/

2. SQLite JDBC -> sqlite-jdbc-3.8.11.1.jar
    für die Speicherung im SQLite Format.
    https://bitbucket.org/xerial/sqlite-jdbc/downloads

3. IText 5.5.6 
    für die Erstellung der PDF Dateien.
    http://itextpdf.com/

4. Bilder: Open Icon Library
    http://sourceforge.net/projects/openiconlibrary/

5. Java API von Peter Fahsel -> dewis.jar
   Zugriff auf DWZ-Listen mit Java.
   http://www.schachbund.de/api.html

6. Look and Feel: JGoodies
   http://www.jgoodies.com/
