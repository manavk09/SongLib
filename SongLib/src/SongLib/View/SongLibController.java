package SongLib.View;

import SongLib.App.SongLibApp;
import SongLib.App.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
<<<<<<< HEAD
import javafx.stage.Stage;
=======
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.*;
>>>>>>> branch 'master' of git@github.com:manavk09/SongLib.git

public class SongLibController {
	
	@FXML Button addButton;
	@FXML Button deleteButton;
	@FXML Button editButton;
	@FXML Button saveButton;
	@FXML Button cancelButton;
	
	@FXML TextField nameField;
	@FXML TextField artistField;
	@FXML TextField albumField;
	@FXML TextField yearField;
	
	@FXML ListView<Song> songList;
	
	public ObservableList<Song> obsSongList;
	public Stage mainStage;
	
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		obsSongList = FXCollections.observableArrayList(SongLibApp.songList);
		songList.setItems(obsSongList);
		
		songList.getSelectionModel().select(0);
		showItem(primaryStage);
		
		songList.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldval, newval) -> showItem(primaryStage));
		
	}
	
	private void showItem(Stage primaryStage) {
		String name, artist, album;
		int year;
		name = songList.getSelectionModel().getSelectedItem().getSongName();
		artist = songList.getSelectionModel().getSelectedItem().getArtistName();
		album = songList.getSelectionModel().getSelectedItem().getAlbumName();
		year = songList.getSelectionModel().getSelectedItem().getYear();
		nameField.setText(name);
		artistField.setText(artist);
		albumField.setText(album);
		yearField.setText("" + year);
		
		//Enable and disable fields and buttons appropriately
		setFieldsWritable(false);
		cancelButton.setDisable(true);
		saveButton.setDisable(true);
	}

	//Set if fields can be edited
	public void setFieldsWritable(boolean isWritable) {
		nameField.setEditable(isWritable);
		artistField.setEditable(isWritable);
		albumField.setEditable(isWritable);
		yearField.setEditable(isWritable);
	}
	
	@FXML TextField nameField;
	@FXML TextField artistField;
	@FXML TextField albumField;
	@FXML TextField yearField;
	@FXML ListView<Song> songList;
	
	public ObservableList<Song> obsSongList;
	
	public void start(Stage primaryStage) {
		obsSongList = FXCollections.observableArrayList(SongLibApp.songList);
		songList.setItems(obsSongList);
		
		songList.getSelectionModel().select(0);
		showItem(primaryStage);
		
		songList.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldval, newval) -> showItem(primaryStage));
	}
	
	private void showItem(Stage primaryStage) {
		String name, artist, album;
		int year;
		name = songList.getSelectionModel().getSelectedItem().getSongName();
		artist = songList.getSelectionModel().getSelectedItem().getArtistName();
		album = songList.getSelectionModel().getSelectedItem().getAlbumName();
		year = songList.getSelectionModel().getSelectedItem().getYear();
		nameField.setText(name);
		artistField.setText(artist);
		albumField.setText(album);
		yearField.setText("" + year);
	}

	public void addSong(ActionEvent e) {
		
	}
	
	public void deleteSong(ActionEvent e) {
		
	}
	
	public void editSongInfo(ActionEvent e) {
		//Enable and disable fields and buttons appropriately
		setFieldsWritable(true);
		editButton.setDisable(true);
		addButton.setDisable(true);
		deleteButton.setDisable(true);
		cancelButton.setDisable(false);
		saveButton.setDisable(false);
		
	}
	
	public void saveEdit(ActionEvent e) {
		songList.getSelectionModel().getSelectedItem().setName(nameField.getText());
		songList.getSelectionModel().getSelectedItem().setAlbumName(albumField.getText());
		songList.getSelectionModel().getSelectedItem().setArtistName(artistField.getText());
		try {
			int year = Integer.parseInt(yearField.getText());
			songList.getSelectionModel().getSelectedItem().setYear(year);
		}
		catch(Exception ex) {
			yearField.setText("" + songList.getSelectionModel().getSelectedItem().getYear());
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainStage);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid input! Make sure you don't have the '|' character anywhere and only numbers for the year.");
			alert.showAndWait();
		}
		setFieldsWritable(false);
		editButton.setDisable(false);
		addButton.setDisable(false);
		deleteButton.setDisable(false);
		cancelButton.setDisable(true);
		saveButton.setDisable(true);
	}
	
	public void cancelEdit(ActionEvent e) {
		
	}
	

}