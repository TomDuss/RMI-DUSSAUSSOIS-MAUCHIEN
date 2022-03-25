package implementation;

import connexion.Connexion;
import interfacermi.Inscription_Interface;
import metier.Banque;
import metier.Compte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Inscription_Implementation extends UnicastRemoteObject implements Inscription_Interface {

    Connexion c1 = new Connexion();
    private static boolean inscription = false;
    public Inscription_Implementation() throws RemoteException {
    }

    @Override
    public List<Banque> getChoixBanque() throws Exception {
        List<Banque> liste_banque = new ArrayList<Banque>();
        try(Statement stmt = c1.creeConnexion().createStatement()){

            String sql = "SELECT * FROM banque";

            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                int idbanque = res.getInt("idBanque");
                String nombanque = res.getString("NomBanque");

                Banque banque = new Banque();
                banque.setIdBanque(idbanque);
                banque.setNomBanque(nombanque);

                liste_banque.add(banque);

            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste_banque;
    }

    @Override
    public boolean inscriptionClient(String nom, String prenom, String nomBanque, String login, String mdp) throws Exception {
        Compte nouveau_compte = new Compte();
        try(Statement stmt = c1.creeConnexion().createStatement()){
            if(nom.length() != 0 && prenom.length() !=0 && nomBanque.length() != 0 && login.length() != 0 && mdp.length() != 0){

                //verification si le login existe déja
                String sql = "SELECT client.idClient "+
                             "FROM client, compte, banque "+
                             "WHERE Login='"+login+"'"+
                             "AND client.idClient = compte.idClient "+
                             "AND compte.idBanque = banque.idBanque "+
                             "AND NomBanque ='"+nomBanque+"'";

                ResultSet res = stmt.executeQuery(sql);
                boolean empty = true;

                while (res.next()) {
                    empty = false;
                }
                res.close();

                //si le login n'existe pas on cree l'utilisateur
                if(empty){
                    stmt.executeUpdate("INSERT INTO client (NomClient, PrenomClient, Login, MDP) VALUES ('"+nom+"','" +prenom+"','"+login+"','"+mdp+"')");

                    //on réupère l'id du nouveaux clients
                    String sqlclient="SELECT idClient FROM client WHERE Login='"+login+"'";
                    ResultSet resClientNouveaux = stmt.executeQuery(sqlclient);
                    while(resClientNouveaux.next()){
                        int idClient = resClientNouveaux.getInt("idClient");
                        nouveau_compte.setIdClient(idClient);
                    }
                    resClientNouveaux.close();

                    //récupération de l'id de la banque
                    String sql_idBanque = "SELECT idBanque FROM banque WHERE NomBanque='"+nomBanque+"'";
                    ResultSet res_idbanque = stmt.executeQuery(sql_idBanque);
                    while(res_idbanque.next()){
                        int idbanque = res_idbanque.getInt("idBanque");
                        nouveau_compte.setIdBanque(idbanque);
                    }
                    res_idbanque.close();

                    nouveau_compte.setSolde(1);
                    //System.out.println("INSERT INTO compte (idBanque, Solde, idClient) VALUES ('"+nouveau_compte.getIdBanque()+"','" +nouveau_compte.getSolde()+"','"+nouveau_compte.getIdClient()+"')");
                    stmt.executeUpdate("INSERT INTO compte (idBanque, Solde, idClient) VALUES ('"+nouveau_compte.getIdBanque()+"','" +nouveau_compte.getSolde()+"','"+nouveau_compte.getIdClient()+"')");

                    inscription = true;
                }
                else{
                    inscription = false;
                }


            }
        }catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        return inscription;
    }
}
