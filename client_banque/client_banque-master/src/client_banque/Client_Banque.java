package client_banque;


import interfacermi.Banque_Interface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.Naming;

public class Client_Banque extends Application {
    public static void main(String[] args) {
        try {
            //lance le javafx
            launch(args);

        } catch (Exception e) {
            System.out.println("Client banque exception : " +"\n"+ e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("../vue/connexion.fxml"));
            stage.setScene(new Scene(root));

            stage.setTitle("Serveur multi banque");
            stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
