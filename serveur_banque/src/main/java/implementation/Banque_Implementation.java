package implementation;

import connexion.Connexion;
import interfacermi.Banque_Interface;
import metier.Banque;
import metier.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Banque_Implementation extends UnicastRemoteObject  implements Banque_Interface, Serializable {

    public Banque_Implementation() throws RemoteException {
    }

    private static List<Banque> liste_banque = new ArrayList<Banque>();
    Connexion c1 = new Connexion();
    @Override
    public boolean getVerifSolvabiliteCompte(int id, int total_commande) throws Exception {
        int solde = 0;
        boolean solvable = false;
        try(Statement stmt = c1.creeConnexion().createStatement()){
            String sql = "SELECT Solde FROM compte WHERE idClient=" + id;

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                int resultat = res.getInt("Solde");
                solde = resultat;
            }
            res.close();

            if( solde > total_commande)
                    solvable = true;
            else
                solvable = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solvable;
    }

    @Override
    public int getSoldeCompte(int id) throws Exception {
        int solde = 0;
        try(Statement stmt = c1.creeConnexion().createStatement()){

            String sql = "SELECT Solde FROM compte WHERE idClient=" + id;

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                int resultat = res.getInt("Solde");
                solde = resultat;
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solde;
    }

    @Override
    public void ajoutArgent(int id, Double montant) throws Exception {
        int solde = getSoldeCompte(id);
        try(Statement stmt = c1.creeConnexion().createStatement()){
            Double nouveaux_solde = solde + montant;
            if(montant > 0){
                stmt.executeUpdate("UPDATE compte SET solde ='"+nouveaux_solde+"' WHERE idClient="+id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void commandePasser(int idClient, int total) throws Exception {
        try(Statement stmt = c1.creeConnexion().createStatement()){

            int ancien_solde = getSoldeCompte(idClient);
            int nouveaux_solde;

               nouveaux_solde = ancien_solde - total;

            stmt.executeUpdate("UPDATE compte SET solde ='"+nouveaux_solde+"' WHERE idClient="+idClient);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
