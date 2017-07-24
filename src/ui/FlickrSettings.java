package ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FlickrSettings {
	private Stage stage;
	
	private Button addUserBtn;
	private Button removeUserBtn;
	
	private ListView<String> usersList;
	
	public FlickrSettings () {
		initUI();
	}
	
	private void initUI () {
		BorderPane usersPane = new BorderPane();
		
		//	List view for users
		usersList = new ListView<String>();
		usersList.setPrefHeight(250);
		usersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//	users label
		Label usersLabel = new Label("Get photos from these users:");
		usersLabel.setContentDisplay(ContentDisplay.CENTER);
		usersLabel.setFont(new Font(16));
		usersLabel.setPadding(new Insets(10));
		
		//	Add/remove users
		HBox usersBox = new HBox(10);
		usersBox.setAlignment(Pos.CENTER);
		usersBox.setPadding(new Insets(10));
		
		addUserBtn = new Button("Add user");
		removeUserBtn = new Button("Remove user(s)");
		usersBox.getChildren().addAll(addUserBtn, removeUserBtn);
		
		//	Add everything to users pane
		usersPane.setTop(usersLabel);
		usersPane.setCenter(usersList);
		usersPane.setBottom(usersBox);
		
		FlowPane mainPane = new FlowPane();
		mainPane.getChildren().add(usersPane);
		
		Scene scene = new Scene(mainPane, 600, 400);
		
		stage = new Stage();
		stage.setScene(scene);
		
		setBtnHandlers();
	}
	
	public void open () {
		stage.show();
	}
	
	private void setBtnHandlers () {
		addUserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				//	Open window to enter username
				HBox addUserPane = new HBox(10);
				addUserPane.setAlignment(Pos.CENTER);
				
				Label addUserLabel = new Label("Enter user name:");
				addUserLabel.setFont(new Font(14));
				
				TextField addUserField = new TextField();
				addUserField.setFont(new Font(14));
				
				Button ok = new Button("OK");
				
				addUserPane.getChildren().addAll(addUserLabel, addUserField, ok);
				
				Scene addUserScene = new Scene(addUserPane);
				Stage s = new Stage();
				s.setScene(addUserScene);
				s.show();
				
				ok.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle (ActionEvent evt) {
						usersList.getItems().add(addUserField.getText());
						s.close();
					}
				});
				addUserField.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle (KeyEvent key) {
						if (key.getCode() == KeyCode.ENTER) {
							ok.fire();
						}
					}
				});
			}
		});
		
		removeUserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				//	Remove selected users
				ObservableList<Integer> selected = usersList.getSelectionModel().getSelectedIndices();
				for (int i = 0; i < selected.size(); i++) {
					usersList.getItems().remove(selected.get(i).intValue());
				}
			}
		});
	}
}
