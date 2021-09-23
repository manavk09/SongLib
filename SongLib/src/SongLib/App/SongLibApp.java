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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Load the songList
		songList.add(new Song("songname", "artistname", 2069, "albumyee"));
		songList.add(new Song("songname2", "artistname2", 2222, "alsadfsd"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/SongLib/View/SongLibUI.fxml"));
		System.out.println(loader.getLocation());
		VBox root = (VBox) loader.load();
		
		SongLibController songLibController = loader.getController();
		songLibController.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}