package application;
	
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Main extends Application {
	
	private Server server;
	
	private GridPane rootPane;
	
	private final WebView browser = new WebView();
	private final WebEngine engine = browser.getEngine();
	
	private FlickrAgent fAgent;
	
	@Override
	public void start (Stage primaryStage) {
		try {
			//	Kick off agents
			fAgent = new FlickrAgent();
			
			primaryStage.setTitle("Photo Butler");
			
			initUI();
			
			Scene scene = new Scene(rootPane,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initUI () {
		rootPane = new GridPane();
		rootPane.setAlignment(Pos.TOP_CENTER);
		rootPane.setHgap(10);
		rootPane.setVgap(10);
		rootPane.setPadding(new Insets(25, 25, 25, 25));
		
		Button instagramBtn = new Button();
		instagramBtn.setText("Login with Instagram");
		instagramBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				JOptionPane.showMessageDialog(null, "Hah sux2suk it's not ready yet");
//				try {
//					//	Get our public IP address
//					InetAddress host = InetAddress.getLocalHost();
//					
//					//	Open Instagram login
//					engine.load("https://api.instagram.com/oauth/authorize/?client_id=cf2ca24a18c9465ba5b411dcb5926d2c&redirect_uri=" + host.getHostAddress() + ":9000&response_type=code");
//					
//					//	Show in new window
//					BorderPane pane = new BorderPane();
//					pane.setCenter(browser);
//					
//					Scene scene = new Scene(pane, 400, 230);
//					Stage stage = new Stage();
//					stage.setScene(scene);
//					stage.show();
//				} catch (UnknownHostException e) {
//					e.printStackTrace();
//				}
			}
		});
		rootPane.add(instagramBtn, 0, 0);
		
		Button flickrBtn = new Button();
		flickrBtn.setText("Open Flickr Settings");
		flickrBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
			}
		});
		rootPane.add(flickrBtn, 1, 0);
	}
	
	public static void main (String[] args) {
		launch(args);
	}
}
