package implementation;

import connexion.Connexion;
import interfacermi.Magasin_Interface;
import metier.Produit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Magasin_Implementation extends UnicastRemoteObject implements Magasin_Interface {

    Connexion connexion = new Connexion();

    public Magasin_Implementation() throws RemoteException {
    }

    @Override
    public List<Produit> getProduits(int id) throws Exception {
        List<Produit> liste_Produits = new ArrayList<Produit>();

        try(Statement stmt = connexion.creeConnexion().createStatement()){
            // executer la requete
            String sql = "SELECT * FROM produit WHERE idMagasin=" +id;
            ResultSet res = stmt.executeQuery(sql);


            //extration des données de ResultSet
            while (res.next()) {

                // Récupérer par nom de colonne
                int stock = res.getInt("QuantiteProduit");
                int id_produit = res.getInt("idProduit");
                String nom_produit = res.getString("NomProduit");
                int id_Magasin = res.getInt("idMagasin");
                int prix = res.getInt("prix");

                // Définir les valeurs
                if(stock != 0){
                    Produit p = new Produit();
                    p.setIdProduit(id_produit);
                    p.setNomProduit(nom_produit);
                    p.setIdMagasin(id_Magasin);
                    p.setPrix(prix);
                    liste_Produits.add(p);
                }
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste_Produits;
    }

    @Override
    public int getIdMagasin(String nomMagasin) throws Exception {
        int id = 0;
        try(Statement stmt = connexion.creeConnexion().createStatement()){
            String sql = "SELECT idMagasin FROM magasin WHERE NomMagasin ='"+nomMagasin+"'";
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                id = res.getInt("idMagasin");
            }
            res.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
