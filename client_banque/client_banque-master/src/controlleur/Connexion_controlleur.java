package controlleur;

import interfacermi.Banque_Interface;
import interfacermi.Connexion_Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metier.Banque;
import metier.Client;

import java.io.IOException;
import java.rmi.Naming;
import java.util.*;

public class Connexion_controlleur {

    @FXML
    private TextField textfield_login;
    @FXML
    private TextField textfield_mdp;
    @FXML
    private Button btn_connexion;
    private static List<Client> liste_Client = new ArrayList<Client>();
    private static List<Banque> liste_banque = new ArrayList<Banque>();
    private static String nomBanque;

    private static final int port = 8001;

    public void connexionBanque(ActionEvent actionEvent) {

        try {
            Connexion_Interface obj = (Connexion_Interface) Naming.lookup("rmi://localhost:" + port + "/connexion");
            liste_Client = obj.banqueConnexion(textfield_login.getText().trim(), textfield_mdp.getText());

            if(liste_Client.size() != 0){
                //fermeture de la page courante
                Stage stage = (Stage) btn_connexion.getScene().getWindow();
                stage.close();


                for(Client cl : liste_Client){
                    liste_banque = obj.serveurBanque(cl.getIdClient());
                }

                for(Banque banque: liste_banque){
                    nomBanque = banque.getNomBanque();
                }
                Banque_Controlleur b1 = new Banque_Controlleur(liste_Client, nomBanque);
                Stage stage_Banque = new Stage();
                AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/banque.fxml"));
                stage_Banque.setScene(new Scene(root));
                stage_Banque.setTitle(nomBanque);
                stage_Banque.setResizable(false);
                stage_Banque.show();
            }
            else{
                //faire un message d'alerte pas de connexion possible
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connexion");
                alert.setHeaderText("");
                alert.setContentText("L'identifiant et le mot de passe est incorrect!!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Client banque connexion exception : " +"\n"+ e);
        }

    }

    public void inscriptionClientBanque(ActionEvent actionEvent) throws IOException {
        //fermeture de la page courante
        Stage stage = (Stage) btn_connexion.getScene().getWindow();
        stage.close();

        Stage stage_Inscription = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/inscription.fxml"));
        stage_Inscription.setScene(new Scene(root));
        stage_Inscription.setTitle("Inscription a une banque");
        stage_Inscription.setResizable(false);
        stage_Inscription.show();
    }
}
