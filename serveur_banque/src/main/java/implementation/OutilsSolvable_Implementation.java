package implementation;

import connexion.Connexion;
import interfacermi.OutilsSolvable_Interface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OutilsSolvable_Implementation extends UnicastRemoteObject  implements OutilsSolvable_Interface, Serializable {

    public OutilsSolvable_Implementation() throws RemoteException {
    }


    Connexion c1 = new Connexion();
    @Override
    public String getBanque(String nom, String prenom) throws Exception {
        String nomBanque = "";
        try(Statement stmt = c1.creeConnexion().createStatement()){
            String sql = "SELECT NomBanque "+
                         "FROM client,banque, compte "+
                         "WHERE NomClient = '"+nom+"'"+
                         "AND PrenomClient = '"+prenom+"'"+
                         "AND client.idClient = compte.idClient "+
                         "AND compte.idBanque = banque.idBanque";

            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                nomBanque = res.getString("NomBanque");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return nomBanque;
    }

    @Override
    public int getIDClient(String nom, String prenom) throws Exception {
        int idClient = 0;
        try(Statement stmt = c1.creeConnexion().createStatement()){

            String sql = "SELECT client.idClient" +
                    " FROM client, compte" +
                    " WHERE NomClient='"+nom+"'"+
                    " AND PrenomClient='"+prenom+"'"+
                    " AND client.idClient = compte.idclient";

            ResultSet res = stmt.executeQuery(sql);

            while(res.next()){
                idClient = res.getInt("idClient");
            }
            res.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return idClient;
    }
}
