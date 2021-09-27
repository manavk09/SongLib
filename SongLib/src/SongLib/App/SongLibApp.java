package SongLib.App;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import SongLib.View.SongLibController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SongLibApp extends Application{

	public static List<Song> songList = new ArrayList<>();
	@Override
	public void start(Stage primaryStage) throws Exception {
		//S//ystem.out.println("the path is" + System.getProperty("user.dir"));
		readFromfile();

		
		//Load the songList

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
	public void readFromfile(){
		String file = "src/songList.txt";
	   
		Scanner sc;
		try {
			sc = new Scanner(new File(file));
			sc.useDelimiter("\n");
		    
		    while (sc.hasNextLine()) {
		    	String t = sc.nextLine();
		    	List<String> temp = Arrays.asList(t.split("\t"));
				songList.add(new Song(temp.get(0), temp.get(1), (temp.get(2)), temp.get(3)));		
		    }
		    sc.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}
	public static void writeToFile() {
		
		try {
			 String tempPath = System.getProperty("user.dir");
			 String fullPath = tempPath+"/src/songList.txt";
			 
			 FileWriter fwrite = new FileWriter(fullPath);
			 BufferedWriter f_writer = new BufferedWriter(fwrite);
			 
			 for(int i = 0; i < SongLibController.obsSongList.size(); i++) {
				 String text = SongLibController.obsSongList.get(i).fullName() + "\n";
				 f_writer.write(text);
			 }
			 
			 f_writer.close();
        }
 
        catch (IOException e) {
            // Print the exception
            System.out.print(e.getMessage());
        }
		
	}

	
	
	public static void main(String[] args) {
		launch(args);
		

	}
}