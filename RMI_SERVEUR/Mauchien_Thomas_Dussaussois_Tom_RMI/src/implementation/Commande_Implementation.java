package implementation;

import connexion.Connexion;
import interfacermi.Banque_Interface;
import interfacermi.Commande_interface;
import interfacermi.OutilsSolvable_Interface;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Commande_Implementation extends UnicastRemoteObject implements Commande_interface, Serializable {


    public Commande_Implementation() throws Exception{
    }
    Connexion c1 = new Connexion();

    @Override
    public boolean verifClientSolvable(String nom, String prenom, int totalCommande) throws Exception {
        boolean solvable = false;
        int port = 8001;
        int idClient;
        String nomBanque;

        OutilsSolvable_Interface obj = (OutilsSolvable_Interface) Naming.lookup("rmi://localhost:" + port + "/commande");

        nomBanque = obj.getBanque(nom, prenom);
        idClient = obj.getIDClient(nom,prenom);

        Banque_Interface objBanque = (Banque_Interface) Naming.lookup("rmi://localhost:" + port + "/"+nomBanque);
        solvable = objBanque.getVerifSolvabiliteCompte(idClient, totalCommande);

        return solvable;
    }

    @Override
    public int getQteProduit(int id) throws Exception {
        int qte = 0;

        try(Statement stmt = c1.creeConnexion().createStatement()){

            String sql = "SELECT QuantiteProduit FROM produit WHERE idProduit=" + id;

            ResultSet res = stmt.executeQuery(sql);

            //extration des données de ResultSet
            while (res.next()) {
                int resultat = res.getInt("QuantiteProduit");
                qte = resultat;
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qte;
        }

    @Override
    public int setCommande(int id) throws Exception {

        int id_commande = 0;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try(Statement stmt = c1.creeConnexion().createStatement()){
            stmt.executeUpdate("INSERT INTO commande (date, idClient) VALUES ('"+dateFormat.format(date)+"','" +id+"')");

            String sql = "SELECT idCommande FROM commande" +
                    " WHERE idClient="+id+
                    " AND date='"+dateFormat.format(date)+"'";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                int resultat = res.getInt("idCommande");
                id_commande = resultat;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return id_commande;
    }

    @Override
    public void setLigneCommande(int idCommande, int idProduit, int qntCommande, int sousTotal) throws Exception {

        try(Statement stmt = c1.creeConnexion().createStatement()){

            stmt.executeUpdate("INSERT INTO ligne_commande (idCommande, idProduit, QuantiteCommande, tarif) VALUES ('"+idCommande+"','" +idProduit+"','"+qntCommande+"','"+sousTotal+"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSoldeCompte(String nom, String prenom, int total) throws Exception {
        int port = 8001;
        int idClient;
        String nomBanque;

        OutilsSolvable_Interface obj = (OutilsSolvable_Interface) Naming.lookup("rmi://localhost:" + port + "/commande");
        idClient = obj.getIDClient(nom,prenom);
        nomBanque = obj.getBanque(nom, prenom);

        Banque_Interface objBanque = (Banque_Interface) Naming.lookup("rmi://localhost:" + port + "/"+nomBanque);
        objBanque.commandePasser(idClient, total);


    }
    @Override
    public void updateQuantiteProduit(int id, int qte) throws Exception {
        int quantite = getQteProduit(id);
        int new_quantite = quantite - qte;

        try(Statement stmt = c1.creeConnexion().createStatement()){
            stmt.executeUpdate("UPDATE produit SET QuantiteProduit = '"+new_quantite+"'WHERE idProduit= "+id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
