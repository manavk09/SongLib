package SongLib.App;

import java.util.ArrayList;

import SongLib.View.SongLibController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SongLibApp extends Application{

	public static ArrayList<Song> songList = new ArrayList<Song>();
	public static ArrayList<String> songStrings = new ArrayList<String>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Load the songList
		songList.add(new Song("songname", "artistname", 2069, "albumyee"));
		for(int i = 0; i < songList.size(); i++) {
			songStrings.add(songList.get(i).toString());
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/SongLib/View/SongLibUI.fxml"));
		System.out.println(loader.getLocation());
		VBox root = (VBox) loader.load();
		
		SongLibController songLibController = loader.getController();
		songLibController.start();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}