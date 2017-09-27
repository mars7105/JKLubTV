package de.turnierverwaltung.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLParserModel {
	public XMLPlayerList  readXML(String[] args) {

	    if (args.length == 0) {
	      System.err.println("Usage: java XHTMLOutliner url" );
	      return null;
	    }
	    String input = args[0];

	    try {
	      URL u = new URL(input);
	      InputStream in = u.openStream();
	      XMLInputFactory factory = XMLInputFactory.newInstance();
	      XMLStreamReader parser = factory.createXMLStreamReader(in);
	        
	      int inHeader = 0;
	      for (int event = parser.next();  
	       event != XMLStreamConstants.END_DOCUMENT;
	       event = parser.next()) {
	        switch (event) {
	          case XMLStreamConstants.START_ELEMENT:
	            if (isHeader(parser.getLocalName())) {
	              inHeader++;
	            }
	            break;
	          case XMLStreamConstants.END_ELEMENT:
	            if (isHeader(parser.getLocalName())) {
	              inHeader--;
	              if (inHeader == 0) System.out.println();
	            }
	            break;
	          case XMLStreamConstants.CHARACTERS:
	            if (inHeader > 0)  System.out.print(parser.getText());
	            break;
	          case XMLStreamConstants.CDATA:
	            if (inHeader > 0)  System.out.print(parser.getText());
	            break;
	        } // end switch
	      } // end while
	      parser.close();
	    }
	    catch (XMLStreamException ex) {
	       System.out.println(ex);
	    }
	    catch (IOException ex) {
	      System.out.println("IOException while parsing " + input);
	    }
		return null;

	  }

	   /**
	    * Determine if this is an XHTML heading element or not
	    * @param  name tag name
	    * @return boolean true if this is h1, h2, h3, h4, h5, or h6; 
	    *                 false otherwise
	    */
	    private static boolean isHeader(String name) {
	      if (name.equals("h1")) return true;
	      if (name.equals("h2")) return true;
	      if (name.equals("h3")) return true;
	      if (name.equals("h4")) return true;
	      if (name.equals("h5")) return true;
	      if (name.equals("h6")) return true;
	      return false;
	    }
}
