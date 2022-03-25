package controleur;

import interfacermi.Liste_Magasin_Interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metier.Client;
import metier.Magasin;
import metier.Produit;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Accueil_Controleur extends UnicastRemoteObject implements Initializable {

    @FXML
    private Button btn_acceuil;
    @FXML
    private ComboBox comboBox_magasin;
    @FXML
    private Label lbl_nom;
    @FXML
    private Label  lbl_prenom;

    private static final int port = 8000;

    private static List<Magasin> liste= new ArrayList<Magasin>();
    private static List<Produit> produit= new ArrayList<Produit>();
    private static List<Client> client = new ArrayList<Client>();

    private static String nom="";
    private int id_magasin;

    public Accueil_Controleur() throws Exception {
        super();
    }
    public Accueil_Controleur(List<Client> clients) throws Exception {
        this.client = clients;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (Client cl : client) {
                lbl_nom.setText(cl.getNomClient());
                lbl_prenom.setText(cl.getPrenomClient());
            }

            Liste_Magasin_Interface obj = (Liste_Magasin_Interface) Naming.lookup("rmi://localhost:" + port + "/magasins");
            liste = obj.getMagasins();

            for (Magasin m : liste) {
                //insertion de la liste des magasins dans la combobox
                ObservableList<String> list = FXCollections.observableArrayList(m.getNomMagasin());
                comboBox_magasin.getItems().addAll(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//a refaire
    public void choixMagasin(ActionEvent event) throws Exception {

        //ferme la fenetre d'accueil
        Stage stage_accueil = (Stage) btn_acceuil.getScene().getWindow();
        stage_accueil.close();
        
        
        for(int i = 0 ; i < liste.size(); i++){

            if(liste.get(i).getNomMagasin().equals(comboBox_magasin.getValue())){
                nom = liste.get(i).getNomMagasin();
                id_magasin = liste.get(i).getIdMagasin();
            }
        }

        Magasin_Controleur m1 = new Magasin_Controleur (nom, client);
        Stage stage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/magasin.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle(nom);
        stage.setResizable(false);
        stage.show();

    }
}
