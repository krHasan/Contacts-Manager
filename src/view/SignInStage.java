package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import operation.Constraints;

public class SignInStage extends Application {
	@Override
	public void start(Stage signInStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
			Scene scene = new Scene(root, 800, 600);
			signInStage.setTitle("Welcome to Contacts Manager");
			signInStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
			signInStage.setScene(scene);
			signInStage.show();

			EventHandler<WindowEvent> event = new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
				}
			};
			signInStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event);
			Constraints.setStageEvent(event);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
