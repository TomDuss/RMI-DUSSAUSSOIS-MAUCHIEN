package controlleur;

import interfacermi.Banque_Interface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import metier.Client;

import java.net.URL;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Banque_Controlleur implements Initializable {

    @FXML
    private Label lbl_solde;
    @FXML
    private Label lbl_identite_compte;
    @FXML
    private Button btn_valider;
    @FXML
    private TextField textfield_virement;

    private static List<Client> liste_Client = new ArrayList<Client>();
    private static String nomBanque;
    private static final int port = 8001;
    private static Banque_Interface banqueClient;
    private static int id;

    public Banque_Controlleur() {
    }

    public Banque_Controlleur(List<Client> liste_Client, String NomBanque) {
        this.liste_Client = liste_Client;
        this.nomBanque = NomBanque;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            banqueClient = (Banque_Interface) Naming.lookup("rmi://localhost:" + port + "/"+nomBanque);

            for(Client cl : liste_Client){
                id = cl.getIdClient();
                lbl_solde.setText("Solde de votre compte : "+banqueClient.getSoldeCompte(cl.getIdClient())+" €");
                lbl_identite_compte.setText("Compte courant de "+cl.getNomClient()+" "+cl.getPrenomClient());
            }


        } catch (Exception e) {
            System.out.println("Client banque compte exception : " +"\n"+ e);
        }

    }

    public void ajoutArgentCompte(ActionEvent actionEvent) throws Exception {

        banqueClient.ajoutArgent(id, Double.parseDouble(textfield_virement.getText().trim()));
        lbl_solde.setText("Solde de votre compte : "+banqueClient.getSoldeCompte(id)+" €");
        textfield_virement.setText("");
    }

    public void fermerClientBanque(ActionEvent actionEvent) {
        System.exit(0);
    }
}
