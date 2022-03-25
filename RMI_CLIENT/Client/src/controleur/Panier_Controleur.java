package controleur;

import interfacermi.Commande_interface;
import interfacermi.Connexion_Interface;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import metier.Client;
import metier.Produit;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Panier_Controleur implements Initializable {

    @FXML
    private Button btn_fermer;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Label lbl_nom_magasin;
    @FXML
    private TableView TableView_panier;


    private static String nom;

    private int total = 0;

    private int stock = 0;

    private int sous_total = 0;

    private int id_commande;

    private static final int port = 8000;

    private static boolean stock_valide = false;

    private static boolean client_solvable;

    private static List<Produit> panier= new ArrayList<Produit>();
    private static List<Produit> panier_magasin = new ArrayList<Produit>();
    private static List<Produit> liste= new ArrayList<Produit>();
    private static List<Client> client = new ArrayList<Client>();


    public Panier_Controleur() throws RemoteException {
    }


    public Panier_Controleur(List<Produit> Panier, String Nom, List<Client> Client, List<Produit> Liste) throws RemoteException {
        this.panier = Panier;
        nom = Nom;
        client = Client;
        liste = Liste;

    }

    public void validerPanier(ActionEvent actionEvent) {
        try {
            Commande_interface obj = (Commande_interface) Naming.lookup("rmi://localhost:" + port + "/commande" + nom);

            if(TableView_panier.getItems().size() != 0) {

                for (int i = 0; i < TableView_panier.getItems().size(); i++) {
                    Produit p = (Produit) TableView_panier.getItems().get(i);

                    stock = obj.getQteProduit(p.getIdProduit());

                    //on récupère le pirx total du panier
                    total = total + p.getPrix() * p.getQuantiteProduit();

                    //on vérifie la validité du stock
                    if (p.getQuantiteProduit() > stock) {

                        stock_valide = false;

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Panier du magasin : " + nom);
                        alert.setHeaderText("Nons ne possédons que " + stock + " exemplaires maximum du produit : " + p.getNomProduit());
                        alert.setContentText("");
                        alert.showAndWait();

                    } else {
                        stock_valide = true;
                    }
                }

                    //on vérifie la solvabilité du compte
                    for(Client cl : client){
                        client_solvable = obj.verifClientSolvable(cl.getNomClient(), cl.getPrenomClient(), total);
                    }
                        System.out.println(client_solvable);
                    if (stock_valide && client_solvable) {
                        for(Client cl: client){
                            id_commande = obj.setCommande(cl.getIdClient());
                        }

                        for (int i = 0; i < TableView_panier.getItems().size(); i++) {
                            Produit Produit_Commande = (Produit) TableView_panier.getItems().get(i);
                            sous_total = Produit_Commande.getQuantiteProduit() * Produit_Commande.getPrix();

                            obj.setLigneCommande(id_commande, Produit_Commande.getIdProduit(), Produit_Commande.getQuantiteProduit(), sous_total);
                            obj.updateQuantiteProduit(Produit_Commande.getIdProduit(), Produit_Commande.getQuantiteProduit());

                            sous_total = 0;

                        }
                        for(Client cl :client){
                            obj.updateSoldeCompte(cl.getNomClient(), cl.getPrenomClient(), total);
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Panier du magasin : " + nom);
                        alert.setHeaderText("Votre commande N° "+id_commande+" a été enregistré avec succès");
                        alert.setContentText("");
                        alert.showAndWait();

                        total = 0;
                    }
            }
        }catch(Exception e) {
            System.out.println("Client exception : " + "\n" + e);
        }

    }

    public void fermerPanier(ActionEvent actionEvent) throws IOException {
        for(int i = 0; i < TableView_panier.getItems().size(); i++){
            Produit p = (Produit) TableView_panier.getItems().get(i);
            panier_magasin.add(p);
        }

        Stage stage = (Stage) btn_fermer.getScene().getWindow();
        stage.close();


        Magasin_Controleur m1 = new Magasin_Controleur (liste, client, panier_magasin);
        Stage stage_magasin = new Stage();
        AnchorPane root_panier = FXMLLoader.load(getClass().getResource("../vue/magasin.fxml"));
        stage_magasin.setScene(new Scene(root_panier));
        stage_magasin.setTitle(nom);
        stage_magasin.setResizable(false);
        stage_magasin.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //rend la table editable
        TableView_panier.setEditable(true);

        //on affiche le nom du magasin
        lbl_nom_magasin.setText(nom);

        //column de la table qui vas contenir le nom du produit
        TableColumn<Produit, String> nomProduit = new TableColumn<Produit, String>("Article");
        nomProduit.setMinWidth(350);
        nomProduit.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));

        TableColumn<Produit, Integer> prixProduit = new TableColumn<Produit, Integer>("Prix a l'unite en €");
        prixProduit.setMinWidth(130);
        prixProduit.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Produit, Integer> qteProduitpanier = new TableColumn<Produit, Integer>("Quantite");
        qteProduitpanier.setMinWidth(50);
        qteProduitpanier.setCellValueFactory(new PropertyValueFactory<>("QuantiteProduit"));
        qteProduitpanier.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        //rend le champ quantité modifiable
        qteProduitpanier.setOnEditCommit((TableColumn.CellEditEvent<Produit, Integer> event) -> {

            TablePosition<Produit, Integer> pos = event.getTablePosition();

            Integer newqteProduitPanier = event.getNewValue();

            if(newqteProduitPanier > 0 && newqteProduitPanier < 1000){
                int row = pos.getRow();
                Produit produit = event.getTableView().getItems().get(row);

                produit.setQuantiteProduit(newqteProduitPanier);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Panier du magasin : "+nom);
                alert.setHeaderText("La valeur modifier est soit trop grande ou trop petite");
                alert.setContentText("");
                alert.showAndWait();
            }

        });

        TableColumn<Produit, Produit> SupprimerLigne = new TableColumn<Produit, Produit>("Supprimer");
        SupprimerLigne.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );

        //bouton supprimer pour la ligne
        SupprimerLigne.setCellFactory(param -> new TableCell<Produit, Produit>() {
            private final Button btn_supprimer = new Button("Supprimer");

            @Override
            protected void updateItem(Produit produit, boolean empty) {
                super.updateItem(produit, empty);

                if (produit == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(btn_supprimer);
                btn_supprimer.setOnAction(
                        event -> getTableView().getItems().remove(produit)
                );
            }
        });
        for (Produit p : panier) {
            //insertion de la liste des produits du magasin dans la tableview
            ObservableList<Produit> list = FXCollections.observableArrayList(p);

            TableView_panier.getItems().addAll(list);
        }

        TableView_panier.getColumns().addAll(nomProduit, prixProduit, qteProduitpanier, SupprimerLigne);
    }

}
