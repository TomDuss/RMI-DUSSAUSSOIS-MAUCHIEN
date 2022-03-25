package controleur;

import interfacermi.Connexion_Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metier.Client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Connexion_Controleur extends UnicastRemoteObject {

    @FXML
    private TextField textfield_login;
    @FXML
    private PasswordField textfield_mdp;
    @FXML
    private Button btn_connexion;

    private static final int port = 8000;

    private static List<Client> client_liste = new ArrayList<>();

    public Connexion_Controleur () throws RemoteException {
        super();
    }

    public void connexion(ActionEvent event){
        String login, mdp;
        login = textfield_login.getText().trim();
        mdp = textfield_mdp.getText();

        try{
            Connexion_Interface obj = (Connexion_Interface) Naming.lookup("rmi://localhost:" + port + "/connexion");
            client_liste = obj.getClients(login, mdp);

            if(client_liste.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection magasin");
                alert.setHeaderText("Resultat:");
                alert.setContentText("L'identifiant ou le mot de passe est incorecte");
                alert.showAndWait();
            }
            else{
                //ferme la fenetre de connexion
                Stage stage = (Stage) btn_connexion.getScene().getWindow();
                stage.close();
                Accueil_Controleur a1 = new Accueil_Controleur(client_liste);

/*
                for (Client cl : client_liste) {
                    System.out.println(cl.getNomClient()+"  "+cl.getPrenomClient()+" "+cl.getIdClient());

                }
 */
                //lance la fenetre de choix de magasin
                Stage stage_accueil = new Stage();
                AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/accueil.fxml"));
                stage_accueil.setScene(new Scene(root));
                stage_accueil.setTitle("Choix magasins");
                stage_accueil.setResizable(false);
                stage_accueil.show();

            }

        } catch (Exception e) {
            System.out.println("Client exception : " +"\n"+ e);
        }
    }

    public void inscriptionClient(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btn_connexion.getScene().getWindow();
        stage.close();

        Stage stage_inscription = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/inscription.fxml"));
        stage_inscription.setScene(new Scene(root));
        stage_inscription.setTitle("Inscription");
        stage_inscription.setResizable(false);
        stage_inscription.show();
    }
}
