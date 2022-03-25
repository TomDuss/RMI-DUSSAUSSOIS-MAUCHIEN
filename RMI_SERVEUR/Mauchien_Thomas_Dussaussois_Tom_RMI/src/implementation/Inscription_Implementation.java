package implementation;

import connexion.Connexion;
import interfacermi.Inscription_Interface;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class Inscription_Implementation extends UnicastRemoteObject implements Inscription_Interface, Serializable {


    public Inscription_Implementation() throws Exception {
    }
    Connexion c1 = new Connexion();


    @Override
    public boolean inscriptionClient(String nom, String prenom, String login, String mdp) throws Exception {
        Boolean inscription = false;
        try(Statement stmt = c1.creeConnexion().createStatement()){
            if(nom.length() != 0 && prenom.length() !=0 && login.length() != 0 && mdp.length() != 0){

                //verification si le login existe déja
                String sql = "SELECT idClient "+
                             "FROM client "+
                             "WHERE Login='"+login+"'";

                ResultSet res = stmt.executeQuery(sql);
                boolean empty = true;

                while (res.next()) {
                    empty = false;
                }
                res.close();

                //si le login n'existe pas on cree l'utilisateur
                if(empty){
                    stmt.executeUpdate("INSERT INTO client (NomClient, PrenomClient, Login, MotDePasse) VALUES ('"+nom+"','" +prenom+"','"+login+"','"+mdp+"')");

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
