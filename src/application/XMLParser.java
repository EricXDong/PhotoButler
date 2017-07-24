package application;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLParser {
	private DocumentBuilder dbuilder;
	
	public XMLParser () {
		try {
			dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Document parseXML (InputStream xmlStream) throws SAXException, IOException {
		try {
			return dbuilder.parse(xmlStream);
		} catch (SAXException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
}
