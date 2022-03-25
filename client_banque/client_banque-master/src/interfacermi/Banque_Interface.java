package interfacermi;

import java.rmi.Remote;

public interface Banque_Interface extends Remote {
    public int getSoldeCompte(int id) throws Exception;
    public void ajoutArgent(int id, Double montant) throws Exception;

}
