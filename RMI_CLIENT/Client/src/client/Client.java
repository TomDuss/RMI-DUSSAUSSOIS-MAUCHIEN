package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {

	public static void main(String[] args) {
		try {

			//lance le javafx
			launch(args);

		} catch (Exception e) {
			System.out.println("Client exception : " +"\n"+ e);
		}
	}

	@Override
	public void start(final Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("../vue/connexion.fxml"));
			primaryStage.setScene(new Scene(root));

			primaryStage.setTitle("Serveur de magasin");
			primaryStage.setResizable(false);
			primaryStage.show();


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
