package controleur;

import interfacermi.Connexion_Interface;
import interfacermi.Inscription_Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Inscription_Controleur extends UnicastRemoteObject{

    @FXML
    private TextField textfield_nom;
    @FXML
    private TextField textfield_prenom;
    @FXML
    private TextField textfield_login;
    @FXML
    private TextField textfield_mdp;
    @FXML
    private Button btn_inscription;

    private static final int port = 8000;

    private Boolean inscription;

    private int id_cl = 0;

    public Inscription_Controleur() throws RemoteException {
        super();
    }

    public void validerInscription(ActionEvent actionEvent) {
        try{
            Inscription_Interface obj = (Inscription_Interface) Naming.lookup("rmi://localhost:" + port + "/inscription");
            inscription = obj.inscriptionClient(textfield_nom.getText().trim(), textfield_prenom.getText().trim(), textfield_login.getText().trim(), textfield_mdp.getText());

            if(inscription){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Inscription magasin");
                alert.setHeaderText("Resultat:");
                alert.setContentText("Bienvenue parmis nous "+textfield_nom.getText()+". Vous pouvez des a présen accéder à notre catalogue de magasin !");
                alert.showAndWait();

                //fermerture de la page d'inscription
                Stage stage = (Stage) btn_inscription.getScene().getWindow();
                stage.close();

                //ouverture de la page de connexion
                Stage stage_connexion = new Stage();
                AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/connexion.fxml"));
                stage_connexion.setScene(new Scene(root));
                stage_connexion.setTitle("Serveur de magasin");
                stage_connexion.setResizable(false);
                stage_connexion.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inscription magasin");
                alert.setHeaderText("Echec de l'inscription");
                alert.setContentText("L'un des champs n'est pas renseigné ou votre login est déja utilisé par un autre client.");
                alert.showAndWait();
            }
        }catch(Exception e){
            e.printStackTrace();
        }








                        }

}
