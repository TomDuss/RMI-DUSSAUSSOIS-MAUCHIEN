package interfacermi;

import java.rmi.Remote;

public interface Banque_Interface extends Remote {
    public boolean getVerifSolvabiliteCompte(int id, int total_commande) throws Exception;
    public int getSoldeCompte(int id) throws Exception;
    public void ajoutArgent(int id, Double montant) throws Exception;
    public void commandePasser(int idClient, int total) throws Exception;

}
