package SongLib;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class F2CPgm extends Application {
	public void start(Stage primaryStage) {
		
		GridPane root = makeGridPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private static GridPane makeGridPane() {// all the widgets
		Text fText = new Text("Fahrenheit");
		Text cText = new Text("Celsius");
		TextField f = new TextField();
		TextField c = new TextField();
		Button f2c = new Button(">>>");
		Button c2f = new Button("<<<");
		GridPane gridPane = new GridPane();
		gridPane.add(fText, 0, 0);
		gridPane.add(f2c, 1, 0);
		gridPane.add(cText, 2, 0);
		gridPane.add(f, 0, 1);
		gridPane.add(c2f, 1, 1);
		gridPane.add(c, 2, 1);
		return gridPane;
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
