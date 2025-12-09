package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GuestHomePage;

public class Main extends Application {

	private static Stage stg;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stg = primaryStage;
		primaryStage.setTitle("JoymarKet");
		
		//start di GuestHomePage
		primaryStage.setScene(new Scene(new GuestHomePage(), 800, 600));
		primaryStage.show();
	}

	public static void changeScene(Parent root) {
        stg.getScene().setRoot(root);
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
