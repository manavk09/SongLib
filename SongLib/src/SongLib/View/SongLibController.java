package SongLib.View;

import SongLib.App.SongLibApp;

import java.util.Collections;
import java.util.Comparator;

import SongLib.App.Song;
import javafx.collections.*;
/*
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    //private InputErrorType inputError;
    
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        obsSongList = FXCollections.observableArrayList(SongLibApp.songList);
        songList.setItems(obsSongList);
        
        songList.getSelectionModel().select(0);
        showItem(primaryStage);
        
        sort();
        songList.getSelectionModel().selectedIndexProperty()
        .addListener((obs, oldval, newval) -> showItem(primaryStage));
        
        
    }
    
    private void showItem(Stage primaryStage) {
    	if(isSorting || obsSongList.isEmpty())
    		return;
    	
        String name, artist, album, year;
        System.out.println(songList.getSelectionModel().getSelectedItem());
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
    	nameField.setText("");
        artistField.setText("");
        albumField.setText("");
        yearField.setText("");
    	setFieldsWritable(true);
        
    	editButton.setDisable(true);
    	addButton.setDisable(true);
    	deleteButton.setDisable(true);
    	cancelButton.setDisable(false);
    	saveButton.setDisable(false);
    	saveButton.setText("Add song");
    
    	
    	//Need to check if the song user is trying to add is valid to add
    	
        
    }
    
    public void deleteSong(ActionEvent e) {
    	//If all songs are deleted/the list is empty, block out the delete button
    	int index = songList.getSelectionModel().getSelectedIndex();
    	obsSongList.remove(index);
    }
    
    public void editSongInfo(ActionEvent e) {
        setEditing(true);
    }
    
    public void saveEdit(ActionEvent e) {
    	String name = nameField.getText().trim();
		String artist = artistField.getText().trim();
		String album = albumField.getText().trim();
		String year = yearField.getText().trim();
		
		
		
    	if(saveAction == SaveAction.EDITING_SONG) {
	    	int index = songList.getSelectionModel().getSelectedIndex();
	    	Song song = obsSongList.get(index);
	    	if(checkValidSong(name, artist, album, year)) {
	    		song.setName(name);
		    	song.setAlbumName(album);
		    	song.setArtistName(artist);
		    	song.setYear(year);
		    	setEditing(false);
		    	sort();
	    	}
	        
    	}
    	else {
    		if(checkValidSong(name, artist, album, year)) {
    			Song newSong = new Song(name, artist, year, album);
                obsSongList.add(newSong);
                saveButton.setText("Save");
                setEditing(false);
                sort();
    		}
    		
    	}
    	SongLibApp.writeToFile();
        
    }
    
    public boolean checkValidSong(String name, String artist, String album, String year) {
    	InputErrorType errorType = null;
    	boolean result = true;
    	int yearNum = 0;
    	try{
    		if(!year.isBlank()) {
    			yearNum = Integer.parseInt(year);
    		}
    		if(!obsSongList.isEmpty() && findSong(name, artist) != null) {
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
    		errorType = InputErrorType.INVALID_CHARS;;
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
    
    public Song findSong(String songName, String songArtist) {
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
    	int index = Collections.binarySearch(obsSongList, keySong, comparator);
    	if(index >= 0)
    		return obsSongList.get(index);
    	else
    		return null;
    }
    
    public void cancelEdit(ActionEvent e) {
    	setEditing(false);
    	showItem(mainStage);
    }
    
    public void setEditing(boolean isEditing) {
    	saveAction = SaveAction.EDITING_SONG;
    	setFieldsWritable(isEditing);
    	
        editButton.setDisable(isEditing);
        addButton.setDisable(isEditing);
        deleteButton.setDisable(isEditing);
        cancelButton.setDisable(!isEditing);
        saveButton.setDisable(!isEditing);
        
        songList.setDisable(isEditing);
    }
    

}