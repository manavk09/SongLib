/*	Authors:
 *	Kritik Patel
 * 	Manav Kumar 
 */
package SongLib.View;

import SongLib.App.SongLib;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import SongLib.App.Song;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
    
    public static ObservableList<Song> obsSongList;
    public Stage mainStage;
    public boolean isSorting = false;
    
    private enum SaveAction{
    	ADDING_SONG, EDITING_SONG;
    }
    private enum InputErrorType{
    	INVALID_CHARS, SONG_EXISTS, EMPTY_SONG_OR_ARTIST;
    }
    private SaveAction saveAction;
    
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        obsSongList = FXCollections.observableArrayList(SongLib.songList);
        songList.setItems(obsSongList);
        
        songList.getSelectionModel().select(0);
        showItem(primaryStage);
        
        sort();
        songList.getSelectionModel().selectedIndexProperty()
        .addListener((obs, oldval, newval) -> showItem(primaryStage));
        
        setEditing(false);
        
    }
    
    private void showItem(Stage primaryStage) {
    	if(isSorting || obsSongList.isEmpty() || songList.getSelectionModel().getSelectedItem() == null)
    		return;
    	
        String name, artist, album, year;
        name = songList.getSelectionModel().getSelectedItem().getSongName();
        artist = songList.getSelectionModel().getSelectedItem().getArtistName();
        album = songList.getSelectionModel().getSelectedItem().getAlbumName();
        year = songList.getSelectionModel().getSelectedItem().getYear();
        nameField.setText(name);
        artistField.setText(artist);
        albumField.setText(album);
        yearField.setText(year);
        
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
    
    public void addSong(ActionEvent e) {
    	saveAction = SaveAction.ADDING_SONG;
    	setFieldsBlank();
    	setFieldsWritable(true);
        
    	editButton.setDisable(true);
    	addButton.setDisable(true);
    	deleteButton.setDisable(true);
    	cancelButton.setDisable(false);
    	saveButton.setDisable(false);
    	saveButton.setText("Add song");
    
    }
    
    public void deleteSong(ActionEvent e) {
    	int index = songList.getSelectionModel().getSelectedIndex();
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(mainStage);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText("Confirm deletion:");
        alert.setContentText("Are you sure you want to delete " + obsSongList.get(index) + "?");
        Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			obsSongList.remove(index);
			if(!obsSongList.isEmpty() || index != obsSongList.size())
				songList.getSelectionModel().select(index);
	    	showItem(mainStage);
		}
    	
    	//If there's no songs in the list, then block out the delete button
    	if(obsSongList.isEmpty()) {
    		deleteButton.setDisable(true);
    		editButton.setDisable(true);
    		setFieldsBlank();
    	}
    	SongLib.writeToFile();
    }
    
    public void editSongInfo(ActionEvent e) {
    	saveAction = SaveAction.EDITING_SONG;
        setEditing(true);
        SongLib.writeToFile();
    }
    
    public void saveEdit(ActionEvent e) {
    	String name = nameField.getText().trim();
		String artist = artistField.getText().trim();
		String album = albumField.getText().trim();
		String year = yearField.getText().trim();
		int index = 0;
    	if(saveAction == SaveAction.EDITING_SONG) {
	    	index = songList.getSelectionModel().getSelectedIndex();
	    	Song song = obsSongList.get(index);
	    	if(checkValidSong(name, artist, album, year)) {
	    		song.setName(name);
		    	song.setAlbumName(album);
		    	song.setArtistName(artist);
		    	song.setYear(year);
		    	sort();
		    	index = findSong(name, artist);
		    	songList.getSelectionModel().select(index);
	    	}
    	}
    	else {//Adding song
    		if(checkValidSong(name, artist, album, year)) {
    			Song newSong = new Song(name, artist, year, album);
                obsSongList.add(newSong);
                
                saveButton.setText("Save");
                deleteButton.setDisable(false);
                
                sort();
                index = findSong(name, artist);
                songList.getSelectionModel().select(index);
    		}
    		
    	}
    	showItem(mainStage);
    	setEditing(false);
    	SongLib.writeToFile();
    	if(obsSongList.size() == 1) {
    		songList.getSelectionModel().select(0);
    	}
        
    }
    
    public boolean checkValidSong(String name, String artist, String album, String year) {
    	InputErrorType errorType = null;
    	boolean result = true;
    	int yearNum = 0;
    	try{
    		if(!year.isBlank()) {
    			yearNum = Integer.parseInt(year);
    		}
    		int songSearchIndex = findSong(name, artist);
    		int index = songList.getSelectionModel().getSelectedIndex();
    		
    		if((saveAction == SaveAction.EDITING_SONG && songSearchIndex >= 0 && songSearchIndex != index) || (saveAction == SaveAction.ADDING_SONG && songSearchIndex >= 0)) {
    			errorType = InputErrorType.SONG_EXISTS;
    		}
    		if(name.isBlank() || artist.isBlank()) {
    			errorType = InputErrorType.EMPTY_SONG_OR_ARTIST;
    		}
    		if(name.contains("|") || artist.contains("|") || album.contains("|") || year.contains("|") || yearNum < 0) {
    			errorType = InputErrorType.INVALID_CHARS;
    		}
    		
    	}
    	catch(Exception e) {
    		errorType = InputErrorType.INVALID_CHARS;
    	}
    	finally{
    		if(errorType != null) {
    			result = false;
    			Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(mainStage);
	            alert.setTitle("Error");
    			if(errorType == InputErrorType.SONG_EXISTS) {
    	            alert.setHeaderText("This song already exists under this artist!");
    	            alert.setContentText("Please enter a song name and artist that do not already exist in the library.");
    			}
    			else if(errorType == InputErrorType.INVALID_CHARS) {
    				alert.setHeaderText("You entered an invalid input!");
    	            alert.setContentText("Please do not use the '|' character anywhere and make sure to only input a positive whole number for the year.");
    			}
    			else {
    				alert.setHeaderText("You are missing neccessary inputs!");
    	            alert.setContentText("You MUST at least include a song name and artist name.");
    			}
    			alert.showAndWait();
    		}
    	}
    	return result;
    }
    
    public void sort() {
    	isSorting = true;
    	FXCollections.sort(obsSongList, new Comparator<Song>(){
            public int compare(Song a, Song b){
            	Song s1 = a;
        		Song s2 = b;
        		
        		int result = s1.getSongName().compareToIgnoreCase(s2.getSongName());
        		if(result != 0) {
        			return result;
        		}
        		else{
        			return s1.getArtistName().compareToIgnoreCase(s2.getArtistName());
        		}
            }});
    	isSorting = false;
    }
    
    public int findSong(String songName, String songArtist) {
    	Comparator<Song> comparator = new Comparator<Song>() {
    		public int compare(Song a, Song b) {
    			int result = a.getSongName().compareToIgnoreCase(b.getSongName());
    			if(result != 0) {
    				return result;
    			}
    			else {
    				return a.getArtistName().compareToIgnoreCase(b.getArtistName());
    			}
    			
    		}
    	};
    	Song keySong = new Song(songName, songArtist, "", "");
    	return Collections.binarySearch(obsSongList, keySong, comparator);
    }
    
    public void cancelEdit(ActionEvent e) {
    	setEditing(false);
    	if(obsSongList.isEmpty()) {
    		editButton.setDisable(true);
    		deleteButton.setDisable(true);
    	}
    	showItem(mainStage);
    }
    
    public void setEditing(boolean isEditing) {
    	if(obsSongList.isEmpty()) {
    		editButton.setDisable(true);
    		addButton.setDisable(false);
            deleteButton.setDisable(true);
            cancelButton.setDisable(true);
            saveButton.setDisable(true);
    	}
    	else {
    		setFieldsWritable(isEditing);
            editButton.setDisable(isEditing);
            addButton.setDisable(isEditing);
            deleteButton.setDisable(isEditing);
            cancelButton.setDisable(!isEditing);
            saveButton.setDisable(!isEditing);
            songList.setDisable(isEditing);
    	}
    	saveButton.setText("Save");
    	
    }
    
    public void setFieldsBlank() {
    	nameField.setText("");
        artistField.setText("");
        albumField.setText("");
        yearField.setText("");
    }

}