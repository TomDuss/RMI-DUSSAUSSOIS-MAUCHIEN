package interfacermi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Commande_interface extends Remote {
    public boolean verifClientSolvable(String nom, String prenom, int totalCommande) throws Exception;
    public int getQteProduit(int id) throws Exception;
    public int setCommande(int id) throws Exception;
    public void setLigneCommande(int idCommande, int idProduit, int qntCommande, int sousTotal) throws Exception;
    public void updateQuantiteProduit(int id, int qte) throws Exception;
    public void updateSoldeCompte(String nom, String prenom, int total) throws Exception;

}
