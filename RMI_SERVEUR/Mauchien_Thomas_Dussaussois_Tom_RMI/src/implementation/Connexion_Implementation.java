package implementation;

import connexion.Connexion;
import interfacermi.Connexion_Interface;
import metier.Banque;
import metier.Client;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Connexion_Implementation extends UnicastRemoteObject implements Connexion_Interface, Serializable {

    public Connexion_Implementation() throws Exception {

    }
    Connexion c1 = new Connexion();
    @Override
    public List<Client> getClients(String login, String mdp) throws Exception {

        List<Client> liste_Client = new ArrayList<Client>();

        try(Statement stmt = c1.creeConnexion().createStatement()){

            // executer la requete
            String sql = "SELECT NomClient, PrenomClient, idClient FROM client "+
                    "WHERE Login='"+login+"'"+
                    "AND MotDePasse='"+mdp+"'";

            ResultSet res = stmt.executeQuery(sql);
            //extration des données de ResultSet
            while(res.next()) {

                // Récupérer par nom de colonne
                int id = res.getInt("idClient");
                String nom = res.getString("NomClient");
                String prenom = res.getString("PrenomClient");

                // Définir les valeurs

                Client client = new Client();
                client.setIdClient(id);
                client.setNomClient(nom);
                client.setPrenomClient(prenom);
                client.setLogin(login);
                client.setMotDePasse(mdp);
                liste_Client.add(client);


            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste_Client;
    }


}
