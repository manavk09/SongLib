package SongLib.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		readFromfile("/SongLib/App/songList");
		//Load the songList
		//songList.add(new Song("songname", "artistname", 2069, "albumyee"));
		//songList.add(new Song("songname2", "artistname2", 2222, "alsadfsd"));
		
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
	public void readFromfile(String filePath) throws IOException {
		File file = new File(filePath);
		  
		BufferedReader br = new BufferedReader(new FileReader(file));
		  
		String st;
		while ((st = br.readLine()) != null) {
			List<String> temp = Arrays.asList(st.split("\t"));
			songList.add(new Song(temp.get(0),temp.get(1),Integer.parseInt(temp.get(2)),temp.get(3)));		  
		}
		
		br.close();

	}
	
	public static void main(String[] args) {
		launch(args);

	}
}