package controlleur;

import interfacermi.Inscription_Interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metier.Banque;

import java.net.URL;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Inscription_Controleur implements Initializable {

    @FXML
    private ComboBox combobox_banque;
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

    private static final int port = 8001;

    private static String banqueChoisie;
    private List<Banque> liste_banque = new ArrayList<Banque>();
    private Inscription_Interface obj;
    public Inscription_Controleur() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            obj = (Inscription_Interface) Naming.lookup("rmi://localhost:" + port + "/inscription");
            liste_banque = obj.getChoixBanque();

            for (Banque banque : liste_banque) {
                //insertion de la liste des banque dans la combobox
                ObservableList<String> list = FXCollections.observableArrayList(banque.getNomBanque());
                combobox_banque.getItems().addAll(list);
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void validerInscription(ActionEvent actionEvent) throws Exception {
        Boolean inscription;
        for(Banque banque : liste_banque){
            if(banque.getNomBanque().equals(combobox_banque.getValue())){
                banqueChoisie = banque.getNomBanque();
            }
        }
        inscription = obj.inscriptionClient(textfield_nom.getText().trim(), textfield_prenom.getText().trim(), banqueChoisie, textfield_login.getText().trim(), textfield_mdp.getText());
        if(inscription == true){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(banqueChoisie);
            alert.setHeaderText("Bonjour Mr "+textfield_nom.getText().trim()+". Votre inscription a bien été prise en compte ! Vous pouvez vous connecter pour consulter votre compte bancaire. Pour la création de votre compte la banque "+banqueChoisie+" vous a offert 1 €");
            alert.setContentText("");
            alert.showAndWait();

            //fermeture de la page courante
            Stage stage = (Stage) btn_inscription.getScene().getWindow();
            stage.close();

            //lancement de la page de connexion
            Stage stage_connexion = new Stage();
            AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/connexion.fxml"));
            stage_connexion.setScene(new Scene(root));
            stage_connexion.setTitle("Inscription à une banque");
            stage_connexion.setResizable(false);
            stage_connexion.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inscription à une banque");
            alert.setHeaderText("L'un des champ n'est pas renseigné ou votre login est déja utilisé par un autre client dans la banque "+banqueChoisie);
            alert.setContentText("");
            alert.showAndWait();
        }
        //public boolean inscriptionClient(String nom, String prenom, String nomBanque, String login, String mdp) throws Exception;
    }
}
