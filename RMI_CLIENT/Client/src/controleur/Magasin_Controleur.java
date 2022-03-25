package controleur;

import interfacermi.Magasin_Interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import metier.Client;
import metier.Produit;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Magasin_Controleur extends UnicastRemoteObject implements Initializable, Remote {

    @FXML
    private TableView<Produit> TableView_produit = new TableView<Produit>();
    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_panier;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Spinner spinner_qte;


    private static List<Produit> liste= new ArrayList<Produit>();
    private static List<Produit> panier= new ArrayList<Produit>();
    private static List<Client> client = new ArrayList<Client>();

    private final int initialValue = 1;

    private static int qte;

    private boolean existe = false;

    private static String titre;

    private static String nomMagasin;

    private static final int port = 8000;

    public Magasin_Controleur() throws RemoteException {
    }
    public Magasin_Controleur(List<Produit> liste, List<Client> Client,List<Produit> Panier) throws RemoteException {
        this.liste = liste;
        client = Client;
        panier = Panier;
    }

//a suppr je pense a verifier
    public Magasin_Controleur(List<Produit> liste, List<Client> Client) throws RemoteException {
        this.liste = liste;
        client = Client;
    }

    public Magasin_Controleur(String NomMagasin, List<Client> Client) throws RemoteException {
        this.nomMagasin = NomMagasin;
        client = Client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Magasin_Interface obj = (Magasin_Interface) Naming.lookup("rmi://localhost:"+port+"/"+nomMagasin);
            int idMagasin = obj.getIdMagasin(nomMagasin);
            liste = obj.getProduits(idMagasin);

            //spinner qui gère la quantité
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, initialValue);
            spinner_qte.setValueFactory(valueFactory);


            //column de la table qui vas contenir le nom du produit
            TableColumn<Produit, String> nomProduit = new TableColumn<Produit, String>("Article");
            nomProduit.setMinWidth(400);
            nomProduit.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));

            TableColumn<Produit, Integer> prixProduit = new TableColumn<Produit, Integer>("Prix a l'unite en €");
            prixProduit.setMinWidth(150);
            prixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));


            for (Produit p : liste) {
                //insertion de la liste des produits du magasin dans la tableview
                ObservableList<Produit> list = FXCollections.observableArrayList(p);

                TableView_produit.getItems().addAll(list);
            }

            TableView_produit.getColumns().addAll(nomProduit, prixProduit);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajoutPanier(ActionEvent actionEvent) {
        //on récupère la quantité sélectionnée
       qte = (int) spinner_qte.getValue();

       //on injecte le produit dans un objet de type produit
        Produit article;
        article = TableView_produit.getSelectionModel().getSelectedItem();
        article.setQuantiteProduit(qte);

                for (Produit produit : panier) {

                    if (produit.equals(article)) {
                        existe = true;
                    }
                }

        Stage stage = (Stage) btn_retour.getScene().getWindow();
         titre = stage.getTitle();

        if(!existe){
            //on injecte le produit dans le panier
            panier.add(article);
            //message d'information pour prévenir l'utilisateur que la sélection a bien fonctionnée

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Panier du magasin : "+nomMagasin);
            alert.setHeaderText("Ajout de l'article "+article.getNomProduit()+" dans le panier du magasin "+nomMagasin);
            alert.setContentText("");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Panier du magasin : "+nomMagasin);
            alert.setHeaderText("L'article "+article.getNomProduit()+" existe déjà dans le panier du magasin "+nomMagasin+". Si vous ne voulez plus de cet article, vous pouvez modifier la quantité dans le panier");
            alert.setContentText("");
            alert.showAndWait();

            existe = false;
        }

    }

    public void afficherPanier(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) btn_retour.getScene().getWindow();
        String titre = stage.getTitle();

        if( panier.size() != 0){
            stage.close();

            Panier_Controleur p1 = new Panier_Controleur(panier, titre, client, liste);
            Stage stage_panier = new Stage();
            AnchorPane root_panier = FXMLLoader.load(getClass().getResource("../vue/panier.fxml"));
            stage_panier.setScene(new Scene(root_panier));
            stage_panier.setTitle("Panier du magasin "+nomMagasin);
            stage_panier.setResizable(false);
            stage_panier.show();

            //vide le panier
            panier.clear();
        }
        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Panier du magasin : "+titre);
            alert.setHeaderText("Votre panier du magasin "+titre+" est vide. Veuillez le remplir d'au moins un article pour pouvoir le consulter");
            alert.setContentText("");
            alert.showAndWait();
        }
    }


    public void choixRetour(ActionEvent actionEvent) throws IOException {
        //vide le panier
        panier.clear();

        //ferme la fenetre du magasin
        Stage stage = (Stage) btn_retour.getScene().getWindow();
        stage.close();

        //lance la fenetre de choix de magasin
        Stage stage_accueil = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("../vue/accueil.fxml"));
        stage_accueil.setScene(new Scene(root));
        stage_accueil.setTitle("Choix magasins");
        stage_accueil.setResizable(false);
        stage_accueil.show();

    }

}
