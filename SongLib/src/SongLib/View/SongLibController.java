package SongLib.View;

import SongLib.App.SongLibApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SongLibController {
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML Button save;
	@FXML Button cancel;
	@FXML ListView<String> songList;
	
	public ObservableList<String> obsSongList;
	
	public void start() {
		obsSongList = FXCollections.observableArrayList(SongLibApp.songStrings);
		songList.setItems(obsSongList);
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