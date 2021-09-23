package SongLib.View;
import SongLib.App.SongLibApp;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ListController {
	@FXML
	ListView<String> listView;
	
	private ObservableList<String> obsSongList;
	
	public void start() {
		obsSongList = FXCollections.observableArrayList(SongLibApp.songStrings);
		listView.setItems(obsSongList);
	}
}
