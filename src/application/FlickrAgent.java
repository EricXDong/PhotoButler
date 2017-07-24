package application;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FlickrAgent {
	private final String baseUrl = Config.flickrApiUrl + "api_key=" + Config.flickrKey + "&";
	
	private XMLParser parser;
	
	private List<String> users;
	private List<String> userIds;
	private List<String> photoIds;
	
	public FlickrAgent () {
		photoIds = new ArrayList<String>();
		users = new ArrayList<String>();
		userIds = new ArrayList<String>();
		parser = new XMLParser();
		
		users.add("rosiehardy");
		fetchUserIds();
		fetchPhotoIds();
	}
	
	private void fetchUserIds () {
		try {
			//	For each user, get user ID
			for (int i = 0; i < users.size(); i++) {
				String username = users.get(i);
				String[][] query = {{"username", username}};
				InputStream is = makeApiCall(buildUrl("flickr.people.findByUsername", query));
				
				//	Parse the XML response
				Document doc = parser.parseXML(is);
				doc.getDocumentElement().normalize();
				
				//	Get the user node
				NodeList nlist = doc.getElementsByTagName("user");
				if (nlist.getLength() == 0) {
					System.out.println("Username " + username + " not found!");
				} else {
					for (int j = 0; j < nlist.getLength(); j++) {
						//	Get nsid value
						Node n = nlist.item(j);
						NamedNodeMap nnm = n.getAttributes();
						userIds.add(nnm.getNamedItem("nsid").getNodeValue());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	private void fetchPhotoIds () {
		try {
			for (int i = 0; i < userIds.size(); i++) {
				String id = userIds.get(i);
				String[][] query = {{"user_id", id}};
				InputStream is = makeApiCall(buildUrl("flickr.people.getPhotos", query));
				
				//	Parse the XML response
				Document doc = parser.parseXML(is);
				doc.getDocumentElement().normalize();
				
				//	Get all gallery nodes
				NodeList nlist = doc.getElementsByTagName("photo");
				if (nlist.getLength() == 0) {
					System.out.println("User #" + id + " has no photos!");
				} else {
					for (int j = 0; j < nlist.getLength(); j++) {
						//	Store gallery ID
						Node n = nlist.item(j);
						NamedNodeMap nnm = n.getAttributes();
						photoIds.add(nnm.getNamedItem("id").getNodeValue());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser (String user) {
		users.add(user);
	}
	
	private InputStream makeApiCall (String url) throws IOException {
		try {
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			return connection.getInputStream();
		} catch (IOException e) {
			throw e;
		}
	}
	
	private String buildUrl (String method, String[][] queryParams) {
		//	Build Flickr API call
		String url = baseUrl + "method=" + method + "&";
		for (int i = 0; i < queryParams.length; i++) {
			url += queryParams[i][0] + "=" + queryParams[i][1] + "&";
		}
		return url;
	}
}
