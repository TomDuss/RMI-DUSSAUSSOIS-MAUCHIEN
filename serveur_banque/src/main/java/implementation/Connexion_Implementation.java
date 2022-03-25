package implementation;

import connexion.Connexion;
import interfacermi.Connexion_Interface;
import metier.Banque;
import metier.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Connexion_Implementation extends UnicastRemoteObject implements Connexion_Interface {

    public Connexion_Implementation() throws RemoteException {
    }

    List<Banque> liste_banque = new ArrayList<Banque>();
    Connexion c1 = new Connexion();
    @Override
    public List<Client> banqueConnexion(String login, String mdp) throws RemoteException {


        List<Client> liste_Client = new ArrayList<Client>();

        try(Statement stmt = c1.creeConnexion().createStatement()){

            // executer la requete
            String sql = "SELECT NomClient, PrenomClient, idClient FROM client "+
                    "WHERE Login='"+login+"'"+
                    "AND MDP='"+mdp+"'";

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

    @Override
    public List<Banque> serveurBanque(int idClient) throws Exception {
        try(Statement stmt = c1.creeConnexion().createStatement()){

            String sql = "SELECT NomBanque, banque.idBanque\n" +
                    "FROM banque, compte, client\n" +
                    "WHERE compte.idBanque = banque.idBanque\n" +
                    "AND client.idClient = compte.idClient\n" +
                    "AND client.idClient ="+idClient;

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                int idbanque = res.getInt("idBanque");
                String nombanque = res.getString("NomBanque");
                Banque banque = new Banque(idbanque, nombanque);
                liste_banque.add(banque);

            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste_banque;
    }

}
