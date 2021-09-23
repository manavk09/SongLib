package SongLib.View;

import SongLib.App.SongLibApp;
import SongLib.App.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongLibController {
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML Button save;
	@FXML Button cancel;
	
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
		
	}
	public void saveEdit(ActionEvent e) {
		
	}
	public void cancelEdit(ActionEvent e) {
		
	}
	

}