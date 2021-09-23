package SongLib.App;""

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SongLibApp extends Application{

	public static ArrayList<Song> songList;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Load the songList
		songList.add(new Song("songname", "artistname", 2069, "albumyee"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/SongLib/View/SongLibUI.fxml"));
		System.out.println(loader.getLocation());
		VBox root = (VBox) loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}