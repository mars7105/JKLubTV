package de.turnierverwaltung.model;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class ReadXMLFile {

	public ReadXMLFile() {

	}

	public ArrayList<ELOPlayer> readFile(String filename) {
		ELOPlayer eloPlayer = null;
		ArrayList<ELOPlayer> playerList = new ArrayList<ELOPlayer>();
		try {

			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			// System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

//				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

//					System.out.println("Staff id : " + eElement.getAttribute("id"));
//					System.out.println(
//							"First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
//					System.out.println(
//							"Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//					System.out.println(
//							"Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
					String fideid = eElement.getElementsByTagName("fideid").item(0).getTextContent();
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					String country = eElement.getElementsByTagName("country").item(0).getTextContent();
					String sex = eElement.getElementsByTagName("sex").item(0).getTextContent();
					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String w_title = eElement.getElementsByTagName("w_title").item(0).getTextContent();
					String o_title = eElement.getElementsByTagName("o_title").item(0).getTextContent();
					String foa_title = eElement.getElementsByTagName("foa_title").item(0).getTextContent();
					String rating = eElement.getElementsByTagName("rating").item(0).getTextContent();
					String games = eElement.getElementsByTagName("games").item(0).getTextContent();
					String k = eElement.getElementsByTagName("k").item(0).getTextContent();
					String birthday = eElement.getElementsByTagName("birthday").item(0).getTextContent();
					String flag = eElement.getElementsByTagName("flag").item(0).getTextContent();
					eloPlayer = new ELOPlayer(fideid, name, country, sex, title, w_title, o_title, foa_title, rating,
							games, k, birthday, flag);
					playerList.add(eloPlayer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerList;
	}
}
